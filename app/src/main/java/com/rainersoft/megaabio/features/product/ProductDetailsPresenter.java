package com.rainersoft.megaabio.features.product;

import com.rainersoft.megaabio.data.DataManager;
import com.rainersoft.megaabio.data.model.request.AllResponseRequest;
import com.rainersoft.megaabio.data.model.request.GetCompaniesRequest;
import com.rainersoft.megaabio.features.base.BasePresenter;
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
                            if (allResponse != null && allResponse.getProducts() != null) {
                                getView().products(allResponse.getProducts(), allResponse.getProductDetails());
                            }
                        },
                        throwable -> {
                            getView().showProgress(false);
                            getView().showError(throwable);
                        });
    }

    public void getCompanies(GetCompaniesRequest getCompaniesRequest) {
        checkViewAttached();
        getView().showProgress(true);
        dataManager
                .getCompanies(getCompaniesRequest)
                .compose(SchedulerUtils.ioToMain())
                .subscribe(
                        allResponse -> {
                            getView().showProgress(false);
                            if (allResponse != null && allResponse.getResponseData() != null) {
                                getView().getCompanies(allResponse.getResponseData());
                            }
                        },
                        throwable -> {
                            getView().showProgress(false);
                            getView().showError(throwable);
                        });
    }
}
