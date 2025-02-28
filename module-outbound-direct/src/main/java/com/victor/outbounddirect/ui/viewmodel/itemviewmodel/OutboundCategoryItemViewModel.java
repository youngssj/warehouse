package com.victor.outbounddirect.ui.viewmodel.itemviewmodel;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.alibaba.android.arouter.launcher.ARouter;
import com.victor.base.data.entity.OperateCategory;
import com.victor.base.router.RouterActivityPath;
import com.victor.outbounddirect.ui.viewmodel.OutboundCategoryViewModel;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class OutboundCategoryItemViewModel extends ItemViewModel<OutboundCategoryViewModel> {

    public ObservableField<OperateCategory> entity = new ObservableField<>();

    public BindingCommand itemClick = new BindingCommand(() -> {
        ARouter.getInstance().build(RouterActivityPath.Outbound.PAGER_OUTBOUND_SCAN)
                .withParcelable("category", entity.get())
                .navigation();
        viewModel.finish();
    });

    public OutboundCategoryItemViewModel(@NonNull OutboundCategoryViewModel viewModel, OperateCategory operateCategory) {
        super(viewModel);
        this.entity.set(operateCategory);
    }
}
