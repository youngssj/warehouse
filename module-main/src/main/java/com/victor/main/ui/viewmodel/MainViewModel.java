package com.victor.main.ui.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.victor.base.data.Repository.AppRepository;
import com.victor.base.data.entity.UserInfoBean;
import com.victor.base.data.http.ApiDisposableObserver;
import com.victor.main.BuildConfig;
import com.victor.main.R;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.RxUtils;

public class MainViewModel extends BaseViewModel<AppRepository> {

    public ObservableField<String> appVersion = new ObservableField<>("");
    public ObservableField<String> hello = new ObservableField<>("");

    public class UIChangeObservable {
        //密码开关观察者
        public SingleLiveEvent<Integer> checkedEvent = new SingleLiveEvent<>();
    }

    public UIChangeObservable uc = new UIChangeObservable();

    public MainViewModel(@NonNull Application application, AppRepository model) {
        super(application, model);
        appVersion.set(getApplication().getResources().getString(R.string.main_version_text) + BuildConfig.VERSIONNAME);
    }

    @Override
    public void onResume() {
        super.onResume();
        // 获取用户信息
        getUserInfo();
    }

    private void getUserInfo() {
        model.userInfo()
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(disposable -> showDialog(getApplication().getResources().getString(R.string.app_loading_text)))
                .subscribe(new ApiDisposableObserver<UserInfoBean>() {
                    @Override
                    public void onResult(UserInfoBean userInfoBean) {
                        KLog.i(userInfoBean);
                        hello.set(getApplication().getResources().getString(R.string.main_hello_text) + (TextUtils.isEmpty(userInfoBean.getNickName()) ? userInfoBean.getUserName() : userInfoBean.getNickName()));
                    }

                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }
                });
    }

    public BindingCommand<Integer> onCheckedChangedCommand = new BindingCommand<>(new BindingConsumer<Integer>() {
        @Override
        public void call(Integer checkedId) {
            uc.checkedEvent.setValue(checkedId);
        }
    });
}
