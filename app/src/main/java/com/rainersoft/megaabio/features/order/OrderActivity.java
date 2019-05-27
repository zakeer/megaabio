package com.rainersoft.megaabio.features.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.rainersoft.megaabio.R;
import com.rainersoft.megaabio.data.model.response.ResponseData;
import com.rainersoft.megaabio.data.model.response.SingleRecordOrder;
import com.rainersoft.megaabio.features.base.BaseActivity;
import com.rainersoft.megaabio.features.common.ErrorView;
import com.rainersoft.megaabio.injection.component.ActivityComponent;

import java.util.List;

import javax.inject.Inject;

public class OrderActivity extends BaseActivity implements OrderMvpView, ErrorView.ErrorListener {

    @Inject
    OrderPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainPresenter.getAllCustomerOrderRecords(getLoginUser().getCustId());
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
    public void orders(List<ResponseData> responseData) {

    }

    @Override
    public void orderDetail(SingleRecordOrder singleRecordOrder) {

    }
}
