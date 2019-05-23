package com.rainersoft.megaabio.features.order;

import com.rainersoft.megaabio.features.base.MvpView;

public interface OrderMvpView extends MvpView {
    void showProgress(boolean show);
    void showError(Throwable error);
}
