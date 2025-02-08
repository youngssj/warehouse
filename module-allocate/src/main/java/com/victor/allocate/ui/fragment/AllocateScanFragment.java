package com.victor.allocate.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.victor.allocate.BR;
import com.victor.allocate.R;
import com.victor.allocate.databinding.AllocateScanDetailBinding;
import com.victor.allocate.databinding.AllocateScanFragmentBinding;
import com.victor.allocate.ui.viewmodel.AllocateScanListViewModel;
import com.victor.base.data.entity.AllocateData;
import com.victor.base.router.RouterFragmentPath;

import me.goldze.mvvmhabit.base.BaseFragment;

@Route(path = RouterFragmentPath.Allocate.PAGER_ALLOCATE_SCAN)
public class AllocateScanFragment extends BaseFragment<AllocateScanFragmentBinding, AllocateScanListViewModel> {

    @Autowired(name = "position")
    int position;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.allocate_scan_fragment;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        viewModel.setPosition(position);
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.showCustomEvent.observe(this, allocateScanItemViewModel -> {
            AllocateScanDetailBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.allocate_scan_detail, null, false);
            binding.setViewModel(allocateScanItemViewModel);
            AllocateData.AllocateMaterial dataListBean = allocateScanItemViewModel.entity.get();
            showCustomDialog(getResources().getString(R.string.workbench_allocate_detail_text), binding, (dialog, which) -> {
            });
        });
    }
}
