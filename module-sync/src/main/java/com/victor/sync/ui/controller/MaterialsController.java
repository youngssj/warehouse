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

    private int page = 1;
    private int pageSize = 3;

    public void download(AppRepository model, LifecycleProvider lifecycleProvider, SyncInfo syncInfo, SyncItemViewModel.SyncInfoUpDownLoadListener listener) {
        model.listAllMaterials(page, pageSize)
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

                        syncInfo.setDownTotalValue(listData.getTotal());

                        int downloadSize = (page - 1) * pageSize + data.size();
                        syncInfo.setDownValue(downloadSize * 100 / 16);

                        if(page == 1) {
                            // 删除本地物资数据
                            model._deleteMaterialsData();
                        }

                        // 插入物资数据
                        model._insertMaterialsData(data.toArray(new MaterialsData[data.size()]));

                        listener.onDownloadSuccess(syncInfo);

                        if(downloadSize < listData.getTotal()){
                            page++;
                            download(model, lifecycleProvider, syncInfo, listener);
                        }
                    }
                });
    }
}
