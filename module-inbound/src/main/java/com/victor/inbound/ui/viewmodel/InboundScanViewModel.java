package com.victor.inbound.ui.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.victor.base.data.Repository.AppRepository;
import com.victor.base.data.entity.InboundDetail;
import com.victor.base.data.http.ApiDisposableObserver;
import com.victor.base.utils.Constants;
import com.victor.inbound.R;
import com.victor.inbound.bean.InboundScanAddItemsBean;
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

    public List<InboundDetail.ElecMaterialList> elecMaterialList;
    public ObservableField<InboundDetail> entity = new ObservableField<>();
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
//        List<String> pddIds = new ArrayList<>();
//        List<String> wPddIds = new ArrayList<>();
////        if (mPddList != null && mPddList.size() > 0) {
////            for (ZcpdVpRvItemViewModel zc : mPddList) {
////                if (mPyListId.indexOf(String.valueOf(zc.entity.get().getMaterialId())) == -1) {  //不在盘盈列表中
////                    pddIds.add(Objects.requireNonNull(zc.entity.get()).getCheckDetailId() + "");
////                }
////            }
////            for (ZcpdVpRvItemViewModel zc : mWpddList) {
////                wPddIds.add(Objects.requireNonNull(zc.entity.get()).getCheckDetailId() + "");
////            }
////
        InboundDetail mainInfo = entity.get();
//
//        List<InboundDetail.ElecMaterialListDTO> elecMaterialListDTOS = new ArrayList<>();
//        for (ZcpdVpRvItemViewModel zcpdVpRvItemViewModel : items.get(0).observableList) {
//            InboundDetail.ElecMaterialListDTO elecMaterialListDTO = zcpdVpRvItemViewModel.entity.get();
//            elecMaterialListDTOS.add(elecMaterialListDTO);
//        }

//        mainInfo.setElecMaterialList(elecMaterialListDTOS);


        if (Constants.CONFIG.IS_OFFLINE) {
            // 盘盈
            List<InboundDetail.ElecMaterialList> pyDataList = new ArrayList<>();
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
                            finish();
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else
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
                            finish();
                        }

                        @Override
                        public void onComplete() {
                            dismissProgress();
                        }
                    });
//        } else
//            ToastUtils.showShort("未盘到资产");
    });

    public void getNetData(int inId) {
        if (Constants.CONFIG.IS_OFFLINE) {
            Observable.create((ObservableOnSubscribe<InboundDetail>) emitter -> {
//                InboundDetail data = model._selectOneCheck(checkId);
//                emitter.onNext(data);
                    })
                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                    .compose(RxUtils.schedulersTransformer())
                    .subscribe(new DefaultObserver<InboundDetail>() {
                        @Override
                        public void onNext(InboundDetail data) {
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

        } else
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
                    .subscribe(new ApiDisposableObserver<InboundDetail>() {
                        @Override
                        public void onResult(InboundDetail data) {
                            if (data == null || data.getElecMaterialList().size() == 0) {
                                setNoDataVisibleObservable(View.VISIBLE);
                                return;
                            }

                            if (data != null) {
                                entity.set(data);
                                checkDataNum.set("0/" + data.getElecMaterialList().size());
                                // 向fragment发送数据，刚进入只有待入库数据
                                InboundScanAddItemsBean inboundScanAddItemsBean = new InboundScanAddItemsBean();
                                inboundScanAddItemsBean.setPosition(0);
                                inboundScanAddItemsBean.setElecMaterialList(data.getElecMaterialList());
                                RxBus.getDefault().post(inboundScanAddItemsBean);
                                elecMaterialList = data.getElecMaterialList();
                            }
                        }

                        @Override
                        public void onComplete() {
                            dismissProgress();
                        }
                    });
    }

    private Set<InboundDetail.ElecMaterialList> rvSet = new HashSet<>();  //盘点到单子集合

    //
    public void updatePDItemModel(Set<String> sets) {
        btnVisiable.set(true);
        for (InboundDetail.ElecMaterialList bean : elecMaterialList) {
            if (sets.contains(bean.getRfidCode())) {
                bean.setBgColor(Utils.getContext().getDrawable(R.color.color_6684FF));
                bean.setCheckResult(1);
                bean.setCheckResultMessage(getApplication().getResources().getString(R.string.workbench_check_success_text));
                sets.remove(bean.getRfidCode());
                rvSet.add(bean);  //防止添加的数据重复
            } else {
                bean.setCheckResult(2);
                bean.setCheckResultMessage(getApplication().getResources().getString(R.string.workbench_check_failure_text));
                bean.setBgColor(Utils.getContext().getDrawable(R.color.color_fc6666));
            }
        }

        if (rvSet.size() > 0) {
            muc.pageSelectEvent.setValue(1);  //选中盘点到 页面
        }

        InboundScanAddItemsBean inboundScanAddItemsBean = new InboundScanAddItemsBean();
        inboundScanAddItemsBean.setPosition(1);
        inboundScanAddItemsBean.setElecMaterialList(new ArrayList<>());

        InboundScanAddItemsBean inboundScanRemoveItemsBean = new InboundScanAddItemsBean();
        inboundScanRemoveItemsBean.setPosition(0);
        inboundScanRemoveItemsBean.setElecMaterialList(new ArrayList<>());

        for (InboundDetail.ElecMaterialList bean : rvSet) {
            if (rvSet.size() <= entity.get().getElecMaterialList().size()) {
                inboundScanAddItemsBean.getElecMaterialList().add(bean);
                inboundScanRemoveItemsBean.getElecMaterialList().add(bean);
            }
        }
        RxBus.getDefault().post(inboundScanAddItemsBean);
        RxBus.getDefault().post(inboundScanRemoveItemsBean);

//        setBgColor();

        checkDataNum.set(inboundScanAddItemsBean.getElecMaterialList().size() + "/" + entity.get().getElecMaterialList().size());

        if (elecMaterialList.size() == rvSet.size()) {
            muc.scanFinishEvent.setValue(true);
        }
    }
}
