package com.victor.base.data.Repository;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.victor.base.data.entity.AllocateData;
import com.victor.base.data.entity.BusinessReminder;
import com.victor.base.data.entity.DeptData;
import com.victor.base.data.entity.DeviceData;
import com.victor.base.data.entity.HomeTotalData;
import com.victor.base.data.entity.IllegalTakeout;
import com.victor.base.data.entity.InboundData;
import com.victor.base.data.entity.InventoryData;
import com.victor.base.data.entity.LocationBean;
import com.victor.base.data.entity.MaterialsData;
import com.victor.base.data.entity.MaterialsStatisticsData;
import com.victor.base.data.entity.MovementData;
import com.victor.base.data.entity.OutboundData;
import com.victor.base.data.entity.RfidsBean;
import com.victor.base.data.entity.StatisticsInfo;
import com.victor.base.data.entity.SyncInfo;
import com.victor.base.data.entity.TokenBean;
import com.victor.base.data.entity.UserData;
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
    public Observable<ListResponse<List<MaterialsData>>> listMaterials(int page, String materialStatus, String materialName, String rfidCode) {
        return mHttpDataSource.listMaterials(page, materialStatus, materialName, rfidCode);
    }

    @Override
    public Observable<ListResponse<List<MaterialsData>>> listAllMaterials(int page, int pageSize) {
        return mHttpDataSource.listAllMaterials(page, pageSize);
    }

    @Override
    public Observable<ListResponse<List<InventoryData>>> listInventory(int page) {
        return mHttpDataSource.listInventory(page);
    }

    @Override
    public Observable<ListResponse<List<InventoryData>>> listAllInventory(int page) {
        return mHttpDataSource.listAllInventory(page);
    }

    @Override
    public Observable<ListResponse<List<InboundData>>> listInbound(int page) {
        return mHttpDataSource.listInbound(page);
    }

    @Override
    public Observable<ListResponse<List<InboundData>>> listAllInbound(int page) {
        return mHttpDataSource.listAllInbound(page);
    }

    @Override
    public Observable<ListResponse<List<OutboundData>>> listOutbound(int page) {
        return mHttpDataSource.listOutbound(page);
    }

    @Override
    public Observable<ListResponse<List<OutboundData>>> listAllOutbound(int page) {
        return mHttpDataSource.listAllOutbound(page);
    }

    @Override
    public Observable<ListResponse<List<MovementData>>> listMovement(int page) {
        return mHttpDataSource.listMovement(page);
    }

    @Override
    public Observable<ListResponse<List<MovementData>>> listAllMovement(int page) {
        return mHttpDataSource.listAllMovement(page);
    }

    @Override
    public Observable<ListResponse<List<AllocateData>>> listAllocate(int page) {
        return mHttpDataSource.listAllocate(page);
    }

    @Override
    public Observable<ListResponse<List<AllocateData>>> listAllAllocate(int page) {
        return mHttpDataSource.listAllAllocate(page);
    }

    @Override
    public Observable<BaseResponse<InventoryData>> selectByCheck(int checkId) {
        return mHttpDataSource.selectByCheck(checkId);
    }

    @Override
    public Observable<BaseResponse<InboundData>> selectByInbound(int inId) {
        return mHttpDataSource.selectByInbound(inId);
    }

    @Override
    public Observable<BaseResponse<OutboundData>> selectByOutbound(int outId) {
        return mHttpDataSource.selectByOutbound(outId);
    }

    @Override
    public Observable<BaseResponse<MovementData>> selectByMovement(int movementId) {
        return mHttpDataSource.selectByMovement(movementId);
    }

    @Override
    public Observable<BaseResponse<AllocateData>> selectByAllocate(int allocateId) {
        return mHttpDataSource.selectByAllocate(allocateId);
    }

    @Override
    public Observable<BaseResponse> saveCheckedResult(InventoryData inventoryData) {
        return mHttpDataSource.saveCheckedResult(inventoryData);
    }

    @Override
    public Observable<BaseResponse> saveInboundResult(InboundData inboundData) {
        return mHttpDataSource.saveInboundResult(inboundData);
    }

    @Override
    public Observable<BaseResponse> saveOutboundResult(OutboundData outboundDetail) {
        return mHttpDataSource.saveOutboundResult(outboundDetail);
    }

    @Override
    public Observable<BaseResponse> saveMovementResult(MovementData movementDetail) {
        return mHttpDataSource.saveMovementResult(movementDetail);
    }

    @Override
    public Observable<BaseResponse> saveAllocateResult(AllocateData allocateData) {
        return mHttpDataSource.saveAllocateResult(allocateData);
    }

    @Override
    public Observable<StatisticsInfo> getStatisticsInfo() {
        return mHttpDataSource.getStatisticsInfo();
    }

    @Override
    public Observable<List<BusinessReminder>> getBusinessReminder() {
        return mHttpDataSource.getBusinessReminder();
    }

    @Override
    public Observable<List<IllegalTakeout>> getIllegalTakeout() {
        return mHttpDataSource.getIllegalTakeout();
    }

    @Override
    public Observable<List<HomeTotalData>> getTotalData() {
        return mHttpDataSource.getTotalData();
    }

    @Override
    public Observable<List<DeviceData>> getDeviceData() {
        return mHttpDataSource.getDeviceData();
    }

    @Override
    public Observable<BaseResponse<MaterialsStatisticsData>> getMaterialsStatistics() {
        return mHttpDataSource.getMaterialsStatistics();
    }

    @Override
    public Observable<ListResponse<List<UserData>>> getUserList() {
        return mHttpDataSource.getUserList();
    }

    @Override
    public Observable<BaseResponse<List<DeptData>>> getDeptList() {
        return mHttpDataSource.getDeptList();
    }

    @Override
    public Observable<BaseResponse<List<InboundData.InboundElecMaterial>>> getInMaterialListByRfids(RfidsBean rfidsBean) {
        return mHttpDataSource.getInMaterialListByRfids(rfidsBean);
    }

    @Override
    public Observable<BaseResponse<List<InventoryData.InventoryElecMaterial>>> getInventoryMaterialListByRfids(RfidsBean rfidsBean) {
        return mHttpDataSource.getInventoryMaterialListByRfids(rfidsBean);
    }

    @Override
    public Observable<BaseResponse<List<OutboundData.OutboundElecMaterial>>> getOutMaterialListByRfids(RfidsBean rfidsBean) {
        return mHttpDataSource.getOutMaterialListByRfids(rfidsBean);
    }

    @Override
    public Observable<BaseResponse<LocationBean>> getLocationByRfids(String code) {
        return mHttpDataSource.getLocationByRfids(code);
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
    public void _saveSyncDate(SyncInfo syncInfo) {
        mLocalDataSource._saveSyncDate(syncInfo);
    }

    @Override
    public SyncInfo _getSyncById(int syncId) {
        return mLocalDataSource._getSyncById(syncId);
    }

    @Override
    public void _deleteInventoryData() {
        mLocalDataSource._deleteInventoryData();
    }

    @Override
    public void _insertInventoryData(InventoryData... inventoryDatas) {
        mLocalDataSource._insertInventoryData(inventoryDatas);
    }

    @Override
    public void _insertInventoryElecMaterial(InventoryData.InventoryElecMaterial... inventoryElecMaterials) {
        mLocalDataSource._insertInventoryElecMaterial(inventoryElecMaterials);
    }

    @Override
    public void _deleteInboundData() {
        mLocalDataSource._deleteInboundData();
    }

    @Override
    public void _insertInboundData(InboundData... inboundDatas) {
        mLocalDataSource._insertInboundData(inboundDatas);
    }

    @Override
    public void _insertInboundElecMaterial(InboundData.InboundElecMaterial... inboundElecMaterials) {
        mLocalDataSource._insertInboundElecMaterial(inboundElecMaterials);
    }

    @Override
    public Maybe<List<InventoryData>> _listInventory(int page) {
        return mLocalDataSource._listInventory(page);
    }

    @Override
    public InventoryData _selectOneInventory(int checkId) {
        return mLocalDataSource._selectOneInventory(checkId);
    }

    @Override
    public void _saveInventoryResult(InventoryData inventoryData) {
        mLocalDataSource._saveInventoryResult(inventoryData);
    }

    @Override
    public List<InventoryData> _selectFinishedInventoryByDate(String syncDate) {
        return mLocalDataSource._selectFinishedInventoryByDate(syncDate);
    }

    @Override
    public void _deleteInventoryDataById(int checkId) {
        mLocalDataSource._deleteInventoryDataById(checkId);
    }

    @Override
    public Maybe<List<InboundData>> _listInbound(int page) {
        return mLocalDataSource._listInbound(page);
    }

    @Override
    public InboundData _selectOneInbound(int inId) {
        return mLocalDataSource._selectOneInbound(inId);
    }

    @Override
    public void _saveInboundResult(InboundData inboundData) {
        mLocalDataSource._saveInboundResult(inboundData);
    }

    @Override
    public List<InboundData> _selectFinishedInboundByDate(String syncDate) {
        return mLocalDataSource._selectFinishedInboundByDate(syncDate);
    }

    @Override
    public void _deleteInboundDataById(int inId) {
        mLocalDataSource._deleteInboundDataById(inId);
    }

    @Override
    public void _deleteOutboundData() {
        mLocalDataSource._deleteOutboundData();
    }

    @Override
    public void _insertOutboundData(OutboundData... outboundDatas) {
        mLocalDataSource._insertOutboundData(outboundDatas);
    }

    @Override
    public void _insertOutboundElecMaterial(OutboundData.OutboundElecMaterial... outboundElecMaterials) {
        mLocalDataSource._insertOutboundElecMaterial(outboundElecMaterials);
    }

    @Override
    public Maybe<List<OutboundData>> _listOutbound(int page) {
        return mLocalDataSource._listOutbound(page);
    }

    @Override
    public OutboundData _selectOneOutbound(int outId) {
        return mLocalDataSource._selectOneOutbound(outId);
    }

    @Override
    public void _saveOutboundResult(OutboundData outboundData) {
        mLocalDataSource._saveOutboundResult(outboundData);
    }

    @Override
    public Maybe<List<MovementData>> _listMovement(int page) {
        return mLocalDataSource._listMovement(page);
    }

    @Override
    public MovementData _selectOneMovement(int moveId) {
        return mLocalDataSource._selectOneMovement(moveId);
    }

    @Override
    public void _saveMovementResult(MovementData movementData) {
        mLocalDataSource._saveMovementResult(movementData);
    }

    @Override
    public Maybe<List<AllocateData>> _listAllocate(int page) {
        return mLocalDataSource._listAllocate(page);
    }

    @Override
    public AllocateData _selectOneAllocate(int allocateId) {
        return mLocalDataSource._selectOneAllocate(allocateId);
    }

    @Override
    public void _saveAllocateResult(AllocateData allocateData) {
        mLocalDataSource._saveAllocateResult(allocateData);
    }

    @Override
    public Maybe<List<MaterialsData>> _listMaterials(int page, String materialStatus, String materialName, String rfidCode) {
        return mLocalDataSource._listMaterials(page, materialStatus, materialName, rfidCode);
    }

    @Override
    public List<OutboundData> _selectFinishedOutboundByDate(String syncDate) {
        return mLocalDataSource._selectFinishedOutboundByDate(syncDate);
    }

    @Override
    public void _deleteOutboundDataById(int outId) {
        mLocalDataSource._deleteOutboundDataById(outId);
    }

    @Override
    public void _deleteMovementData() {
        mLocalDataSource._deleteMovementData();
    }

    @Override
    public void _insertMovementData(MovementData... movementDatas) {
        mLocalDataSource._insertMovementData(movementDatas);
    }

    @Override
    public void _insertMovementElecMaterial(MovementData.MovementElecMaterial... movementElecMaterials) {
        mLocalDataSource._insertMovementElecMaterial(movementElecMaterials);
    }

    @Override
    public List<MovementData> _selectFinishedMovementByDate(String syncDate) {
        return mLocalDataSource._selectFinishedMovementByDate(syncDate);
    }

    @Override
    public void _deleteMovementDataById(int moveId) {
        mLocalDataSource._deleteMovementDataById(moveId);
    }

    @Override
    public void _deleteAllocateData() {
        mLocalDataSource._deleteAllocateData();
    }

    @Override
    public void _insertAllocateData(AllocateData... allocateDatas) {
        mLocalDataSource._insertAllocateData(allocateDatas);
    }

    @Override
    public void _insertAllocateMaterial(AllocateData.AllocateMaterial... allocateMaterials) {
        mLocalDataSource._insertAllocateMaterial(allocateMaterials);
    }

    @Override
    public List<AllocateData> _selectFinishedAllocateByDate(String syncDate) {
        return mLocalDataSource._selectFinishedAllocateByDate(syncDate);
    }

    @Override
    public void _deleteAllocateDataById(int allocateId) {
        mLocalDataSource._deleteAllocateDataById(allocateId);
    }

    @Override
    public void _deleteMaterialsData() {
        mLocalDataSource._deleteMaterialsData();
    }

    @Override
    public void _insertMaterialsData(MaterialsData... materialsDatas) {
        mLocalDataSource._insertMaterialsData(materialsDatas);
    }
}
