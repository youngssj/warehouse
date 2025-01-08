package com.victor.main.ui.viewmodel.itemviewmodel;

import androidx.databinding.ObservableField;

import com.victor.main.ui.viewmodel.HomeViewModel;

import me.goldze.mvvmhabit.base.ItemViewModel;

/**
 * 业务提醒Item
 */
public class BusinessNotificationItemViewModel extends ItemViewModel<HomeViewModel> {

    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> status = new ObservableField<>();

    public BusinessNotificationItemViewModel(HomeViewModel homeViewModel, String name, String status) {
        super(homeViewModel);

        this.name.set(name);
        this.status.set(status);
    }
}
