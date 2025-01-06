package com.victor.main.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.victor.main.BuildConfig;
import com.victor.main.R;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

public class MainViewModel extends BaseViewModel {

    public ObservableField<String> appVersion = new ObservableField<>("");

    public class UIChangeObservable {
        //密码开关观察者
        public SingleLiveEvent<Integer> checkedEvent = new SingleLiveEvent<>();
    }

    public UIChangeObservable uc = new UIChangeObservable();

    public MainViewModel(@NonNull Application application) {
        super(application);
        appVersion.set(getApplication().getResources().getString(R.string.main_version_text) + BuildConfig.VERSIONNAME);
    }

    public BindingCommand<Integer> onCheckedChangedCommand = new BindingCommand<>(new BindingConsumer<Integer>() {
        @Override
        public void call(Integer checkedId) {
            uc.checkedEvent.setValue(checkedId);
        }
    });
}
