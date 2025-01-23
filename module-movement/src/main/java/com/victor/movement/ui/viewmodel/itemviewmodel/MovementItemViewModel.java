package com.victor.movement.ui.viewmodel.itemviewmodel;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.victor.base.data.entity.MovementData;
import com.victor.base.router.RouterActivityPath;
import com.victor.movement.ui.viewmodel.MovementListViewModel;
import com.victor.workbench.ui.base.BaseRecycleItemViewModel;

public class MovementItemViewModel extends BaseRecycleItemViewModel<MovementListViewModel, MovementData> {


    public MovementItemViewModel(@NonNull MovementListViewModel viewModel, MovementData movementData) {
        super(viewModel, movementData);
    }

    @Override
    protected void itemClickCallback() {
        ARouter.getInstance().build(RouterActivityPath.Movement.PAGER_MOVEMENT_SCAN)
                .withInt("inId", entity.get().getInId())
                .navigation();
    }
}
