package com.victor.sync.ui.controller;

import android.app.Activity;

import androidx.lifecycle.MutableLiveData;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.victor.base.data.Repository.AppRepository;
import com.victor.base.data.entity.InboundData;
import com.victor.base.data.entity.ListData;
import com.victor.base.data.entity.OutboundData;
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

public class OutboundController {
    public void download(MutableLiveData<Activity> activityLiveData, AppRepository model, LifecycleProvider lifecycleProvider, SyncInfo syncInfo, SyncItemViewModel.SyncInfoUpDownLoadListener listener) {
        String syncDate = syncInfo.getSyncDate();
        if (syncDate != null) {
            List<OutboundData> outboundDatas = model._selectFinishedOutboundByDate(syncDate);
            if (outboundDatas != null && outboundDatas.size() > 0) {
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
        model.listAllOutbound(1)
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.bindToLifecycle(lifecycleProvider))
                .subscribe(new ApiListDisposableObserver<List<OutboundData>>() {
                    @Override
                    public void onResult(ListData<List<OutboundData>> listData) {
                        List<OutboundData> data = listData.getList();
                        if (data == null || data.size() == 0) {
                            ToastUtils.showShort("无出库单数据");
                            return;
                        }

                        // 删除本地盘点数据
                        model._deleteOutboundData();
                        // 插入盘点单数组
                        model._insertOutboundData(data.toArray(new OutboundData[data.size()]));

                        // 设置下载条数
                        syncInfo.setDownTotalValue(data.size());

                        Observable<BaseResponse<OutboundData>> baseResponseObservable = null;
                        if (data.size() > 0) {
                            baseResponseObservable = model.selectByOutbound(data.get(0).getOutId());
                        }
                        for (int i = 1; i < data.size(); i++) {
                            baseResponseObservable = baseResponseObservable.concatWith(model.selectByOutbound(data.get(i).getOutId()));
                        }

                        baseResponseObservable.compose(RxUtils.schedulersTransformer())
                                .compose(RxUtils.bindToLifecycle(lifecycleProvider))
                                .subscribe(new ApiDisposableObserver<OutboundData>() {
                                    @Override
                                    public void onResult(OutboundData data) {
                                        if (null != data) {
                                            List<OutboundData.OutboundElecMaterial> dataList = data.getElecMaterialList();
                                            model._insertOutboundElecMaterial(dataList.toArray(new OutboundData.OutboundElecMaterial[dataList.size()]));
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
            ToastUtils.showShort("无出库数据上传");
            return;
        }

        List<OutboundData> outboundDatas = model._selectFinishedOutboundByDate(syncDate);
        if (outboundDatas == null || outboundDatas.size() == 0) {
            ToastUtils.showShort("无出库数据上传");
            return;
        }

        for (OutboundData outboundData : outboundDatas) {
            OutboundData tmpOutboundData = model._selectOneOutbound(outboundData.getOutId());
            outboundData.setElecMaterialList(tmpOutboundData.getElecMaterialList());
        }

        Observable<BaseResponse> saveObservable = model.saveOutboundResult(outboundDatas.get(0));
        for (int i = 1; i < outboundDatas.size(); i++) {
            Observable<BaseResponse> observable = model.saveOutboundResult(outboundDatas.get(i));
            saveObservable = saveObservable.concatWith(observable);
        }

        syncInfo.setUpTotalValue(outboundDatas.size());
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
                            for (OutboundData outboundData : outboundDatas) {
                                model._deleteOutboundDataById(outboundData.getOutId());
                            }
                            if (gotoDownload) {
                                toDownload(model, lifecycleProvider, syncInfo, listener);
                            }
                        }
                    }
                });
    }
}
