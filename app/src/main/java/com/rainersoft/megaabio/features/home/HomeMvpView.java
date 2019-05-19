package com.rainersoft.megaabio.features.home;

import com.rainersoft.megaabio.data.model.response.LoginResponse;
import com.rainersoft.megaabio.data.model.response.MetaResponseProduct;
import com.rainersoft.megaabio.features.base.MvpView;

import java.util.List;

public interface HomeMvpView extends MvpView {

    void showProgress(boolean show);
    void showError(Throwable error);

    void productsList(List<MetaResponseProduct> metaResponseProduct);
}
