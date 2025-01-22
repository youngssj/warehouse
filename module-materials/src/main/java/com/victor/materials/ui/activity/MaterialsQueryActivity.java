package com.victor.materials.ui.activity;

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
import com.victor.materials.BR;
import com.victor.materials.R;
import com.victor.materials.databinding.MaterialsActivityQueryBinding;
import com.victor.materials.ui.viewmodel.MaterialsQueryViewModel;
import com.victor.workbench.ui.base.BaseUhfActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Route(path = RouterActivityPath.Materials.PAGER_MATERIALS_QUERY)
public class MaterialsQueryActivity extends BaseUhfActivity<MaterialsActivityQueryBinding, MaterialsQueryViewModel> {

    private List<Fragment> mFragments;
    private VPFragmentAdapter vpFragmentAdapter;

    private String[] titles;

    @Override
    protected void readUhfCallback(Set<String> epcSet) {
        if (epcSet != null && epcSet.size() > 0) {
            Iterator<String> iterator = epcSet.iterator();
            String firstValue = iterator.next();
            viewModel.updateRfid(firstValue);
        }
    }

    @Override
    protected void scanBarCodeCallback(String barCode) {

    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.materials_activity_query;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public MaterialsQueryViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return new ViewModelProvider(this, factory).get(MaterialsQueryViewModel.class);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        viewModel.setBackVisibleObservable(View.VISIBLE);
        viewModel.setTitleText(getResources().getString(R.string.workbench_title1_text));
        titles = new String[]{"全部", "在库", "待入库", "待出库", "待移库", "待盘点", "待调拨"};

        setRead(true, true);

        mFragments = new ArrayList<>();
        if (savedInstanceState == null) {
            mFragments.add((Fragment) ARouter.getInstance().build(RouterFragmentPath.Materials.PAGER_MATERIALS_LIST).withString("materialStatus", null).navigation());
            mFragments.add((Fragment) ARouter.getInstance().build(RouterFragmentPath.Materials.PAGER_MATERIALS_LIST).withString("materialStatus", "1").navigation());
            mFragments.add((Fragment) ARouter.getInstance().build(RouterFragmentPath.Materials.PAGER_MATERIALS_LIST).withString("materialStatus", "11").navigation());
            mFragments.add((Fragment) ARouter.getInstance().build(RouterFragmentPath.Materials.PAGER_MATERIALS_LIST).withString("materialStatus", "12").navigation());
            mFragments.add((Fragment) ARouter.getInstance().build(RouterFragmentPath.Materials.PAGER_MATERIALS_LIST).withString("materialStatus", "14").navigation());
            mFragments.add((Fragment) ARouter.getInstance().build(RouterFragmentPath.Materials.PAGER_MATERIALS_LIST).withString("materialStatus", "13").navigation());
            mFragments.add((Fragment) ARouter.getInstance().build(RouterFragmentPath.Materials.PAGER_MATERIALS_LIST).withString("materialStatus", "15").navigation());
        } else {
            mFragments.add(getSupportFragmentManager().findFragmentByTag(makeFragmentName(binding.mViewPager.getId(), 0)));
            mFragments.add(getSupportFragmentManager().findFragmentByTag(makeFragmentName(binding.mViewPager.getId(), 1)));
            mFragments.add(getSupportFragmentManager().findFragmentByTag(makeFragmentName(binding.mViewPager.getId(), 2)));
            mFragments.add(getSupportFragmentManager().findFragmentByTag(makeFragmentName(binding.mViewPager.getId(), 3)));
            mFragments.add(getSupportFragmentManager().findFragmentByTag(makeFragmentName(binding.mViewPager.getId(), 4)));
            mFragments.add(getSupportFragmentManager().findFragmentByTag(makeFragmentName(binding.mViewPager.getId(), 5)));
            mFragments.add(getSupportFragmentManager().findFragmentByTag(makeFragmentName(binding.mViewPager.getId(), 6)));
        }
        vpFragmentAdapter = new VPFragmentAdapter(this, mFragments);
        binding.mViewPager.setUserInputEnabled(false);
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
