package com.victor.sync.ui.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;

import com.victor.base.data.Repository.AppRepository;
import com.victor.base.data.entity.InventoryData;
import com.victor.base.data.entity.ListData;
import com.victor.base.data.entity.SyncInfo;
import com.victor.base.data.http.ApiDisposableObserver;
import com.victor.base.data.http.ApiListDisposableObserver;
import com.victor.base.utils.DateUtil;
import com.victor.workbench.ui.base.BaseRecycleItemViewModel;

import java.util.List;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.RxUtils;
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

    public BindingCommand itemDownClick = new BindingCommand(() -> {
        final SyncInfo syncInfo = entity.get();

        switch (syncInfo.getSyncText()) {
            case "盘点":
                downloadInventoryData(syncInfo);
                break;
        }
    });

    // 下载盘点数据
    private void downloadInventoryData(SyncInfo syncInfo) {
        model.listAllInventory(1)
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.bindToLifecycle(viewModel.getLifecycleProvider()))
                .subscribe(new ApiListDisposableObserver<List<InventoryData>>() {
                    @Override
                    public void onResult(ListData<List<InventoryData>> listData) {
                        List<InventoryData> data = listData.getList();
                        if (data == null || data.size() == 0) {
                            ToastUtils.showShort("无盘点单数据");
                            return;
                        }

                        // 删除本地盘点数据
                        model._deleteInventoryData();
                        // 插入盘点单数组
                        model._insertInventoryData(data.toArray(new InventoryData[data.size()]));

                        // 设置下载条数
                        syncInfo.setDownTotalValue(data.size());

                        Observable<BaseResponse<InventoryData>> baseResponseObservable = null;
                        if (data.size() > 0) {
                            baseResponseObservable = model.selectByCheck(data.get(0).getCheckId());
                        }
                        for (int i = 1; i < data.size(); i++) {
                            baseResponseObservable = baseResponseObservable.concatWith(model.selectByCheck(data.get(i).getCheckId()));
                        }

                        baseResponseObservable.compose(RxUtils.schedulersTransformer())
                                .compose(RxUtils.bindToLifecycle(viewModel.getLifecycleProvider()))
                                .subscribe(new ApiDisposableObserver<InventoryData>() {
                                    @Override
                                    public void onResult(InventoryData data) {
                                        if (null != data) {
                                            List<InventoryData.InventoryElecMaterial> dataList = data.getElecMaterialList();
                                            model._insertElecMaterial(dataList.toArray(new InventoryData.InventoryElecMaterial[dataList.size()]));
                                            setDownProcess(syncInfo);
                                        }
                                    }
                                });
                    }
                });
    }

    public BindingCommand itemUpClick = new BindingCommand(() -> {
        final SyncInfo syncInfo = entity.get();
        SyncInfo syncInfoByDate = model._getSyncDate(syncInfo.getSyncId());
        if (syncInfoByDate == null) {
            ToastUtils.showShort("无本地数据");
            return;
        }
        String syncDate = syncInfoByDate.getSyncDate();
        Observable<BaseResponse> saveRes = null;
        switch (syncInfo.getSyncText()) {
            case "盘点":
                uploadInventoryData(syncInfo, syncDate);
                break;
        }
    });


    // 上传盘点数据
    private void uploadInventoryData(SyncInfo syncInfo, String syncDate) {
        List<InventoryData> inventoryDatas = model._selectFinishedInventoryByDate(syncDate);
        if (inventoryDatas == null || inventoryDatas.size() == 0) {
            ToastUtils.showShort("无数据上传");
            syncInfo.setUpValue(0);
            return;
        }

        for (InventoryData inventoryData : inventoryDatas) {
            InventoryData tmpInventoryData = model._selectOneInventory(inventoryData.getCheckId());
            inventoryData.setElecMaterialList(tmpInventoryData.getElecMaterialList());
        }
        KLog.d(inventoryDatas);

        Observable<BaseResponse> saveObservable = model.saveCheckedResult(inventoryDatas.get(0));
        for (int i = 1; i < inventoryDatas.size(); i++) {
            Observable<BaseResponse> observable = model.saveCheckedResult(inventoryDatas.get(i));
            saveObservable = saveObservable.concatWith(observable);
        }

        syncInfo.setUpTotalValue(inventoryDatas.size());
        saveObservable.compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .compose(RxUtils.bindToLifecycle(viewModel.getLifecycleProvider()))
                .subscribe(new ApiDisposableObserver() {
                    @Override
                    public void onResult(Object o) {
                        setUpProcess(syncInfo, inventoryDatas);
                    }
                });
    }

    public SyncItemViewModel(@NonNull SyncViewModel viewModel, SyncInfo data, AppRepository appRepository) {
        super(viewModel, data);
        this.model = appRepository;
        final SyncInfo syncInfo = entity.get();
        switch (syncInfo.getSyncText()) {
            case "盘点":
                break;
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
        Log.e("123", syncInfo.toString());
        int interval = 100 / syncInfo.getDownTotalValue();

        int downValue = syncInfo.getDownValue() + interval;
        if (100 - downValue < interval) {
            syncInfo.setDownValue(100);
        } else {
            syncInfo.setDownValue(downValue);
        }
        if (syncInfo.getDownValue() == 100) {
            ToastUtils.showShortSafe("下载完成");
            saveSyncDate(syncInfo);
        }
        entity.notifyChange();
    }

    private void setUpProcess(SyncInfo syncInfo, List<InventoryData> inventoryDatas) {
        Log.e("123", syncInfo.toString());
        int interval = 100 / syncInfo.getUpTotalValue();

        int upValue = syncInfo.getUpValue() + interval;
        if (100 - upValue < interval) {
            syncInfo.setUpValue(100);
        } else {
            syncInfo.setUpValue(upValue);
        }
        if (syncInfo.getUpValue() == 100) {
            // 删除盘点单
            for (InventoryData inventoryData : inventoryDatas) {
                model._deleteInventoryDataById(inventoryData.getCheckId());
            }
            ToastUtils.showShortSafe("上传完成");
            saveSyncDate(syncInfo);
        }
        entity.notifyChange();
    }

    private void saveSyncDate(SyncInfo syncInfo) {
        syncInfo.setSyncDate(DateUtil.getNowTime());
        model._saveSyncDate(syncInfo);
    }

    // 下载信息监听
    public interface SyncInfoUpDownLoadListener {
        void onSuccess(SyncInfo syncInfo);
    }
}
