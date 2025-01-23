package com.victor.movement.ui.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import com.victor.base.data.entity.MovementDetail;
import com.victor.movement.BR;
import com.victor.movement.R;
import com.victor.movement.bean.MovementScanAddItemsBean;
import com.victor.movement.bean.MovementScanRemoveItemsBean;
import com.victor.movement.bean.MovementScanUpdateItemsBean;
import com.victor.movement.ui.viewmodel.itemviewmodel.MovementScanItemViewModel;

import java.util.Objects;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.bus.RxSubscriptions;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.Utils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class MovementScanListViewModel extends BaseViewModel {

    private int position;
    public ObservableList<MovementScanItemViewModel> movementScanList = new ObservableArrayList<>();
    public ItemBinding<MovementScanItemViewModel> movementScanItemBinding = ItemBinding.of(BR.viewModel, R.layout.movement_item_scan);
    public ObservableInt noDataVisibleObservable = new ObservableInt(View.VISIBLE);

    public class UIChangeObservable {
        public SingleLiveEvent<MovementScanItemViewModel> showCustomEvent = new SingleLiveEvent<>();
    }

    public UIChangeObservable uc = new UIChangeObservable();

    public MovementScanListViewModel(@NonNull Application application) {
        super(application);
    }

    //订阅者
    private Disposable mSubscriptionAdd, mSubscriptionRemove, mSubscriptionUpdate;

    //注册RxBus
    @Override
    public void registerRxBus() {
        super.registerRxBus();
        mSubscriptionAdd = RxBus.getDefault().toObservable(MovementScanAddItemsBean.class)
                .subscribe(new Consumer<MovementScanAddItemsBean>() {
                    @Override
                    public void accept(MovementScanAddItemsBean movementScanItemsBean) throws Exception {
                        if (movementScanItemsBean != null && movementScanItemsBean.getPosition() == position) {
                            if (movementScanItemsBean.getElecMaterialList() != null && movementScanItemsBean.getElecMaterialList().size() > 0) {
                                for (MovementDetail.ElecMaterialList bean : movementScanItemsBean.getElecMaterialList()) {
                                    movementScanList.add(new MovementScanItemViewModel(MovementScanListViewModel.this, bean));
                                }
                            }
                        }
                        if (movementScanList.size() > 0) {
                            noDataVisibleObservable.set(View.GONE);
                        } else {
                            noDataVisibleObservable.set(View.VISIBLE);
                        }
                    }
                });
        //将订阅者加入管理站
        mSubscriptionRemove = RxBus.getDefault().toObservable(MovementScanRemoveItemsBean.class)
                .subscribe(new Consumer<MovementScanRemoveItemsBean>() {
                    @Override
                    public void accept(MovementScanRemoveItemsBean movementScanItemsBean) throws Exception {
                        if (movementScanItemsBean != null && movementScanItemsBean.getPosition() == position) {
                            if (movementScanItemsBean.getElecMaterialList() != null && movementScanItemsBean.getElecMaterialList().size() > 0) {
                                for (MovementDetail.ElecMaterialList bean : movementScanItemsBean.getElecMaterialList()) {
                                    movementScanList.remove(new MovementScanItemViewModel(MovementScanListViewModel.this, bean));
                                }
                            }
                        }
                        if (movementScanList.size() > 0) {
                            noDataVisibleObservable.set(View.GONE);
                        } else {
                            noDataVisibleObservable.set(View.VISIBLE);
                        }
                    }
                });
        //将订阅者加入管理站
        mSubscriptionUpdate = RxBus.getDefault().toObservable(MovementScanUpdateItemsBean.class)
                .subscribe(new Consumer<MovementScanUpdateItemsBean>() {
                    @Override
                    public void accept(MovementScanUpdateItemsBean movementScanItemsBean) throws Exception {
                        if (movementScanItemsBean != null && movementScanItemsBean.getPosition() == position) {
                            for (MovementDetail.ElecMaterialList bean : movementScanItemsBean.getElecMaterialList()) {
                                for (MovementScanItemViewModel itemViewModel : movementScanList) {
                                    if (Objects.requireNonNull(itemViewModel.entity.get()).getMaterialId() == bean.getMaterialId()) {
                                        itemViewModel.entity.get().setIsIn(0);
                                        itemViewModel.entity.get().setIsInMessage(getApplication().getResources().getString(R.string.workbench_movement_failure_text));
                                        itemViewModel.entity.get().setBgColor(Utils.getContext().getDrawable(R.color.color_fc6666));
                                        itemViewModel.entity.notifyChange();
                                    }
                                }
                            }
                        }
                    }
                });
        //将订阅者加入管理站
        RxSubscriptions.add(mSubscriptionAdd);
        RxSubscriptions.add(mSubscriptionRemove);
        RxSubscriptions.add(mSubscriptionUpdate);
    }

    //移除RxBus
    @Override
    public void removeRxBus() {
        super.removeRxBus();
        //将订阅者从管理站中移除
        RxSubscriptions.remove(mSubscriptionAdd);
        RxSubscriptions.remove(mSubscriptionRemove);
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
