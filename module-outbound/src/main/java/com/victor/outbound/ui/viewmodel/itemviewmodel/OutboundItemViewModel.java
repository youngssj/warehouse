package com.victor.outbound.ui.viewmodel.itemviewmodel;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.victor.base.data.entity.OutboundData;
import com.victor.base.router.RouterActivityPath;
import com.victor.outbound.ui.viewmodel.OutboundListViewModel;
import com.victor.workbench.ui.base.BaseRecycleItemViewModel;

public class OutboundItemViewModel extends BaseRecycleItemViewModel<OutboundListViewModel, OutboundData> {


    public OutboundItemViewModel(@NonNull OutboundListViewModel viewModel, OutboundData outboundData) {
        super(viewModel, outboundData);
    }

    @Override
    protected void itemClickCallback() {
        ARouter.getInstance().build(RouterActivityPath.Outbound.PAGER_OUTBOUND_SCAN)
                .withInt("inId", entity.get().getInId())
                .navigation();
    }
}
