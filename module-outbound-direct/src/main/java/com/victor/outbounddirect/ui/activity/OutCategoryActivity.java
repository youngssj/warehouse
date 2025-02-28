package com.victor.outbounddirect.ui.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.victor.base.base.MBaseActivity;
import com.victor.base.router.RouterActivityPath;
import com.victor.outbounddirect.BR;
import com.victor.outbounddirect.R;
import com.victor.outbounddirect.databinding.OutbounddirectCategoryBinding;
import com.victor.outbounddirect.ui.viewmodel.OutboundCategoryViewModel;

@Route(path = RouterActivityPath.Outbound.PAGER_OUTBOUND_CATEGORY)
public class OutCategoryActivity extends MBaseActivity<OutbounddirectCategoryBinding, OutboundCategoryViewModel> {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.outbounddirect_category;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}
