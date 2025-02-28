package com.victor.inbounddirect.ui.viewmodel;

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
import com.victor.base.data.entity.InboundData;
import com.victor.base.data.entity.LocationBean;
import com.victor.base.data.entity.RfidsBean;
import com.victor.base.data.http.ApiDisposableObserver;
import com.victor.base.event.MessageEvent;
import com.victor.base.event.MessageType;
import com.victor.base.utils.Constants;
import com.victor.base.utils.DateUtil;
import com.victor.inbounddirect.BR;
import com.victor.inbounddirect.R;
import com.victor.inbounddirect.ui.viewmodel.itemviewmodel.InboundScanItemViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class InboundScanViewModel extends BaseTitleViewModel<AppRepository> {

    public ObservableField<InboundData> entity = new ObservableField<>();
    public ObservableField<OperateCategory> category = new ObservableField<>();
    public ObservableField<LocationBean> location = new ObservableField<>();
    public ObservableField<Boolean> btnVisiable = new ObservableField<>(false);

    public ObservableList<InboundScanItemViewModel> inboundScanList = new ObservableArrayList<>();
    public ItemBinding<InboundScanItemViewModel> inboundScanItemBinding = ItemBinding.of(BR.viewModel, R.layout.inbounddirect_item_scan);
    public ObservableInt noDataVisibleObservable = new ObservableInt(View.VISIBLE);

    public void setLocation(LocationBean locationBean) {
        location.set(locationBean);
    }

    public class UIChangeObservable {
        public SingleLiveEvent<String> selectLocationEvent = new SingleLiveEvent<>();
        public SingleLiveEvent<InboundScanItemViewModel> showCustomEvent = new SingleLiveEvent<>();
    }

    public UIChangeObservable uc = new UIChangeObservable();

    public InboundScanViewModel(@NonNull Application application, AppRepository model) {
        super(application, model);
    }

    public BindingCommand selectLocationClickCommand = new BindingCommand(() -> {
        uc.selectLocationEvent.setValue(null);
    });

    public BindingCommand pdFinishClickCommand = new BindingCommand(() -> {
        if (location.get() == null) {
            ToastUtils.showShort(R.string.workbench_inbound_scan_location_hint_text);
            return;
        }
        InboundData inboundData = entity.get();
        inboundData.setFinished(1);
        inboundData.setCheckDate(DateUtil.getNowTime());
        List<InboundData.InboundElecMaterial> elecMaterialList = inboundData.getElecMaterialList();
        if (elecMaterialList != null) {
            for (InboundData.InboundElecMaterial inboundElecMaterial : elecMaterialList) {
                inboundElecMaterial.setWhAreaId(location.get().getWhAreaId());
                inboundElecMaterial.setWhLocationId(location.get().getWhLocationId());
                inboundElecMaterial.setWarehouseId(location.get().getWarehouseId());
            }
        }

        if (Constants.CONFIG.IS_OFFLINE) {
//            Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
//                        model._saveInboundResult(inboundData);
//                        emitter.onNext(true);
//                    })
//                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
//                    .compose(RxUtils.schedulersTransformer())
//                    .subscribe(new DefaultObserver<Boolean>() {
//                        @Override
//                        public void onNext(Boolean b) {
//                            btnVisiable.set(false);
//                            ToastUtils.showShort(R.string.workbench_check_submit_success_text);
//                            RxBus.getDefault().post(new MessageEvent<>(MessageType.EVENT_TYPE_INBOUND_LIST_REFRESH, null));
//                            finish();
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//
//                        }
//
//                        @Override
//                        public void onComplete() {
//
//                        }
//                    });
        } else {
            model.saveInboundResult(inboundData).compose(RxUtils.schedulersTransformer()).compose(RxUtils.exceptionTransformer()).doOnSubscribe(disposable -> {
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
        model.getInMaterialListByRfids(rfidsBean).compose(RxUtils.schedulersTransformer()).compose(RxUtils.exceptionTransformer()).subscribe(new ApiDisposableObserver<List<InboundData.InboundElecMaterial>>() {
            @Override
            public void onResult(List<InboundData.InboundElecMaterial> materialsDatas) {
                if (materialsDatas != null && materialsDatas.size() > 0) {
                    InboundData inboundData = entity.get();
                    inboundData.getElecMaterialList().addAll(materialsDatas);
                    for (InboundData.InboundElecMaterial materialBean : materialsDatas) {
                        inboundScanList.add(new InboundScanItemViewModel(InboundScanViewModel.this, materialBean));
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
        if (inboundScanList.size() > 0) {
            noDataVisibleObservable.set(View.GONE);
            btnVisiable.set(true);
        } else {
            noDataVisibleObservable.set(View.VISIBLE);
        }
    }
}
