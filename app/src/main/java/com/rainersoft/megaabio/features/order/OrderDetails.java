package com.rainersoft.megaabio.features.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.rainersoft.megaabio.R;
import com.rainersoft.megaabio.data.model.response.ResponseData;
import com.rainersoft.megaabio.data.model.response.SingleRecordOrder;
import com.rainersoft.megaabio.features.base.BaseActivity;
import com.rainersoft.megaabio.features.common.ErrorView;
import com.rainersoft.megaabio.injection.component.ActivityComponent;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class OrderDetails extends BaseActivity implements OrderMvpView, ErrorView.ErrorListener {

    private final static String ORDER_ID = "ORDER_ID";

    @Inject
    OrderPresenter mainPresenter;

    @BindView(R.id.rcvOrdersListView)
    RecyclerView rcvOrdersListView;

    @Inject
    SingleOrderProductsAdapter singleOrderProductsAdapter;

    @BindView(R.id.tvCustomerName)
    TextView tvCustomerName;

    @BindView(R.id.tvCustomerAddress)
    TextView tvCustomerAddress;

    @BindView(R.id.tvCustomerEmail)
    TextView tvCustomerEmail;

    @BindView(R.id.tvTotal)
    TextView tvTotal;

    @BindView(R.id.tvSubtotal)
    TextView tvSubtotal;

    @BindView(R.id.tvTax)
    TextView tvTax;

    @BindView(R.id.tvInvoiceNo)
    TextView tvInvoiceNo;

    @BindView(R.id.tvDateOfInvoice)
    TextView tvDateOfInvoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getExtras() != null) {
            String orderId = getIntent().getExtras().getString(OrderDetails.ORDER_ID);
            if (orderId == null) {
                gotoHome();
                return;
            }

            mainPresenter.getSingleRecordOrder(orderId);
        } else {
            gotoHome();
            return;
        }

        rcvOrdersListView.setAdapter(singleOrderProductsAdapter);
        singleOrderProductsAdapter.setContext(this);
    }

    public static void startActivity(Context context, ResponseData order) {
        Intent intent = new Intent(context, OrderDetails.class);
        intent.putExtra(OrderDetails.ORDER_ID, order.getOrderId());
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_order_details;
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
    public void orders(List<ResponseData> responseData) {

    }

    @Override
    public void orderDetail(SingleRecordOrder singleRecordOrder) {
        if (singleRecordOrder == null || singleRecordOrder.getStatus() == null) {
            return;
        }

        tvCustomerAddress.setText(singleRecordOrder.getCustomerDetails().getAddress());
        tvCustomerEmail.setText(singleRecordOrder.getCustomerDetails().getCustEmail());
        tvCustomerName.setText(singleRecordOrder.getCustomerDetails().getCustName());

        tvInvoiceNo.setText(String.format("Invoice # %s", singleRecordOrder.getOrderDetails().getOrderId()));
        tvDateOfInvoice.setText(singleRecordOrder.getOrderDetails().getOrderDate());

        tvTotal.setText(String.format("Rs. %s /-", singleRecordOrder.getOrderDetails().getAmount()));
        float totalAmount = Float.parseFloat(singleRecordOrder.getOrderDetails().getAmount());
        float tax = (totalAmount / 100) * 5;
        float subTotal = totalAmount - tax;
        tvSubtotal.setText("Rs." + subTotal + "/-");
        tvTax.setText("Rs." + tax + "/-");


        singleOrderProductsAdapter.setOrders(singleRecordOrder.getOrderItemDetails());
    }
}
