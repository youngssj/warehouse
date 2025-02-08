package com.victor.base.data.Repository;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.victor.base.data.entity.AllocateData;
import com.victor.base.data.entity.InboundData;
import com.victor.base.data.entity.InboundDetail;
import com.victor.base.data.entity.InventoryData;
import com.victor.base.data.entity.MaterialsData;
import com.victor.base.data.entity.MovementData;
import com.victor.base.data.entity.MovementDetail;
import com.victor.base.data.entity.OutboundData;
import com.victor.base.data.entity.OutboundDetail;
import com.victor.base.data.entity.SyncInfo;
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
    public Observable<ListResponse<List<MaterialsData>>> listMaterials(int page, String materialStatus, String materialName, String rfidCode) {
        return mHttpDataSource.listMaterials(page, materialStatus, materialName, rfidCode);
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
    public Observable<BaseResponse<InventoryData>> selectByCheck(int checkId) {
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
    public Observable<BaseResponse<AllocateData>> selectByAllocate(int allocateId) {
        return mHttpDataSource.selectByAllocate(allocateId);
    }

    @Override
    public Observable<BaseResponse> saveCheckedResult(InventoryData inventoryData) {
        return mHttpDataSource.saveCheckedResult(inventoryData);
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
    public Observable<BaseResponse> saveAllocateResult(AllocateData allocateData) {
        return mHttpDataSource.saveAllocateResult(allocateData);
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
    public SyncInfo _getSyncDate(int syncId) {
        return mLocalDataSource._getSyncDate(syncId);
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
    public void _insertElecMaterial(InventoryData.InventoryElecMaterial... InventoryElecMaterialS) {
        mLocalDataSource._insertElecMaterial(InventoryElecMaterialS);
    }

    @Override
    public Maybe<List<InventoryData>> _listInventory(int page) {
        return mLocalDataSource._listInventory(page);
    }

    @Override
    public InventoryData _selectOneInventory(int checkId){
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
}
