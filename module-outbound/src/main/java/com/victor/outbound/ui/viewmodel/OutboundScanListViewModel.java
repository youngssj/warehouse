package com.victor.outbound.ui.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import com.victor.base.data.entity.OutboundData;
import com.victor.base.event.Event;
import com.victor.base.event.MessageEvent;
import com.victor.base.event.MessageType;
import com.victor.outbound.BR;
import com.victor.outbound.R;
import com.victor.outbound.bean.OutboundScanItemsBean;
import com.victor.outbound.ui.viewmodel.itemviewmodel.OutboundScanItemViewModel;

import java.util.Objects;

import io.reactivex.disposables.Disposable;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.bus.RxBus;
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

    //注册RxBus
    @Override
    public void registerRxBus() {
        super.registerRxBus();
        Disposable mSubscription = RxBus.getDefault().toObservable(Event.class)
                .subscribe(event -> {
                    if (event instanceof MessageEvent) {
                        switch (((MessageEvent<?>) event).getMessageType()) {
                            case MessageType.EVENT_TYPE_OUTBOUND_SCAN_ADD_ITEM:
                                OutboundScanItemsBean outboundScanAddItemsBean = ((MessageEvent<OutboundScanItemsBean>) event).getData();
                                if (outboundScanAddItemsBean != null && outboundScanAddItemsBean.getPosition() == position) {
                                    if (outboundScanAddItemsBean.getElecMaterialList() != null && outboundScanAddItemsBean.getElecMaterialList().size() > 0) {
                                        for (OutboundData.OutboundElecMaterial bean : outboundScanAddItemsBean.getElecMaterialList()) {
                                            outboundScanList.add(new OutboundScanItemViewModel(OutboundScanListViewModel.this, bean));
                                        }
                                    }
                                }
                                setNoDataVisibleObservable();
                                break;
                            case MessageType.EVENT_TYPE_OUTBOUND_SCAN_REMOVE_ITEM:
                                OutboundScanItemsBean outboundScanRemoveItemsBean = ((MessageEvent<OutboundScanItemsBean>) event).getData();
                                if (outboundScanRemoveItemsBean != null && outboundScanRemoveItemsBean.getPosition() == position) {
                                    if (outboundScanRemoveItemsBean.getElecMaterialList() != null && outboundScanRemoveItemsBean.getElecMaterialList().size() > 0) {
                                        for (OutboundData.OutboundElecMaterial bean : outboundScanRemoveItemsBean.getElecMaterialList()) {
                                            outboundScanList.remove(new OutboundScanItemViewModel(OutboundScanListViewModel.this, bean));
                                        }
                                    }
                                }
                                setNoDataVisibleObservable();
                                break;
                            case MessageType.EVENT_TYPE_OUTBOUND_SCAN_UPDATE_ITEM:
                                OutboundScanItemsBean outboundScanUpdateItemsBean = ((MessageEvent<OutboundScanItemsBean>) event).getData();
                                if (outboundScanUpdateItemsBean != null && outboundScanUpdateItemsBean.getPosition() == position) {
                                    for (OutboundData.OutboundElecMaterial bean : outboundScanUpdateItemsBean.getElecMaterialList()) {
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
                                break;
                        }
                    }
                });
        addSubscribe(mSubscription);
    }

    private void setNoDataVisibleObservable() {
        if (outboundScanList.size() > 0) {
            noDataVisibleObservable.set(View.GONE);
        } else {
            noDataVisibleObservable.set(View.VISIBLE);
        }
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
