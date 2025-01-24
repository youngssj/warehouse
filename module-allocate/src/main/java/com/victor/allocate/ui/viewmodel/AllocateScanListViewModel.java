package com.victor.allocate.ui.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import com.victor.base.data.entity.AllocateDetail;
import com.victor.allocate.BR;
import com.victor.allocate.R;
import com.victor.allocate.bean.AllocateScanAddItemsBean;
import com.victor.allocate.bean.AllocateScanRemoveItemsBean;
import com.victor.allocate.bean.AllocateScanUpdateItemsBean;
import com.victor.allocate.ui.viewmodel.itemviewmodel.AllocateScanItemViewModel;

import java.util.Objects;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.bus.RxSubscriptions;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.Utils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class AllocateScanListViewModel extends BaseViewModel {

    private int position;
    public ObservableList<AllocateScanItemViewModel> allocateScanList = new ObservableArrayList<>();
    public ItemBinding<AllocateScanItemViewModel> allocateScanItemBinding = ItemBinding.of(BR.viewModel, R.layout.allocate_item_scan);
    public ObservableInt noDataVisibleObservable = new ObservableInt(View.VISIBLE);

    public class UIChangeObservable {
        public SingleLiveEvent<AllocateScanItemViewModel> showCustomEvent = new SingleLiveEvent<>();
    }

    public UIChangeObservable uc = new UIChangeObservable();

    public AllocateScanListViewModel(@NonNull Application application) {
        super(application);
    }

    //订阅者
    private Disposable mSubscriptionAdd, mSubscriptionRemove, mSubscriptionUpdate;

    //注册RxBus
    @Override
    public void registerRxBus() {
        super.registerRxBus();
        mSubscriptionAdd = RxBus.getDefault().toObservable(AllocateScanAddItemsBean.class)
                .subscribe(new Consumer<AllocateScanAddItemsBean>() {
                    @Override
                    public void accept(AllocateScanAddItemsBean allocateScanItemsBean) throws Exception {
                        if (allocateScanItemsBean != null && allocateScanItemsBean.getPosition() == position) {
                            if (allocateScanItemsBean.getElecMaterialList() != null && allocateScanItemsBean.getElecMaterialList().size() > 0) {
                                for (AllocateDetail.ElecMaterialList bean : allocateScanItemsBean.getElecMaterialList()) {
                                    allocateScanList.add(new AllocateScanItemViewModel(AllocateScanListViewModel.this, bean));
                                }
                            }
                        }
                        if (allocateScanList.size() > 0) {
                            noDataVisibleObservable.set(View.GONE);
                        } else {
                            noDataVisibleObservable.set(View.VISIBLE);
                        }
                    }
                });
        //将订阅者加入管理站
        mSubscriptionRemove = RxBus.getDefault().toObservable(AllocateScanRemoveItemsBean.class)
                .subscribe(new Consumer<AllocateScanRemoveItemsBean>() {
                    @Override
                    public void accept(AllocateScanRemoveItemsBean allocateScanItemsBean) throws Exception {
                        if (allocateScanItemsBean != null && allocateScanItemsBean.getPosition() == position) {
                            if (allocateScanItemsBean.getElecMaterialList() != null && allocateScanItemsBean.getElecMaterialList().size() > 0) {
                                for (AllocateDetail.ElecMaterialList bean : allocateScanItemsBean.getElecMaterialList()) {
                                    allocateScanList.remove(new AllocateScanItemViewModel(AllocateScanListViewModel.this, bean));
                                }
                            }
                        }
                        if (allocateScanList.size() > 0) {
                            noDataVisibleObservable.set(View.GONE);
                        } else {
                            noDataVisibleObservable.set(View.VISIBLE);
                        }
                    }
                });
        //将订阅者加入管理站
        mSubscriptionUpdate = RxBus.getDefault().toObservable(AllocateScanUpdateItemsBean.class)
                .subscribe(new Consumer<AllocateScanUpdateItemsBean>() {
                    @Override
                    public void accept(AllocateScanUpdateItemsBean allocateScanItemsBean) throws Exception {
                        if (allocateScanItemsBean != null && allocateScanItemsBean.getPosition() == position) {
                            for (AllocateDetail.ElecMaterialList bean : allocateScanItemsBean.getElecMaterialList()) {
                                for (AllocateScanItemViewModel itemViewModel : allocateScanList) {
                                    if (Objects.requireNonNull(itemViewModel.entity.get()).getMaterialId() == bean.getMaterialId()) {
                                        itemViewModel.entity.get().setIsIn(0);
                                        itemViewModel.entity.get().setIsInMessage(getApplication().getResources().getString(R.string.workbench_allocate_failure_text));
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
