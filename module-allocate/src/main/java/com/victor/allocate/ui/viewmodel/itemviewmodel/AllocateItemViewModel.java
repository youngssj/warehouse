package com.victor.allocate.ui.viewmodel.itemviewmodel;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.victor.base.data.entity.AllocateData;
import com.victor.base.router.RouterActivityPath;
import com.victor.allocate.ui.viewmodel.AllocateListViewModel;
import com.victor.workbench.ui.base.BaseRecycleItemViewModel;

public class AllocateItemViewModel extends BaseRecycleItemViewModel<AllocateListViewModel, AllocateData> {


    public AllocateItemViewModel(@NonNull AllocateListViewModel viewModel, AllocateData allocateData) {
        super(viewModel, allocateData);
    }

    @Override
    protected void itemClickCallback() {
        ARouter.getInstance().build(RouterActivityPath.Allocate.PAGER_ALLOCATE_SCAN)
                .withInt("allocateId", entity.get().getId())
                .navigation();
    }
}
