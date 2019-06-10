package com.rainersoft.megaabio.features.order;

import com.rainersoft.megaabio.data.DataManager;
import com.rainersoft.megaabio.features.base.BasePresenter;
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

    public void getAllCustomerOrderRecords(String customerId) {
        checkViewAttached();
        getView().showProgress(true);
        dataManager
                .getAllCustomerOrderRecords(customerId)
                .compose(SchedulerUtils.ioToMain())
                .subscribe(
                        allCustomerRecordsResponse -> {
                            getView().showProgress(false);

                            if (allCustomerRecordsResponse != null && allCustomerRecordsResponse.getResponseData() != null) {
                                getView().orders(allCustomerRecordsResponse.getResponseData());
                            }
                        },
                        throwable -> {
                            getView().showProgress(false);
                            getView().showError(throwable);
                        });
    }

    public void getSingleRecordOrder(String orderId) {
        checkViewAttached();
        getView().showProgress(true);
        dataManager
                .getSingleRecordOrder(orderId)
                .compose(SchedulerUtils.ioToMain())
                .subscribe(
                        singleRecordOrder -> {
                            getView().showProgress(false);
                            getView().orderDetail(singleRecordOrder);
                        },
                        throwable -> {
                            getView().showProgress(false);
                            getView().showError(throwable);
                        });
    }


}
