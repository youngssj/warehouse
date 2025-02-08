package com.victor.base.data.source;

import com.victor.base.BuildConfig;
import com.victor.base.app.AppDatabase;
import com.victor.base.data.entity.InboundData;
import com.victor.base.data.entity.InventoryData;
import com.victor.base.data.entity.SyncInfo;
import com.victor.base.utils.Constants.CONFIG;
import com.victor.base.utils.Constants.SP;

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
        _deleteInventoryData();
        db.syncInfoDao().deleteAll();
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
    public void _deleteInventoryData() {
        db.inventoryDataDao().deleteAll();
        db.inventoryElecMaterialDao().deleteAll();
    }

    @Override
    public void _insertInventoryData(InventoryData... inventoryDatas) {
        db.inventoryDataDao().insertAll(inventoryDatas);
    }

    @Override
    public void _insertInventoryElecMaterial(InventoryData.InventoryElecMaterial... inventoryElecMaterials) {
        db.inventoryElecMaterialDao().insertAll(inventoryElecMaterials);
    }

    @Override
    public void _deleteInboundData() {
        db.inboundDataDao().deleteAll();
        db.inboundElecMaterialDao().deleteAll();
    }

    @Override
    public void _insertInboundData(InboundData... inboundDatas) {
        db.inboundDataDao().insertAll(inboundDatas);
    }

    @Override
    public void _insertInboundElecMaterial(InboundData.InboundElecMaterial... inboundElecMaterials) {
        db.inboundElecMaterialDao().insertAll(inboundElecMaterials);
    }

    @Override
    public Maybe<List<InventoryData>> _listInventory(int page) {
        return db.inventoryDataDao().getAll(10 * (page - 1), page * 10);
    }

    @Override
    public InventoryData _selectOneInventory(int checkId) {
        InventoryData inventoryData = db.inventoryDataDao().getOneById(checkId);
        if (null == inventoryData) {
            KLog.i("inventoryData");
        }
        inventoryData.setElecMaterialList(db.inventoryElecMaterialDao().getAll(checkId));
        return inventoryData;
    }

    @Override
    public void _saveInventoryResult(InventoryData inventoryData) {
        _insertInventoryData(inventoryData);
        _insertInventoryElecMaterial(inventoryData.getElecMaterialList().toArray(new InventoryData.InventoryElecMaterial[0]));
    }

    @Override
    public List<InventoryData> _selectFinishedInventoryByDate(String syncDate) {
        return db.inventoryDataDao().getFinishedByDate(syncDate);
    }

    @Override
    public void _deleteInventoryDataById(int checkId) {
        db.inventoryDataDao().deleteById(checkId);
        db.inventoryElecMaterialDao().deleteByCheckId(checkId);
    }
}
