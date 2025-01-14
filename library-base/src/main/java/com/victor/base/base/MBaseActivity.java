package com.victor.base.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.ViewDataBinding;

import com.alibaba.android.arouter.launcher.ARouter;
import com.victor.base.router.RouterActivityPath;

import me.goldze.mvvmhabit.base.AppStatusManager;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ContainerActivity;
import me.goldze.mvvmhabit.utils.KLog;

public abstract class MBaseActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends BaseActivity<V, VM> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        KLog.d("getAppStatus::" + AppStatusManager.getInstance().getAppStatus() + "..." + this.getClass().getSimpleName());
        if ("LoginActivity".equals(this.getClass().getSimpleName())) {
            AppStatusManager.getInstance().setAppStatus(AppStatusManager.AppStatusConstant.APP_NORMAL);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean restartApp() {
//        Log.d("getAppStatus::" + AppStatusManager.getInstance().getAppStatus());
        if (AppStatusManager.getInstance().getAppStatus() == AppStatusManager.AppStatusConstant.APP_FORCE_KILLED) {
            //应用启动入口SplashActivity，走重启流程
            if (!"LoginActivity".equals(getClass().getSimpleName())) {
                KLog.d("getAppStatus::main");
                AppStatusManager.getInstance().setAppStatus(AppStatusManager.AppStatusConstant.ACTION_BACK_TO_HOME);
                ARouter.getInstance().build(RouterActivityPath.Sign.PAGER_LOGIN)
                        .withInt(AppStatusManager.AppStatusConstant.KEY_HOME_ACTION, AppStatusManager.AppStatusConstant.ACTION_RESTART_APP)
                        .navigation();
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     * @param bundle        跳转所携带的信息
     */
    public void startContainerActivity(String canonicalName, Bundle bundle) {
        Intent intent = new Intent(this, ContainerActivity.class);
        intent.putExtra(ContainerActivity.FRAGMENT, canonicalName);
        if (bundle != null) {
            intent.putExtra(ContainerActivity.BUNDLE, bundle);
        }
        startActivity(intent);
    }
}
