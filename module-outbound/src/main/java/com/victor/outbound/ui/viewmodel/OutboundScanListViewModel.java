package com.victor.outbound.ui.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import com.victor.base.data.entity.OutboundDetail;
import com.victor.outbound.BR;
import com.victor.outbound.R;
import com.victor.outbound.bean.OutboundScanAddItemsBean;
import com.victor.outbound.bean.OutboundScanRemoveItemsBean;
import com.victor.outbound.bean.OutboundScanUpdateItemsBean;
import com.victor.outbound.ui.viewmodel.itemviewmodel.OutboundScanItemViewModel;

import java.util.Objects;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.bus.RxSubscriptions;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.Utils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class OutboundScanListViewModel extends BaseViewModel {

    private int position;
    public ObservableList<OutboundScanItemViewModel> outboundScanList = new ObservableArrayList<>();
    public ItemBinding<OutboundScanItemViewModel> outboundScanItemBinding = ItemBinding.of(BR.viewModel, R.layout.outbound_item_scan);
    public ObservableInt noDataVisibleObservable = new ObservableInt(View.VISIBLE);

    public class UIChangeObservable {
        public SingleLiveEvent<OutboundScanItemViewModel> showCustomEvent = new SingleLiveEvent<>();
    }

    public UIChangeObservable uc = new UIChangeObservable();

    public OutboundScanListViewModel(@NonNull Application application) {
        super(application);
    }

    //订阅者
    private Disposable mSubscriptionAdd, mSubscriptionRemove, mSubscriptionUpdate;

    //注册RxBus
    @Override
    public void registerRxBus() {
        super.registerRxBus();
        mSubscriptionAdd = RxBus.getDefault().toObservable(OutboundScanAddItemsBean.class)
                .subscribe(new Consumer<OutboundScanAddItemsBean>() {
                    @Override
                    public void accept(OutboundScanAddItemsBean outboundScanItemsBean) throws Exception {
                        if (outboundScanItemsBean != null && outboundScanItemsBean.getPosition() == position) {
                            if (outboundScanItemsBean.getElecMaterialList() != null && outboundScanItemsBean.getElecMaterialList().size() > 0) {
                                for (OutboundDetail.ElecMaterialList bean : outboundScanItemsBean.getElecMaterialList()) {
                                    outboundScanList.add(new OutboundScanItemViewModel(OutboundScanListViewModel.this, bean));
                                }
                            }
                        }
                        if (outboundScanList.size() > 0) {
                            noDataVisibleObservable.set(View.GONE);
                        } else {
                            noDataVisibleObservable.set(View.VISIBLE);
                        }
                    }
                });
        //将订阅者加入管理站
        mSubscriptionRemove = RxBus.getDefault().toObservable(OutboundScanRemoveItemsBean.class)
                .subscribe(new Consumer<OutboundScanRemoveItemsBean>() {
                    @Override
                    public void accept(OutboundScanRemoveItemsBean outboundScanItemsBean) throws Exception {
                        if (outboundScanItemsBean != null && outboundScanItemsBean.getPosition() == position) {
                            if (outboundScanItemsBean.getElecMaterialList() != null && outboundScanItemsBean.getElecMaterialList().size() > 0) {
                                for (OutboundDetail.ElecMaterialList bean : outboundScanItemsBean.getElecMaterialList()) {
                                    outboundScanList.remove(new OutboundScanItemViewModel(OutboundScanListViewModel.this, bean));
                                }
                            }
                        }
                        if (outboundScanList.size() > 0) {
                            noDataVisibleObservable.set(View.GONE);
                        } else {
                            noDataVisibleObservable.set(View.VISIBLE);
                        }
                    }
                });
        //将订阅者加入管理站
        mSubscriptionUpdate = RxBus.getDefault().toObservable(OutboundScanUpdateItemsBean.class)
                .subscribe(new Consumer<OutboundScanUpdateItemsBean>() {
                    @Override
                    public void accept(OutboundScanUpdateItemsBean outboundScanItemsBean) throws Exception {
                        if (outboundScanItemsBean != null && outboundScanItemsBean.getPosition() == position) {
                            for (OutboundDetail.ElecMaterialList bean : outboundScanItemsBean.getElecMaterialList()) {
                                for (OutboundScanItemViewModel itemViewModel : outboundScanList) {
                                    if (Objects.requireNonNull(itemViewModel.entity.get()).getMaterialId() == bean.getMaterialId()) {
                                        itemViewModel.entity.get().setIsOut("0");
                                        itemViewModel.entity.get().setIsOutMessage(getApplication().getResources().getString(R.string.workbench_outbound_failure_text));
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
