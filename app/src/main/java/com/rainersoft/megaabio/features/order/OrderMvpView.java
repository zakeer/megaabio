package com.rainersoft.megaabio.features.order;

import com.rainersoft.megaabio.data.model.response.ResponseData;
import com.rainersoft.megaabio.data.model.response.SingleRecordOrder;
import com.rainersoft.megaabio.features.base.MvpView;

import java.util.List;

public interface OrderMvpView extends MvpView {
    void showProgress(boolean show);
    void showError(Throwable error);
    void orders(List<ResponseData> responseData);

    void orderDetail(SingleRecordOrder singleRecordOrder);
}
