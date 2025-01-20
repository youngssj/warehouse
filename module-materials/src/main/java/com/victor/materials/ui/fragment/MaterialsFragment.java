package com.victor.materials.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.victor.base.router.RouterFragmentPath;
import com.victor.materials.BR;
import com.victor.materials.R;
import com.victor.materials.databinding.MaterialsFragmentBinding;
import com.victor.materials.ui.viewmodel.MaterialsViewModel;

import me.goldze.mvvmhabit.base.BaseFragment;

@Route(path = RouterFragmentPath.Materials.PAGER_MATERIALS_LIST)
public class MaterialsFragment extends BaseFragment<MaterialsFragmentBinding, MaterialsViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.materials_fragment;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}
