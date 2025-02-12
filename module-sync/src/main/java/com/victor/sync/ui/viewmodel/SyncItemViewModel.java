package com.victor.sync.ui.viewmodel;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.victor.base.data.Repository.AppRepository;
import com.victor.base.data.entity.SyncInfo;
import com.victor.base.utils.DateUtil;
import com.victor.sync.ui.controller.AllocateController;
import com.victor.sync.ui.controller.InboundController;
import com.victor.sync.ui.controller.InventoryController;
import com.victor.sync.ui.controller.MovementController;
import com.victor.sync.ui.controller.OutboundController;
import com.victor.workbench.ui.base.BaseRecycleItemViewModel;

import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2021/9/10
 * 邮箱：jxfengmtx@gmail.com
 */
public class SyncItemViewModel extends BaseRecycleItemViewModel<SyncViewModel, SyncInfo> {
    private AppRepository model;
    public MutableLiveData<Activity> activityLiveData = new MutableLiveData<>();

    SyncInfoUpDownLoadListener syncInfoListener = new SyncInfoUpDownLoadListener() {
        @Override
        public boolean onDownloadSuccess(SyncInfo syncInfo) {
            setDownProcess(syncInfo);
            return true;
        }

        @Override
        public boolean onUploadSuccess(SyncInfo syncInfo) {
            return setUpProcess(syncInfo);
        }
    };

    public BindingCommand itemDownClick = new BindingCommand(() -> {
        final SyncInfo syncInfo = entity.get();

        switch (syncInfo.getSyncText()) {
            case "入库":
                new InboundController().download(activityLiveData, model, viewModel.getLifecycleProvider(), syncInfo, syncInfoListener);
                break;
            case "出库":
                new OutboundController().download(activityLiveData, model, viewModel.getLifecycleProvider(), syncInfo, syncInfoListener);
                break;
            case "移库":
                new MovementController().download(activityLiveData, model, viewModel.getLifecycleProvider(), syncInfo, syncInfoListener);
                break;
            case "调拨":
                new AllocateController().download(activityLiveData, model, viewModel.getLifecycleProvider(), syncInfo, syncInfoListener);
                break;
            case "盘点":
                new InventoryController().download(activityLiveData, model, viewModel.getLifecycleProvider(), syncInfo, syncInfoListener);
                break;
        }
    });

    public BindingCommand itemUpClick = new BindingCommand(() -> {
        final SyncInfo syncInfo = entity.get();
        switch (syncInfo.getSyncText()) {
            case "入库":
                new InboundController().upload(model, viewModel.getLifecycleProvider(), syncInfo, syncInfoListener);
                break;
            case "出库":
                new OutboundController().upload(model, viewModel.getLifecycleProvider(), syncInfo, syncInfoListener);
                break;
            case "移库":
                new MovementController().upload(model, viewModel.getLifecycleProvider(), syncInfo, syncInfoListener);
                break;
            case "调拨":
                new AllocateController().upload(model, viewModel.getLifecycleProvider(), syncInfo, syncInfoListener);
                break;
            case "盘点":
                new InventoryController().upload(model, viewModel.getLifecycleProvider(), syncInfo, syncInfoListener);
                break;
        }
    });

    public SyncItemViewModel(MutableLiveData<Activity> activityLiveData, @NonNull SyncViewModel viewModel, SyncInfo syncInfo, AppRepository appRepository) {
        super(viewModel, syncInfo);
        this.model = appRepository;
        this.activityLiveData = activityLiveData;
        switch (syncInfo.getSyncText()) {
            case "查询":
                syncInfo.setUpValue(-1);
                break;
        }
    }

    @Override
    protected void itemClickCallback() {

    }

    // 计算下载进度
    private void setDownProcess(SyncInfo syncInfo) {
        int interval = 100 / syncInfo.getDownTotalValue();

        int downValue = syncInfo.getDownValue() + interval;
        if (100 - downValue < interval) {
            syncInfo.setDownValue(100);
        } else {
            syncInfo.setDownValue(downValue);
        }
        if (syncInfo.getDownValue() == 100) {
            ToastUtils.showShortSafe("下载完成");
            syncInfo.setSyncDate(DateUtil.getNowTime());
            model._saveSyncDate(syncInfo);
        }
        entity.notifyChange();
    }

    private boolean setUpProcess(SyncInfo syncInfo) {
        int interval = 100 / syncInfo.getUpTotalValue();

        int upValue = syncInfo.getUpValue() + interval;
        if (100 - upValue < interval) {
            syncInfo.setUpValue(100);
        } else {
            syncInfo.setUpValue(upValue);
        }
        entity.notifyChange();
        if (syncInfo.getUpValue() == 100) {
            // 删除盘点单
            ToastUtils.showShortSafe("上传完成");
            syncInfo.setSyncDate(DateUtil.getNowTime());
            model._saveSyncDate(syncInfo);
            return true;
        }
        return false;
    }

    // 下载信息监听
    public interface SyncInfoUpDownLoadListener {
        boolean onDownloadSuccess(SyncInfo syncInfo);

        boolean onUploadSuccess(SyncInfo syncInfo);
    }
}
