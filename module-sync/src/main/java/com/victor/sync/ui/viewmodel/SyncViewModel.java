package com.victor.sync.ui.viewmodel;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.android.arouter.launcher.ARouter;
import com.victor.base.data.Repository.AppRepository;
import com.victor.base.data.entity.SyncInfo;
import com.victor.base.router.RouterActivityPath;
import com.victor.base.utils.Constants;
import com.victor.sync.R;
import com.victor.workbench.ui.base.BaseOddViewModel;
import com.victor.workbench.ui.base.BaseRecycleItemViewModel;

import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class SyncViewModel extends BaseOddViewModel<SyncItemViewModel> {

    public MutableLiveData<Activity> activityLiveData = new MutableLiveData<>();

    public SyncViewModel(@NonNull Application application, AppRepository model) {
        super(application, model);
    }

    @Override
    protected int initItemLayout() {
        return R.layout.sync_item_list;
    }

    @Override
    protected void loadData(int page) {

    }

    public void loadData(String[] stringArray) {
        observableList.clear();
        for (int i = 0; i < stringArray.length; i++) {
            SyncInfo syncInfo = model._getSyncById(i);
            if(syncInfo == null){
                syncInfo = new SyncInfo(i, stringArray[i]);
            }
            observableList.add(new SyncItemViewModel(activityLiveData, this, syncInfo, model));
        }
    }

    public BindingCommand<Void> clearClick = new BindingCommand<Void>(() -> {
        model._deleteAll();
        for (BaseRecycleItemViewModel baseRecycleItemViewModel : observableList) {
            ObservableField<SyncInfo> entity = ((SyncItemViewModel) baseRecycleItemViewModel).entity;
            SyncInfo syncInfo = entity.get();
            syncInfo.setDownValue(0);
            syncInfo.setUpValue(0);
            if (syncInfo.getSyncText().equals("查询"))
                syncInfo.setUpValue(-1);
            entity.notifyChange();
        }
        ToastUtils.showShort("清空完成");
    });

    public BindingCommand<Void> reloadLogin = new BindingCommand<Void>(() -> {
        reloadLogin();
    });

    public BindingCommand<Void> allDownLoad = new BindingCommand<Void>(() -> {
        for (BaseRecycleItemViewModel itemViewModel : observableList) {
            ((SyncItemViewModel) itemViewModel).itemDownClick.execute();
        }
    });
    public BindingCommand<Void> allUpLoad = new BindingCommand<Void>(() -> {
        for (BaseRecycleItemViewModel itemViewModel : observableList) {
            ((SyncItemViewModel) itemViewModel).itemUpClick.execute();
        }
    });

    private void reloadLogin() {
        SPUtils.getInstance().put(Constants.SP.TOKEN, "");
        ARouter.getInstance().build(RouterActivityPath.Sign.PAGER_LOGIN).navigation();
        finish();
    }
}
