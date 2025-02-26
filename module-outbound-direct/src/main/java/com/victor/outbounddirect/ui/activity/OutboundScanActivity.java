package com.victor.outbounddirect.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.victor.base.app.AppViewModelFactory;
import com.victor.base.data.entity.OutboundData;
import com.victor.base.router.RouterActivityPath;
import com.victor.outbounddirect.BR;
import com.victor.outbounddirect.R;
import com.victor.outbounddirect.databinding.OutbounddirectScanActivityBinding;
import com.victor.outbounddirect.databinding.OutbounddirectScanDetailBinding;
import com.victor.outbounddirect.ui.viewmodel.OutboundScanViewModel;
import com.victor.workbench.ui.base.BaseUhfActivity;

import java.util.HashSet;
import java.util.Set;

@Route(path = RouterActivityPath.Outbound.PAGER_OUTBOUND_SCAN)
public class OutboundScanActivity extends BaseUhfActivity<OutbounddirectScanActivityBinding, OutboundScanViewModel> {

    @Autowired(name = "outTheme")
    String outTheme;
    @Autowired(name = "planOutDate")
    String planOutDate;
    @Autowired(name = "remark")
    String remark;

    @Override
    protected void readUhfCallback(Set<String> epcSet) {
        viewModel.updatePDItemModel(epcSet);
    }

    @Override
    protected void scanBarCodeCallback(String barCode) {
        Set<String> set = new HashSet<>();
        set.add(barCode);
        viewModel.updatePDItemModel(set);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.outbounddirect_scan_activity;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public OutboundScanViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return new ViewModelProvider(this, factory).get(OutboundScanViewModel.class);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        viewModel.setBackVisibleObservable(View.VISIBLE);
        viewModel.setPowerVisibleObservable(View.VISIBLE);
        viewModel.setTitleText(getResources().getString(R.string.workbench_outbound_title_text));
        setRead(true);

        viewModel.entity.set(new OutboundData());
        viewModel.entity.get().setOutTheme(outTheme);
        viewModel.entity.get().setPlanOutDate(planOutDate);
        viewModel.entity.get().setRemark(remark);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.showCustomEvent.observe(this, outboundScanItemViewModel -> {
            OutbounddirectScanDetailBinding binding = DataBindingUtil.inflate(LayoutInflater.from(OutboundScanActivity.this), R.layout.outbounddirect_scan_detail, null, false);
            binding.setViewModel(outboundScanItemViewModel);
            showCustomDialog(getResources().getString(R.string.workbench_outbound_detail_text), binding, (dialog, which) -> {
            });
        });
    }
}
