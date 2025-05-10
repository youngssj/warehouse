package com.victor.purchaseinbound.ui.viewmodel.itemviewmodel;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.victor.base.data.entity.InboundData;
import com.victor.base.router.RouterActivityPath;
import com.victor.purchaseinbound.ui.viewmodel.InboundListViewModel;
import com.victor.workbench.ui.base.BaseRecycleItemViewModel;

public class InboundItemViewModel extends BaseRecycleItemViewModel<InboundListViewModel, InboundData> {


    public InboundItemViewModel(@NonNull InboundListViewModel viewModel, InboundData inboundData) {
        super(viewModel, inboundData);
    }

    @Override
    protected void itemClickCallback() {
        ARouter.getInstance().build(RouterActivityPath.Inbound.PAGER_INBOUND_SCAN)
                .withInt("inId", entity.get().getInId())
                .navigation();
    }
}
