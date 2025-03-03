package com.victor.inbounddirect.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.victor.base.app.AppRequestCode;
import com.victor.base.app.AppViewModelFactory;
import com.victor.base.data.entity.InboundData;
import com.victor.base.data.entity.LocationBean;
import com.victor.base.data.entity.OperateCategory;
import com.victor.base.router.RouterActivityPath;
import com.victor.base.utils.PopUtils;
import com.victor.inbounddirect.BR;
import com.victor.inbounddirect.R;
import com.victor.inbounddirect.databinding.InbounddirectScanActivityBinding;
import com.victor.inbounddirect.databinding.InbounddirectScanDetailBinding;
import com.victor.inbounddirect.ui.viewmodel.InboundScanViewModel;
import com.victor.workbench.ui.base.BaseUhfActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Route(path = RouterActivityPath.Inbound.PAGER_INBOUND_SCAN)
public class InboundScanActivity extends BaseUhfActivity<InbounddirectScanActivityBinding, InboundScanViewModel> {

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

        InboundData inboundData = new InboundData();
        inboundData.setElecMaterialList(new ArrayList<>());
        viewModel.entity.set(inboundData);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppRequestCode.REQUEST_CODE_SCAN_LOCATION && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                LocationBean locationBean = (LocationBean) data.getParcelableExtra("locationBean");
                viewModel.setLocation(locationBean);
            }
        }
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
        viewModel.uc.selectLocationEvent.observe(this, inboundScanItemViewModel -> {
            ARouter.getInstance().build(RouterActivityPath.WorkBench.PAGER_SCAN_LOCATION)
                    .navigation(this, AppRequestCode.REQUEST_CODE_SCAN_LOCATION);
        });
        viewModel.uc.selectCategoryEvent.observe(this, operateCategories -> {
            List<String> items = new ArrayList<>();
            for (OperateCategory operateCategory : operateCategories) {
                items.add(operateCategory.getCategoryName());
            }

            String defaultItem = "";
            OperateCategory operateCategory = viewModel.category.get();
            if (operateCategory != null) {
                defaultItem = operateCategory.getCategoryName();
            }
            new PopUtils().showBottomPops(this, items, defaultItem, binding.rootView, binding.rootView, new PopUtils.OnPopItemClickListener() {
                @Override
                public boolean onItemClick(String item, int position) {
                    OperateCategory operateCategory = operateCategories.get(position);
                    viewModel.category.set(operateCategory);
                    viewModel.entity.get().setInCategory(operateCategory.getCategoryId());
                    return true;
                }
            });
        });
    }
}
