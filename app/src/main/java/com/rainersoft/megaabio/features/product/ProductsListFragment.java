package com.rainersoft.megaabio.features.product;


import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.rainersoft.megaabio.R;
import com.rainersoft.megaabio.data.model.response.AllResponse;
import com.rainersoft.megaabio.data.model.response.Product;
import com.rainersoft.megaabio.data.model.response.ProductDetail;
import com.rainersoft.megaabio.data.model.response.company.ResponseDatum;
import com.rainersoft.megaabio.features.base.BaseActivity;
import com.rainersoft.megaabio.features.base.BaseFragment;
import com.rainersoft.megaabio.features.common.ErrorView;
import com.rainersoft.megaabio.injection.component.FragmentComponent;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import timber.log.Timber;

import static com.rainersoft.megaabio.features.product.ProductsActivity.DECREMENT;
import static com.rainersoft.megaabio.features.product.ProductsActivity.INCREMENT;
import static com.rainersoft.megaabio.features.product.ProductsActivity.REMOVE;


public class ProductsListFragment extends BaseFragment implements  ErrorView.ErrorListener  {

    @Inject
    ProductDetailsPresenter mainPresenter;

    @BindView(R.id.rcvProductListView)
    RecyclerView rcvProductListView;


    public ProductsListFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_products_list;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {

    }

    @Override
    protected void attachView() {
        // mainPresenter.attachView(this);
    }

    @Override
    protected void detachPresenter() {
        // mainPresenter.detachView();
    }

    @Override
    public void onReloadData() {

    }


    public void products(List<Product> productDetails, List<ProductDetail> productDetailList, ProductsActivity.IProductClick iProductClick) {
        ProductsAdapter adapter = new ProductsAdapter();
        adapter.setContext( getActivity() );
        adapter.setProductsList(productDetails);
        adapter.setProductDetailList(productDetailList);
        rcvProductListView.setAdapter(adapter);

        adapter.getProductUpdate()
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

                            iProductClick.click(product);
                            adapter.notifyDataSetChanged();
                        },
                        throwable -> {
                            Timber.e(throwable, "Product click failed");
                        });
    }

}
