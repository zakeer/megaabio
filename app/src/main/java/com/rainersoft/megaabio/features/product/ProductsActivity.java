package com.rainersoft.megaabio.features.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rainersoft.megaabio.R;
import com.rainersoft.megaabio.data.model.request.AllResponseRequest;
import com.rainersoft.megaabio.data.model.request.GetCompaniesRequest;
import com.rainersoft.megaabio.data.model.response.AllResponse;
import com.rainersoft.megaabio.data.model.response.MetaResponseProduct;
import com.rainersoft.megaabio.data.model.response.Product;
import com.rainersoft.megaabio.data.model.response.ProductDetail;
import com.rainersoft.megaabio.data.model.response.company.ResponseDatum;
import com.rainersoft.megaabio.features.base.BaseActivity;
import com.rainersoft.megaabio.features.common.ErrorView;
import com.rainersoft.megaabio.injection.component.ActivityComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class ProductsActivity extends BaseActivity implements ProductDetailsMvpView, ErrorView.ErrorListener, TabLayout.OnTabSelectedListener {

    public final static String PRODUCT_KEY = "PRODUCT_KEY";
    public final static String INCREMENT = "INCREMENT";
    public final static String DECREMENT = "DECREMENT";
    public final static String REMOVE = "REMOVE";

    MetaResponseProduct metaResponseProduct;

    @Inject
    ProductDetailsPresenter mainPresenter;

    @Inject
    ProductsAdapter productsAdapter;

    ProductsTabAdapter adapter;
    List<ResponseDatum> companies = new ArrayList<>();
    int currentTabPosition = 0;

    @BindView(R.id.rcvProductListView)
    RecyclerView rcvProductListView;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.tablayout)
    TabLayout tabLayout;

    @BindView(R.id.pager)
    ViewPager pager;

    @BindView(R.id.tvDetails)
    TextView tvDetails;

    @BindView(R.id.tvBenefits)
    TextView tvBenefits;

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


        GetCompaniesRequest getCompaniesRequest = new GetCompaniesRequest();
        getCompaniesRequest.setProductMetaId(metaResponseProduct.getProductMetaId());
        mainPresenter.getCompanies(getCompaniesRequest);

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
    public void products(List<Product> productDetails, List<ProductDetail> productDetailList) {
        HashMap<String, Product> cart = getCartProducts();
        for (Product product : productDetails) {
            String productId = product.getProductId();
            if (cart.containsKey(productId)) {
                product.setQuantity(cart.get(productId).getQuantity());
            }
        }
        adapter.getItem(this.currentTabPosition).products(productDetails, productDetailList, this::updateCartProduct);

        if(productDetailList.size() > 0) {
            tvBenefits.setText(
                    String.format("%s", productDetailList.get(0).getBenifits())
            );

            tvDetails.setText(
                    String.format("%s", productDetailList.get(0).getDetails())
            );

            tvBenefits.setVisibility(View.VISIBLE);
            tvDetails.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getCompanies(List<ResponseDatum> companies) {
        this.companies = companies;
        adapter = new ProductsTabAdapter(getSupportFragmentManager());
        for (ResponseDatum company : companies) {
            adapter.addFragment(new ProductsListFragment(), company.getCompanyName());
        }
        pager.setAdapter(adapter);
        tabLayout.addOnTabSelectedListener(ProductsActivity.this);
        tabLayout.setupWithViewPager(pager);
        fetchCompanyProducts();
    }

    public void getProducts(ResponseDatum company) {
        AllResponseRequest allResponseRequest = new AllResponseRequest();
        allResponseRequest.setCompanyId(company.getCompanyId());
        allResponseRequest.setProductMetaId(company.getProductMetaId());
        mainPresenter.getAllResponse(allResponseRequest);
    }

    public void fetchCompanyProducts() {
        if (this.companies == null || this.companies.size() <= 0) {
            return;
        }

        ResponseDatum company = this.companies.get(this.currentTabPosition);
        List<Product> products = this.adapter.getProducts(this.currentTabPosition);
        List<ProductDetail> productDetails = this.adapter.getProductDetailsList(this.currentTabPosition);
        if(products == null || products.size() <= 0) {
            getProducts(company);
            return;
        }
        this.products(products, productDetails);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Timber.i("::: Products Activity :::: -> onTabSelected = %s | %d", tab.getText(), tab.getPosition());
        this.currentTabPosition = tab.getPosition();
        fetchCompanyProducts();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        Timber.i("::: Products Activity :::: -> onTabUnselected = %s | %d", tab.getText(), tab.getPosition());
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        Timber.i("::: Products Activity :::: -> onTabReselected = %s | %d", tab.getText(), tab.getPosition());
    }

    public interface IProductClick {
        void click(Product product);
    }
}
