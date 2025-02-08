package com.victor.inbound.ui.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import com.victor.base.data.entity.InboundData;
import com.victor.inbound.BR;
import com.victor.inbound.R;
import com.victor.inbound.bean.InboundScanAddItemsBean;
import com.victor.inbound.bean.InboundScanRemoveItemsBean;
import com.victor.inbound.bean.InboundScanUpdateItemsBean;
import com.victor.inbound.ui.viewmodel.itemviewmodel.InboundScanItemViewModel;

import java.util.Objects;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.bus.RxSubscriptions;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.Utils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class InboundScanListViewModel extends BaseViewModel {

    private int position;
    public ObservableList<InboundScanItemViewModel> inboundScanList = new ObservableArrayList<>();
    public ItemBinding<InboundScanItemViewModel> inboundScanItemBinding = ItemBinding.of(BR.viewModel, R.layout.inbound_item_scan);
    public ObservableInt noDataVisibleObservable = new ObservableInt(View.VISIBLE);

    public class UIChangeObservable {
        public SingleLiveEvent<InboundScanItemViewModel> showCustomEvent = new SingleLiveEvent<>();
    }

    public UIChangeObservable uc = new UIChangeObservable();

    public InboundScanListViewModel(@NonNull Application application) {
        super(application);
    }

    //订阅者
    private Disposable mSubscriptionAdd, mSubscriptionRemove, mSubscriptionUpdate;

    //注册RxBus
    @Override
    public void registerRxBus() {
        super.registerRxBus();
        mSubscriptionAdd = RxBus.getDefault().toObservable(InboundScanAddItemsBean.class)
                .subscribe(new Consumer<InboundScanAddItemsBean>() {
                    @Override
                    public void accept(InboundScanAddItemsBean inboundScanItemsBean) throws Exception {
                        if (inboundScanItemsBean != null && inboundScanItemsBean.getPosition() == position) {
                            if (inboundScanItemsBean.getElecMaterialList() != null && inboundScanItemsBean.getElecMaterialList().size() > 0) {
                                for (InboundData.ElecMaterialList bean : inboundScanItemsBean.getElecMaterialList()) {
                                    inboundScanList.add(new InboundScanItemViewModel(InboundScanListViewModel.this, bean));
                                }
                            }
                        }
                        if (inboundScanList.size() > 0) {
                            noDataVisibleObservable.set(View.GONE);
                        } else {
                            noDataVisibleObservable.set(View.VISIBLE);
                        }
                    }
                });
        //将订阅者加入管理站
        mSubscriptionRemove = RxBus.getDefault().toObservable(InboundScanRemoveItemsBean.class)
                .subscribe(new Consumer<InboundScanRemoveItemsBean>() {
                    @Override
                    public void accept(InboundScanRemoveItemsBean inboundScanItemsBean) throws Exception {
                        if (inboundScanItemsBean != null && inboundScanItemsBean.getPosition() == position) {
                            if (inboundScanItemsBean.getElecMaterialList() != null && inboundScanItemsBean.getElecMaterialList().size() > 0) {
                                for (InboundData.ElecMaterialList bean : inboundScanItemsBean.getElecMaterialList()) {
                                    inboundScanList.remove(new InboundScanItemViewModel(InboundScanListViewModel.this, bean));
                                }
                            }
                        }
                        if (inboundScanList.size() > 0) {
                            noDataVisibleObservable.set(View.GONE);
                        } else {
                            noDataVisibleObservable.set(View.VISIBLE);
                        }
                    }
                });
        //将订阅者加入管理站
        mSubscriptionUpdate = RxBus.getDefault().toObservable(InboundScanUpdateItemsBean.class)
                .subscribe(new Consumer<InboundScanUpdateItemsBean>() {
                    @Override
                    public void accept(InboundScanUpdateItemsBean inboundScanItemsBean) throws Exception {
                        if (inboundScanItemsBean != null && inboundScanItemsBean.getPosition() == position) {
                            for (InboundData.ElecMaterialList bean : inboundScanItemsBean.getElecMaterialList()) {
                                for (InboundScanItemViewModel itemViewModel : inboundScanList) {
                                    if (Objects.requireNonNull(itemViewModel.entity.get()).getMaterialId() == bean.getMaterialId()) {
                                        itemViewModel.entity.get().setIsIn(0);
                                        itemViewModel.entity.get().setIsInMessage(getApplication().getResources().getString(R.string.workbench_inbound_failure_text));
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
