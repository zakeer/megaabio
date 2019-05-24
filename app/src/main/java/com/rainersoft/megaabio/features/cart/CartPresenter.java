package com.rainersoft.megaabio.features.cart;

import com.rainersoft.megaabio.data.DataManager;
import com.rainersoft.megaabio.data.model.request.AllResponseRequest;
import com.rainersoft.megaabio.data.model.request.OrderRequest;
import com.rainersoft.megaabio.features.base.BasePresenter;
import com.rainersoft.megaabio.injection.ConfigPersistent;
import com.rainersoft.megaabio.util.rx.scheduler.SchedulerUtils;

import javax.inject.Inject;

@ConfigPersistent
public class CartPresenter extends BasePresenter<CartMvpView> {

    private final DataManager dataManager;

    @Inject
    public CartPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(CartMvpView mvpView) {
        super.attachView(mvpView);
    }

    public void orderInsert(OrderRequest orderRequest) {
        checkViewAttached();
        getView().showProgress(true);
        dataManager
                .orderInsert(orderRequest)
                .compose(SchedulerUtils.ioToMain())
                .subscribe(
                        newOrderResponse -> {
                            getView().showProgress(false);
                            getView().orderSuccess(newOrderResponse);

                        },
                        throwable -> {
                            getView().showProgress(false);
                            getView().showError(throwable);
                        });
    }
}
