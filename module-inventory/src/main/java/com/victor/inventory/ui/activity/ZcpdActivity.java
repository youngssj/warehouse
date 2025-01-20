package com.victor.inventory.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableList;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.tabs.TabLayout;
import com.victor.base.app.AppViewModelFactory;
import com.victor.base.data.entity.TakeStockDetail;
import com.victor.base.router.RouterActivityPath;
import com.victor.base.utils.Constants;
import com.victor.inventory.BR;
import com.victor.inventory.R;
import com.victor.inventory.databinding.InventoryActivityZcpdBinding;
import com.victor.inventory.databinding.InventoryViewZcpdDetailBinding;
import com.victor.inventory.ui.adapter.ZcpdVpBindingAdapter;
import com.victor.inventory.ui.viewmodel.ZcpdViewModel;
import com.victor.inventory.ui.viewmodel.itemViewmodel.ZcpdVpRvItemViewModel;
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
public class ZcpdActivity extends BaseUhfActivity<InventoryActivityZcpdBinding, ZcpdViewModel> {

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
        Set set = new HashSet();
        ObservableList<ZcpdVpRvItemViewModel> allList = viewModel.items.get(0).observableList;
        for (ZcpdVpRvItemViewModel zcpdVpRvItemViewModel : allList) {
            TakeStockDetail.ElecMaterialListDTO dataListBean = zcpdVpRvItemViewModel.entity.get();
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
        return R.layout.inventory_activity_zcpd;
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
        setRead(true, 30);
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
            InventoryViewZcpdDetailBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.inventory_view_zcpd_detail, null, false);
            binding.setViewModel(vpRvItemViewModel);
            TakeStockDetail.ElecMaterialListDTO dataListBean = vpRvItemViewModel.entity.get();
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
