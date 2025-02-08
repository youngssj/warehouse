package com.victor.sync.ui.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;

import com.victor.base.data.Repository.AppRepository;
import com.victor.base.data.entity.InventoryData;
import com.victor.base.data.entity.InventoryData;
import com.victor.base.data.entity.SyncInfo;
import com.victor.base.data.http.ApiDisposableObserver;
import com.victor.base.data.http.DownDisposableObserver;
import com.victor.base.data.http.ListResponse;
import com.victor.base.utils.DateUtil;
import com.victor.workbench.ui.base.BaseRecycleItemViewModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
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
//            case "巡检":
//                new ZcxjController().downLoadData(model, viewModel.getLifecycleProvider(), syncInfo, new SyncInfoUpDownLoadListener() {
//                    @Override
//                    public void onSuccess(SyncInfo syncInfo) {
//                        setDownProcess(syncInfo);
//                    }
//                });
//                break;
//            case "维修":
//                new ZcwxController().downLoadData(model, viewModel.getLifecycleProvider(), syncInfo, new SyncInfoUpDownLoadListener() {
//                    @Override
//                    public void onSuccess(SyncInfo syncInfo) {
//                        setDownProcess(syncInfo);
//                    }
//                });
//                break;
//            case "查询":
//                ToastUtils.showShort("请稍候");
//                downloadAssetData(syncInfo);
//                break;
        }
    });

    // 下载盘点数据
    private void downloadInventoryData(SyncInfo syncInfo) {
        model.listAllTakeStock(1)
                .flatMap(new Function<ListResponse<List<InventoryData>>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull ListResponse<List<InventoryData>> takeStockDatas) throws Exception {
                        handleExcept(takeStockDatas);
                        List<InventoryData> data = takeStockDatas.getRows();
                        if (data == null || data.size() == 0) {
                            KLog.i("无盘点单数据");
                            return null;
                        }
                        // 删除本地盘点数据
                        model._deleteTakeStockData();
                        // 插入盘点单数组
                        model._insertTakeStockData(data.toArray(new InventoryData[data.size()]));
                        // 设置下载条数
                        syncInfo.setDownTotalValue(data.size());
                        Observable<BaseResponse<InventoryData>> baseResponseObservable = null;
                        if (data.size() > 0) {
                            baseResponseObservable = model.selectByCheck(data.get(0).getCheckId());
                        }
                        for (int i = 1; i < data.size(); i++) {
                            baseResponseObservable = baseResponseObservable.concatWith(model.selectByCheck(data.get(i).getCheckId()));
                        }
                        return baseResponseObservable;
                    }
                })
                .compose(RxUtils.schedulersTransformer())
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

    public BindingCommand itemUpClick = new BindingCommand(() -> {
        final SyncInfo syncInfo = entity.get();
        SyncInfo syncInfo1 = model._getSyncDate(syncInfo.getSyncId());
        if (syncInfo1 == null) {
            ToastUtils.showShort("无本地数据");
            return;
        }
        String syncDate = syncInfo1.getSyncDate();
        Observable<BaseResponse> saveRes = null;
        switch (syncInfo.getSyncText()) {
            case "盘点":
                uploadCheckData(syncInfo, syncDate);
                break;
//            case "巡检":
//                new ZcxjController().uploadData(model, viewModel.getLifecycleProvider(), syncInfo, syncDate, new SyncInfoUpDownLoadListener() {
//                    @Override
//                    public void onSuccess(SyncInfo syncInfo) {
//                        setUpProcess(syncInfo);
//                    }
//                });
//                break;
//            case "维修":
//                new ZcwxController().uploadData(model, viewModel.getLifecycleProvider(), syncInfo, syncDate, new SyncInfoUpDownLoadListener() {
//                    @Override
//                    public void onSuccess(SyncInfo syncInfo) {
//                        setUpProcess(syncInfo);
//                    }
//                });
//                break;
        }
    });


    // 上传盘点数据
    private void uploadCheckData(SyncInfo syncInfo, String syncDate) {
//        Observable<BaseResponse> saveRes;
//        List<AssetCheckOdd> checkOdds = model._selectCheckOddAll(syncDate);
//        List<AssetCheckData.DataListBean> checkDetail = model._selectCheckDetailAll(syncDate);
//        if (checkOdds == null || checkOdds.size() == 0) {
//            ToastUtils.showShort("无数据上传");
//            syncInfo.setUpValue(0);
//            return;
//        }
//        List<String> pddIds = new ArrayList<>();
//        List<String> wPddIds = new ArrayList<>();
//        List<String> pyIds = new ArrayList<>();
//        List<AssetCheckData.DataListBean> pyBeans = new ArrayList<>();
//        for (AssetCheckData.DataListBean ac :
//                checkDetail) {
//            switch (ac.getCheckResult()) {
//                case "1":
//                    pddIds.add(String.valueOf(ac.getCheckDetailId()));
//                    break;
//                case "2":
//                    wPddIds.add(String.valueOf(ac.getCheckDetailId()));
//                    break;
//                case "0":
//                    pyIds.add(String.valueOf(ac.getMaterialId()));
//                    pyBeans.add(ac);
//                    break;
//            }
//        }
//        AssetCheckOdd ac = checkOdds.get(0);
//        saveRes = model.saveCheckResult(ac.getCheckId(),
//                StringUtils.listToStr(pddIds, ","),
//                StringUtils.listToStr(wPddIds, ","), ac.getBatchNumber(), StringUtils.listToStr(pyIds, ","));
//        for (int i = 1; i < checkOdds.size(); i++) {
//            ac = checkOdds.get(i);
//            Observable<BaseResponse> responseObservable = model.saveCheckResult(ac.getCheckId(),
//                    StringUtils.listToStr(pddIds, ","),
//                    StringUtils.listToStr(wPddIds, ","), ac.getBatchNumber(), StringUtils.listToStr(pyIds, ","));
//            saveRes = saveRes.concatWith(responseObservable);
//        }
//        syncInfo.setUpTotalValue(checkOdds.size());
//        saveRes.compose(RxUtils.schedulersTransformer())
//                .compose(RxUtils.exceptionTransformer())
//                .compose(RxUtils.bindToLifecycle(viewModel.getLifecycleProvider()))
//                .subscribe(new ApiDisposableObserver<BaseResponse>() {
//                    @Override
//                    public void onResult(BaseResponse o) {
//                        setUpProcess(syncInfo);
//                    }
//                });
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

    private void setUpProcess(SyncInfo syncInfo) {
        Log.e("123", syncInfo.toString());
        int interval = 100 / syncInfo.getUpTotalValue();

        int upValue = syncInfo.getUpValue() + interval;
        if (100 - upValue < interval) {
            syncInfo.setUpValue(100);
        } else {
            syncInfo.setUpValue(upValue);
        }
        if (syncInfo.getUpValue() == 100) {
            ToastUtils.showShortSafe("上传完成");
            saveSyncDate(syncInfo);
        }
        entity.notifyChange();
    }

    private void saveSyncDate(SyncInfo syncInfo) {
        syncInfo.setSyncDate(DateUtil.getNowTime());
        model._saveSyncDate(syncInfo);
    }

    private void handleExcept(ListResponse l) {
        new DownDisposableObserver() {  //处理异常
            @Override
            public void onResult(Object o) {

            }
        }.onNext(l);
    }

    // 下载信息监听
    public interface SyncInfoUpDownLoadListener {
        void onSuccess(SyncInfo syncInfo);
    }
}
