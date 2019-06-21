package com.rainersoft.megaabio.features.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rainersoft.megaabio.R;
import com.rainersoft.megaabio.data.model.response.ResponseData;
import com.rainersoft.megaabio.data.model.response.SingleRecordOrder;
import com.rainersoft.megaabio.features.base.BaseActivity;
import com.rainersoft.megaabio.features.common.ErrorView;
import com.rainersoft.megaabio.injection.component.ActivityComponent;

import java.util.ArrayList;
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

    List<ResponseData> orders = new ArrayList<>();

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
        this.orders = orders;
        ordersAdapter.setOrders(orders);
    }

    @Override
    public void orderDetail(SingleRecordOrder singleRecordOrder) {

    }

    public void filterOrders(View view) {
        int id = view.getId();
        if (id == R.id.btnAll) {
            renderOrders(orders);
        } else if (id == R.id.btnBooked) {
            List<ResponseData> bookedOrders = new ArrayList<>();
            for (ResponseData order : orders) {
                if (order.getOrderStatus() != null && order.getOrderStatus().toString().equals("Booked")) {
                    bookedOrders.add(order);
                }
            }
            renderOrders(bookedOrders);
        } else {
            List<ResponseData> dispatchedOrders = new ArrayList<>();
            for (ResponseData order : orders) {
                if (order.getOrderStatus() != null && order.getOrderStatus().toString().equals("Dispatched")) {
                    dispatchedOrders.add(order);
                }
            }
            renderOrders(dispatchedOrders);
        }
        updateFilterButton(id);
    }

    public void renderOrders(List<ResponseData> orders) {
        ordersAdapter.setOrders(orders);
    }

    void updateFilterButton(int type) {

        Button btnAll = findViewById(R.id.btnAll);
        Button btnBooked = findViewById(R.id.btnBooked);
        Button btnDispatched = findViewById(R.id.btnDispatched);

        clearBtnStyle(btnAll);
        clearBtnStyle(btnBooked);
        clearBtnStyle(btnDispatched);

        selectBtnStyle(findViewById(type));

    }

    void clearBtnStyle(Button btn) {
        btn.setBackgroundColor(getResources().getColor(R.color.light_gray));
        btn.setTextColor(getResources().getColor(R.color.darkgray));
    }

    void selectBtnStyle(Button btn) {
        btn.setBackgroundColor(getResources().getColor(R.color.primary));
        btn.setTextColor(getResources().getColor(R.color.white));
    }
}
