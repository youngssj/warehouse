package com.victor.base.data.Repository;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.victor.base.data.entity.AllocateData;
import com.victor.base.data.entity.AllocateDetail;
import com.victor.base.data.entity.AssetApproveOdd;
import com.victor.base.data.entity.AssetCheckData;
import com.victor.base.data.entity.AssetCheckOdd;
import com.victor.base.data.entity.AssetData;
import com.victor.base.data.entity.AssetInspectionData;
import com.victor.base.data.entity.AssetInspectionOdd;
import com.victor.base.data.entity.AssetLocation;
import com.victor.base.data.entity.AssetRepairData;
import com.victor.base.data.entity.AssetRepairOdd;
import com.victor.base.data.entity.InboundData;
import com.victor.base.data.entity.InboundDetail;
import com.victor.base.data.entity.LocationInfo;
import com.victor.base.data.entity.MaterialsData;
import com.victor.base.data.entity.MovementData;
import com.victor.base.data.entity.MovementDetail;
import com.victor.base.data.entity.OddNum;
import com.victor.base.data.entity.OutboundData;
import com.victor.base.data.entity.OutboundDetail;
import com.victor.base.data.entity.SyncInfo;
import com.victor.base.data.entity.TakeStockData;
import com.victor.base.data.entity.TakeStockDetail;
import com.victor.base.data.entity.TokenBean;
import com.victor.base.data.entity.UserInfoBean;
import com.victor.base.data.http.ListResponse;
import com.victor.base.data.source.HttpDataSource;
import com.victor.base.data.source.LocalDataSource;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import me.goldze.mvvmhabit.base.BaseModel;
import me.goldze.mvvmhabit.http.BaseResponse;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/9/9
 * 邮箱：jxfengmtx@gmail.com
 * <p>
 * MVVM的Model层，统一模块的数据仓库，包含网络数据和本地数据（一个应用可以有多个Repository）
 */
public class AppRepository extends BaseModel implements HttpDataSource, LocalDataSource {
    private volatile static AppRepository INSTANCE = null;
    private final HttpDataSource mHttpDataSource;

    private final LocalDataSource mLocalDataSource;

    private AppRepository(@NonNull HttpDataSource httpDataSource,
                          @NonNull LocalDataSource localDataSource) {
        this.mHttpDataSource = httpDataSource;
        this.mLocalDataSource = localDataSource;
    }

    public static AppRepository getInstance(HttpDataSource httpDataSource,
                                            LocalDataSource localDataSource) {
        if (INSTANCE == null) {
            synchronized (AppRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppRepository(httpDataSource, localDataSource);
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }


    @Override
    public Observable<BaseResponse<TokenBean>> login(String username, String pwd) {
        return mHttpDataSource.login(username, pwd);
    }

    @Override
    public Observable<BaseResponse<UserInfoBean>> userInfo() {
        return mHttpDataSource.userInfo();
    }

    @Override
    public Observable<BaseResponse<List<AssetCheckOdd>>> listCheck(int page) {
        return mHttpDataSource.listCheck(page);
    }

    @Override
    public Observable<ListResponse<List<TakeStockData>>> listTakeStock(int page) {
        return mHttpDataSource.listTakeStock(page);
    }

    @Override
    public Observable<ListResponse<List<InboundData>>> listInbound(int page) {
        return mHttpDataSource.listInbound(page);
    }

    @Override
    public Observable<ListResponse<List<OutboundData>>> listOutbound(int page) {
        return mHttpDataSource.listOutbound(page);
    }

    @Override
    public Observable<ListResponse<List<MovementData>>> listMovement(int page) {
        return mHttpDataSource.listMovement(page);
    }

    @Override
    public Observable<ListResponse<List<AllocateData>>> listAllocate(int page) {
        return mHttpDataSource.listAllocate(page);
    }

    @Override
    public Observable<ListResponse<List<TakeStockData>>> listAllTakeStock(int page) {
        return mHttpDataSource.listAllTakeStock(page);
    }

    @Override
    public Observable<BaseResponse<AssetCheckData>> selectOneCheck(int checkId) {
        return mHttpDataSource.selectOneCheck(checkId);
    }

    @Override
    public Observable<BaseResponse<TakeStockDetail>> selectByCheck(int checkId) {
        return mHttpDataSource.selectByCheck(checkId);
    }

    @Override
    public Observable<BaseResponse<InboundDetail>> selectByInbound(int inId) {
        return mHttpDataSource.selectByInbound(inId);
    }

    @Override
    public Observable<BaseResponse<OutboundDetail>> selectByOutbound(int outId) {
        return mHttpDataSource.selectByOutbound(outId);
    }

    @Override
    public Observable<BaseResponse<MovementDetail>> selectByMovement(int movementId) {
        return mHttpDataSource.selectByMovement(movementId);
    }

    @Override
    public Observable<BaseResponse<AllocateDetail>> selectByAllocate(int allocateId) {
        return mHttpDataSource.selectByAllocate(allocateId);
    }

    @Override
    public Observable<BaseResponse> saveCheckResult(int checkId, String checkPks, String noCheckPks, String batchNumber, String surplusCheckPks) {
        return mHttpDataSource.saveCheckResult(checkId, checkPks, noCheckPks, batchNumber, surplusCheckPks);
    }

    @Override
    public Observable<BaseResponse> saveCheckedResult(TakeStockDetail mainInfo) {
        return mHttpDataSource.saveCheckedResult(mainInfo);
    }

    @Override
    public Observable<BaseResponse> saveInboundResult(InboundDetail inboundDetail) {
        return mHttpDataSource.saveInboundResult(inboundDetail);
    }

    @Override
    public Observable<BaseResponse> saveOutboundResult(OutboundDetail outboundDetail) {
        return mHttpDataSource.saveOutboundResult(outboundDetail);
    }

    @Override
    public Observable<BaseResponse> saveMovementResult(MovementDetail movementDetail) {
        return mHttpDataSource.saveMovementResult(movementDetail);
    }

    @Override
    public Observable<BaseResponse> saveAllocateResult(AllocateDetail allocateDetail) {
        return mHttpDataSource.saveAllocateResult(allocateDetail);
    }

    @Override
    public Observable<BaseResponse<List<AssetInspectionOdd>>> listInspection(int page) {
        return mHttpDataSource.listInspection(page);
    }


    @Override
    public Observable<BaseResponse<AssetInspectionData>> selectOneInspection(int inspectionId) {
        return mHttpDataSource.selectOneInspection(inspectionId);
    }

    @Override
    public Observable<BaseResponse> saveInspection(int inspectionId, String batchNumber, String inspectionDate) {
        return mHttpDataSource.saveInspection(inspectionId, batchNumber, inspectionDate);
    }

    @Override
    public Observable<BaseResponse> saveInspectionResult(int inspectionDetailsId, int inspectionResult, String inspectionContent) {
        return mHttpDataSource.saveInspectionResult(inspectionDetailsId, inspectionResult, inspectionContent);
    }

    @Override
    public Observable<BaseResponse<List<AssetRepairOdd>>> listRepair(int page) {
        return mHttpDataSource.listRepair(page);
    }

    @Override
    public Observable<BaseResponse<AssetRepairData>> selectOneRepair(int repairId) {
        return mHttpDataSource.selectOneRepair(repairId);
    }

    @Override
    public Observable<BaseResponse> saveRepair(int repairId, String batchNumber) {
        return mHttpDataSource.saveRepair(repairId, batchNumber);
    }

    @Override
    public Observable<BaseResponse> saveRepairResult(int repairDetailId, int materialId, double repairCosts, String inspectionContent) {
        return mHttpDataSource.saveRepairResult(repairDetailId, materialId, repairCosts, inspectionContent);
    }

    @Override
    public Observable<BaseResponse<List<OddNum>>> selectNum() {
        return mHttpDataSource.selectNum();
    }

    @Override
    public Observable<BaseResponse<List<AssetLocation>>> materialLocation(List<LocationInfo> locationInfoList) {
        return mHttpDataSource.materialLocation(locationInfoList);
    }

    @Override
    public Observable<BaseResponse<List<AssetData>>> rfidToMaterialInfo(List<String> epcs) {
        return mHttpDataSource.rfidToMaterialInfo(epcs);
    }

    @Override
    public Observable<BaseResponse<List<AssetData>>> barCodeToMaterialInfo(String barCode) {
        return mHttpDataSource.barCodeToMaterialInfo(barCode);
    }

    @Override
    public Observable<BaseResponse<List<AssetApproveOdd>>> listPage(int approveLevel) {
        return mHttpDataSource.listPage(approveLevel);
    }

    @Override
    public Observable<BaseResponse<AssetApproveOdd>> selectOne(String batchNumber) {
        return mHttpDataSource.selectOne(batchNumber);
    }

    @Override
    public Observable<BaseResponse<String>> borrowMaterialStr(String batchNumber) {
        return mHttpDataSource.borrowMaterialStr(batchNumber);
    }

    @Override
    public Observable<BaseResponse<List<AssetCheckData.DataListBean>>> selectMany(String detailDataIndex) {
        return mHttpDataSource.selectMany(detailDataIndex);
    }

    @Override
    public Observable<BaseResponse> assAgree(String pks, String approveNote) {
        return mHttpDataSource.assAgree(pks, approveNote);
    }

    @Override
    public Observable<BaseResponse> assReject(String pks, String approveNote) {
        return mHttpDataSource.assReject(pks, approveNote);
    }

    @Override
    public Observable<BaseResponse<List<AssetData>>> queryMaterialList() {
        return mHttpDataSource.queryMaterialList();
    }

    @Override
    public Observable<ListResponse<List<MaterialsData>>> listMaterials(int page, String materialStatus, String materialName, String rfidCode) {
        return mHttpDataSource.listMaterials(page, materialStatus, materialName, rfidCode);
    }

    @Override
    public void saveUserName2Local(String userName) {
        mLocalDataSource.saveUserName2Local(userName);
    }

    public void savePassword2Local(String password) {
        mLocalDataSource.savePassword2Local(password);
    }

    @Override
    public String getUserName() {
        return mLocalDataSource.getUserName();
    }

    @Override
    public String getPassword() {
        return mLocalDataSource.getPassword();
    }

    public void saveToken2Local(String token) {
        mLocalDataSource.saveToken2Local(token);
    }

    @Override
    public String getToken() {
        return mLocalDataSource.getToken();
    }

    @Override
    public void _saveIp(String ip) {
        mLocalDataSource._saveIp(ip);
    }

    @Override
    public String _getIp() {
        return mLocalDataSource._getIp();
    }

    @Override
    public void _savePort(String port) {
        mLocalDataSource._savePort(port);
    }

    @Override
    public String _getPort() {
        return mLocalDataSource._getPort();
    }

    @Override
    public void _saveConfig(boolean config) {
        mLocalDataSource._saveConfig(config);
    }

    @Override
    public boolean _getConfig() {
        return mLocalDataSource._getConfig();
    }

    @Override
    public void _deleteAll() {
        mLocalDataSource._deleteAll();
    }

    @Override
    public void _deleteCheckData() {
        mLocalDataSource._deleteCheckData();
    }

    @Override
    public void _deleteTakeStockData() {
        mLocalDataSource._deleteTakeStockData();
    }

    @Override
    public void _deleteInspectionData() {
        mLocalDataSource._deleteInspectionData();
    }

    @Override
    public void _insertAssetCheckOdd(AssetCheckOdd... assetCheckOdds) {
        mLocalDataSource._insertAssetCheckOdd(assetCheckOdds);
    }

    @Override
    public void _insertTakeStockData(TakeStockData... takeStockDatas) {
        mLocalDataSource._insertTakeStockData(takeStockDatas);
    }

    @Override
    public void _insertAssetCheckData(AssetCheckData.DataListBean... dataListBeans) {
        mLocalDataSource._insertAssetCheckData(dataListBeans);
    }

    @Override
    public void _insertElecMaterial(TakeStockDetail.ElecMaterialListDTO... elecMaterialListDTOS) {
        mLocalDataSource._insertElecMaterial(elecMaterialListDTOS);
    }

    @Override
    public void _insertAssetData(AssetData... assetDatas) {
        mLocalDataSource._insertAssetData(assetDatas);
    }

    @Override
    public void _saveSyncDate(SyncInfo syncInfo) {
        mLocalDataSource._saveSyncDate(syncInfo);
    }

    @Override
    public SyncInfo _getSyncDate(int syncId) {
        return mLocalDataSource._getSyncDate(syncId);
    }

    @Override
    public List<AssetCheckData.DataListBean> _selectCheckDetailAll(String lastDate) {
        return mLocalDataSource._selectCheckDetailAll(lastDate);
    }

    @Override
    public List<AssetCheckOdd> _selectCheckOddAll(String lastDate) {
        return mLocalDataSource._selectCheckOddAll(lastDate);
    }

    @Override
    public Maybe<List<AssetCheckOdd>> _listCheck(int page) {
        return mLocalDataSource._listCheck(page);
    }

    @Override
    public void _saveCheckResult(int checkId, String checkPks, String noCheckPks, String batchNumber,List<AssetCheckData.DataListBean> surplusCheckPks) {
        mLocalDataSource._saveCheckResult(checkId, checkPks, noCheckPks, batchNumber,surplusCheckPks);
    }

    @Override
    public AssetCheckData _selectOneCheck(int checkId) {
        return mLocalDataSource._selectOneCheck(checkId);
    }

    @Override
    public List<AssetData> _rfidToMaterialInfo(List<String> epcs) {
        return mLocalDataSource._rfidToMaterialInfo(epcs);
    }

    @Override
    public List<AssetData> _barCodeToMaterialInfo(String barCode) {
        return mLocalDataSource._barCodeToMaterialInfo(barCode);
    }

    // 巡检
    @Override
    public Maybe<List<AssetInspectionOdd>> _listInspection(int page) {
        return mLocalDataSource._listInspection(page);
    }

    @Override
    public void _saveInspectionResult(int inspectionId, int result, String inspectionContent) {
        mLocalDataSource._saveInspectionResult(inspectionId,result,inspectionContent);
    }

    @Override
    public AssetInspectionData _selectInspectionDataById(int inspectionId) {
        return mLocalDataSource._selectInspectionDataById(inspectionId);
    }

    @Override
    public void _saveInspection(int inspectionId, String batchNumber) {
        mLocalDataSource._saveInspection(inspectionId,batchNumber);
    }

    @Override
    public void _insertAssetInspectionOds(List<AssetInspectionOdd> assetInspectionOddList) {
        mLocalDataSource._insertAssetInspectionOds(assetInspectionOddList);
    }

    @Override
    public List<AssetInspectionData.DataListBean> _selectInspectionDetailAll(String lastDate) {
        return mLocalDataSource._selectInspectionDetailAll(lastDate);
    }

    @Override
    public List<AssetInspectionOdd> _selectInspectionOddAll(String lastDate) {
        return mLocalDataSource._selectInspectionOddAll(lastDate);
    }

    @Override
    public void _insertAssetInspectionData(List<AssetInspectionData.DataListBean> dataListBeans) {
        mLocalDataSource._insertAssetInspectionData(dataListBeans);
    }

    // 维修

    @Override
    public void _deleteRepairData() {
        mLocalDataSource._deleteRepairData();
    }
    @Override
    public void _insertAssetRepairOds(List<AssetRepairOdd> assetRepairOddList) {
        mLocalDataSource._insertAssetRepairOds(assetRepairOddList);
    }

    @Override
    public List<AssetRepairOdd> _selectRepairOddAll(String lastDate) {
        return mLocalDataSource._selectRepairOddAll(lastDate);
    }

    @Override
    public List<AssetRepairData.DataListBean> _selectRepairDetailAll(String lastDate) {
        return mLocalDataSource._selectRepairDetailAll(lastDate);
    }

    @Override
    public void _insertAssetRepairData(List<AssetRepairData.DataListBean> dataListBeans) {
        mLocalDataSource._insertAssetRepairData(dataListBeans);
    }

    @Override
    public void _saveRepairResult(int repairDetailId, int materialId, String repairCosts, String repariContent) {
        mLocalDataSource._saveRepairResult(repairDetailId, materialId, repairCosts, repariContent);
    }

    @Override
    public void _saveRepair(int repairId, String batchNumber) {
        mLocalDataSource._saveRepair(repairId,batchNumber);
    }

    @Override
    public AssetRepairData _selectRepairDataById(int repairId) {
        return mLocalDataSource._selectRepairDataById(repairId);
    }

    // 巡检
    @Override
    public Maybe<List<AssetRepairOdd>> _listRepair(int page) {
        return mLocalDataSource._listRepair(page);
    }
}
