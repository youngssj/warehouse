package com.victor.materials.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.victor.base.data.entity.TakeStockDetail;
import com.victor.base.router.RouterFragmentPath;
import com.victor.materials.BR;
import com.victor.materials.R;
import com.victor.materials.databinding.MaterialsFragmentBinding;
import com.victor.materials.databinding.MaterialsItemDetailBinding;
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

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.showCustomEvent.observe(this, materialsItemViewModel -> {
            MaterialsItemDetailBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.materials_item_detail, null, false);
            binding.setViewModel(materialsItemViewModel);
            showCustomDialog(getResources().getString(R.string.workbench_materials_detail_text), binding, (dialog, which) -> {
            });
        });
    }
}
