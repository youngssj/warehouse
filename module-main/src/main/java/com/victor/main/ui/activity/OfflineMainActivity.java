package com.victor.main.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.victor.base.app.AppViewModelFactory;
import com.victor.base.router.RouterActivityPath;
import com.victor.base.router.RouterFragmentPath;
import com.victor.main.R;
import com.victor.main.databinding.MainActivityOfflineMainBinding;
import com.victor.main.ui.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouterActivityPath.Main.PAGER_OFFLINE_MAIN)
public class OfflineMainActivity extends MainActivity<MainActivityOfflineMainBinding, MainViewModel> {

    private List<Fragment> mFragments;
    private VPFragmentAdapter vpFragmentAdapter;
    private int currentIndex = 0;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.main_activity_offline_main;
    }

    @Override
    public MainViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return new ViewModelProvider(this, factory).get(MainViewModel.class);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        binding.rbWorkBench.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding.rbSync.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));

        mFragments = new ArrayList<>();
        if (savedInstanceState == null) {
            mFragments.add((Fragment) ARouter.getInstance().build(RouterFragmentPath.WorkBench.PAGER_WorkBench).navigation());
            mFragments.add((Fragment) ARouter.getInstance().build(RouterFragmentPath.Sync.PAGER_SYNC).navigation());
        } else {
            mFragments.add(getSupportFragmentManager().findFragmentByTag(makeFragmentName(binding.mViewPager.getId(), 0)));
            mFragments.add(getSupportFragmentManager().findFragmentByTag(makeFragmentName(binding.mViewPager.getId(), 1)));
        }

        vpFragmentAdapter = new VPFragmentAdapter(this, mFragments);
        binding.mViewPager.setUserInputEnabled(true);
        binding.mViewPager.setOffscreenPageLimit(mFragments.size());
        binding.mViewPager.setAdapter(vpFragmentAdapter);
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.radioCheckedEvent.observe(this, checkedId -> {
            if (checkedId == R.id.rbWorkBench) {
                currentIndex = 0;
            } else if (checkedId == R.id.rbSync) {
                currentIndex = 1;
            }
            selectPager(binding.mViewPager, currentIndex, true);
        });

        viewModel.uc.pageSelectedEvent.observe(this, position -> {
            if (position == 0) {
                binding.rbWorkBench.setChecked(true);
            } else if (position == 1) {
                binding.rbSync.setChecked(true);
            }
        });
    }
}
