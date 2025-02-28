package com.victor.inbounddirect.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.victor.base.base.MBaseActivity;
import com.victor.base.router.RouterActivityPath;
import com.victor.inbounddirect.BR;
import com.victor.inbounddirect.R;
import com.victor.inbounddirect.databinding.InbounddirectCategoryBinding;
import com.victor.inbounddirect.ui.viewmodel.InboundCategoryViewModel;

@Route(path = RouterActivityPath.Inbound.PAGER_INBOUND_CATEGORY)
public class InboundCategoryActivity extends MBaseActivity<InbounddirectCategoryBinding, InboundCategoryViewModel> {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.inbounddirect_category;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        viewModel.setBackVisibleObservable(View.VISIBLE);
        viewModel.setPowerVisibleObservable(View.GONE);
        viewModel.setTitleText(getResources().getString(R.string.workbench_inbound_type_text));
    }
}
