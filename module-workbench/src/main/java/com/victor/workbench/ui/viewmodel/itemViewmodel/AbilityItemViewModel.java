package com.victor.workbench.ui.viewmodel.itemViewmodel;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.alibaba.android.arouter.launcher.ARouter;
import com.victor.base.router.RouterActivityPath;
import com.victor.workbench.R;
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
            if (viewModel.getApplication().getResources().getString(R.string.workbench_title1_text).equals(name.get())) {
                ARouter.getInstance().build(RouterActivityPath.Materials.PAGER_MATERIALS_QUERY).navigation();
            } else if (viewModel.getApplication().getResources().getString(R.string.workbench_title2_text).equals(name.get())) {
                ARouter.getInstance().build(RouterActivityPath.Inbound.PAGER_INBOUND_LIST).navigation();
            } else if (viewModel.getApplication().getResources().getString(R.string.workbench_title3_text).equals(name.get())) {
                ARouter.getInstance().build(RouterActivityPath.Outbound.PAGER_OUTBOUND_LIST).navigation();
            } else if (viewModel.getApplication().getResources().getString(R.string.workbench_title4_text).equals(name.get())) {
                ARouter.getInstance().build(RouterActivityPath.Movement.PAGER_MOVEMENT_LIST).navigation();
            } else if (viewModel.getApplication().getResources().getString(R.string.workbench_title5_text).equals(name.get())) {

            } else if (viewModel.getApplication().getResources().getString(R.string.workbench_title6_text).equals(name.get())) {
                ARouter.getInstance().build(RouterActivityPath.Inventory.PAGER_INVENTORY_CHECK_LIST).navigation();
            }
        }
    });

    public AbilityItemViewModel(WorkBenchViewModel workBenchViewModel, String name, Integer icon, Integer background) {
        super(workBenchViewModel);

        this.name.set(name);
        this.icon.set(icon);
        this.background.set(background);
    }
}
