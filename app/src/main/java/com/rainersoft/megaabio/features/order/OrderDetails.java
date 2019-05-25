package com.rainersoft.megaabio.features.order;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rainersoft.megaabio.R;
import com.rainersoft.megaabio.data.model.response.ResponseData;
import com.rainersoft.megaabio.features.base.BaseActivity;
import com.rainersoft.megaabio.features.common.ErrorView;
import com.rainersoft.megaabio.injection.component.ActivityComponent;

import java.util.List;

public class OrderDetails extends BaseActivity  implements OrderMvpView, ErrorView.ErrorListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_order_details;
    }

    @Override
    protected void inject(ActivityComponent activityComponent) {

    }

    @Override
    protected void attachView() {

    }

    @Override
    protected void detachPresenter() {

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
}
