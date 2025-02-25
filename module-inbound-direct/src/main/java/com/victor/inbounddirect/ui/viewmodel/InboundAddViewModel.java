package com.victor.inbounddirect.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.alibaba.android.arouter.launcher.ARouter;
import com.victor.base.base.BaseTitleViewModel;
import com.victor.base.data.Repository.AppRepository;
import com.victor.base.data.entity.ListData;
import com.victor.base.data.entity.UserData;
import com.victor.base.data.http.ApiListDisposableObserver;
import com.victor.base.router.RouterActivityPath;

import java.util.List;

import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;

public class InboundAddViewModel extends BaseTitleViewModel<AppRepository> {

    public ObservableField<String> inTheme = new ObservableField<>();
    public ObservableField<UserData> currentUser = new ObservableField<>();
    public ObservableField<String> planInDate = new ObservableField<>();
    public ObservableField<String> remark = new ObservableField<>();

    public class UIChangeObservable {
        public SingleLiveEvent<List<UserData>> uesrClickEvent = new SingleLiveEvent<>();
        public SingleLiveEvent<String> timeClickEvent = new SingleLiveEvent<>();
    }

    public UIChangeObservable uc = new UIChangeObservable();

    public InboundAddViewModel(@NonNull Application application, AppRepository model) {
        super(application, model);
    }

    public BindingCommand userClickCommand = new BindingCommand(() -> {
        model.getUserList()
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(disposable -> {
                    showProgress();
                }).subscribe(new ApiListDisposableObserver<List<UserData>>() {
                    @Override
                    public void onResult(ListData<List<UserData>> listData) {
                        List<UserData> userDatas = listData.getList();
                        uc.uesrClickEvent.setValue(userDatas);
                    }

                    @Override
                    public void onComplete() {
                        dismissProgress();
                    }
                });
    });

    public BindingCommand timeClickCommand = new BindingCommand(() -> {
        uc.timeClickEvent.setValue(null);
    });

    public BindingCommand nextClickCommand = new BindingCommand(() -> {

    });
}
