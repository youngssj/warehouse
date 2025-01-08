package com.victor.main.ui.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.alibaba.android.arouter.launcher.ARouter;
import com.victor.base.data.Repository.AppRepository;
import com.victor.base.data.entity.TokenBean;
import com.victor.base.data.http.ApiDisposableObserver;
import com.victor.base.router.RouterActivityPath;
import com.victor.base.utils.Constants;
import com.victor.main.R;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class LoginViewModel extends BaseViewModel<AppRepository> {

    public ObservableField<String> ipObFiled = new ObservableField<>("");
    public ObservableField<String> portObFiled = new ObservableField<>("");
    public boolean isInternet;

    public ObservableField<String> userName = new ObservableField<>("");
    public ObservableField<String> password = new ObservableField<>("");
    public ObservableInt clearBtnVisibility = new ObservableInt(View.INVISIBLE);

    public LoginViewModel(@NonNull Application application, AppRepository model) {
        super(application, model);
        userName.set(model.getUserName());
        password.set(model.getPassword());

        ipObFiled.set(model._getIp());
        portObFiled.set(model._getPort());
        isInternet = model._getConfig();
        saveIpAndPort();
    }

    public class UIChangeObservable {
        //密码开关观察者
        public SingleLiveEvent<Boolean> pSwitchEvent = new SingleLiveEvent<>();
        public SingleLiveEvent<LoginViewModel> setIpEvent = new SingleLiveEvent<>();
    }

    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public BindingCommand passwordShowSwitchOnClickCommand = new BindingCommand(() -> {
        uc.pSwitchEvent.setValue(uc.pSwitchEvent.getValue() == null || !uc.pSwitchEvent.getValue());
    });

    public BindingCommand<String> onTextChangeCommand = new BindingCommand<>(new BindingConsumer<String>() {
        @Override
        public void call(String text) {
            if (TextUtils.isEmpty(userName.get())) {
                clearBtnVisibility.set(View.INVISIBLE);
            } else {
                clearBtnVisibility.set(View.VISIBLE);
            }
        }
    });

    //清除用户名的点击事件, 逻辑从View层转换到ViewModel层
    public BindingCommand clearUserNameOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            userName.set("");
        }
    });

    public BindingCommand setIpClick = new BindingCommand(() -> {
        uc.setIpEvent.setValue(this);
    });

    public BindingCommand<Boolean> isInternetCommand = new BindingCommand<Boolean>(new BindingConsumer<Boolean>() {
        @Override
        public void call(Boolean internet) {
            isInternet = internet;
        }
    });

    //登录按钮的点击事件
    public BindingCommand loginOnClickCommand = new BindingCommand(() -> {
        login();
    });

    private void login() {
        if (TextUtils.isEmpty(userName.get())) {
            ToastUtils.showShort(getApplication().getResources().getString(R.string.login_username_hint_text));
            return;
        }
        if (TextUtils.isEmpty(password.get())) {
            ToastUtils.showShort(getApplication().getResources().getString(R.string.login_password_hint_text));
            return;
        }
        model.login(userName.get(), password.get())
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(disposable -> showDialog(getApplication().getResources().getString(R.string.app_loading_text)))
                .subscribe(new ApiDisposableObserver<TokenBean>() {
                    @Override
                    public void onResult(TokenBean userToken) {
                        model.saveUserName2Local(userName.get());
                        model.savePassword2Local(password.get());
                        model.saveToken2Local(userToken.getToken());
                        saveIpAndPort();
                        ARouter.getInstance().build(RouterActivityPath.Main.PAGER_MAIN).navigation();
                        finish();
                    }

                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }
                });

    }

    public void saveIpAndPort() {
        model._savePort(portObFiled.get());
        model._saveIp(ipObFiled.get());
        model._saveConfig(isInternet);
        Constants.CONFIG.DEFAULT_IP = ipObFiled.get();
        Constants.CONFIG.DEFAULT_PORT = portObFiled.get();
        Constants.CONFIG.IS_OFFLINE = isInternet;
    }
}
