package com.victor.inbounddirect.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.victor.base.data.Repository.AppRepository;
import com.victor.base.data.entity.ListData;
import com.victor.base.data.entity.UserData;
import com.victor.base.data.http.ApiListDisposableObserver;
import com.victor.workbench.ui.base.BaseTitleViewModel;

import java.util.List;

import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;

public class InboundAddViewModel extends BaseTitleViewModel<AppRepository> {

    public UserData currentUser;

    public class UIChangeObservable {
        public SingleLiveEvent<List<UserData>> uesrClickEvent = new SingleLiveEvent<>();
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

    });
}
