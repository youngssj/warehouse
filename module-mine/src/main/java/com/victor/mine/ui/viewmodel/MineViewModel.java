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
    public ObservableField<String> name = new ObservableField<>("");
    public ObservableField<String> sex = new ObservableField<>("");
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

                        StringBuilder sexSb = new StringBuilder("1".equals(userInfoBean.getSex()) ? "女" : "男");
                        sexSb.append(" ");
                        name.set(TextUtils.isEmpty(userInfoBean.getNickName()) ? userInfoBean.getUserName() : userInfoBean.getNickName());
                        avatar.set("http://" + Constants.CONFIG.DEFAULT_IP + ":" + Constants.CONFIG.DEFAULT_PORT + userInfoBean.getAvatar());
                        phoneNumber.set(userInfoBean.getPhonenumber());
                        email.set(userInfoBean.getEmail());

                        if (userInfoBean.getDept() != null && !TextUtils.isEmpty(userInfoBean.getDept().getDeptName())) {
                            department.set(userInfoBean.getDept().getDeptName());
                            sexSb.append(userInfoBean.getDept().getDeptName());
                            sexSb.append(" ");
                        }

                        StringBuilder roleSb = new StringBuilder();
                        if (userInfoBean.getRoles() != null) {
                            for (UserInfoBean.Roles role : userInfoBean.getRoles()) {
                                roleSb.append(role.getRoleName());
                                roleSb.append("\n");
                                sexSb.append(role.getRoleName());
                                sexSb.append(" ");
                            }
                            if (roleSb.indexOf("\n") != -1) {
                                roleSb = roleSb.delete(roleSb.length() - 1, roleSb.length());
                            }
                        }
                        role.set(roleSb.toString());

                        if (sexSb.indexOf(" ") != -1) {
                            sexSb = sexSb.delete(sexSb.length() - 1, sexSb.length());
                        }
                        sex.set(sexSb.toString());
                    }

                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }
                });
    }
}
