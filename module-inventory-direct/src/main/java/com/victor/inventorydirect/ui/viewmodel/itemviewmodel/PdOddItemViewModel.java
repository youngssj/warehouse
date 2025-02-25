package com.victor.inventorydirect.ui.viewmodel.itemviewmodel;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.victor.base.data.entity.InventoryData;
import com.victor.base.router.RouterActivityPath;
import com.victor.base.utils.Constants;
import com.victor.inventorydirect.ui.viewmodel.PdOddViewModel;
import com.victor.workbench.ui.base.BaseRecycleItemViewModel;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/9/9
 * 邮箱：jxfengmtx@gmail.com
 */
public class PdOddItemViewModel extends BaseRecycleItemViewModel<PdOddViewModel, InventoryData> {


    public PdOddItemViewModel(@NonNull PdOddViewModel viewModel, InventoryData data) {
        super(viewModel, data);
    }

    @Override
    protected void itemClickCallback() {
        ARouter.getInstance().build(RouterActivityPath.Inventory.PAGER_INVENTORY_CHECK)
                .withInt(Constants.BUNDLE.KEY, entity.get().getCheckId())
                .navigation();
    }

}
