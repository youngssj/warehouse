package com.victor.base.data.source;

import com.victor.base.BuildConfig;
import com.victor.base.app.AppDatabase;
import com.victor.base.data.entity.InboundData;
import com.victor.base.data.entity.InventoryData;
import com.victor.base.data.entity.MovementData;
import com.victor.base.data.entity.OutboundData;
import com.victor.base.data.entity.SyncInfo;
import com.victor.base.utils.Constants.CONFIG;
import com.victor.base.utils.Constants.SP;

import java.util.List;

import io.reactivex.Maybe;
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

    @Override
    public Maybe<List<InboundData>> _listInbound(int page) {
        return db.inboundDataDao().getAll(10 * (page - 1), page * 10);
    }

    @Override
    public InboundData _selectOneInbound(int inId) {
        InboundData inboundData = db.inboundDataDao().getOneById(inId);
        inboundData.setElecMaterialList(db.inboundElecMaterialDao().getAll(inId));
        return inboundData;
    }

    @Override
    public void _saveInboundResult(InboundData inboundData) {
        _insertInboundData(inboundData);
        _insertInboundElecMaterial(inboundData.getElecMaterialList().toArray(new InboundData.InboundElecMaterial[0]));
    }

    @Override
    public Maybe<List<OutboundData>> _listOutbound(int page) {
        return db.outboundDataDao().getAll(10 * (page - 1), page * 10);
    }

    @Override
    public OutboundData _selectOneOutbound(int outId) {
        OutboundData outboundData = db.outboundDataDao().getOneById(outId);
        outboundData.setElecMaterialList(db.outboundElecMaterialDao().getAll(outId));
        return outboundData;
    }

    @Override
    public void _saveOutboundResult(OutboundData outboundData) {
        _insertOutboundData(outboundData);
        _insertOutboundElecMaterial(outboundData.getElecMaterialList().toArray(new OutboundData.OutboundElecMaterial[0]));
    }

    @Override
    public Maybe<List<MovementData>> _listMovement(int page) {
        return db.movementDataDao().getAll(10 * (page - 1), page * 10);
    }

    @Override
    public MovementData _selectOneMovement(int moveId) {
        MovementData movementData = db.movementDataDao().getOneById(moveId);
        movementData.setElecMaterialList(db.movementElecMaterialDao().getAll(moveId));
        return movementData;
    }

    @Override
    public void _saveMovementResult(MovementData movementData) {
        _insertMovementData(movementData);
        _insertMovementElecMaterial(movementData.getElecMaterialList().toArray(new MovementData.MovementElecMaterial[0]));
    }

    @Override
    public List<InboundData> _selectFinishedInboundByDate(String syncDate) {
        return db.inboundDataDao().getFinishedByDate(syncDate);
    }

    @Override
    public void _deleteInboundDataById(int inId) {
        db.inboundDataDao().deleteById(inId);
        db.inboundElecMaterialDao().deleteByInId(inId);
    }

    @Override
    public void _deleteOutboundData() {
        db.outboundDataDao().deleteAll();
        db.outboundElecMaterialDao().deleteAll();
    }

    @Override
    public void _insertOutboundData(OutboundData... outboundDatas) {
        db.outboundDataDao().insertAll(outboundDatas);
    }

    @Override
    public void _insertOutboundElecMaterial(OutboundData.OutboundElecMaterial... outboundElecMaterials) {
        db.outboundElecMaterialDao().insertAll(outboundElecMaterials);
    }

    @Override
    public List<OutboundData> _selectFinishedOutboundByDate(String syncDate) {
        return db.outboundDataDao().getFinishedByDate(syncDate);
    }

    @Override
    public void _deleteOutboundDataById(int outId) {
        db.outboundDataDao().deleteById(outId);
        db.outboundElecMaterialDao().deleteByInId(outId);
    }

    @Override
    public void _deleteMovementData() {
        db.movementDataDao().deleteAll();
        db.movementElecMaterialDao().deleteAll();
    }

    @Override
    public void _insertMovementData(MovementData... movementDatas) {
        db.movementDataDao().insertAll(movementDatas);
    }

    @Override
    public void _insertMovementElecMaterial(MovementData.MovementElecMaterial... movementElecMaterials) {
        db.movementElecMaterialDao().insertAll(movementElecMaterials);
    }

    @Override
    public List<MovementData> _selectFinishedMovementByDate(String syncDate) {
        return db.movementDataDao().getFinishedByDate(syncDate);
    }

    @Override
    public void _deleteMovementDataById(int moveId) {
        db.movementDataDao().deleteById(moveId);
        db.movementElecMaterialDao().deleteByMoveId(moveId);
    }
}
