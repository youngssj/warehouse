package com.victor.materials.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.victor.base.data.Repository.AppRepository;
import com.victor.materials.bean.MaterialsQueryConditionBean;
import com.victor.workbench.ui.base.BaseTitleViewModel;

import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.utils.KLog;

public class MaterialsQueryViewModel extends BaseTitleViewModel<AppRepository> {
    public MaterialsQueryViewModel(@NonNull Application application, AppRepository model) {
        super(application, model);
    }

    public ObservableField<String> materialsName = new ObservableField<>("");
    public ObservableField<String> rfid = new ObservableField<>("");

    public BindingCommand searchOnClickCommand = new BindingCommand(() -> {
        search();
    });

    private void search() {
        KLog.i("搜索");
        RxBus.getDefault().post(new MaterialsQueryConditionBean(materialsName.get(), rfid.get()));
    }

    public void updateRfid(String rfid) {
        this.rfid.set(rfid);
        RxBus.getDefault().post(new MaterialsQueryConditionBean(materialsName.get(), rfid));
    }
}
