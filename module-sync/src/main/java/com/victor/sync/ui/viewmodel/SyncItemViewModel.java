package com.victor.sync.ui.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;

import com.victor.base.data.Repository.AppRepository;
import com.victor.base.data.entity.InboundData;
import com.victor.base.data.entity.InventoryData;
import com.victor.base.data.entity.ListData;
import com.victor.base.data.entity.MovementData;
import com.victor.base.data.entity.OutboundData;
import com.victor.base.data.entity.SyncInfo;
import com.victor.base.data.http.ApiDisposableObserver;
import com.victor.base.data.http.ApiListDisposableObserver;
import com.victor.base.utils.DateUtil;
import com.victor.workbench.ui.base.BaseRecycleItemViewModel;

import java.util.List;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.http.BaseResponse;
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
            case "入库":
                downloadInboundData(syncInfo);
                break;
            case "出库":
                downloadOutboundData(syncInfo);
                break;
            case "移库":
                downloadMovementData(syncInfo);
                break;
            case "盘点":
                downloadInventoryData(syncInfo);
                break;
        }
    });

    private void downloadInboundData(SyncInfo syncInfo) {
        model.listAllInbound(1)
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.bindToLifecycle(viewModel.getLifecycleProvider()))
                .subscribe(new ApiListDisposableObserver<List<InboundData>>() {
                    @Override
                    public void onResult(ListData<List<InboundData>> listData) {
                        List<InboundData> data = listData.getList();
                        if (data == null || data.size() == 0) {
                            ToastUtils.showShort("无入库单数据");
                            return;
                        }

                        // 删除本地盘点数据
                        model._deleteInboundData();
                        // 插入盘点单数组
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
                                .compose(RxUtils.bindToLifecycle(viewModel.getLifecycleProvider()))
                                .subscribe(new ApiDisposableObserver<InboundData>() {
                                    @Override
                                    public void onResult(InboundData data) {
                                        if (null != data) {
                                            List<InboundData.InboundElecMaterial> dataList = data.getElecMaterialList();
                                            model._insertInboundElecMaterial(dataList.toArray(new InboundData.InboundElecMaterial[dataList.size()]));
                                            setDownProcess(syncInfo);
                                        }
                                    }
                                });
                    }
                });
    }

    private void downloadOutboundData(SyncInfo syncInfo) {
        model.listAllOutbound(1)
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.bindToLifecycle(viewModel.getLifecycleProvider()))
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
                                .compose(RxUtils.bindToLifecycle(viewModel.getLifecycleProvider()))
                                .subscribe(new ApiDisposableObserver<OutboundData>() {
                                    @Override
                                    public void onResult(OutboundData data) {
                                        if (null != data) {
                                            List<OutboundData.OutboundElecMaterial> dataList = data.getElecMaterialList();
                                            model._insertOutboundElecMaterial(dataList.toArray(new OutboundData.OutboundElecMaterial[dataList.size()]));
                                            setDownProcess(syncInfo);
                                        }
                                    }
                                });
                    }
                });
    }

    private void downloadMovementData(SyncInfo syncInfo) {
        model.listAllMovement(1)
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.bindToLifecycle(viewModel.getLifecycleProvider()))
                .subscribe(new ApiListDisposableObserver<List<MovementData>>() {
                    @Override
                    public void onResult(ListData<List<MovementData>> listData) {
                        List<MovementData> data = listData.getList();
                        if (data == null || data.size() == 0) {
                            ToastUtils.showShort("无移库单数据");
                            return;
                        }

                        // 删除本地盘点数据
                        model._deleteMovementData();
                        // 插入盘点单数组
                        model._insertMovementData(data.toArray(new MovementData[data.size()]));

                        // 设置下载条数
                        syncInfo.setDownTotalValue(data.size());

                        Observable<BaseResponse<MovementData>> baseResponseObservable = null;
                        if (data.size() > 0) {
                            baseResponseObservable = model.selectByMovement(data.get(0).getId());
                        }
                        for (int i = 1; i < data.size(); i++) {
                            baseResponseObservable = baseResponseObservable.concatWith(model.selectByMovement(data.get(i).getId()));
                        }

                        baseResponseObservable.compose(RxUtils.schedulersTransformer())
                                .compose(RxUtils.bindToLifecycle(viewModel.getLifecycleProvider()))
                                .subscribe(new ApiDisposableObserver<MovementData>() {
                                    @Override
                                    public void onResult(MovementData data) {
                                        if (null != data) {
                                            List<MovementData.MovementElecMaterial> dataList = data.getElecMaterialList();
                                            model._insertMovementElecMaterial(dataList.toArray(new MovementData.MovementElecMaterial[dataList.size()]));
                                            setDownProcess(syncInfo);
                                        }
                                    }
                                });
                    }
                });
    }

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
                                            model._insertInventoryElecMaterial(dataList.toArray(new InventoryData.InventoryElecMaterial[dataList.size()]));
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
        switch (syncInfo.getSyncText()) {
            case "入库":
                uploadInboundData(syncInfo, syncDate);
                break;
            case "出库":
                uploadOutboundData(syncInfo, syncDate);
                break;
            case "移库":
                uploadMovementData(syncInfo, syncDate);
                break;
            case "盘点":
                uploadInventoryData(syncInfo, syncDate);
                break;
        }
    });

    // 上传入库数据
    private void uploadInboundData(SyncInfo syncInfo, String syncDate) {
        List<InboundData> inboundDatas = model._selectFinishedInboundByDate(syncDate);
        if (inboundDatas == null || inboundDatas.size() == 0) {
            ToastUtils.showShort("无入库数据上传");
            syncInfo.setUpValue(0);
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
                .compose(RxUtils.bindToLifecycle(viewModel.getLifecycleProvider()))
                .subscribe(new ApiDisposableObserver() {
                    @Override
                    public void onResult(Object o) {
                        boolean finished = setUpProcess(syncInfo);
                        if (finished) {
                            for (InboundData inboundData : inboundDatas) {
                                model._deleteInboundDataById(inboundData.getInId());
                            }
                        }
                    }
                });
    }

    // 上传出库数据
    private void uploadOutboundData(SyncInfo syncInfo, String syncDate) {
        List<OutboundData> outboundDatas = model._selectFinishedOutboundByDate(syncDate);
        if (outboundDatas == null || outboundDatas.size() == 0) {
            ToastUtils.showShort("无出库数据上传");
            syncInfo.setUpValue(0);
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
                .compose(RxUtils.bindToLifecycle(viewModel.getLifecycleProvider()))
                .subscribe(new ApiDisposableObserver() {
                    @Override
                    public void onResult(Object o) {
                        boolean finished = setUpProcess(syncInfo);
                        if (finished) {
                            for (OutboundData outboundData : outboundDatas) {
                                model._deleteOutboundDataById(outboundData.getOutId());
                            }
                        }
                    }
                });
    }

    // 上传移库数据
    private void uploadMovementData(SyncInfo syncInfo, String syncDate) {
        List<MovementData> movementDatas = model._selectFinishedMovementByDate(syncDate);
        if (movementDatas == null || movementDatas.size() == 0) {
            ToastUtils.showShort("无移库数据上传");
            syncInfo.setUpValue(0);
            return;
        }

        for (MovementData movementData : movementDatas) {
            MovementData tmpMovementData = model._selectOneMovement(movementData.getId());
            movementData.setElecMaterialList(tmpMovementData.getElecMaterialList());
        }

        Observable<BaseResponse> saveObservable = model.saveMovementResult(movementDatas.get(0));
        for (int i = 1; i < movementDatas.size(); i++) {
            Observable<BaseResponse> observable = model.saveMovementResult(movementDatas.get(i));
            saveObservable = saveObservable.concatWith(observable);
        }

        syncInfo.setUpTotalValue(movementDatas.size());
        saveObservable.compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .compose(RxUtils.bindToLifecycle(viewModel.getLifecycleProvider()))
                .subscribe(new ApiDisposableObserver() {
                    @Override
                    public void onResult(Object o) {
                        boolean finished = setUpProcess(syncInfo);
                        if (finished) {
                            for (MovementData movementData : movementDatas) {
                                model._deleteMovementDataById(movementData.getId());
                            }
                        }
                    }
                });
    }

    // 上传盘点数据
    private void uploadInventoryData(SyncInfo syncInfo, String syncDate) {
        List<InventoryData> inventoryDatas = model._selectFinishedInventoryByDate(syncDate);
        if (inventoryDatas == null || inventoryDatas.size() == 0) {
            ToastUtils.showShort("无盘点数据上传");
            syncInfo.setUpValue(0);
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
                .compose(RxUtils.bindToLifecycle(viewModel.getLifecycleProvider()))
                .subscribe(new ApiDisposableObserver() {
                    @Override
                    public void onResult(Object o) {
                        boolean finished = setUpProcess(syncInfo);
                        if (finished) {
                            for (InventoryData inventoryData : inventoryDatas) {
                                model._deleteInventoryDataById(inventoryData.getCheckId());
                            }
                        }
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

    private boolean setUpProcess(SyncInfo syncInfo) {
        Log.e("syncInfo::", syncInfo.toString());
        int interval = 100 / syncInfo.getUpTotalValue();

        int upValue = syncInfo.getUpValue() + interval;
        if (100 - upValue < interval) {
            syncInfo.setUpValue(100);
        } else {
            syncInfo.setUpValue(upValue);
        }
        if (syncInfo.getUpValue() == 100) {
            // 删除盘点单
            ToastUtils.showShortSafe("上传完成");
            saveSyncDate(syncInfo);
            return true;
        }
        entity.notifyChange();
        return false;
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
