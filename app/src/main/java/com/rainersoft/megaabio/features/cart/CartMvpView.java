package com.rainersoft.megaabio.features.cart;

import com.rainersoft.megaabio.data.model.response.NewOrderResponse;
import com.rainersoft.megaabio.features.base.MvpView;

public interface CartMvpView extends MvpView {
    void showProgress(boolean show);
    void showError(Throwable error);
    void orderSuccess(NewOrderResponse newOrderResponse);
}
