package com.victor.sync.ui.controller;

import android.app.Activity;

import androidx.lifecycle.MutableLiveData;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.victor.base.data.Repository.AppRepository;
import com.victor.base.data.entity.InventoryData;
import com.victor.base.data.entity.ListData;
import com.victor.base.data.entity.SyncInfo;
import com.victor.base.data.http.ApiDisposableObserver;
import com.victor.base.data.http.ApiListDisposableObserver;
import com.victor.sync.R;
import com.victor.sync.ui.viewmodel.SyncItemViewModel;

import java.util.List;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.utils.MaterialDialogUtils;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class InventoryController {
    public void download(MutableLiveData<Activity> activityLiveData, AppRepository model, LifecycleProvider lifecycleProvider, SyncInfo syncInfo, SyncItemViewModel.SyncInfoUpDownLoadListener listener) {
        String syncDate = syncInfo.getSyncDate();
        if (syncDate != null) {
            List<InventoryData> inventoryDatas = model._selectFinishedInventoryByDate(syncDate);
            if (inventoryDatas != null && inventoryDatas.size() > 0) {
                // 存在未上传的数据，弹窗提示
                MaterialDialogUtils.showBasicDialog(activityLiveData.getValue(), activityLiveData.getValue().getResources().getString(R.string.sync_delete_data_hint_text))
                        .onPositive((dialog, which) -> {
                            toUpload(model, lifecycleProvider, syncInfo, listener, true);
                        })
                        .onNegative((dialog, which) -> {
                            toDownload(model, lifecycleProvider, syncInfo, listener);
                        })
                        .show();
                return;
            }
        }
        toDownload(model, lifecycleProvider, syncInfo, listener);
    }

    private void toDownload(AppRepository model, LifecycleProvider lifecycleProvider, SyncInfo syncInfo, SyncItemViewModel.SyncInfoUpDownLoadListener listener) {
        model.listAllInventory(1)
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.bindToLifecycle(lifecycleProvider))
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
                                .compose(RxUtils.bindToLifecycle(lifecycleProvider))
                                .subscribe(new ApiDisposableObserver<InventoryData>() {
                                    @Override
                                    public void onResult(InventoryData data) {
                                        if (null != data) {
                                            List<InventoryData.InventoryElecMaterial> dataList = data.getElecMaterialList();
                                            model._insertInventoryElecMaterial(dataList.toArray(new InventoryData.InventoryElecMaterial[dataList.size()]));
                                            listener.onDownloadSuccess(syncInfo);
                                        }
                                    }
                                });
                    }
                });
    }

    public void upload(AppRepository model, LifecycleProvider lifecycleProvider, SyncInfo syncInfo, SyncItemViewModel.SyncInfoUpDownLoadListener listener) {
        toUpload(model, lifecycleProvider, syncInfo, listener, false);
    }

    // 上传入库数据
    public void toUpload(AppRepository model,
                         LifecycleProvider lifecycleProvider,
                         SyncInfo syncInfo,
                         SyncItemViewModel.SyncInfoUpDownLoadListener listener,
                         boolean gotoDownload) {
        String syncDate = syncInfo.getSyncDate();
        if (syncDate == null) {
            ToastUtils.showShort("无盘点数据上传");
            return;
        }

        List<InventoryData> inventoryDatas = model._selectFinishedInventoryByDate(syncDate);
        if (inventoryDatas == null || inventoryDatas.size() == 0) {
            ToastUtils.showShort("无盘点数据上传");
            return;
        }

        for (InventoryData inventoryData : inventoryDatas) {
            InventoryData tmpInventoryData = model._selectOneInventory(inventoryData.getCheckId());
            inventoryData.setElecMaterialList(tmpInventoryData.getElecMaterialList());
        }

        Observable<BaseResponse> saveObservable = model.saveCheckedResult(inventoryDatas.get(0));
        for (int i = 1; i < inventoryDatas.size(); i++) {
            Observable<BaseResponse> observable = model.saveCheckedResult(inventoryDatas.get(i));
            saveObservable = saveObservable.concatWith(observable);
        }

        syncInfo.setUpTotalValue(inventoryDatas.size());
        saveObservable.compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .compose(RxUtils.bindToLifecycle(lifecycleProvider))
                .subscribe(new ApiDisposableObserver() {
                    @Override
                    public void onResult(Object o) {
                        boolean finished = listener.onUploadSuccess(syncInfo);
                        if (finished) {
                            for (InventoryData inventoryData : inventoryDatas) {
                                model._deleteInventoryDataById(inventoryData.getCheckId());
                            }
                            if (gotoDownload) {
                                toDownload(model, lifecycleProvider, syncInfo, listener);
                            }
                        }
                    }
                });
    }
}
