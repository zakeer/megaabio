package com.rainersoft.megaabio.features.product;

import com.rainersoft.megaabio.data.DataManager;
import com.rainersoft.megaabio.data.model.request.AllResponseRequest;
import com.rainersoft.megaabio.features.base.BasePresenter;
import com.rainersoft.megaabio.features.home.HomeMvpView;
import com.rainersoft.megaabio.injection.ConfigPersistent;
import com.rainersoft.megaabio.util.rx.scheduler.SchedulerUtils;

import javax.inject.Inject;

@ConfigPersistent
public class ProductDetailsPresenter extends BasePresenter<ProductDetailsMvpView> {

    private final DataManager dataManager;

    @Inject
    public ProductDetailsPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(ProductDetailsMvpView mvpView) {
        super.attachView(mvpView);
    }

    public void getAllResponse(AllResponseRequest allResponseRequest) {
        checkViewAttached();
        getView().showProgress(true);
        dataManager
                .getAllResponse(allResponseRequest)
                .compose(SchedulerUtils.ioToMain())
                .subscribe(
                        allResponse -> {
                            getView().showProgress(false);
                            getView().allResponse(allResponse);
                            if(allResponse != null && allResponse.getProducts() != null) {
                                getView().products(allResponse.getProducts());
                            }
                        },
                        throwable -> {
                            getView().showProgress(false);
                            getView().showError(throwable);
                        });
    }
}
