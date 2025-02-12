package com.victor.sync.ui.controller;

import android.app.Activity;

import androidx.lifecycle.MutableLiveData;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.victor.base.data.Repository.AppRepository;
import com.victor.base.data.entity.AllocateData;
import com.victor.base.data.entity.ListData;
import com.victor.base.data.entity.MovementData;
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

public class AllocateController {
    public void download(MutableLiveData<Activity> activityLiveData, AppRepository model, LifecycleProvider lifecycleProvider, SyncInfo syncInfo, SyncItemViewModel.SyncInfoUpDownLoadListener listener) {
        String syncDate = getSyncDate(model, syncInfo);
        if (syncDate != null) {
            List<AllocateData> allocateDatas = model._selectFinishedAllocateByDate(syncDate);
            if (allocateDatas != null && allocateDatas.size() > 0) {
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
        model.listAllAllocate(1)
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.bindToLifecycle(lifecycleProvider))
                .subscribe(new ApiListDisposableObserver<List<AllocateData>>() {
                    @Override
                    public void onResult(ListData<List<AllocateData>> listData) {
                        List<AllocateData> data = listData.getList();
                        if (data == null || data.size() == 0) {
                            ToastUtils.showShort("无调拨单数据");
                            return;
                        }

                        // 删除本地盘点数据
                        model._deleteAllocateData();
                        // 插入盘点单数组
                        model._insertAllocateData(data.toArray(new AllocateData[data.size()]));

                        // 设置下载条数
                        syncInfo.setDownTotalValue(data.size());

                        Observable<BaseResponse<AllocateData>> baseResponseObservable = null;
                        if (data.size() > 0) {
                            baseResponseObservable = model.selectByAllocate(data.get(0).getId());
                        }
                        for (int i = 1; i < data.size(); i++) {
                            baseResponseObservable = baseResponseObservable.concatWith(model.selectByAllocate(data.get(i).getId()));
                        }

                        baseResponseObservable.compose(RxUtils.schedulersTransformer())
                                .compose(RxUtils.bindToLifecycle(lifecycleProvider))
                                .subscribe(new ApiDisposableObserver<AllocateData>() {
                                    @Override
                                    public void onResult(AllocateData data) {
                                        if (null != data) {
                                            List<AllocateData.AllocateMaterial> dataList = data.getMaterials();
                                            for (AllocateData.AllocateMaterial allocateMaterial : dataList) {
                                                allocateMaterial.setAllocateId(data.getId());
                                            }
                                            model._insertAllocateMaterial(dataList.toArray(new AllocateData.AllocateMaterial[dataList.size()]));
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
        String syncDate = getSyncDate(model, syncInfo);
        if (syncDate == null) {
            ToastUtils.showShort("无本地数据");
            return;
        }

        List<AllocateData> allocateDatas = model._selectFinishedAllocateByDate(syncDate);
        if (allocateDatas == null || allocateDatas.size() == 0) {
            ToastUtils.showShort("无调拨数据上传");
            syncInfo.setUpValue(0);
            return;
        }

        for (AllocateData allocateData : allocateDatas) {
            AllocateData tmpAllocateData = model._selectOneAllocate(allocateData.getId());
            allocateData.setMaterials(tmpAllocateData.getMaterials());
        }

        Observable<BaseResponse> saveObservable = model.saveAllocateResult(allocateDatas.get(0));
        for (int i = 1; i < allocateDatas.size(); i++) {
            Observable<BaseResponse> observable = model.saveAllocateResult(allocateDatas.get(i));
            saveObservable = saveObservable.concatWith(observable);
        }

        syncInfo.setUpTotalValue(allocateDatas.size());
        saveObservable.compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .compose(RxUtils.bindToLifecycle(lifecycleProvider))
                .subscribe(new ApiDisposableObserver() {
                    @Override
                    public void onResult(Object o) {

                    }

                    @Override
                    public void onComplete() {
                        boolean finished = listener.onUploadSuccess(syncInfo);
                        if (finished) {
                            for (AllocateData allocateData : allocateDatas) {
                                model._deleteAllocateDataById(allocateData.getId());
                            }
                            if (gotoDownload) {
                                toDownload(model, lifecycleProvider, syncInfo, listener);
                            }
                        }
                    }
                });
    }

    private String getSyncDate(AppRepository model, SyncInfo syncInfo) {
        SyncInfo syncInfoByDate = model._getSyncDate(syncInfo.getSyncId());
        if (syncInfoByDate == null) {
            return null;
        }
        return syncInfoByDate.getSyncDate();
    }
}
