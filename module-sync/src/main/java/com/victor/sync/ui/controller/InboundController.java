package com.victor.sync.ui.controller;

import android.app.Activity;

import androidx.lifecycle.MutableLiveData;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.victor.base.data.Repository.AppRepository;
import com.victor.base.data.entity.InboundData;
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

public class InboundController {
    public void download(MutableLiveData<Activity> activityLiveData, AppRepository model, LifecycleProvider lifecycleProvider, SyncInfo syncInfo, SyncItemViewModel.SyncInfoUpDownLoadListener listener) {
        String syncDate = syncInfo.getSyncDate();
        if (syncDate != null) {
            List<InboundData> inboundDatas = model._selectFinishedInboundByDate(syncDate);
            if (inboundDatas != null && inboundDatas.size() > 0) {
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
        model.listAllInbound(1)
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.bindToLifecycle(lifecycleProvider))
                .subscribe(new ApiListDisposableObserver<List<InboundData>>() {
                    @Override
                    public void onResult(ListData<List<InboundData>> listData) {
                        List<InboundData> data = listData.getList();
                        if (data == null || data.size() == 0) {
                            ToastUtils.showShort("无入库单数据");
                            return;
                        }

                        // 删除本地入库数据
                        model._deleteInboundData();
                        // 插入入库单数据
                        model._insertInboundData(data.toArray(new InboundData[data.size()]));

                        // 设置下载条数
                        syncInfo.setDownTotalValue(data.size());

                        Observable<BaseResponse<InboundData>> baseResponseObservable = null;
                        if (data.size() > 0) {
                            baseResponseObservable = model.selectByInbound(data.get(0).getInId());
                        }
                        for (int i = 1; i < data.size(); i++) {
                            baseResponseObservable = baseResponseObservable.concatWith(model.selectByInbound(data.get(i).getInId()));
                        }

                        baseResponseObservable.compose(RxUtils.schedulersTransformer())
                                .compose(RxUtils.bindToLifecycle(lifecycleProvider))
                                .subscribe(new ApiDisposableObserver<InboundData>() {
                                    @Override
                                    public void onResult(InboundData data) {
                                        if (null != data) {
                                            List<InboundData.InboundElecMaterial> dataList = data.getElecMaterialList();
                                            model._insertInboundElecMaterial(dataList.toArray(new InboundData.InboundElecMaterial[dataList.size()]));
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
            ToastUtils.showShort("无入库数据上传");
            return;
        }

        List<InboundData> inboundDatas = model._selectFinishedInboundByDate(syncDate);
        if (inboundDatas == null || inboundDatas.size() == 0) {
            ToastUtils.showShort("无入库数据上传");
            return;
        }

        for (InboundData inboundData : inboundDatas) {
            InboundData tmpInboundData = model._selectOneInbound(inboundData.getInId());
            inboundData.setElecMaterialList(tmpInboundData.getElecMaterialList());
        }

        Observable<BaseResponse> saveObservable = model.saveInboundResult(inboundDatas.get(0));
        for (int i = 1; i < inboundDatas.size(); i++) {
            Observable<BaseResponse> observable = model.saveInboundResult(inboundDatas.get(i));
            saveObservable = saveObservable.concatWith(observable);
        }

        syncInfo.setUpTotalValue(inboundDatas.size());
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
                            for (InboundData inboundData : inboundDatas) {
                                model._deleteInboundDataById(inboundData.getInId());
                            }
                            if (gotoDownload) {
                                toDownload(model, lifecycleProvider, syncInfo, listener);
                            }
                        }
                    }
                });
    }
}
