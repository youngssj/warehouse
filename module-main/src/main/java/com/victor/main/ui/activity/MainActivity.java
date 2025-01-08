package com.victor.main.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.victor.base.app.AppViewModelFactory;
import com.victor.base.router.RouterActivityPath;
import com.victor.base.router.RouterFragmentPath;
import com.victor.main.BR;
import com.victor.main.R;
import com.victor.main.databinding.MainActivityMainBinding;
import com.victor.main.ui.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;

@Route(path = RouterActivityPath.Main.PAGER_MAIN)
public class MainActivity extends BaseActivity<MainActivityMainBinding, MainViewModel> {
    private List<Fragment> mFragments;
    private VPFragmentAdapter vpFragmentAdapter;
    private int currentIndex = 0;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.main_activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public MainViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return new ViewModelProvider(this, factory).get(MainViewModel.class);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        binding.rbHome.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding.rbWorkBench.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding.rbMine.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));

        mFragments = new ArrayList<>();
        if (savedInstanceState == null) {
            mFragments.add((Fragment) ARouter.getInstance().build(RouterFragmentPath.Home.PAGER_HOME).navigation());
            mFragments.add((Fragment) ARouter.getInstance().build(RouterFragmentPath.WorkBench.PAGER_WorkBench).navigation());
            mFragments.add((Fragment) ARouter.getInstance().build(RouterFragmentPath.User.PAGER_MINE).navigation());
        } else {
            mFragments.add(getSupportFragmentManager().findFragmentByTag(makeFragmentName(binding.mViewPager.getId(), 0)));
            mFragments.add(getSupportFragmentManager().findFragmentByTag(makeFragmentName(binding.mViewPager.getId(), 1)));
            mFragments.add(getSupportFragmentManager().findFragmentByTag(makeFragmentName(binding.mViewPager.getId(), 2)));
        }

        vpFragmentAdapter = new VPFragmentAdapter(getSupportFragmentManager(), mFragments);
        binding.mViewPager.setEnableScroll(true);
        binding.mViewPager.setOffscreenPageLimit(mFragments.size());
        binding.mViewPager.setAdapter(vpFragmentAdapter);
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.checkedEvent.observe(this, index -> {
            if (index == R.id.rbHome) {
                currentIndex = 0;
            } else if (index == R.id.rbWorkBench) {
                currentIndex = 1;
            } else if (index == R.id.rbMine) {
                currentIndex = 2;
            }
            selectPager(currentIndex, true);
        });
    }

    private void selectPager(int index, boolean smoothScroll) {
        if (index >= 0 && index <= binding.mViewPager.getAdapter().getCount()) {
            binding.mViewPager.setCurrentItem(index, smoothScroll);
        }
    }

    class VPFragmentAdapter extends FragmentPagerAdapter {

        private List<Fragment> mListFragments = null;
        private Fragment mCurrentFragment;

        public VPFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            mListFragments = fragments;
        }

        @Override
        public int getCount() {
            return null != mListFragments ? mListFragments.size() : 0;
        }

        @Override
        public Fragment getItem(int index) {
            if (mListFragments != null && index > -1 && index < mListFragments.size()) {
                return mListFragments.get(index);
            } else {
                return null;
            }
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            mCurrentFragment = (Fragment) object;

            if (position == 0) {
                binding.rbHome.setBackgroundColor(getResources().getColor(R.color.color_FFAA00));
                binding.rbWorkBench.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.rbMine.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            } else if (position == 1) {
                binding.rbHome.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.rbWorkBench.setBackgroundColor(getResources().getColor(R.color.color_FFAA00));
                binding.rbMine.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            } else if (position == 2) {
                binding.rbHome.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.rbWorkBench.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.rbMine.setBackgroundColor(getResources().getColor(R.color.color_FFAA00));
            }
            super.setPrimaryItem(container, position, object);
        }

        public Fragment getCurrentFragment() {
            return mCurrentFragment;
        }
    }
}
