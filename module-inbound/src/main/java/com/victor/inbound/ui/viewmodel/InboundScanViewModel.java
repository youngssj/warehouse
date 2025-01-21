package com.victor.inbound.ui.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.victor.base.data.Repository.AppRepository;
import com.victor.base.data.entity.TakeStockDetail;
import com.victor.base.data.http.ApiDisposableObserver;
import com.victor.base.utils.Constants;
import com.victor.inbound.R;
import com.victor.workbench.ui.base.BaseTitleViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DefaultObserver;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class InboundScanViewModel extends BaseTitleViewModel<AppRepository> {
    public ObservableField<TakeStockDetail> entity = new ObservableField<>();
    public ObservableField<String> checkDataNum = new ObservableField<>("0/0");
    public ObservableField<Boolean> btnVisiable = new ObservableField<>(false);
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
        TakeStockDetail mainInfo = entity.get();
//
//        List<TakeStockDetail.ElecMaterialListDTO> elecMaterialListDTOS = new ArrayList<>();
//        for (ZcpdVpRvItemViewModel zcpdVpRvItemViewModel : items.get(0).observableList) {
//            TakeStockDetail.ElecMaterialListDTO elecMaterialListDTO = zcpdVpRvItemViewModel.entity.get();
//            elecMaterialListDTOS.add(elecMaterialListDTO);
//        }

//        mainInfo.setElecMaterialList(elecMaterialListDTOS);


        if (Constants.CONFIG.IS_OFFLINE) {
            // 盘盈
            List<TakeStockDetail.ElecMaterialListDTO> pyDataList = new ArrayList<>();
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
            model.saveCheckedResult(mainInfo)
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

    public void getNetData(int checkId) {
        if (Constants.CONFIG.IS_OFFLINE) {
            Observable.create((ObservableOnSubscribe<TakeStockDetail>) emitter -> {
//                TakeStockDetail data = model._selectOneCheck(checkId);
//                emitter.onNext(data);
                    })
                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                    .compose(RxUtils.schedulersTransformer())
                    .subscribe(new DefaultObserver<TakeStockDetail>() {
                        @Override
                        public void onNext(TakeStockDetail data) {
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
            model.selectByCheck(checkId)
                    .compose(RxUtils.schedulersTransformer())
                    .compose(RxUtils.exceptionTransformer())
                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            showProgress();
                        }
                    })
                    .subscribe(new ApiDisposableObserver<TakeStockDetail>() {
                        @Override
                        public void onResult(TakeStockDetail data) {
                            if (data == null || data.getElecMaterialList().size() == 0) {
                                setNoDataVisibleObservable(View.VISIBLE);
                                return;
                            }

                            if (data != null) {
                                entity.set(data);
                                checkDataNum.set("0/" + data.getElecMaterialList().size());
//                                for (int i = 0; i < TAB_NUM; i++) {
//                                    ZcpdVpItemViewModel itemViewModel = new ZcpdVpItemViewModel(ZcpdViewModel.this, i, entity.get().getBatchNumber(), entity.get().getElecMaterialList());
//                                    items.add(itemViewModel);
//                                }
                            }
                        }

                        @Override
                        public void onComplete() {
                            dismissProgress();
                        }
                    });
    }
}
