package com.victor.inbounddirect.ui.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import com.victor.base.data.entity.InboundData;
import com.victor.base.event.Event;
import com.victor.base.event.MessageEvent;
import com.victor.base.event.MessageType;
import com.victor.inbounddirect.BR;
import com.victor.inbounddirect.R;
import com.victor.inbounddirect.bean.InboundScanItemsBean;
import com.victor.inbounddirect.ui.viewmodel.itemviewmodel.InboundScanItemViewModel;

import java.util.Objects;

import io.reactivex.disposables.Disposable;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.bus.RxBus;
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

    //注册RxBus
    @Override
    public void registerRxBus() {
        super.registerRxBus();
        Disposable mSubscription = RxBus.getDefault().toObservable(Event.class)
                .subscribe(event -> {
                    if (event instanceof MessageEvent) {
                        switch (((MessageEvent<?>) event).getMessageType()) {
                            case MessageType.EVENT_TYPE_INBOUND_SCAN_ADD_ITEM:
                                InboundScanItemsBean inboundScanAddItemsBean = ((MessageEvent<InboundScanItemsBean>) event).getData();
                                if (inboundScanAddItemsBean != null && inboundScanAddItemsBean.getPosition() == position) {
                                    if (inboundScanAddItemsBean.getElecMaterialList() != null && inboundScanAddItemsBean.getElecMaterialList().size() > 0) {
                                        for (InboundData.InboundElecMaterial bean : inboundScanAddItemsBean.getElecMaterialList()) {
                                            inboundScanList.add(new InboundScanItemViewModel(InboundScanListViewModel.this, bean));
                                        }
                                    }
                                }
                                setNoDataVisibleObservable();
                                break;
                            case MessageType.EVENT_TYPE_INBOUND_SCAN_REMOVE_ITEM:
                                InboundScanItemsBean inboundScanRemoveItemsBean = ((MessageEvent<InboundScanItemsBean>) event).getData();
                                if (inboundScanRemoveItemsBean != null && inboundScanRemoveItemsBean.getPosition() == position) {
                                    if (inboundScanRemoveItemsBean.getElecMaterialList() != null && inboundScanRemoveItemsBean.getElecMaterialList().size() > 0) {
                                        for (InboundData.InboundElecMaterial bean : inboundScanRemoveItemsBean.getElecMaterialList()) {
                                            inboundScanList.remove(new InboundScanItemViewModel(InboundScanListViewModel.this, bean));
                                        }
                                    }
                                }
                                setNoDataVisibleObservable();
                                break;
                            case MessageType.EVENT_TYPE_INBOUND_SCAN_UPDATE_ITEM:
                                InboundScanItemsBean inboundScanUpdateItemsBean = ((MessageEvent<InboundScanItemsBean>) event).getData();
                                if (inboundScanUpdateItemsBean != null && inboundScanUpdateItemsBean.getPosition() == position) {
                                    for (InboundData.InboundElecMaterial bean : inboundScanUpdateItemsBean.getElecMaterialList()) {
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
                                break;
                        }
                    }
                });
        addSubscribe(mSubscription);
    }

    private void setNoDataVisibleObservable(){
        if (inboundScanList.size() > 0) {
            noDataVisibleObservable.set(View.GONE);
        } else {
            noDataVisibleObservable.set(View.VISIBLE);
        }
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
