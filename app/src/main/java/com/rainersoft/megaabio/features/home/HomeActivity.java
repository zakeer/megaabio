package com.rainersoft.megaabio.features.home;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.rainersoft.megaabio.R;
import com.rainersoft.megaabio.data.model.response.MetaResponseProduct;
import com.rainersoft.megaabio.features.base.BaseActivity;
import com.rainersoft.megaabio.features.common.ErrorView;
import com.rainersoft.megaabio.features.product.ProductsActivity;
import com.rainersoft.megaabio.injection.component.ActivityComponent;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class HomeActivity extends BaseActivity implements HomeMvpView, ErrorView.ErrorListener {

    @Inject
    HomePresenter mainPresenter;

    @Inject
    MetaProductsAdapter productsAdapter;

    @BindView(R.id.rcvMetaProductListView)
    RecyclerView rcvMetaProductListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rcvMetaProductListView.setAdapter(productsAdapter);
        productsAdapter.setContext(this);
        productClicked();
        mainPresenter.getMetaDetails();
    }

    private void productClicked() {
        Disposable disposable =
                productsAdapter
                        .getProductClick()
                        .subscribe(
                                product -> {
                                    // startActivity(DetailActivity.getStartIntent(this, pokemon))
                                    showError(product.getProductName());
                                    ProductsActivity.startActivity(HomeActivity.this, product);
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
        return R.layout.activity_home;
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
        Timber.e(error, "There was an error in home page");
    }

    @Override
    public void productsList(List<MetaResponseProduct> metaResponseProduct) {
        productsAdapter.setProductsList(metaResponseProduct);
    }
}


