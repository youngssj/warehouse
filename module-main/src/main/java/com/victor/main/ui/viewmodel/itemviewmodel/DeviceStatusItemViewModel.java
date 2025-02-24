package com.victor.main.ui.viewmodel.itemviewmodel;

import androidx.databinding.ObservableField;

import com.victor.main.ui.viewmodel.HomeViewModel;

import me.goldze.mvvmhabit.base.ItemViewModel;

/**
 * 业务提醒Item
 */
public class DeviceStatusItemViewModel extends ItemViewModel<HomeViewModel> {

    public ObservableField<String> deviceCode = new ObservableField<>();
    public ObservableField<String> deviceName = new ObservableField<>();
    public ObservableField<String> deviceTypeString = new ObservableField<>();
    public ObservableField<String> statusString = new ObservableField<>();

    public DeviceStatusItemViewModel(HomeViewModel homeViewModel, String deviceCode, String deviceName, String deviceTypeString, String statusString) {
        super(homeViewModel);

        this.deviceCode.set(deviceCode);
        this.deviceName.set(deviceName);
        this.deviceTypeString.set(deviceTypeString);
        this.statusString.set(statusString);
    }
}
