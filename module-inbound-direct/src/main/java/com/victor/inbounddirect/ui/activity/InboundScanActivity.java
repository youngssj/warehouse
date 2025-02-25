package com.victor.inbounddirect.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.victor.base.app.AppViewModelFactory;
import com.victor.base.data.entity.InboundData;
import com.victor.base.data.entity.MaterialBean;
import com.victor.base.router.RouterActivityPath;
import com.victor.inbounddirect.BR;
import com.victor.inbounddirect.R;
import com.victor.inbounddirect.databinding.InbounddirectScanActivityBinding;
import com.victor.inbounddirect.databinding.InbounddirectScanDetailBinding;
import com.victor.inbounddirect.ui.viewmodel.InboundScanViewModel;
import com.victor.workbench.ui.base.BaseUhfActivity;

import java.util.HashSet;
import java.util.Set;

import me.goldze.mvvmhabit.utils.ToastUtils;

@Route(path = RouterActivityPath.Inbound.PAGER_INBOUND_SCAN)
public class InboundScanActivity extends BaseUhfActivity<InbounddirectScanActivityBinding, InboundScanViewModel> {

    @Autowired(name = "inTheme")
    String inTheme;
    @Autowired(name = "planInDate")
    String planInDate;
    @Autowired(name = "remark")
    String remark;

    @Override
    protected void readUhfCallback(Set<String> epcSet) {
        viewModel.updatePDItemModel(epcSet);
    }

    @Override
    protected void scanBarCodeCallback(String barCode) {
        Set set = new HashSet();
        for (InboundData.InboundElecMaterial dataListBean : viewModel.entity.get().getElecMaterialList()) {
            if (barCode.equals(dataListBean.getMaterialCode())) {
                set.add(dataListBean.getRfidCode());
                break;
            }
        }
        if (set.size() == 0) {
            ToastUtils.showShort(R.string.workbench_check_no_this_data_text);
            return;
        }
        viewModel.updatePDItemModel(set);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.inbounddirect_scan_activity;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public InboundScanViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return new ViewModelProvider(this, factory).get(InboundScanViewModel.class);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        viewModel.setBackVisibleObservable(View.VISIBLE);
        viewModel.setPowerVisibleObservable(View.VISIBLE);
        viewModel.setTitleText(getResources().getString(R.string.workbench_inbound_title_text));
        setRead(true);

        viewModel.entity.set(new InboundData());
        viewModel.entity.get().setInTheme(inTheme);
        viewModel.entity.get().setPlanInDate(planInDate);
        viewModel.entity.get().setRemark(remark);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.showCustomEvent.observe(this, inboundScanItemViewModel -> {
            InbounddirectScanDetailBinding binding = DataBindingUtil.inflate(LayoutInflater.from(InboundScanActivity.this), R.layout.inbounddirect_scan_detail, null, false);
            binding.setViewModel(inboundScanItemViewModel);
            showCustomDialog(getResources().getString(R.string.workbench_inbound_detail_text), binding, (dialog, which) -> {
            });
        });
    }
}
