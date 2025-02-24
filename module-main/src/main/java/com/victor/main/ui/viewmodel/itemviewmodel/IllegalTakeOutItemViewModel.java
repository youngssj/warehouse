package com.victor.main.ui.viewmodel.itemviewmodel;

import androidx.databinding.ObservableField;

import com.victor.main.ui.viewmodel.HomeViewModel;

import me.goldze.mvvmhabit.base.ItemViewModel;

/**
 * 业务提醒Item
 */
public class IllegalTakeOutItemViewModel extends ItemViewModel<HomeViewModel> {

    public ObservableField<String> houseName = new ObservableField<>();
    public ObservableField<String> materialName = new ObservableField<>();
    public ObservableField<String> statusString = new ObservableField<>();
    public ObservableField<String> createTime = new ObservableField<>();

    public IllegalTakeOutItemViewModel(HomeViewModel homeViewModel, String houseName, String materialName, String statusString, String createTime) {
        super(homeViewModel);

        this.houseName.set(houseName);
        this.materialName.set(materialName);
        this.statusString.set(statusString);
        this.createTime.set(createTime);
    }
}
