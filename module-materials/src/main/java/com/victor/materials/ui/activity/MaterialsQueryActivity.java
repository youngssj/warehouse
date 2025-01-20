package com.victor.materials.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.victor.base.app.AppViewModelFactory;
import com.victor.base.router.RouterActivityPath;
import com.victor.materials.BR;
import com.victor.materials.R;
import com.victor.materials.databinding.MaterialsActivityQueryBinding;
import com.victor.materials.ui.viewmodel.MaterialsQueryViewModel;
import com.victor.workbench.ui.base.BaseUhfActivity;

import java.util.Set;

@Route(path = RouterActivityPath.Materials.PAGER_MATERIALS_QUERY)
public class MaterialsQueryActivity extends BaseUhfActivity<MaterialsActivityQueryBinding, MaterialsQueryViewModel> {
    @Override
    protected void readUhfCallback(Set<String> epcSet) {

    }

    @Override
    protected void scanBarCodeCallback(String barCode) {

    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.materials_activity_query;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public MaterialsQueryViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return new ViewModelProvider(this, factory).get(MaterialsQueryViewModel.class);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        viewModel.setBackVisibleObservable(View.VISIBLE);
        viewModel.setTitleText(getResources().getString(R.string.workbench_title1_text));
    }
}
