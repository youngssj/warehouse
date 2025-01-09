package com.victor.workbench.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.victor.workbench.BR;
import com.victor.workbench.R;
import com.victor.workbench.ui.viewmodel.itemViewmodel.AbilityItemViewModel;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.utils.KLog;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class WorkBenchViewModel extends BaseViewModel {

    public ObservableList<AbilityItemViewModel> abilityList = new ObservableArrayList<>();
    public ItemBinding<AbilityItemViewModel> abilityItemBinding = ItemBinding.of(BR.viewModel, R.layout.workbench_item_ability);

    public WorkBenchViewModel(@NonNull Application application) {
        super(application);

        abilityList.add(new AbilityItemViewModel(this,
                getApplication().getString(R.string.workbench_title1_text),
                R.mipmap.workbench_icon1, R.drawable.selector_workbench_ability_background1));
        abilityList.add(new AbilityItemViewModel(this,
                getApplication().getString(R.string.workbench_title2_text),
                R.mipmap.workbench_icon2, R.drawable.selector_workbench_ability_background2));
        abilityList.add(new AbilityItemViewModel(this,
                getApplication().getString(R.string.workbench_title3_text),
                R.mipmap.workbench_icon3, R.drawable.selector_workbench_ability_background3));
        abilityList.add(new AbilityItemViewModel(this,
                getApplication().getString(R.string.workbench_title4_text),
                R.mipmap.workbench_icon4, R.drawable.selector_workbench_ability_background4));
        abilityList.add(new AbilityItemViewModel(this,
                getApplication().getString(R.string.workbench_title5_text),
                R.mipmap.workbench_icon5, R.drawable.selector_workbench_ability_background5));
        abilityList.add(new AbilityItemViewModel(this,
                getApplication().getString(R.string.workbench_title6_text),
                R.mipmap.workbench_icon6, R.drawable.selector_workbench_ability_background6));
    }
}
