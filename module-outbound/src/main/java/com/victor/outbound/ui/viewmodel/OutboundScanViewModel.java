package com.victor.outbound.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.victor.base.data.Repository.AppRepository;
import com.victor.base.data.entity.OutboundData;
import com.victor.base.data.http.ApiDisposableObserver;
import com.victor.base.event.MessageEvent;
import com.victor.base.event.MessageType;
import com.victor.base.utils.Constants;
import com.victor.base.utils.DateUtil;
import com.victor.outbound.R;
import com.victor.outbound.bean.OutboundScanItemsBean;
import com.victor.workbench.ui.base.BaseTitleViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DefaultObserver;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.goldze.mvvmhabit.utils.Utils;

public class OutboundScanViewModel extends BaseTitleViewModel<AppRepository> {

    public ObservableField<OutboundData> entity = new ObservableField<>();
    public ObservableField<String> checkDataNum = new ObservableField<>("0/0");
    public ObservableField<Boolean> btnVisiable = new ObservableField<>(false);

    public class UIChangeObservable {
        public SingleLiveEvent<Boolean> scanFinishEvent = new SingleLiveEvent<>();
        public SingleLiveEvent<Integer> pageSelectEvent = new SingleLiveEvent<>();
    }

    public UIChangeObservable muc = new UIChangeObservable();

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
                            RxBus.getDefault().post(new MessageEvent<>(MessageType.EVENT_TYPE_OUTBOUND_LIST_REFRESH, null));
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
                            RxBus.getDefault().post(new MessageEvent<>(MessageType.EVENT_TYPE_OUTBOUND_LIST_REFRESH, null));
                            finish();
                        }

                        @Override
                        public void onComplete() {
                            dismissProgress();
                        }
                    });
        }
    });

    public void getNetData(int outId) {
        if (Constants.CONFIG.IS_OFFLINE) {
            Observable.create((ObservableOnSubscribe<OutboundData>) emitter -> {
                        OutboundData data = model._selectOneOutbound(outId);
                        emitter.onNext(data);
                    })
                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                    .compose(RxUtils.schedulersTransformer())
                    .subscribe(new DefaultObserver<OutboundData>() {
                        @Override
                        public void onNext(OutboundData data) {
                            handleOutboundData(data);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        } else {
            model.selectByOutbound(outId)
                    .compose(RxUtils.schedulersTransformer())
                    .compose(RxUtils.exceptionTransformer())
                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            showProgress();
                        }
                    })
                    .subscribe(new ApiDisposableObserver<OutboundData>() {
                        @Override
                        public void onResult(OutboundData data) {
                            handleOutboundData(data);
                        }

                        @Override
                        public void onComplete() {
                            dismissProgress();
                        }
                    });
        }
    }

    private void handleOutboundData(OutboundData data) {
        if (data != null && data.getElecMaterialList().size() > 0) {
            entity.set(data);
            checkDataNum.set("0/" + data.getElecMaterialList().size());
            // 向fragment发送数据，刚进入只有待入库数据
            OutboundScanItemsBean outboundScanAddItemsBean = new OutboundScanItemsBean();
            outboundScanAddItemsBean.setPosition(0);
            outboundScanAddItemsBean.setElecMaterialList(data.getElecMaterialList());
            RxBus.getDefault().post(new MessageEvent<>(MessageType.EVENT_TYPE_OUTBOUND_SCAN_ADD_ITEM, outboundScanAddItemsBean));
        }
    }

    private Set<OutboundData.OutboundElecMaterial> rvSet = new HashSet<>();  //盘点到单子集合

    public void updatePDItemModel(Set<String> sets) {
        btnVisiable.set(true);
        boolean hasData = false;
        for (OutboundData.OutboundElecMaterial bean : entity.get().getElecMaterialList()) {
            if (sets.contains(bean.getRfidCode())) {
                sets.remove(bean.getRfidCode());

                if (!rvSet.contains(bean)) {
                    rvSet.add(bean);  //防止添加的数据重复

                    bean.setBgColor(Utils.getContext().getDrawable(R.color.color_6684FF));
                    bean.setIsOut("1");
                    bean.setIsOutMessage(getApplication().getResources().getString(R.string.workbench_outbound_success_text));

                    // 添加
                    OutboundScanItemsBean outboundScanAddItemsBean = new OutboundScanItemsBean();
                    outboundScanAddItemsBean.setPosition(1);
                    outboundScanAddItemsBean.setElecMaterialList(new ArrayList<>());
                    outboundScanAddItemsBean.getElecMaterialList().add(bean);
                    RxBus.getDefault().post(new MessageEvent<>(MessageType.EVENT_TYPE_OUTBOUND_SCAN_ADD_ITEM, outboundScanAddItemsBean));

                    // 移除
                    OutboundScanItemsBean outboundScanRemoveItemsBean = new OutboundScanItemsBean();
                    outboundScanRemoveItemsBean.setPosition(0);
                    outboundScanRemoveItemsBean.setElecMaterialList(new ArrayList<>());
                    outboundScanRemoveItemsBean.getElecMaterialList().add(bean);
                    RxBus.getDefault().post(new MessageEvent<>(MessageType.EVENT_TYPE_OUTBOUND_SCAN_REMOVE_ITEM, outboundScanRemoveItemsBean));

                    hasData = true;
                }
            } else if (!rvSet.contains(bean)) {
                // 更新列表未扫描到的条目为红色
                OutboundScanItemsBean outboundScanUpdateItemsBean = new OutboundScanItemsBean();
                outboundScanUpdateItemsBean.setPosition(0);
                outboundScanUpdateItemsBean.setElecMaterialList(new ArrayList<>());
                outboundScanUpdateItemsBean.getElecMaterialList().add(bean);
                RxBus.getDefault().post(new MessageEvent<>(MessageType.EVENT_TYPE_OUTBOUND_SCAN_UPDATE_ITEM, outboundScanUpdateItemsBean));
            }
        }

        if (hasData && rvSet.size() > 0) {
            muc.pageSelectEvent.setValue(1);  //选中入库完成页面
        }

        if (entity.get().getElecMaterialList().size() == rvSet.size()) {
            muc.scanFinishEvent.setValue(true);
        }

        checkDataNum.set(rvSet.size() + "/" + entity.get().getElecMaterialList().size());
    }
}
