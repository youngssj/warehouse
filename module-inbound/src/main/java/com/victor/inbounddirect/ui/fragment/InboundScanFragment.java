package com.victor.inbounddirect.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.victor.base.data.entity.InboundData;
import com.victor.base.router.RouterFragmentPath;
import com.victor.inbounddirect.BR;
import com.victor.inbounddirect.R;
import com.victor.inbounddirect.databinding.InboundScanDetailBinding;
import com.victor.inbounddirect.databinding.InboundScanFragmentBinding;
import com.victor.inbounddirect.ui.viewmodel.InboundScanListViewModel;

import me.goldze.mvvmhabit.base.BaseFragment;

@Route(path = RouterFragmentPath.Inbound.PAGER_INBOUND_SCAN)
public class InboundScanFragment extends BaseFragment<InboundScanFragmentBinding, InboundScanListViewModel> {

    @Autowired(name = "position")
    int position;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.inbound_scan_fragment;
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
        viewModel.uc.showCustomEvent.observe(this, inboundScanItemViewModel -> {
            InboundScanDetailBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.inbound_scan_detail, null, false);
            binding.setViewModel(inboundScanItemViewModel);
            InboundData.InboundElecMaterial dataListBean = inboundScanItemViewModel.entity.get();
            showCustomDialog(getResources().getString(R.string.workbench_inbound_detail_text), binding, (dialog, which) -> {
            });
        });
    }
}
