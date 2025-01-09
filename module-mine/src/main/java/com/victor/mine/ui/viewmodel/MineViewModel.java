package com.victor.mine.ui.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.victor.base.data.Repository.AppRepository;
import com.victor.base.data.entity.UserInfoBean;
import com.victor.base.data.http.ApiDisposableObserver;
import com.victor.base.utils.Constants;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.RxUtils;

public class MineViewModel extends BaseViewModel<AppRepository> {

    public ObservableField<String> avatar = new ObservableField<>("");
    public ObservableField<String> phoneNumber = new ObservableField<>("");
    public ObservableField<String> email = new ObservableField<>("");
    public ObservableField<String> department = new ObservableField<>("");
    public ObservableField<String> role = new ObservableField<>("");

    public class UIChangeObservable {
        //密码开关观察者
        public SingleLiveEvent<MineViewModel> exitEvent = new SingleLiveEvent<>();
    }

    public UIChangeObservable uc = new UIChangeObservable();

    public BindingCommand exitOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.exitEvent.setValue(MineViewModel.this);
        }
    });

    public MineViewModel(@NonNull Application application, AppRepository model) {
        super(application, model);
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
                .subscribe(new ApiDisposableObserver<UserInfoBean>() {
                    @Override
                    public void onResult(UserInfoBean userInfoBean) {
                        KLog.i(userInfoBean.toString());
                        RxBus.getDefault().post(userInfoBean);

                        avatar.set("http://" + Constants.CONFIG.DEFAULT_IP + ":" + Constants.CONFIG.DEFAULT_PORT + "/dev-api" + userInfoBean.getAvatar());
                        phoneNumber.set(userInfoBean.getPhonenumber());
                        email.set(userInfoBean.getEmail());

                        if (userInfoBean.getDept() != null && !TextUtils.isEmpty(userInfoBean.getDept().getDeptName())) {
                            department.set(userInfoBean.getDept().getDeptName());
                        }

                        StringBuilder sb = new StringBuilder();
                        if (userInfoBean.getRoles() != null) {
                            for (UserInfoBean.Roles role : userInfoBean.getRoles()) {
                                sb.append(role.getRoleName());
                                sb.append("\n");
                            }
                            if (sb.indexOf("\n") != -1) {
                                sb = sb.delete(sb.length() - 1, sb.length());
                            }
                        }
                        role.set(sb.toString());
                    }

                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }
                });
    }
}
