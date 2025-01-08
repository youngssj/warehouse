package com.victor.main.ui.viewmodel.itemviewmodel;

import androidx.databinding.ObservableField;

import com.victor.main.ui.viewmodel.HomeViewModel;

import me.goldze.mvvmhabit.base.ItemViewModel;

/**
 * 业务提醒Item
 */
public class OutBoundItemViewModel extends ItemViewModel<HomeViewModel> {

    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> status = new ObservableField<>();
    public ObservableField<String> time = new ObservableField<>();
    public ObservableField<String> operator = new ObservableField<>();

    public OutBoundItemViewModel(HomeViewModel homeViewModel, String name, String status, String time, String operator) {
        super(homeViewModel);

        this.name.set(name);
        this.status.set(status);
        this.time.set(time);
        this.operator.set(operator);
    }
}
