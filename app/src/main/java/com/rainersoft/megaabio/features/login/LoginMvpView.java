package com.rainersoft.megaabio.features.login;

import com.rainersoft.megaabio.data.model.response.LoginResponse;
import com.rainersoft.megaabio.features.base.MvpView;

import java.util.List;

public interface LoginMvpView extends MvpView {

    void showProgress(boolean show);
    void showError(Throwable error);

    void loginSuccess(LoginResponse loginResponse);
}
