package com.victor.materials.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.victor.base.data.Repository.AppRepository;
import com.victor.workbench.ui.base.BaseTitleViewModel;

public class MaterialsQueryViewModel extends BaseTitleViewModel<AppRepository> {
    public MaterialsQueryViewModel(@NonNull Application application, AppRepository model) {
        super(application, model);
    }
}
