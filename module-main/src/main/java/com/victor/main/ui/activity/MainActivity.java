package com.victor.main.ui.activity;

import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.victor.main.BR;
import com.victor.main.R;
import com.victor.main.ui.viewmodel.MainViewModel;

import java.util.List;

import me.goldze.mvvmhabit.base.AppManager;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.ToastUtils;

public abstract class MainActivity<V extends ViewDataBinding, VM extends MainViewModel> extends BaseActivity<V, VM> {

    private static boolean isExit;//标示是否退出

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    public void selectPager(ViewPager2 viewPager, int position, boolean smoothScroll) {
        if (position >= 0 && position <= viewPager.getAdapter().getItemCount()) {
            viewPager.setCurrentItem(position, smoothScroll);
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
