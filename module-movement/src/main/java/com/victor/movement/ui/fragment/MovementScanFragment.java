package com.victor.movement.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.victor.base.data.entity.MovementData;
import com.victor.base.router.RouterFragmentPath;
import com.victor.movement.BR;
import com.victor.movement.R;
import com.victor.movement.databinding.MovementScanDetailBinding;
import com.victor.movement.databinding.MovementScanFragmentBinding;
import com.victor.movement.ui.viewmodel.MovementScanListViewModel;

import me.goldze.mvvmhabit.base.BaseFragment;

@Route(path = RouterFragmentPath.Movement.PAGER_MOVEMENT_SCAN)
public class MovementScanFragment extends BaseFragment<MovementScanFragmentBinding, MovementScanListViewModel> {

    @Autowired(name = "position")
    int position;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.movement_scan_fragment;
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
        viewModel.uc.showCustomEvent.observe(this, movementScanItemViewModel -> {
            MovementScanDetailBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.movement_scan_detail, null, false);
            binding.setViewModel(movementScanItemViewModel);
            MovementData.MovementElecMaterial dataListBean = movementScanItemViewModel.entity.get();
            showCustomDialog(getResources().getString(R.string.workbench_movement_detail_text), binding, (dialog, which) -> {
            });
        });
    }
}
