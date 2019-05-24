package com.rainersoft.megaabio.features.order;

import com.rainersoft.megaabio.data.DataManager;
import com.rainersoft.megaabio.data.model.request.AllResponseRequest;
import com.rainersoft.megaabio.data.model.request.OrderRequest;
import com.rainersoft.megaabio.features.base.BasePresenter;
import com.rainersoft.megaabio.features.cart.CartMvpView;
import com.rainersoft.megaabio.injection.ConfigPersistent;
import com.rainersoft.megaabio.util.rx.scheduler.SchedulerUtils;

import javax.inject.Inject;

@ConfigPersistent
public class OrderPresenter extends BasePresenter<OrderMvpView> {

    private final DataManager dataManager;

    @Inject
    public OrderPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(OrderMvpView mvpView) {
        super.attachView(mvpView);
    }


}
