package com.rainersoft.megaabio.features.cart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rainersoft.megaabio.R;
import com.rainersoft.megaabio.data.model.request.OrderDetail;
import com.rainersoft.megaabio.data.model.request.OrderRequest;
import com.rainersoft.megaabio.data.model.response.NewOrderResponse;
import com.rainersoft.megaabio.data.model.response.Product;
import com.rainersoft.megaabio.data.model.response.User;
import com.rainersoft.megaabio.features.base.BaseActivity;
import com.rainersoft.megaabio.features.common.ErrorView;
import com.rainersoft.megaabio.features.order.OrderActivity;
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

public class CartActivity extends BaseActivity implements CartMvpView, ErrorView.ErrorListener {

    @Inject
    CartPresenter mainPresenter;

    @Inject
    ProductsAdapter productsAdapter;

    @BindView(R.id.rcvProductListView)
    RecyclerView rcvProductListView;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.tvSubtotal)
    TextView tvSubtotal;

    @BindView(R.id.tvTotal)
    TextView tvTotal;

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
        for (String productId : cart.keySet()) {
            productDetails.add(cart.get(productId));
        }
        productsAdapter.setProductsList(productDetails);
        calculateAndUpdateTotalValues();
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
                                    calculateAndUpdateTotalValues();
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
        orderSuccess(null);
    }

    @Override
    public void orderSuccess(NewOrderResponse newOrderResponse) {
        clearCart(null);
        Toast.makeText(this, "Order place successfull", Toast.LENGTH_SHORT).show();
        gotoOrders(null);
    }

    @Override
    public void onReloadData() {

    }

    public void proceedOrder(View view) {
        User user = getLoginUser();
        if (user == null) {
            gotoLogin();
            finish();
            return;
        }

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setCustId(user.getCustId());
        orderRequest.setAmount(getOrderDetailsSubtotal());
        orderRequest.setOrderDetails(getOrderDetails());

        mainPresenter.orderInsert(orderRequest);
    }

    public List<OrderDetail> getOrderDetails() {
        HashMap<String, Product> cart = getCartProducts();
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (String productId : cart.keySet()) {
            Product product = cart.get(productId);
            OrderDetail orderDetail = new OrderDetail();
            int productAmount = Integer.parseInt(product.getAmount());
            int quantity = product.getQuantity();
            orderDetail.setItemAmount(productAmount);
            orderDetail.setProductId(Integer.parseInt(productId));
            orderDetail.setQuantity(quantity);
            orderDetails.add(orderDetail);
            //amount += ( productAmount * quantity );
        }

        return orderDetails;
    }

    public int getOrderDetailsSubtotal() {
        int amount = 0;
        List<OrderDetail> orderDetails = getOrderDetails();
        for (OrderDetail orderDetail : orderDetails) {
            amount += (orderDetail.getItemAmount() * orderDetail.getQuantity());
        }
        return amount;
    }

    public void calculateAndUpdateTotalValues() {
        int cartSubtotal = getOrderDetailsSubtotal();
        int tax = 5;
        int total = ((cartSubtotal * tax) / 100) + cartSubtotal;

        tvSubtotal.setText( String.format("%s /-", cartSubtotal) );
        tvTotal.setText( String.format("%s /-", total) );

    }
}
