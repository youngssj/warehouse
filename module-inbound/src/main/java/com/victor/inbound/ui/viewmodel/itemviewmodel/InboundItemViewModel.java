package com.victor.inbound.ui.viewmodel.itemviewmodel;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.victor.base.data.entity.TakeStockData;
import com.victor.base.router.RouterActivityPath;
import com.victor.base.utils.Constants;
import com.victor.inbound.ui.viewmodel.InboundListViewModel;
import com.victor.workbench.ui.base.BaseRecycleItemViewModel;

public class InboundItemViewModel extends BaseRecycleItemViewModel<InboundListViewModel, TakeStockData> {


    public InboundItemViewModel(@NonNull InboundListViewModel viewModel, TakeStockData data) {
        super(viewModel, data);
    }

    @Override
    protected void itemClickCallback() {
        ARouter.getInstance().build(RouterActivityPath.Inbound.PAGER_INBOUND_SCAN)
                .withInt(Constants.BUNDLE.KEY, entity.get().getCheckId())
                .navigation();
    }
}
