package com.rainersoft.megaabio.features.cart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.rainersoft.megaabio.R;
import com.rainersoft.megaabio.data.model.response.Product;
import com.rainersoft.megaabio.features.base.BaseActivity;
import com.rainersoft.megaabio.features.common.ErrorView;
import com.rainersoft.megaabio.features.product.ProductsAdapter;
import com.rainersoft.megaabio.injection.component.ActivityComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

import static com.rainersoft.megaabio.features.product.ProductsActivity.DECREMENT;
import static com.rainersoft.megaabio.features.product.ProductsActivity.INCREMENT;
import static com.rainersoft.megaabio.features.product.ProductsActivity.REMOVE;

public class CartActivity extends BaseActivity implements  CartMvpView, ErrorView.ErrorListener {

    @Inject
    CartPresenter mainPresenter;

    @Inject
    ProductsAdapter productsAdapter;

    @BindView(R.id.rcvProductListView)
    RecyclerView rcvProductListView;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rcvProductListView.setAdapter(productsAdapter);
        productsAdapter.setContext(this);

        showProducts();
        productClickUpdate();
    }

    private void showProducts() {
        HashMap<String, Product> cart = getCartProducts();
        List<Product> productDetails = new ArrayList<>();
        for(String productId: cart.keySet()) {
            productDetails.add(cart.get(productId));
        }
        productsAdapter.setProductsList(productDetails);
    }

    private void productClickUpdate() {
        Disposable disposable =
                productsAdapter
                        .getProductUpdate()
                        .subscribe(
                                productClickUpdate -> {
                                    // startActivity(DetailActivity.getStartIntent(this, pokemon))
                                    String type = productClickUpdate.type;
                                    Product product = productClickUpdate.product;
                                    if (type.equalsIgnoreCase(INCREMENT)) {
                                        product.increamentQuanity();
                                    } else if (type.equalsIgnoreCase(DECREMENT)) {
                                        product.decreamentQuanity();
                                    } else if (type.equalsIgnoreCase(REMOVE)) {
                                        product.setQuantity(0);
                                    }

                                    updateCartProduct(product);
                                    productsAdapter.notifyDataSetChanged();
                                },
                                throwable -> {
                                    Timber.e(throwable, "Product click failed");
                                    Toast.makeText(
                                            this,
                                            R.string.error_something_bad_happened,
                                            Toast.LENGTH_LONG)
                                            .show();
                                });
        mainPresenter.addDisposable(disposable);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_cart;
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CartActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void attachView() {
        mainPresenter.attachView(this);
    }

    @Override
    protected void detachPresenter() {
        mainPresenter.detachView();
    }

    @Override
    public void showProgress(boolean show) {

    }

    @Override
    public void showError(Throwable error) {

    }

    @Override
    public void onReloadData() {

    }
}
