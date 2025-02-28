package com.victor.inbounddirect.ui.viewmodel.itemviewmodel;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.alibaba.android.arouter.launcher.ARouter;
import com.victor.base.data.entity.InboundCategory;
import com.victor.base.router.RouterActivityPath;
import com.victor.inbounddirect.ui.viewmodel.InboundCategoryViewModel;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class InboundCategoryItemViewModel extends ItemViewModel<InboundCategoryViewModel> {

    public ObservableField<InboundCategory> entity = new ObservableField<>();

    public BindingCommand itemClick = new BindingCommand(() -> {
        ARouter.getInstance().build(RouterActivityPath.Inbound.PAGER_INBOUND_SCAN)
                .withParcelable("category", entity.get())
                .navigation();
        viewModel.finish();
    });

    public InboundCategoryItemViewModel(@NonNull InboundCategoryViewModel viewModel, InboundCategory inboundCategory) {
        super(viewModel);
        this.entity.set(inboundCategory);
    }
}
