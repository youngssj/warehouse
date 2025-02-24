package com.victor.main.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import com.victor.base.data.Repository.AppRepository;
import com.victor.base.data.entity.BusinessReminder;
import com.victor.base.data.entity.DeviceData;
import com.victor.base.data.entity.HomeTotalData;
import com.victor.base.data.entity.IllegalTakeout;
import com.victor.base.data.entity.ListData;
import com.victor.base.data.entity.MaterialsStatisticsData;
import com.victor.base.data.entity.StatisticsInfo;
import com.victor.base.data.http.ApiDisposableObserver;
import com.victor.base.data.http.ApiListDisposableObserver;
import com.victor.main.BR;
import com.victor.main.R;
import com.victor.main.ui.viewmodel.itemviewmodel.BusinessNotificationItemViewModel;
import com.victor.main.ui.viewmodel.itemviewmodel.DeviceStatusItemViewModel;
import com.victor.main.ui.viewmodel.itemviewmodel.IllegalTakeOutItemViewModel;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class HomeViewModel extends BaseViewModel<AppRepository> {

    public ObservableField<StatisticsInfo> statisticsInfo = new ObservableField<>();
    // 业务提醒
    public ObservableList<BusinessNotificationItemViewModel> businessNotificationList = new ObservableArrayList<>();
    // 违规带出
    public ObservableList<IllegalTakeOutItemViewModel> illegalTakeoutList = new ObservableArrayList<>();
    // 入库状态
    public ObservableList<DeviceStatusItemViewModel> deviceStatusList = new ObservableArrayList<>();

    public ItemBinding<BusinessNotificationItemViewModel> businessNotificationItemBinding = ItemBinding.of(BR.viewModel, R.layout.main_item_home_layout2);
    public ItemBinding<IllegalTakeOutItemViewModel> illegalTakeoutListItemBinding = ItemBinding.of(BR.viewModel, R.layout.main_item_home_layout5);
    public ItemBinding<DeviceStatusItemViewModel> deviceStatusListItemBinding = ItemBinding.of(BR.viewModel, R.layout.main_item_home_layout6);

    public class UIChangeObservable {
        public SingleLiveEvent<List<HomeTotalData>> totalDataEvent = new SingleLiveEvent<>();
        public SingleLiveEvent<MaterialsStatisticsData> materialsStatisticsDataEvent = new SingleLiveEvent<>();
    }

    public UIChangeObservable uc = new UIChangeObservable();

    public HomeViewModel(@NonNull Application application, AppRepository model) {
        super(application, model);
    }

    @Override
    public void onResume() {
        super.onResume();
        // 获取用户信息
        getStatisticsInfo();
        getBusinessReminder();
        getIllegalTakeoutList();
        getTotalData();
        getDeviceData();
        getMaterialsStatistics();
    }

    private void getMaterialsStatistics() {
        model.getMaterialsStatistics()
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<MaterialsStatisticsData>() {
                    @Override
                    public void onResult(MaterialsStatisticsData materialsStatisticsData) {
                        uc.materialsStatisticsDataEvent.setValue(materialsStatisticsData);
                    }
                });
    }

    private void getDeviceData() {
        model.getDeviceData()
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<List<DeviceData>>() {
                    @Override
                    public void onResult(List<DeviceData> deviceDatas) {
                        deviceStatusList.clear();
                        for (DeviceData deviceData : deviceDatas) {
                            deviceStatusList.add(new DeviceStatusItemViewModel(HomeViewModel.this,
                                    deviceData.getDeviceCode(),
                                    deviceData.getDeviceName(),
                                    deviceData.getDeviceTypeString(),
                                    deviceData.getStatusString()));
                        }
                    }
                });
    }

    private void getTotalData() {
        model.getTotalData()
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<List<HomeTotalData>>() {
                    @Override
                    public void onResult(List<HomeTotalData> homeTotalDatas) {
                        uc.totalDataEvent.setValue(homeTotalDatas);
                    }
                });
    }

    private void getIllegalTakeoutList() {
        model.getIllegalTakeout()
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiListDisposableObserver<List<IllegalTakeout>>() {
                    @Override
                    public void onResult(ListData<List<IllegalTakeout>> listData) {
                        illegalTakeoutList.clear();
                        List<IllegalTakeout> illegalTakeouts = listData.getList();
                        for (IllegalTakeout illegalTakeout : illegalTakeouts) {
                            illegalTakeoutList.add(new IllegalTakeOutItemViewModel(HomeViewModel.this,
                                    illegalTakeout.getHouseName(),
                                    illegalTakeout.getMaterialName(),
                                    illegalTakeout.getStatusString(),
                                    illegalTakeout.getCreateTime()));
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
                        businessNotificationList.clear();
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
