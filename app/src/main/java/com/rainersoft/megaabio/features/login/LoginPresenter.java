package com.rainersoft.megaabio.features.login;

import com.rainersoft.megaabio.data.DataManager;
import com.rainersoft.megaabio.data.model.request.LoginRequest;
import com.rainersoft.megaabio.features.base.BasePresenter;
import com.rainersoft.megaabio.injection.ConfigPersistent;
import com.rainersoft.megaabio.util.rx.scheduler.SchedulerUtils;

import javax.inject.Inject;

@ConfigPersistent
public class LoginPresenter extends BasePresenter<LoginMvpView> {

    private final DataManager dataManager;

    @Inject
    public LoginPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(LoginMvpView mvpView) {
        super.attachView(mvpView);
    }

    public void loginUser(LoginRequest loginRequest) {
        checkViewAttached();
        getView().showProgress(true);
        dataManager
                .loginUser(loginRequest)
                .compose(SchedulerUtils.ioToMain())
                .subscribe(
                        loginResponse -> {
                            getView().showProgress(false);
                             getView().loginSuccess(loginResponse);
                        },
                        throwable -> {
                            getView().showProgress(false);
                            getView().showError(throwable);
                        });
    }
}
