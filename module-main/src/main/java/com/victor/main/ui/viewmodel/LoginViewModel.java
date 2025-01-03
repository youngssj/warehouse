package com.victor.main.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

public class LoginViewModel extends BaseViewModel {

    public ObservableField<String> ipObFiled = new ObservableField<>("");
    public ObservableField<String> portObFiled = new ObservableField<>("");
    public boolean isInternet;

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public class UIChangeObservable {
        //密码开关观察者
        public SingleLiveEvent<Boolean> pSwitchEvent = new SingleLiveEvent<>();
        public SingleLiveEvent<LoginViewModel> setIpEvent = new SingleLiveEvent<>();
    }

    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public BindingCommand pSwitchEvent = new BindingCommand(() -> {
        uc.pSwitchEvent.setValue(uc.pSwitchEvent.getValue() == null || !uc.pSwitchEvent.getValue());
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
}
