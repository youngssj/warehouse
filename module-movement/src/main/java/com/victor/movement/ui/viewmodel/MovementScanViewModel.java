package com.victor.movement.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.victor.base.data.Repository.AppRepository;
import com.victor.base.data.entity.InboundData;
import com.victor.base.data.entity.MovementData;
import com.victor.base.data.http.ApiDisposableObserver;
import com.victor.base.utils.Constants;
import com.victor.base.utils.DateUtil;
import com.victor.movement.R;
import com.victor.movement.bean.MovementListRefreshBean;
import com.victor.movement.bean.MovementScanAddItemsBean;
import com.victor.movement.bean.MovementScanRemoveItemsBean;
import com.victor.movement.bean.MovementScanUpdateItemsBean;
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

public class MovementScanViewModel extends BaseTitleViewModel<AppRepository> {

    public ObservableField<MovementData> entity = new ObservableField<>();
    public ObservableField<String> checkDataNum = new ObservableField<>("0/0");
    public ObservableField<Boolean> btnVisiable = new ObservableField<>(false);

    public class UIChangeObservable {
        public SingleLiveEvent<Boolean> scanFinishEvent = new SingleLiveEvent<>();
        public SingleLiveEvent<Integer> pageSelectEvent = new SingleLiveEvent<>();
    }

    public UIChangeObservable muc = new UIChangeObservable();

    public MovementScanViewModel(@NonNull Application application, AppRepository model) {
        super(application, model);
    }

    public BindingCommand pdFinishClickCommand = new BindingCommand(() -> {
        MovementData movementData = entity.get();
        movementData.setFinished(1);
        movementData.setCheckDate(DateUtil.getNowTime());

        if (Constants.CONFIG.IS_OFFLINE) {
            Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
                        model._saveMovementResult(movementData);
                        emitter.onNext(true);
                    })
                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                    .compose(RxUtils.schedulersTransformer())
                    .subscribe(new DefaultObserver<Boolean>() {
                        @Override
                        public void onNext(Boolean b) {
                            btnVisiable.set(false);
                            ToastUtils.showShort(R.string.workbench_check_submit_success_text);
                            RxBus.getDefault().post(new MovementListRefreshBean());
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
            model.saveMovementResult(movementData)
                    .compose(RxUtils.schedulersTransformer())
                    .compose(RxUtils.exceptionTransformer())
                    .doOnSubscribe(disposable -> {
                        showProgress();
                    }).subscribe(new ApiDisposableObserver() {
                        @Override
                        public void onResult(Object o) {
                            btnVisiable.set(false);
                            ToastUtils.showShort(R.string.workbench_check_submit_success_text);
                            RxBus.getDefault().post(new MovementListRefreshBean());
                            finish();
                        }

                        @Override
                        public void onComplete() {
                            dismissProgress();
                        }
                    });
        }
    });

    public void getNetData(int movementId) {
        if (Constants.CONFIG.IS_OFFLINE) {
            Observable.create((ObservableOnSubscribe<MovementData>) emitter -> {
                        MovementData data = model._selectOneMovement(movementId);
                        emitter.onNext(data);
                    })
                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                    .compose(RxUtils.schedulersTransformer())
                    .subscribe(new DefaultObserver<MovementData>() {
                        @Override
                        public void onNext(MovementData data) {
                            handleMovementData(data);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        } else {
            model.selectByMovement(movementId)
                    .compose(RxUtils.schedulersTransformer())
                    .compose(RxUtils.exceptionTransformer())
                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            showProgress();
                        }
                    })
                    .subscribe(new ApiDisposableObserver<MovementData>() {
                        @Override
                        public void onResult(MovementData data) {
                            if (data != null && data.getElecMaterialList().size() > 0) {
                                entity.set(data);
                                checkDataNum.set("0/" + data.getElecMaterialList().size());
                                // 向fragment发送数据，刚进入只有待入库数据
                                MovementScanAddItemsBean movementScanAddItemsBean = new MovementScanAddItemsBean();
                                movementScanAddItemsBean.setPosition(0);
                                movementScanAddItemsBean.setElecMaterialList(data.getElecMaterialList());
                                RxBus.getDefault().post(movementScanAddItemsBean);
                            }
                        }

                        @Override
                        public void onComplete() {
                            dismissProgress();
                        }
                    });
        }
    }

    private void handleMovementData(MovementData data) {
        if (data != null && data.getElecMaterialList().size() > 0) {
            entity.set(data);
            checkDataNum.set("0/" + data.getElecMaterialList().size());
            // 向fragment发送数据，刚进入只有待入库数据
            MovementScanAddItemsBean movementScanAddItemsBean = new MovementScanAddItemsBean();
            movementScanAddItemsBean.setPosition(0);
            movementScanAddItemsBean.setElecMaterialList(data.getElecMaterialList());
            RxBus.getDefault().post(movementScanAddItemsBean);
        }
    }

    private Set<MovementData.MovementElecMaterial> rvSet = new HashSet<>();  //盘点到单子集合

    public void updatePDItemModel(Set<String> sets) {
        btnVisiable.set(true);
        boolean hasData = false;
        for (MovementData.MovementElecMaterial bean : entity.get().getElecMaterialList()) {
            if (sets.contains(bean.getRfidCode())) {
                sets.remove(bean.getRfidCode());

                if (!rvSet.contains(bean)) {
                    rvSet.add(bean);  //防止添加的数据重复

                    bean.setBgColor(Utils.getContext().getDrawable(R.color.color_6684FF));
                    bean.setIsMove("1");
                    bean.setIsMoveMessage(getApplication().getResources().getString(R.string.workbench_movement_success_text));

                    // 添加
                    MovementScanAddItemsBean movementScanAddItemsBean = new MovementScanAddItemsBean();
                    movementScanAddItemsBean.setPosition(1);
                    movementScanAddItemsBean.setElecMaterialList(new ArrayList<>());
                    movementScanAddItemsBean.getElecMaterialList().add(bean);
                    RxBus.getDefault().post(movementScanAddItemsBean);

                    // 移除
                    MovementScanRemoveItemsBean movementScanRemoveItemsBean = new MovementScanRemoveItemsBean();
                    movementScanRemoveItemsBean.setPosition(0);
                    movementScanRemoveItemsBean.setElecMaterialList(new ArrayList<>());
                    movementScanRemoveItemsBean.getElecMaterialList().add(bean);
                    RxBus.getDefault().post(movementScanRemoveItemsBean);

                    hasData = true;
                }
            } else if (!rvSet.contains(bean)) {
                // 更新列表未扫描到的条目为红色
                MovementScanUpdateItemsBean movementScanUpdateItemsBean = new MovementScanUpdateItemsBean();
                movementScanUpdateItemsBean.setPosition(0);
                movementScanUpdateItemsBean.setElecMaterialList(new ArrayList<>());
                movementScanUpdateItemsBean.getElecMaterialList().add(bean);
                RxBus.getDefault().post(movementScanUpdateItemsBean);
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
