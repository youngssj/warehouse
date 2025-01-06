package com.victor.main.data.source;

import com.victor.main.BuildConfig;
import com.victor.main.app.AppDatabase;
import com.victor.main.data.entity.AssetCheckData;
import com.victor.main.data.entity.AssetCheckOdd;
import com.victor.main.data.entity.AssetData;
import com.victor.main.data.entity.AssetInspectionData;
import com.victor.main.data.entity.AssetInspectionOdd;
import com.victor.main.data.entity.AssetRepairData;
import com.victor.main.data.entity.AssetRepairOdd;
import com.victor.main.data.entity.SyncInfo;
import com.victor.main.data.entity.TakeStockData;
import com.victor.main.data.entity.TakeStockDetail;
import com.victor.main.utils.Constants.CONFIG;
import com.victor.main.utils.Constants.SP;
import com.victor.main.utils.Constants.TaskStatus;
import com.victor.main.utils.DateUtil;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Maybe;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.SPUtils;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/9/9
 * 邮箱：jxfengmtx@gmail.com
 */
public class LocalDataSourceImpl implements LocalDataSource {
    private volatile static LocalDataSourceImpl INSTANCE = null;
    private volatile static AppDatabase db = null;

    public static LocalDataSourceImpl getInstance(AppDatabase db) {
        if (INSTANCE == null) {
            synchronized (LocalDataSourceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocalDataSourceImpl(db);
                }
            }
        }
        return INSTANCE;
    }

    public LocalDataSourceImpl(AppDatabase db) {
        this.db = db;
    }


    public static void destroyInstance() {
        INSTANCE = null;
    }


    @Override
    public void saveUserName2Local(String userName) {
        SPUtils.getInstance().put(SP.USERNAME, userName);
    }

    @Override
    public void savePassword2Local(String password) {
        SPUtils.getInstance().put(SP.PASSWORD, password);
    }

    @Override
    public String getUserName() {
        return SPUtils.getInstance().getString(SP.USERNAME);
    }

    @Override
    public String getPassword() {
        return SPUtils.getInstance().getString(SP.PASSWORD);
    }

    @Override
    public void saveToken2Local(String token) {
        SPUtils.getInstance().put(SP.TOKEN, token);
    }

    @Override
    public String getToken() {
        return SPUtils.getInstance().getString(SP.TOKEN);
    }

    @Override
    public void _saveIp(String ip) {
        SPUtils.getInstance().put(SP.IP, ip);
    }

    @Override
    public String _getIp() {
        return SPUtils.getInstance().getString(SP.IP, BuildConfig.API_IP);
    }

    @Override
    public void _savePort(String port) {
        SPUtils.getInstance().put(SP.PORT, port);
    }

    @Override
    public String _getPort() {
        return SPUtils.getInstance().getString(SP.PORT, BuildConfig.API_PORT);
    }

    @Override
    public void _saveConfig(boolean config) {
        SPUtils.getInstance().put(SP.IS_OFFLINE, config);
    }

    @Override
    public boolean _getConfig() {
        return SPUtils.getInstance().getBoolean(SP.IS_OFFLINE, CONFIG.IS_OFFLINE);
    }

    @Override
    public void _deleteAll() {
        db.assetCheckOddDao().deleteAll();
        db.assetCheckDetailDao().deleteAll();
        db.syncInfoDao().deleteAll();
    }

    @Override
    public void _deleteCheckData() {
        db.assetCheckOddDao().deleteAll();
        db.assetCheckDetailDao().deleteAll();
    }

    @Override
    public void _deleteTakeStockData() {
        db.takeStockDataDao().deleteAll();
        db.elecMaterialDao().deleteAll();
    }

    @Override
    public void _deleteInspectionData() {
        db.assetInspectionOddDao().deleteAll();
        db.assetInspectionOddDetailDao().deleteAll();
    }

    @Override
    public void _insertAssetCheckOdd(AssetCheckOdd... assetCheckOdds) {
        db.assetCheckOddDao().insertAll(assetCheckOdds);
    }

    @Override
    public void _insertTakeStockData(TakeStockData... takeStockDatas) {
        db.takeStockDataDao().insertAll(takeStockDatas);
    }

    @Override
    public void _insertAssetCheckData(AssetCheckData.DataListBean... dataListBeans) {
        db.assetCheckDetailDao().insertAll(dataListBeans);
    }

    @Override
    public void _insertElecMaterial(TakeStockDetail.ElecMaterialListDTO... elecMaterialListDTOS) {
        db.elecMaterialDao().insertAll(elecMaterialListDTOS);
    }

    @Override
    public void _insertAssetData(AssetData... assetDatas) {
        db.assetDataDao().insertAll(assetDatas);
    }

    @Override
    public void _saveSyncDate(SyncInfo syncInfo) {
        db.syncInfoDao().insertAll(syncInfo);
    }

    @Override
    public SyncInfo _getSyncDate(int syncId) {
        return db.syncInfoDao().getAllById(syncId);
    }

    @Override
    public List<AssetCheckData.DataListBean> _selectCheckDetailAll(String lastDate) {
        return db.assetCheckDetailDao().getAllByDate(lastDate);
    }

    @Override
    public List<AssetCheckOdd> _selectCheckOddAll(String lastDate) {
        return db.assetCheckOddDao().getAllByDate(lastDate);
    }

    @Override
    public Maybe<List<AssetCheckOdd>> _listCheck(int page) {
        return db.assetCheckOddDao().getAll(10 * (page - 1), page * 10);
    }

    @Override
    public void _saveCheckResult(int checkId, String checkPks, String noCheckPks, String batchNumber,List<AssetCheckData.DataListBean> surplusCheckPks) {
        AssetCheckOdd assetCheckOdd = db.assetCheckOddDao().getOneByIds(checkId);
        if (null == assetCheckOdd) {
            KLog.i("assetCheckOdd为空");
            return;
        }
        String checkDate = DateUtil.getNowTime();
        assetCheckOdd.setCheckDate(checkDate);
        assetCheckOdd.setStatus(3);
        db.assetCheckOddDao().update(assetCheckOdd);
        String[] strs1 = checkPks.split(",");
        String[] strs2 = noCheckPks.split(",");

        db.assetCheckDetailDao().update(Arrays.asList(strs1), "1", checkDate);
        db.assetCheckDetailDao().update(Arrays.asList(strs2), "2", checkDate);
        for (AssetCheckData.DataListBean dataListBean : surplusCheckPks) {
            dataListBean.setCheckResult("0");
            dataListBean.setCheckDate(checkDate);
        }
        db.assetCheckDetailDao().insertAll(surplusCheckPks);
    }

    @Override
    public AssetCheckData _selectOneCheck(int checkId) {
        AssetCheckData assetCheckData = new AssetCheckData();
        AssetCheckOdd assetCheckOdd = db.assetCheckOddDao().getOneByIds(checkId);
        if (null == assetCheckOdd) {
            KLog.i("assetCheckOdd为空");
        }
        assetCheckData.setMainInfo(assetCheckOdd);
        assetCheckData.setDataList(db.assetCheckDetailDao().getAll(assetCheckOdd.getBatchNumber()));
        return assetCheckData;
    }

    @Override
    public List<AssetData> _rfidToMaterialInfo(List<String> epcs) {
        return db.assetDataDao().getAllByRfid(epcs);
    }

    @Override
    public List<AssetData> _barCodeToMaterialInfo(String barCode) {
        return db.assetDataDao().getAllByBarcode(barCode);
    }

    //巡检
    @Override
    public Maybe<List<AssetInspectionOdd>> _listInspection(int page) {
        return db.assetInspectionOddDao().getAll(10 * (page - 1), page * 10);
    }

    // 单个物品的巡检结果
    @Override
    public void _saveInspectionResult(int inspectionDetailId, int result, String inspectionContent) {
        AssetInspectionData.DataListBean inspectionDetail = db.assetInspectionOddDetailDao().getOneByIds(inspectionDetailId);
        if (inspectionDetail != null) {
            inspectionDetail.setInspectionResult(result);
            inspectionDetail.setInspectionContent(inspectionContent);
            inspectionDetail.setInspectionDate(DateUtil.getNowTime());

            db.assetInspectionOddDetailDao().update(inspectionDetail);
        }
    }

    // 保存巡检单结果
    @Override
    public void _saveInspection(int inspectionId, String batchNumber) {
        AssetInspectionOdd assetInspectionOdd = db.assetInspectionOddDao().getOneByIds(inspectionId);
        if (null == assetInspectionOdd) {
            KLog.i("assetInspectionOdd为空");
            return;
        }

        assetInspectionOdd.setInspectionDate(DateUtil.getNowTime());
        assetInspectionOdd.setStatus(TaskStatus.FINISH);
        db.assetInspectionOddDao().update(assetInspectionOdd);
    }

    @Override
    public AssetInspectionData _selectInspectionDataById(int inspectionId) {

        AssetInspectionData assetInspectionData = new AssetInspectionData();
        AssetInspectionOdd assetInspectionOdd = db.assetInspectionOddDao().getOneByIds(inspectionId);
        if (null == assetInspectionOdd) {
            KLog.i("assetInspectionOdd为空");
        }
        assetInspectionData.setMainInfo(assetInspectionOdd);
        assetInspectionData.setDataList(db.assetInspectionOddDetailDao().getAll(assetInspectionOdd.getBatchNumber()));
        return assetInspectionData;
    }

    @Override
    public void _insertAssetInspectionOds(List<AssetInspectionOdd> assetInspectionOddList) {
        db.assetInspectionOddDao().insertAll(assetInspectionOddList);
    }

    @Override
    public List<AssetInspectionOdd> _selectInspectionOddAll(String lastDate) {
        return db.assetInspectionOddDao().getAllByDate(lastDate);
    }

    @Override
    public List<AssetInspectionData.DataListBean> _selectInspectionDetailAll(String lastDate) {
        return db.assetInspectionOddDetailDao().getAllByDate(lastDate);
    }

    @Override
    public void _insertAssetInspectionData(List<AssetInspectionData.DataListBean> dataListBeans) {
        db.assetInspectionOddDetailDao().insertAll(dataListBeans);
    }

    @Override
    public void _deleteRepairData() {
        db.assetRepairOddDao().deleteAll();
        db.assetRepairOddDetailDao().deleteAll();
    }

    // 维修
    @Override
    public void _insertAssetRepairOds(List<AssetRepairOdd> assetRepairOddList) {
        db.assetRepairOddDao().insertAll(assetRepairOddList);
    }

    @Override
    public void _insertAssetRepairData(List<AssetRepairData.DataListBean> dataListBeans) {
        db.assetRepairOddDetailDao().insertAll(dataListBeans);
    }

    @Override
    public List<AssetRepairData.DataListBean> _selectRepairDetailAll(String lastDate) {
        return db.assetRepairOddDetailDao().getAllByDate(lastDate);
    }

    @Override
    public List<AssetRepairOdd> _selectRepairOddAll(String lastDate) {
        return db.assetRepairOddDao().getAllByDate(lastDate);
    }

    // 单个维修
    @Override
    public void _saveRepairResult(int repairDetailId, int materialId, String repairCosts, String repairContent) {
        AssetRepairData.DataListBean repairDetail = db.assetRepairOddDetailDao().getOneByIds(repairDetailId);
        if (repairDetail != null) {
            repairDetail.setRepairCosts(repairCosts);
            repairDetail.setRepairContent(repairContent);
            repairDetail.setRepairDate(DateUtil.getNowTime());

            db.assetRepairOddDetailDao().update(repairDetail);
        }
        db.assetRepairOddDao().update();
    }

    @Override
    public AssetRepairData _selectRepairDataById(int repairId) {
        AssetRepairData assetRepairData = new AssetRepairData();
        AssetRepairOdd assetRepairOdd = db.assetRepairOddDao().getOneByIds(repairId);
        if (null == assetRepairOdd) {
            KLog.i("assetInspectionOdd为空");
        }
        assetRepairData.setMainInfo(assetRepairOdd);
        assetRepairData.setDataList(db.assetRepairOddDetailDao().getAll(assetRepairOdd.getBatchNumber()));
        return assetRepairData;
    }


    // 保存维修单结果
    @Override
    public void _saveRepair(int repairId, String batchNumber) {
        AssetRepairOdd assetRepairOdd = db.assetRepairOddDao().getOneByIds(repairId);
        if (null == assetRepairOdd) {
            KLog.i("assetInspectionOdd为空");
            return;
        }

        assetRepairOdd.setRepairDate(DateUtil.getNowTime());
        assetRepairOdd.setStatus(TaskStatus.FINISH);
        db.assetRepairOddDao().update(assetRepairOdd);
    }

    //维修
    @Override
    public Maybe<List<AssetRepairOdd>> _listRepair(int page) {
        return db.assetRepairOddDao().getAll(10 * (page - 1), page * 10);
    }
}
