package com.victor.workbench.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.victor.base.base.BaseTitleViewModel;
import com.victor.base.data.Repository.AppRepository;
import com.victor.base.data.entity.LocationBean;
import com.victor.base.data.http.ApiDisposableObserver;

import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;

public class ScanLocationViewModel extends BaseTitleViewModel<AppRepository> {

    public class UIChangeObservable {
        public SingleLiveEvent<LocationBean> scanLocationEvent = new SingleLiveEvent<>();
    }

    public UIChangeObservable uc = new UIChangeObservable();

    public ScanLocationViewModel(@NonNull Application application, AppRepository model) {
        super(application, model);
    }

    public void handleBarCode(String rfid) {
        model.getLocationByRfids(rfid)
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<LocationBean>() {
                    @Override
                    public void onResult(LocationBean locationBean) {
                        uc.scanLocationEvent.setValue(locationBean);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
