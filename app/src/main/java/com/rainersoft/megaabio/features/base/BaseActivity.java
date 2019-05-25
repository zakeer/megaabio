package com.rainersoft.megaabio.features.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.LongSparseArray;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.rainersoft.megaabio.AboutActivity;
import com.rainersoft.megaabio.ContactActivity;
import com.rainersoft.megaabio.MvpStarterApplication;
import com.rainersoft.megaabio.data.model.response.Product;
import com.rainersoft.megaabio.data.model.response.User;
import com.rainersoft.megaabio.features.cart.CartActivity;
import com.rainersoft.megaabio.features.home.HomeActivity;
import com.rainersoft.megaabio.features.login.LoginActivity;
import com.rainersoft.megaabio.features.order.OrderActivity;
import com.rainersoft.megaabio.injection.component.ActivityComponent;
import com.rainersoft.megaabio.injection.component.ConfigPersistentComponent;
import com.rainersoft.megaabio.injection.component.DaggerConfigPersistentComponent;
import com.rainersoft.megaabio.injection.module.ActivityModule;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

import butterknife.ButterKnife;
import io.paperdb.Paper;
import timber.log.Timber;

/**
 * Abstract activity that every other Activity in this application must implement. It provides the
 * following functionality: - Handles creation of Dagger components and makes sure that instances of
 * ConfigPersistentComponent are kept across configuration changes. - Set up and handles a
 * GoogleApiClient instance that can be used to access the Google sign in api. - Handles signing out
 * when an authentication error event is received.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    public final static String CART_PRODUCTS = "CART_PRODUCTS";
    public static final String LOGIN_USER = "LOGIN_USER";

    private static final LongSparseArray<ConfigPersistentComponent> componentsArray =
            new LongSparseArray<>();

    private long activityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        Logger.addLogAdapter(new AndroidLogAdapter());
        Paper.init(this);

        // Create the ActivityComponent and reuses cached ConfigPersistentComponent if this is
        // being called after a configuration change.
        activityId =
                savedInstanceState != null
                        ? savedInstanceState.getLong(KEY_ACTIVITY_ID)
                        : NEXT_ID.getAndIncrement();
        ConfigPersistentComponent configPersistentComponent;
        if (componentsArray.get(activityId) == null) {
            Timber.i("Creating new ConfigPersistentComponent id=%d", activityId);
            configPersistentComponent =
                    DaggerConfigPersistentComponent.builder()
                            .appComponent(MvpStarterApplication.get(this).getComponent())
                            .build();
            componentsArray.put(activityId, configPersistentComponent);
        } else {
            Timber.i("Reusing ConfigPersistentComponent id=%d", activityId);
            configPersistentComponent = componentsArray.get(activityId);
        }
        ActivityComponent activityComponent =
                configPersistentComponent.activityComponent(new ActivityModule(this));
        inject(activityComponent);
        attachView();
    }

    protected abstract int getLayout();

    protected abstract void inject(ActivityComponent activityComponent);

    protected abstract void attachView();

    protected abstract void detachPresenter();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_ACTIVITY_ID, activityId);
    }

    @Override
    protected void onDestroy() {
        if (!isChangingConfigurations()) {
            Timber.i("Clearing ConfigPersistentComponent id=%d", activityId);
            componentsArray.remove(activityId);
        }
        detachPresenter();
        super.onDestroy();
    }

    protected void showSuccess(String message) {
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
    }

    protected void showError(String message) {
        showSuccess(message);
    }

    protected void gotoHome() {
        Intent homeIntent = new Intent(this, HomeActivity.class);
        startActivity(homeIntent);
        finish();
    }

    public void gotoHome(View view) {
        gotoHome();
    }

    protected void gotoLogin() {
        Paper.book().delete(LOGIN_USER);
        LoginActivity.startActivity(this);
        finish();
    }

    protected void gotoCart(View view) {
        CartActivity.startActivity(this);
    }

    protected void gotoOrders(View view) {
        OrderActivity.startActivity(this);
    }

    protected void gotoAbout(View view) {
        AboutActivity.startActivity(this);
    }

    protected void gotoContact(View view) {
        ContactActivity.startActivity(this);
    }

    public void close(View view) {
        finish();
    }

    public HashMap<String, Product> getCartProducts() {
        return Paper.book().read(CART_PRODUCTS, new HashMap<>());
    }

    public void setCartProducts(HashMap<String, Product> products) {
        Paper.book().write(CART_PRODUCTS, products);
    }

    public void updateCartProduct(Product product) {
        HashMap<String, Product> products = getCartProducts();
        String productId = product.getProductId();
        if (product.getQuantity() <= 0) {
            products.remove(productId);
        } else {
            products.put(productId, product);
        }
        setCartProducts(products);
    }

    public void clearCart(View view) {
        Paper.book().write(CART_PRODUCTS, new HashMap<>());
        gotoHome();
    }

    public User getLoginUser() {
        return Paper.book().read(LOGIN_USER);
    }

}
