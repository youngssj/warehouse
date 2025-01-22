package com.victor.inbound.ui.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import com.victor.base.data.entity.InboundDetail;
import com.victor.base.data.entity.TakeStockDetail;
import com.victor.inbound.BR;
import com.victor.inbound.R;
import com.victor.inbound.bean.InboundScanAddItemsBean;
import com.victor.inbound.ui.viewmodel.itemviewmodel.InboundScanItemViewModel;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.bus.RxSubscriptions;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
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
    private Disposable mSubscription;

    //注册RxBus
    @Override
    public void registerRxBus() {
        super.registerRxBus();
        mSubscription = RxBus.getDefault().toObservable(InboundScanAddItemsBean.class)
                .subscribe(new Consumer<InboundScanAddItemsBean>() {
                    @Override
                    public void accept(InboundScanAddItemsBean inboundScanItemsBean) throws Exception {
                        if (inboundScanItemsBean != null && inboundScanItemsBean.getPosition() == position) {
                            inboundScanList.clear();
                            if (inboundScanItemsBean.getElecMaterialList() != null && inboundScanItemsBean.getElecMaterialList().size() > 0) {
                                for (InboundDetail.ElecMaterialList bean : inboundScanItemsBean.getElecMaterialList()) {
                                    inboundScanList.add(new InboundScanItemViewModel(InboundScanListViewModel.this, bean));
                                }
                                noDataVisibleObservable.set(View.GONE);
                            } else {
                                noDataVisibleObservable.set(View.VISIBLE);
                            }
                        }
                    }
                });
        //将订阅者加入管理站
        RxSubscriptions.add(mSubscription);
    }

    //移除RxBus
    @Override
    public void removeRxBus() {
        super.removeRxBus();
        //将订阅者从管理站中移除
        RxSubscriptions.remove(mSubscription);
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
