package com.victor.materials.ui.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.victor.materials.ui.viewmodel.MaterialsQueryViewModel;
import com.victor.base.base.MBaseActivity;
import com.victor.base.router.RouterActivityPath;
import com.victor.materials.BR;
import com.victor.materials.R;
import com.victor.materials.databinding.MaterialsActivityQueryBinding;

@Route(path = RouterActivityPath.Materials.PAGER_MATERIALS_QUERY)
public class MaterialsQueryActivity extends MBaseActivity<MaterialsActivityQueryBinding, MaterialsQueryViewModel> {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.materials_activity_query;
    }

    @Override
    public int initVariableId() {
        return BR.viewmodel;
    }
}
