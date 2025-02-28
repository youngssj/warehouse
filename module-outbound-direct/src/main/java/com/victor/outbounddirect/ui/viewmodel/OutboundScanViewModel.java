package com.victor.outbounddirect.ui.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import com.victor.base.base.BaseTitleViewModel;
import com.victor.base.data.Repository.AppRepository;
import com.victor.base.data.entity.OperateCategory;
import com.victor.base.data.entity.OutboundData;
import com.victor.base.data.entity.RfidsBean;
import com.victor.base.data.http.ApiDisposableObserver;
import com.victor.base.event.MessageEvent;
import com.victor.base.event.MessageType;
import com.victor.base.utils.Constants;
import com.victor.base.utils.DateUtil;
import com.victor.outbounddirect.BR;
import com.victor.outbounddirect.R;
import com.victor.outbounddirect.ui.viewmodel.itemviewmodel.OutboundScanItemViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.observers.DefaultObserver;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class OutboundScanViewModel extends BaseTitleViewModel<AppRepository> {

    public ObservableField<OutboundData> entity = new ObservableField<>();
    public ObservableField<OperateCategory> category = new ObservableField<>();
    public ObservableField<Boolean> btnVisiable = new ObservableField<>(false);

    public ObservableList<OutboundScanItemViewModel> outboundScanList = new ObservableArrayList<>();
    public ItemBinding<OutboundScanItemViewModel> outboundScanItemBinding = ItemBinding.of(BR.viewModel, R.layout.outbounddirect_item_scan);
    public ObservableInt noDataVisibleObservable = new ObservableInt(View.VISIBLE);

    public class UIChangeObservable {
        public SingleLiveEvent<OutboundScanItemViewModel> showCustomEvent = new SingleLiveEvent<>();
    }

    public UIChangeObservable uc = new UIChangeObservable();

    public OutboundScanViewModel(@NonNull Application application, AppRepository model) {
        super(application, model);
    }

    public BindingCommand pdFinishClickCommand = new BindingCommand(() -> {
        OutboundData outboundData = entity.get();
        outboundData.setFinished(1);
        outboundData.setCheckDate(DateUtil.getNowTime());

        if (Constants.CONFIG.IS_OFFLINE) {
            Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
                        model._saveOutboundResult(outboundData);
                        emitter.onNext(true);
                    })
                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                    .compose(RxUtils.schedulersTransformer())
                    .subscribe(new DefaultObserver<Boolean>() {
                        @Override
                        public void onNext(Boolean b) {
                            btnVisiable.set(false);
                            ToastUtils.showShort(R.string.workbench_check_submit_success_text);
                            RxBus.getDefault().post(new MessageEvent<>(MessageType.EVENT_TYPE_INBOUND_LIST_REFRESH, null));
                            finish();
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            model.saveOutboundResult(outboundData)
                    .compose(RxUtils.schedulersTransformer())
                    .compose(RxUtils.exceptionTransformer())
                    .doOnSubscribe(disposable -> {
                        showProgress();
                    }).subscribe(new ApiDisposableObserver() {
                        @Override
                        public void onResult(Object o) {
                            btnVisiable.set(false);
                            ToastUtils.showShort(R.string.workbench_check_submit_success_text);
                            RxBus.getDefault().post(new MessageEvent<>(MessageType.EVENT_TYPE_INBOUND_LIST_REFRESH, null));
                            finish();
                        }

                        @Override
                        public void onComplete() {
                            dismissProgress();
                        }
                    });
        }
    });

    private Set<String> rfidSet = new HashSet<>();  //盘点到单子集合

    public void updatePDItemModel(Set<String> sets) {
        List<String> rfidList = new ArrayList<>();
        for (String rfid : sets) {
            if (!rfidSet.contains(rfid)) {
                rfidList.add(rfid);
            }
        }
        rfidSet.addAll(rfidList);
        RfidsBean rfidsBean = new RfidsBean();
        rfidsBean.setRfidCodes(rfidList);
        model.getOutMaterialListByRfids(rfidsBean)
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<List<OutboundData.OutboundElecMaterial>>() {
                    @Override
                    public void onResult(List<OutboundData.OutboundElecMaterial> materialsDatas) {
                        if (materialsDatas != null && materialsDatas.size() > 0) {
                            OutboundData outboundData = entity.get();
                            outboundData.getElecMaterialList().addAll(materialsDatas);
                            for (OutboundData.OutboundElecMaterial materialBean : materialsDatas) {
                                outboundScanList.add(new OutboundScanItemViewModel(OutboundScanViewModel.this, materialBean));
                            }
                            setNoDataVisibleObservable();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void setNoDataVisibleObservable() {
        if (outboundScanList.size() > 0) {
            noDataVisibleObservable.set(View.GONE);
            btnVisiable.set(true);
        } else {
            noDataVisibleObservable.set(View.VISIBLE);
        }
    }
}
