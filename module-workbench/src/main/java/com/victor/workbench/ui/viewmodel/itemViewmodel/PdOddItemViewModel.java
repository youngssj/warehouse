package com.victor.workbench.ui.viewmodel.itemViewmodel;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.victor.base.data.entity.TakeStockData;
import com.victor.base.utils.Constants;
import com.victor.workbench.ui.activity.ZcpdActivity;
import com.victor.workbench.ui.base.BaseRecycleItemViewModel;
import com.victor.workbench.ui.viewmodel.PdOddViewModel;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/9/9
 * 邮箱：jxfengmtx@gmail.com
 */
public class PdOddItemViewModel extends BaseRecycleItemViewModel<PdOddViewModel, TakeStockData> {


    public PdOddItemViewModel(@NonNull PdOddViewModel viewModel, TakeStockData data) {
        super(viewModel, data);
    }

    @Override
    protected void itemClickCallback() {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BUNDLE.KEY, entity.get().getCheckId());
        viewModel.startActivity(ZcpdActivity.class, bundle);
    }

}
