package com.rainersoft.megaabio.features.home;

import com.rainersoft.megaabio.data.DataManager;
import com.rainersoft.megaabio.features.base.BasePresenter;
import com.rainersoft.megaabio.injection.ConfigPersistent;
import com.rainersoft.megaabio.util.rx.scheduler.SchedulerUtils;

import javax.inject.Inject;

@ConfigPersistent
public class HomePresenter extends BasePresenter<HomeMvpView> {

    private final DataManager dataManager;

    @Inject
    public HomePresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(HomeMvpView mvpView) {
        super.attachView(mvpView);
    }

    public void getMetaDetails() {
        checkViewAttached();
        getView().showProgress(true);
        dataManager
                .getMetaDetails()
                .compose(SchedulerUtils.ioToMain())
                .subscribe(
                        productMetaDetailListResponse -> {
                            getView().showProgress(false);
                            getView().productsList(productMetaDetailListResponse.getMetaResponseProduct());
                        },
                        throwable -> {
                            getView().showProgress(false);
                            getView().showError(throwable);
                        });
    }
}
