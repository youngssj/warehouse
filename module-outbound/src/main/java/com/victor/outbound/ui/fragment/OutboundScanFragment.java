package com.victor.outbound.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.victor.base.data.entity.InboundDetail;
import com.victor.base.router.RouterFragmentPath;
import com.victor.outbound.R;
import com.victor.outbound.databinding.OutboundScanDetailBinding;
import com.victor.outbound.databinding.OutboundScanFragmentBinding;
import com.victor.outbound.ui.viewmodel.OutboundScanListViewModel;

import cn.bingoogolapple.refreshlayout.BR;
import me.goldze.mvvmhabit.base.BaseFragment;

@Route(path = RouterFragmentPath.Outbound.PAGER_OUTBOUND_SCAN)
public class OutboundScanFragment extends BaseFragment<OutboundScanFragmentBinding, OutboundScanListViewModel> {

    @Autowired(name = "position")
    int position;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.outbound_scan_fragment;
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
        viewModel.uc.showCustomEvent.observe(this, outboundScanItemViewModel -> {
            OutboundScanDetailBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.outbound_scan_detail, null, false);
            binding.setViewModel(outboundScanItemViewModel);
            InboundDetail.ElecMaterialList dataListBean = outboundScanItemViewModel.entity.get();
            showCustomDialog(getResources().getString(R.string.workbench_outbound_detail_text), binding, (dialog, which) -> {
            });
        });
    }
}
