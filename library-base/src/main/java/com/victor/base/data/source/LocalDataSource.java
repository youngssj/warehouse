package com.victor.base.data.source;

import com.victor.base.data.entity.InboundData;
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
    void _deleteInventoryData();
    void _insertInventoryData(InventoryData... inventoryDatas);
    void _insertInventoryElecMaterial(InventoryData.InventoryElecMaterial... inventoryElecMaterials);
    void _deleteInboundData();
    void _insertInboundData(InboundData... inboundDatas);
    void _insertInboundElecMaterial(InboundData.InboundElecMaterial... inboundElecMaterials);
    /* ----------------同步数据------------- */

    /* ---------------本地接口，数据操作----------------- */
    Maybe<List<InventoryData>> _listInventory(int page);

    InventoryData _selectOneInventory(int checkId);

    void _saveInventoryResult(InventoryData inventoryData);

    List<InventoryData> _selectFinishedInventoryByDate(String syncDate);

    void _deleteInventoryDataById(int checkId);
    /* ---------------本地接口，数据操作----------------- */
}
