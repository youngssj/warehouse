package com.victor.main.data.source;

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

    void _deleteCheckData();

    //----------------同步数据-------------

    void _insertAssetCheckOdd(AssetCheckOdd... assetCheckOdds);

    void _insertTakeStockData(TakeStockData... takeStockDatas);

    void _insertAssetCheckData(AssetCheckData.DataListBean... dataListBeans);

    void _insertElecMaterial(TakeStockDetail.ElecMaterialListDTO... elecMaterialListDTOS);

    void _insertAssetData(AssetData... assetDatas);

    void _saveSyncDate(SyncInfo syncInfo);

    SyncInfo _getSyncDate(int syncId);


    //---------------本地接口，数据操作-----------------


    List<AssetCheckData.DataListBean> _selectCheckDetailAll(String lastDate);

    List<AssetCheckOdd> _selectCheckOddAll(String lastDate);

    Maybe<List<AssetCheckOdd>> _listCheck(int page);

    void _saveCheckResult(int checkId, String checkPks, String noCheckPks, String batchNumber,List<AssetCheckData.DataListBean> surplusCheckPks);

    AssetCheckData _selectOneCheck(int checkId);

    List<AssetData> _rfidToMaterialInfo(List<String> epcs);

    List<AssetData> _barCodeToMaterialInfo(String barCode);
    // 巡检
    Maybe<List<AssetInspectionOdd>> _listInspection(int page);

    void _saveInspectionResult(int inspectionId, int result,String inspectionContent);

    void _saveInspection(int inspectionId,String batchNumber);

    AssetInspectionData _selectInspectionDataById(int inspectionId);

    void _insertAssetInspectionOds(List<AssetInspectionOdd> assetInspectionOddList);

    void _insertAssetInspectionData(List<AssetInspectionData.DataListBean> dataListBeans);

    List<AssetInspectionData.DataListBean> _selectInspectionDetailAll(String lastDate);

    List<AssetInspectionOdd> _selectInspectionOddAll(String lastDate);

    void _deleteTakeStockData();

    void _deleteInspectionData();

    void _deleteRepairData();

    void _insertAssetRepairOds(List<AssetRepairOdd> assetRepairOddList);

    void _insertAssetRepairData(List<AssetRepairData.DataListBean> dataListBeans);

    List<AssetRepairOdd> _selectRepairOddAll(String lastDate);

    List<AssetRepairData.DataListBean> _selectRepairDetailAll(String lastDate);

    void _saveRepairResult(int repairDetailId,int materialId,String repairCosts,String  repairContent);

    AssetRepairData _selectRepairDataById(int repairId);

    void _saveRepair(int repairId, String batchNumber);

    Maybe<List<AssetRepairOdd>> _listRepair(int page);
}
