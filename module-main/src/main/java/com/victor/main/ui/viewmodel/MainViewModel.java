package com.victor.main.ui.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.alibaba.android.arouter.launcher.ARouter;
import com.victor.base.data.Repository.AppRepository;
import com.victor.base.data.entity.UserInfoBean;
import com.victor.base.data.http.ApiDisposableObserver;
import com.victor.base.event.Event;
import com.victor.base.event.MessageEvent;
import com.victor.base.event.MessageType;
import com.victor.base.router.RouterActivityPath;
import com.victor.base.utils.Constants;
import com.victor.main.BuildConfig;
import com.victor.main.R;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.AppManager;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class MainViewModel extends BaseViewModel<AppRepository> {

    public ObservableField<String> appVersion = new ObservableField<>("");
    public ObservableField<String> hello = new ObservableField<>("");

    public class UIChangeObservable {
        //密码开关观察者
        public SingleLiveEvent<Integer> radioCheckedEvent = new SingleLiveEvent<>();
        public SingleLiveEvent<Integer> pageSelectedEvent = new SingleLiveEvent<>();
    }

    public UIChangeObservable uc = new UIChangeObservable();

    public MainViewModel(@NonNull Application application, AppRepository model) {
        super(application, model);
        appVersion.set(getApplication().getResources().getString(R.string.main_version_text) + BuildConfig.VERSIONNAME);
    }

    public BindingCommand<Integer> onCheckedChangedCommand = new BindingCommand<>(new BindingConsumer<Integer>() {
        @Override
        public void call(Integer checkedId) {
            uc.radioCheckedEvent.setValue(checkedId);
        }
    });

    public BindingCommand<Integer> onPageSelectedCommand = new BindingCommand<>(new BindingConsumer<Integer>() {
        @Override
        public void call(Integer selected) {
            uc.pageSelectedEvent.setValue(selected);
        }
    });

    @Override
    public void onCreate() {
        super.onCreate();
        // 获取用户信息
        getUserInfo();
    }

    private void getUserInfo() {
        model.userInfo()
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiDisposableObserver<UserInfoBean>() {
                    @Override
                    public void onResult(UserInfoBean userInfoBean) {
                        KLog.i(userInfoBean.toString());
                        hello.set(getApplication().getResources().getString(R.string.main_hello_text)
                                + (TextUtils.isEmpty(userInfoBean.getNickName()) ? userInfoBean.getUserName() : userInfoBean.getNickName()));
                    }

                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }
                });
    }

    //注册RxBus
    @Override
    public void registerRxBus() {
        super.registerRxBus();
        Disposable mSubscription = RxBus.getDefault().toObservable(Event.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(event -> {
                    if (event instanceof MessageEvent) {
                        switch (((MessageEvent<?>) event).getMessageType()) {
                            case MessageType.EVENT_TYPE_TOKEN_INVALID:
                                //无效的Token，提示跳入登录页
                                ToastUtils.showShort(getApplication().getResources().getString(R.string.app_token_invalid));
                                //关闭所有页面
                                AppManager.getAppManager().finishAllActivity();
                                SPUtils.getInstance().put(Constants.SP.TOKEN, "");
                                //跳入登录界面
                                ARouter.getInstance().build(RouterActivityPath.Sign.PAGER_LOGIN).navigation();
                                break;
                            case MessageType.EVENT_TYPE_USER_MESSAGE:
                                UserInfoBean userInfoBean = ((MessageEvent<UserInfoBean>) event).getData();
                                hello.set(getApplication().getResources().getString(R.string.main_hello_text)
                                        + (TextUtils.isEmpty(userInfoBean.getNickName()) ? userInfoBean.getUserName() : userInfoBean.getNickName()));
                                break;
                        }
                    }
                });
        //将订阅者加入管理站
        addSubscribe(mSubscription);
    }
}
