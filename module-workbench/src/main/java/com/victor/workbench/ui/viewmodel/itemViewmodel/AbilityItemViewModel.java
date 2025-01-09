package com.victor.workbench.ui.viewmodel.itemViewmodel;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.victor.workbench.ui.viewmodel.WorkBenchViewModel;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.KLog;

/**
 * 业务提醒Item
 */
public class AbilityItemViewModel extends ItemViewModel<WorkBenchViewModel> {

    public ObservableField<String> name = new ObservableField<>();
    public ObservableInt icon = new ObservableInt();
    public ObservableField<Integer> background = new ObservableField<>();
    public BindingCommand onClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            KLog.i(name.get() + "点击了");
        }
    });

    public AbilityItemViewModel(WorkBenchViewModel workBenchViewModel, String name, Integer icon, Integer background) {
        super(workBenchViewModel);

        this.name.set(name);
        this.icon.set(icon);
        this.background.set(background);
    }
}
