package com.victor.main.ui.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.victor.base.data.entity.UserInfoBean;
import com.victor.main.BuildConfig;
import com.victor.main.R;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.bus.RxSubscriptions;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

public class MainViewModel extends BaseViewModel {

    public ObservableField<String> appVersion = new ObservableField<>("");
    public ObservableField<String> hello = new ObservableField<>("");

    public class UIChangeObservable {
        //密码开关观察者
        public SingleLiveEvent<Integer> radioCheckedEvent = new SingleLiveEvent<>();
        public SingleLiveEvent<Integer> pageSelectedEvent = new SingleLiveEvent<>();
    }

    public UIChangeObservable uc = new UIChangeObservable();

    public MainViewModel(@NonNull Application application) {
        super(application);
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

    //订阅者
    private Disposable mSubscription;

    //注册RxBus
    @Override
    public void registerRxBus() {
        super.registerRxBus();
        mSubscription = RxBus.getDefault().toObservable(UserInfoBean.class)
                .subscribe(new Consumer<UserInfoBean>() {
                    @Override
                    public void accept(UserInfoBean userInfoBean) throws Exception {
                        hello.set(getApplication().getResources().getString(R.string.main_hello_text)
                                + (TextUtils.isEmpty(userInfoBean.getNickName()) ? userInfoBean.getUserName() : userInfoBean.getNickName()));
                    }
                });
        //将订阅者加入管理站
        RxSubscriptions.add(mSubscription);
    }

    //移除RxBus
    @Override
    public void removeRxBus() {
        super.removeRxBus();
        //将订阅者从管理站中移除
        RxSubscriptions.remove(mSubscription);
    }
}
