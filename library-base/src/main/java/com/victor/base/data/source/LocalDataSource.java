package com.victor.base.data.source;

import com.victor.base.data.entity.AllocateData;
import com.victor.base.data.entity.InboundData;
import com.victor.base.data.entity.MovementData;
import com.victor.base.data.entity.OutboundData;
import com.victor.base.data.entity.SyncInfo;
import com.victor.base.data.entity.InventoryData;
import com.victor.base.data.entity.InventoryData;

import java.util.List;

import io.reactivex.Maybe;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/9/9
 * 邮箱：jxfengmtx@gmail.com
 */
public interface LocalDataSource {

    /**
     * 保存用户名
     */
    void saveUserName2Local(String userName);

    /**
     * 保存用户密码
     */

    void savePassword2Local(String password);

    /**
     * 获取用户名
     */
    String getUserName();

    /**
     * 获取用户密码
     */
    String getPassword();

    void saveToken2Local(String token);

    String getToken();

    void _saveIp(String ip);

    String _getIp();

    void _savePort(String port);

    String _getPort();

    void _saveConfig(boolean config);

    boolean _getConfig();

    void _deleteAll();

    /* ----------------同步数据------------- */
    void _saveSyncDate(SyncInfo syncInfo);
    SyncInfo _getSyncDate(int syncId);
    // 盘点
    void _deleteInventoryData();
    void _insertInventoryData(InventoryData... inventoryDatas);
    void _insertInventoryElecMaterial(InventoryData.InventoryElecMaterial... inventoryElecMaterials);
    List<InventoryData> _selectFinishedInventoryByDate(String syncDate);
    void _deleteInventoryDataById(int checkId);
    // 入库
    void _deleteInboundData();
    void _insertInboundData(InboundData... inboundDatas);
    void _insertInboundElecMaterial(InboundData.InboundElecMaterial... inboundElecMaterials);
    List<InboundData> _selectFinishedInboundByDate(String syncDate);
    void _deleteInboundDataById(int inId);
    // 出库
    void _deleteOutboundData();
    void _insertOutboundData(OutboundData... outboundDatas);
    void _insertOutboundElecMaterial(OutboundData.OutboundElecMaterial... outboundElecMaterials);
    List<OutboundData> _selectFinishedOutboundByDate(String syncDate);
    void _deleteOutboundDataById(int outId);
    // 移库
    void _deleteMovementData();
    void _insertMovementData(MovementData... movementDatas);
    void _insertMovementElecMaterial(MovementData.MovementElecMaterial... movementElecMaterials);
    List<MovementData> _selectFinishedMovementByDate(String syncDate);
    void _deleteMovementDataById(int moveId);
    // 调拨
    void _deleteAllocateData();
    void _insertAllocateData(AllocateData... allocateDatas);
    void _insertAllocateMaterial(AllocateData.AllocateMaterial... allocateMaterials);
    List<AllocateData> _selectFinishedAllocateByDate(String syncDate);
    void _deleteAllocateDataById(int allocateId);
    /* ----------------同步数据------------- */

    /* ---------------本地接口，数据操作----------------- */
    // 盘点
    Maybe<List<InventoryData>> _listInventory(int page);
    InventoryData _selectOneInventory(int checkId);
    void _saveInventoryResult(InventoryData inventoryData);
    // 入库
    Maybe<List<InboundData>> _listInbound(int page);
    InboundData _selectOneInbound(int inId);
    void _saveInboundResult(InboundData inboundData);
    // 出库
    Maybe<List<OutboundData>> _listOutbound(int page);
    OutboundData _selectOneOutbound(int outId);
    void _saveOutboundResult(OutboundData outboundData);
    // 移库
    Maybe<List<MovementData>> _listMovement(int page);
    MovementData _selectOneMovement(int moveId);
    void _saveMovementResult(MovementData movementData);
    // 调拨
    Maybe<List<AllocateData>> _listAllocate(int page);
    AllocateData _selectOneAllocate(int allocateId);
    void _saveAllocateResult(AllocateData allocateData);
    /* ---------------本地接口，数据操作----------------- */
}
