package com.victor.main.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;

import androidx.databinding.ViewDataBinding;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.launcher.ARouter;
import com.victor.base.base.MBaseActivity;
import com.victor.base.router.RouterActivityPath;
import com.victor.base.utils.Constants;
import com.victor.main.BR;
import com.victor.main.R;
import com.victor.main.ui.viewmodel.MainViewModel;

import me.goldze.mvvmhabit.base.AppManager;
import me.goldze.mvvmhabit.base.AppStatusManager;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public abstract class MainActivity<V extends ViewDataBinding, VM extends MainViewModel> extends MBaseActivity<V, VM> {

    private static boolean isExit;//标示是否退出

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int action = intent.getIntExtra(AppStatusManager.AppStatusConstant.KEY_HOME_ACTION, AppStatusManager.AppStatusConstant.ACTION_BACK_TO_HOME);
        switch (action) {
            case AppStatusManager.AppStatusConstant.ACTION_RESTART_APP:
                restartApplication();
                break;
        }
    }

    public void restartApplication() {
        SPUtils.getInstance().put(Constants.SP.TOKEN, "");
        ARouter.getInstance().build(RouterActivityPath.Sign.PAGER_LOGIN)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .navigation();
        finish();
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    public void selectPager(ViewPager2 viewPager, int position, boolean smoothScroll) {
        if (position >= 0 && position <= viewPager.getAdapter().getItemCount()) {
            viewPager.setCurrentItem(position, smoothScroll);
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
