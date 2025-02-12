package com.victor.sync.ui.controller;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.victor.base.data.Repository.AppRepository;
import com.victor.base.data.entity.InboundData;
import com.victor.base.data.entity.ListData;
import com.victor.base.data.entity.MaterialsData;
import com.victor.base.data.entity.SyncInfo;
import com.victor.base.data.http.ApiDisposableObserver;
import com.victor.base.data.http.ApiListDisposableObserver;
import com.victor.base.utils.RxTimer;
import com.victor.sync.ui.viewmodel.SyncItemViewModel;

import java.util.List;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class MaterialsController {

    public void download(AppRepository model, LifecycleProvider lifecycleProvider, SyncInfo syncInfo, SyncItemViewModel.SyncInfoUpDownLoadListener listener) {
        syncInfo.setDownTotalValue(100);
        RxTimer.interval(1, 200, new RxTimer.RxAction() {
            @Override
            public void action(long number) {
                if (syncInfo.getDownValue() >= 100) {
                    RxTimer.cancel();
                }
                listener.onDownloadSuccess(syncInfo);
            }
        });
        model.listAllMaterials(1)
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.bindToLifecycle(lifecycleProvider))
                .subscribe(new ApiListDisposableObserver<List<MaterialsData>>() {
                    @Override
                    public void onResult(ListData<List<MaterialsData>> listData) {
                        List<MaterialsData> data = listData.getList();
                        if (data == null || data.size() == 0) {
                            ToastUtils.showShort("无物资数据");
                            return;
                        }

                        // 删除本地物资数据
                        model._deleteAllMaterials();
                        // 插入物资数据
                        model._insertMaterialsData(data.toArray(new MaterialsData[data.size()]));

                        syncInfo.setDownValue(100);
                        listener.onDownloadSuccess(syncInfo);
                    }
                });
    }
}
