package com.victor.sync.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.victor.base.data.Repository.AppRepository;

import me.goldze.mvvmhabit.base.BaseViewModel;

public class SyncViewModel extends BaseViewModel<AppRepository> {

    public SyncViewModel(@NonNull Application application, AppRepository model) {
        super(application, model);
    }

}
