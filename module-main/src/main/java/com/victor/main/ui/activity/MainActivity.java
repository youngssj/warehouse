package com.victor.main.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.victor.base.router.RouterActivityPath;
import com.victor.base.router.RouterFragmentPath;
import com.victor.main.BR;
import com.victor.main.R;
import com.victor.main.databinding.MainActivityMainBinding;
import com.victor.main.ui.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.AppManager;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.ToastUtils;

@Route(path = RouterActivityPath.Main.PAGER_MAIN)
public class MainActivity extends BaseActivity<MainActivityMainBinding, MainViewModel> {
    private List<Fragment> mFragments;
    private VPFragmentAdapter vpFragmentAdapter;
    private int currentIndex = 0;
    private static boolean isExit;//标示是否退出

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.main_activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
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

        vpFragmentAdapter = new VPFragmentAdapter(this, mFragments);
        binding.mViewPager.setUserInputEnabled(true);
        binding.mViewPager.setOffscreenPageLimit(mFragments.size());
        binding.mViewPager.setAdapter(vpFragmentAdapter);
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.radioCheckedEvent.observe(this, checkedId -> {
            if (checkedId == R.id.rbHome) {
                currentIndex = 0;
            } else if (checkedId == R.id.rbWorkBench) {
                currentIndex = 1;
            } else if (checkedId == R.id.rbMine) {
                currentIndex = 2;
            }
            selectPager(currentIndex, true);
        });

        viewModel.uc.pageSelectedEvent.observe(this, position -> {
            if (position == 0) {
                binding.rbHome.setChecked(true);
            } else if (position == 1) {
                binding.rbWorkBench.setChecked(true);
            } else if (position == 2) {
                binding.rbMine.setChecked(true);
            }
        });
    }

    private void selectPager(int position, boolean smoothScroll) {
        if (position >= 0 && position <= binding.mViewPager.getAdapter().getItemCount()) {
            binding.mViewPager.setCurrentItem(position, smoothScroll);
        }
    }

    static class VPFragmentAdapter extends FragmentStateAdapter {

        private List<Fragment> mListFragments = null;

        public VPFragmentAdapter(FragmentActivity fm, List<Fragment> fragments) {
            super(fm);
            mListFragments = fragments;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (mListFragments != null && position > -1 && position < mListFragments.size()) {
                return mListFragments.get(position);
            } else {
                return null;
            }
        }

        @Override
        public int getItemCount() {
            return null != mListFragments ? mListFragments.size() : 0;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    /**
     * 连按两次退出程序
     */
    public void exit() {
        if (!isExit) {
            isExit = true;
            ToastUtils.showShort(R.string.main_exit_hint);
            MyHandler myHandler = new MyHandler();
            myHandler.sendEmptyMessageDelayed(100, 2000);
        } else {
            AppManager.getAppManager().AppExit();
        }
    }

    private static class MyHandler extends Handler {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case 100:
                    isExit = false;
                    break;
                default:
                    break;
            }
        }
    }
}
