package com.victor.inbound.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.victor.base.data.Repository.AppRepository;
import com.victor.base.data.entity.InboundData;
import com.victor.base.data.http.ApiDisposableObserver;
import com.victor.base.utils.Constants;
import com.victor.inbound.R;
import com.victor.inbound.bean.InboundListRefreshBean;
import com.victor.inbound.bean.InboundScanAddItemsBean;
import com.victor.inbound.bean.InboundScanRemoveItemsBean;
import com.victor.inbound.bean.InboundScanUpdateItemsBean;
import com.victor.workbench.ui.base.BaseTitleViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

public class InboundScanViewModel extends BaseTitleViewModel<AppRepository> {

    public ObservableField<InboundData> entity = new ObservableField<>();
    public ObservableField<String> checkDataNum = new ObservableField<>("0/0");
    public ObservableField<Boolean> btnVisiable = new ObservableField<>(false);

    public class UIChangeObservable {
        public SingleLiveEvent<Boolean> scanFinishEvent = new SingleLiveEvent<>();
        public SingleLiveEvent<Integer> pageSelectEvent = new SingleLiveEvent<>();
    }

    public UIChangeObservable muc = new UIChangeObservable();

    public InboundScanViewModel(@NonNull Application application, AppRepository model) {
        super(application, model);
    }

    public BindingCommand pdFinishClickCommand = new BindingCommand(() -> {
        InboundData mainInfo = entity.get();

        if (Constants.CONFIG.IS_OFFLINE) {
            // 盘盈
            List<InboundData.ElecMaterialList> pyDataList = new ArrayList<>();
//                for (ZcpdVpRvItemViewModel zcpdVpRvItemViewModel : mPddList) {
//                    if (zcpdVpRvItemViewModel.entity.get().getCheckResult().equals("盘盈")) {
//                        pyDataList.add(zcpdVpRvItemViewModel.entity.get());
//                    }
//                }
            Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
//                    model._saveCheckResult(mainInfo.getCheckId(),
//                            StringUtils.listToStr(pddIds, ","), StringUtils.listToStr(wPddIds, ","), mainInfo.getBatchNumber(), pyDataList);
                        emitter.onNext(true);
                    })
                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                    .compose(RxUtils.schedulersTransformer())
                    .subscribe(new DefaultObserver<Boolean>() {
                        @Override
                        public void onNext(Boolean b) {
                            btnVisiable.set(false);
                            ToastUtils.showShort(R.string.workbench_check_submit_success_text);
                            RxBus.getDefault().post(new InboundListRefreshBean());
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
            model.saveInboundResult(mainInfo)
                    .compose(RxUtils.schedulersTransformer())
                    .compose(RxUtils.exceptionTransformer())
                    .doOnSubscribe(disposable -> {
                        showProgress();
                    }).subscribe(new ApiDisposableObserver() {
                        @Override
                        public void onResult(Object o) {
                            btnVisiable.set(false);
                            ToastUtils.showShort(R.string.workbench_check_submit_success_text);
                            RxBus.getDefault().post(new InboundListRefreshBean());
                            finish();
                        }

                        @Override
                        public void onComplete() {
                            dismissProgress();
                        }
                    });
        }
    });

    public void getNetData(int inId) {
        if (Constants.CONFIG.IS_OFFLINE) {
            Observable.create((ObservableOnSubscribe<InboundData>) emitter -> {
//                InboundData data = model._selectOneCheck(checkId);
//                emitter.onNext(data);
                    })
                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                    .compose(RxUtils.schedulersTransformer())
                    .subscribe(new DefaultObserver<InboundData>() {
                        @Override
                        public void onNext(InboundData data) {
//                            if (data == null || data.getDataList().size() == 0) {
//                                setNoDataVisibleObservable(View.VISIBLE);
//                                return;
//                            }
//                            if (data != null) {
//                                entity.set(data);
//                                checkDataNum.set("0/" + data.getDataList().size());
//                                ZcpdVpItemViewModel itemViewModel = null;
//                                for (int i = 0; i < TAB_NUM; i++) {
//                                    itemViewModel = new ZcpdVpItemViewModel(ZcpdViewModel.this, i, data.getDataList());
//                                    items.add(itemViewModel);
//                                }
//                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {
                        }
                    });

        } else {
            model.selectByInbound(inId)
                    .compose(RxUtils.schedulersTransformer())
                    .compose(RxUtils.exceptionTransformer())
                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            showProgress();
                        }
                    })
                    .subscribe(new ApiDisposableObserver<InboundData>() {
                        @Override
                        public void onResult(InboundData data) {
                            if (data != null && data.getElecMaterialList().size() > 0) {
                                entity.set(data);
                                checkDataNum.set("0/" + data.getElecMaterialList().size());
                                // 向fragment发送数据，刚进入只有待入库数据
                                InboundScanAddItemsBean inboundScanAddItemsBean = new InboundScanAddItemsBean();
                                inboundScanAddItemsBean.setPosition(0);
                                inboundScanAddItemsBean.setElecMaterialList(data.getElecMaterialList());
                                RxBus.getDefault().post(inboundScanAddItemsBean);
                            }
                        }

                        @Override
                        public void onComplete() {
                            dismissProgress();
                        }
                    });
        }
    }

    private Set<InboundData.ElecMaterialList> rvSet = new HashSet<>();  //盘点到单子集合

    public void updatePDItemModel(Set<String> sets) {
        btnVisiable.set(true);
        boolean hasData = false;
        for (InboundData.ElecMaterialList bean : entity.get().getElecMaterialList()) {
            if (sets.contains(bean.getRfidCode())) {
                sets.remove(bean.getRfidCode());

                if (!rvSet.contains(bean)) {
                    rvSet.add(bean);  //防止添加的数据重复

                    bean.setBgColor(Utils.getContext().getDrawable(R.color.color_6684FF));
                    bean.setIsIn(1);
                    bean.setIsInMessage(getApplication().getResources().getString(R.string.workbench_inbound_success_text));

                    // 添加
                    InboundScanAddItemsBean inboundScanAddItemsBean = new InboundScanAddItemsBean();
                    inboundScanAddItemsBean.setPosition(1);
                    inboundScanAddItemsBean.setElecMaterialList(new ArrayList<>());
                    inboundScanAddItemsBean.getElecMaterialList().add(bean);
                    RxBus.getDefault().post(inboundScanAddItemsBean);

                    // 移除
                    InboundScanRemoveItemsBean inboundScanRemoveItemsBean = new InboundScanRemoveItemsBean();
                    inboundScanRemoveItemsBean.setPosition(0);
                    inboundScanRemoveItemsBean.setElecMaterialList(new ArrayList<>());
                    inboundScanRemoveItemsBean.getElecMaterialList().add(bean);
                    RxBus.getDefault().post(inboundScanRemoveItemsBean);

                    hasData = true;
                }
            } else if (!rvSet.contains(bean)) {
                // 更新列表未扫描到的条目为红色
                InboundScanUpdateItemsBean inboundScanUpdateItemsBean = new InboundScanUpdateItemsBean();
                inboundScanUpdateItemsBean.setPosition(0);
                inboundScanUpdateItemsBean.setElecMaterialList(new ArrayList<>());
                inboundScanUpdateItemsBean.getElecMaterialList().add(bean);
                RxBus.getDefault().post(inboundScanUpdateItemsBean);
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
