package com.victor.main.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import com.victor.base.data.Repository.AppRepository;
import com.victor.base.data.entity.BusinessReminder;
import com.victor.base.data.entity.IllegalTakeout;
import com.victor.base.data.entity.ListData;
import com.victor.base.data.entity.StatisticsInfo;
import com.victor.base.data.http.ApiDisposableObserver;
import com.victor.base.data.http.ApiListDisposableObserver;
import com.victor.main.BR;
import com.victor.main.R;
import com.victor.main.ui.viewmodel.itemviewmodel.BusinessNotificationItemViewModel;
import com.victor.main.ui.viewmodel.itemviewmodel.InBoundItemViewModel;
import com.victor.main.ui.viewmodel.itemviewmodel.OutBoundItemViewModel;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class HomeViewModel extends BaseViewModel<AppRepository> {

    public ObservableField<StatisticsInfo> statisticsInfo = new ObservableField<>();
    // 业务提醒
    public ObservableList<BusinessNotificationItemViewModel> businessNotificationList = new ObservableArrayList<>();
    // 出库状态
    public ObservableList<OutBoundItemViewModel> outBoundList = new ObservableArrayList<>();
    // 入库状态
    public ObservableList<InBoundItemViewModel> illegalTakeoutList = new ObservableArrayList<>();

    public ItemBinding<BusinessNotificationItemViewModel> businessNotificationItemBinding = ItemBinding.of(BR.viewModel, R.layout.main_item_home_layout2);
    public ItemBinding<OutBoundItemViewModel> outBoundListItemBinding = ItemBinding.of(BR.viewModel, R.layout.main_item_home_layout5);
    public ItemBinding<InBoundItemViewModel> illegalTakeoutListItemBinding = ItemBinding.of(BR.viewModel, R.layout.main_item_home_layout6);

    public HomeViewModel(@NonNull Application application, AppRepository model) {
        super(application, model);
        outBoundList.add(new OutBoundItemViewModel(this, "xx配件", "入库", "2025-01-07", "admin"));
        outBoundList.add(new OutBoundItemViewModel(this, "xx配件", "入库", "2025-01-07", "admin"));
        outBoundList.add(new OutBoundItemViewModel(this, "xx配件", "入库", "2025-01-07", "admin"));
        outBoundList.add(new OutBoundItemViewModel(this, "xx配件", "入库", "2025-01-07", "admin"));

//        illegalTakeoutList.add(new InBoundItemViewModel(this, "xx配件", "入库", "2025-01-07", "admin"));
//        illegalTakeoutList.add(new InBoundItemViewModel(this, "xx配件", "入库", "2025-01-07", "admin"));
//        illegalTakeoutList.add(new InBoundItemViewModel(this, "xx配件", "入库", "2025-01-07", "admin"));
//        illegalTakeoutList.add(new InBoundItemViewModel(this, "xx配件", "入库", "2025-01-07", "admin"));
    }

    @Override
    public void onResume() {
        super.onResume();
        // 获取用户信息
        getStatisticsInfo();
        getBusinessReminder();
        getIllegalTakeoutList();
    }

    private void getIllegalTakeoutList() {
        model.getIllegalTakeout()
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiListDisposableObserver<List<IllegalTakeout>>() {
                    @Override
                    public void onResult(ListData<List<IllegalTakeout>> listData) {
                        List<IllegalTakeout> illegalTakeouts = listData.getList();
                        for (IllegalTakeout illegalTakeout : illegalTakeouts) {
//                            businessNotificationList.add(new BusinessNotificationItemViewModel(HomeViewModel.this,
//                                    businessReminder.getCode(),
//                                    businessReminder.getName(),
//                                    businessReminder.getStatus()));
                        }
                    }
                });
    }

    private void getBusinessReminder() {
        model.getBusinessReminder()
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiListDisposableObserver<List<BusinessReminder>>() {
                    @Override
                    public void onResult(ListData<List<BusinessReminder>> listData) {
                        List<BusinessReminder> businessReminders = listData.getList();
                        for (BusinessReminder businessReminder : businessReminders) {
                            businessNotificationList.add(new BusinessNotificationItemViewModel(HomeViewModel.this,
                                    businessReminder.getCode(),
                                    businessReminder.getName(),
                                    businessReminder.getStatus()));
                        }
                    }
                });
    }

    private void getStatisticsInfo() {
        model.getStatisticsInfo()
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<StatisticsInfo>() {
                    @Override
                    public void onResult(StatisticsInfo info) {
                        statisticsInfo.set(info);
                    }
                });
    }
}
