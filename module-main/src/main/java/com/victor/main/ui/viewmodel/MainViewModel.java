package com.victor.main.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.victor.main.BuildConfig;
import com.victor.main.R;

import me.goldze.mvvmhabit.base.BaseViewModel;

public class MainViewModel extends BaseViewModel {

    public ObservableField<String> appVersion = new ObservableField<>("");

    public MainViewModel(@NonNull Application application) {
        super(application);
        appVersion.set(getApplication().getResources().getString(R.string.main_version_text) + BuildConfig.VERSIONNAME);
    }
}
