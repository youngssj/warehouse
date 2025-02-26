package com.victor.inventorydirect.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableList;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.tabs.TabLayout;
import com.victor.base.app.AppViewModelFactory;
import com.victor.base.data.entity.InventoryData;
import com.victor.base.router.RouterActivityPath;
import com.victor.base.utils.Constants;
import com.victor.inventorydirect.BR;
import com.victor.inventorydirect.R;
import com.victor.inventorydirect.databinding.InventorydirectActivityZcpdBinding;
import com.victor.inventorydirect.databinding.InventorydirectViewZcpdDetailBinding;
import com.victor.inventorydirect.ui.adapter.ZcpdVpBindingAdapter;
import com.victor.inventorydirect.ui.viewmodel.ZcpdViewModel;
import com.victor.inventorydirect.ui.viewmodel.itemviewmodel.ZcpdVpRvItemViewModel;
import com.victor.workbench.ui.base.BaseUhfActivity;

import java.util.HashSet;
import java.util.Set;

import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/9/16
 * 邮箱：jxfengmtx@gmail.com
 */
@Route(path = RouterActivityPath.Inventory.PAGER_INVENTORY_CHECK)
public class ZcpdActivity extends BaseUhfActivity<InventorydirectActivityZcpdBinding, ZcpdViewModel> {

    private int checkId;

    public void initParam() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            checkId = bundle.getInt(Constants.BUNDLE.KEY, -1);
        }
    }

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
        return R.layout.inventorydirect_activity_zcpd;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        viewModel.setBackVisibleObservable(View.VISIBLE);
        viewModel.setPowerVisibleObservable(View.VISIBLE);
        viewModel.setTitleText(getResources().getString(R.string.workbench_check_title_text));
        viewModel.getNetData(checkId);
        setRead(true);
        //设置Adapter
        binding.setAdapter(new ZcpdVpBindingAdapter());
        binding.viewPager.setOffscreenPageLimit(1);
        binding.tabs.setupWithViewPager(binding.viewPager);
        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabs));
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.itemClickEvent.observe(this, text -> {
            ToastUtils.showShort("position：" + text);
        });
        viewModel.uc.pageSelectEvent.observe(this, pageIndex -> {
            binding.tabs.getTabAt(pageIndex).select();
        });
        viewModel.uc.scanFinishEvent.observe(this, aBoolean -> {
            setReadFinish(aBoolean);
        });

        viewModel.uc.showCustomEvent.observe(this, vpRvItemViewModel -> {
            InventorydirectViewZcpdDetailBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.inventorydirect_view_zcpd_detail, null, false);
            binding.setViewModel(vpRvItemViewModel);
            InventoryData.InventoryElecMaterial dataListBean = vpRvItemViewModel.entity.get();
            showCustomDialog(getResources().getString(R.string.workbench_check_detail_text), binding, (dialog, which) -> {
            });
        });
    }

    @Override
    public ZcpdViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return new ViewModelProvider(this, factory).get(ZcpdViewModel.class);
    }

}
