package com.rainersoft.megaabio.features.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.rainersoft.megaabio.R;
import com.rainersoft.megaabio.data.model.request.OrderDetail;
import com.rainersoft.megaabio.data.model.response.Product;
import com.rainersoft.megaabio.data.model.response.ResponseData;
import com.rainersoft.megaabio.data.model.response.SingleRecordOrder;
import com.rainersoft.megaabio.features.base.BaseActivity;
import com.rainersoft.megaabio.features.common.ErrorView;
import com.rainersoft.megaabio.features.home.HomeActivity;
import com.rainersoft.megaabio.features.product.ProductsActivity;
import com.rainersoft.megaabio.features.product.ProductsAdapter;
import com.rainersoft.megaabio.injection.component.ActivityComponent;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class OrderActivity extends BaseActivity implements OrderMvpView, ErrorView.ErrorListener {

    @Inject
    OrderPresenter mainPresenter;

    @Inject
    OrdersAdapter ordersAdapter;

    @BindView(R.id.rcvOrdersListView)
    RecyclerView rcvOrdersListView;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainPresenter.getAllCustomerOrderRecords(getLoginUser().getCustId());
        rcvOrdersListView.setAdapter(ordersAdapter);
        ordersAdapter.setContext(this);
        orderClicked();
    }

    private void orderClicked() {
        Disposable disposable =
                ordersAdapter
                        .getOrderClick()
                        .subscribe(
                                order -> {
                                    OrderDetails.startActivity(OrderActivity.this, order);
                                },
                                throwable -> {
                                    Timber.e(throwable, "Order click failed");
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
        return R.layout.activity_order;
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, OrderActivity.class);
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
    public void onReloadData() {

    }

    @Override
    public void showProgress(boolean show) {

    }

    @Override
    public void showError(Throwable error) {

    }

    @Override
    public void orders(List<ResponseData> orders) {
        ordersAdapter.setOrders(orders);
    }

    @Override
    public void orderDetail(SingleRecordOrder singleRecordOrder) {

    }
}
