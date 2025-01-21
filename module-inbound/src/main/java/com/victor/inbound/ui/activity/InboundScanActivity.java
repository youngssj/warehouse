package com.victor.inbound.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.victor.base.app.AppViewModelFactory;
import com.victor.base.base.VPFragmentAdapter;
import com.victor.base.router.RouterActivityPath;
import com.victor.base.router.RouterFragmentPath;
import com.victor.base.utils.Constants;
import com.victor.inbound.BR;
import com.victor.inbound.R;
import com.victor.inbound.databinding.InboundScanActivityBinding;
import com.victor.inbound.ui.viewmodel.InboundScanViewModel;
import com.victor.workbench.ui.base.BaseUhfActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Route(path = RouterActivityPath.Inbound.PAGER_INBOUND_SCAN)
public class InboundScanActivity extends BaseUhfActivity<InboundScanActivityBinding, InboundScanViewModel> {

    private List<Fragment> mFragments;
    private VPFragmentAdapter vpFragmentAdapter;

    private String[] titles;

    private int checkId;

    public void initParam() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            checkId = bundle.getInt(Constants.BUNDLE.KEY, -1);
        }
    }

    @Override
    protected void readUhfCallback(Set<String> epcSet) {
//        viewModel.updatePDItemModel(epcSet);
    }

    @Override
    protected void scanBarCodeCallback(String barCode) {
//        Set set = new HashSet();
//        ObservableList<ZcpdVpRvItemViewModel> allList = viewModel.items.get(0).observableList;
//        for (ZcpdVpRvItemViewModel zcpdVpRvItemViewModel : allList) {
//            TakeStockDetail.ElecMaterialListDTO dataListBean = zcpdVpRvItemViewModel.entity.get();
//            if (barCode.equals(dataListBean.getMaterialCode())) {
//                set.add(dataListBean.getRfidCode());
//                break;
//            }
//        }
//        if (set.size() == 0) {
//            ToastUtils.showShort(R.string.workbench_check_no_this_data_text);
//            return;
//        }
//        viewModel.updatePDItemModel(set);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.inbound_scan_activity;
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
        viewModel.setBackVisibleObservable(View.VISIBLE);
        viewModel.setPowerVisibleObservable(View.VISIBLE);
        viewModel.setTitleText(getResources().getString(R.string.workbench_inbound_title_text));
//        viewModel.getNetData(checkId);
        setRead(true, 30);

        titles = new String[]{"全部", "在库", "待入库", "待出库", "待移库", "待盘点", "待调拨"};
        mFragments = new ArrayList<>();
        if (savedInstanceState == null) {
            mFragments.add((Fragment) ARouter.getInstance().build(RouterFragmentPath.Materials.PAGER_MATERIALS_LIST).navigation());
            mFragments.add((Fragment) ARouter.getInstance().build(RouterFragmentPath.Materials.PAGER_MATERIALS_LIST).navigation());
        } else {
            mFragments.add(getSupportFragmentManager().findFragmentByTag(makeFragmentName(binding.mViewPager.getId(), 0)));
            mFragments.add(getSupportFragmentManager().findFragmentByTag(makeFragmentName(binding.mViewPager.getId(), 1)));
        }
        vpFragmentAdapter = new VPFragmentAdapter(this, mFragments);
        binding.mViewPager.setUserInputEnabled(true);
        binding.mViewPager.setOffscreenPageLimit(mFragments.size());
        binding.mViewPager.setAdapter(vpFragmentAdapter);
        new TabLayoutMediator(binding.tabLayout, binding.mViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int i) {
                tab.setText(titles[i]);
            }
        }).attach();
    }
}
