package com.rainersoft.megaabio.features.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rainersoft.megaabio.R;
import com.rainersoft.megaabio.data.model.request.AllResponseRequest;
import com.rainersoft.megaabio.data.model.response.AllResponse;
import com.rainersoft.megaabio.data.model.response.MetaResponseProduct;
import com.rainersoft.megaabio.data.model.response.Product;
import com.rainersoft.megaabio.features.base.BaseActivity;
import com.rainersoft.megaabio.features.common.ErrorView;
import com.rainersoft.megaabio.features.home.HomeActivity;
import com.rainersoft.megaabio.injection.component.ActivityComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.paperdb.Paper;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class ProductsActivity extends BaseActivity implements ProductDetailsMvpView, ErrorView.ErrorListener {

    public final static String PRODUCT_KEY = "PRODUCT_KEY";
    public final static String INCREMENT = "INCREMENT";
    public final static String DECREMENT = "DECREMENT";
    public final static String REMOVE = "REMOVE";

    MetaResponseProduct metaResponseProduct;

    @Inject
    ProductDetailsPresenter mainPresenter;

    @Inject
    ProductsAdapter productsAdapter;

    @BindView(R.id.rcvProductListView)
    RecyclerView rcvProductListView;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getExtras() != null) {
            String metaProductJson = getIntent().getExtras().getString(ProductsActivity.PRODUCT_KEY);
            if (metaProductJson == null) {
                gotoHome();
                return;
            }

            metaResponseProduct = new Gson().fromJson(metaProductJson, MetaResponseProduct.class);
        } else {
            gotoHome();
            return;
        }

        rcvProductListView.setAdapter(productsAdapter);
        productsAdapter.setContext(this);

        AllResponseRequest allResponseRequest = new AllResponseRequest();
        allResponseRequest.setCompanyId(metaResponseProduct.getComapnyId());
        allResponseRequest.setProductMetaId(metaResponseProduct.getProductMetaId());
        mainPresenter.getAllResponse(allResponseRequest);

        tvTitle.setText(metaResponseProduct.getProductName());
        productClickUpdate();

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

    public static void startActivity(Context context, MetaResponseProduct product) {
        Intent intent = new Intent(context, ProductsActivity.class);
        intent.putExtra(ProductsActivity.PRODUCT_KEY, new Gson().toJson(product));
        context.startActivity(intent);
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_products;
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
    public void onReloadData() {

    }

    @Override
    public void showProgress(boolean show) {

    }

    @Override
    public void showError(Throwable error) {

    }

    @Override
    public void allResponse(AllResponse allResponse) {

    }

    @Override
    public void products(List<Product> productDetails) {
        HashMap<String, Product> cart = getCartProducts();
        for (Product product : productDetails) {
            String productId = product.getProductId();
            if (cart.containsKey(productId)) {
                product.setQuantity(cart.get(productId).getQuantity());
            }
        }
        productsAdapter.setProductsList(productDetails);
    }
}
