package com.victor.warehouse;

import android.content.Context;

import androidx.multidex.MultiDex;

import com.example.hiu_931.SoundManager;
import com.victor.base.config.ModuleLifecycleConfig;
import com.victor.base.utils.SystemUtil;
import com.victor.main.ui.activity.LoginActivity;

import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import me.goldze.mvvmhabit.BuildConfig;
import me.goldze.mvvmhabit.base.BaseApplication;
import me.goldze.mvvmhabit.crash.CaocConfig;
import me.goldze.mvvmhabit.utils.KLog;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.unit.Subunits;

/**
 * Created by goldze on 2018/6/21 0021.
 */

public class AppApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化组件(靠前)
        ModuleLifecycleConfig.getInstance().initModuleAhead(this);
        //....
        //初始化组件(靠后)
        ModuleLifecycleConfig.getInstance().initModuleLow(this);

        //是否开启打印日志
        KLog.init(BuildConfig.DEBUG);
        SystemUtil.getSystemModel();
        com.hiultra.c72.SoundUtil.initSoundPool(this);//Init sound pool
        com.hiultra.hiu_961.SoundUtil.initSoundPool(this);//Init sound pool
        SoundManager.initSound(this);;//Init sound pool
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace(); // 这里处理所有的RxJava异常
            }
        });

        //配置全局异常崩溃操作
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
                .enabled(true) //是否启动全局异常捕获
                .showErrorDetails(true) //是否显示错误详细信息
                .showRestartButton(true) //是否显示重启按钮
                .trackActivities(true) //是否跟踪Activity
                .minTimeBetweenCrashesMs(2000) //崩溃的间隔时间(毫秒)
                .errorDrawable(R.mipmap.logo) //错误图标
                .restartActivity(LoginActivity.class) //重新启动后的activity
//                .errorActivity(YourCustomErrorActivity.class) //崩溃后的错误activity
//                .eventListener(new YourCustomEventListener()) //崩溃后的错误监听
                .apply();

        AutoSizeConfig.getInstance().getUnitsManager()
                .setSupportDP(false)
                .setSupportSP(false)
                .setSupportSubunits(Subunits.MM);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}
