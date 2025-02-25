package com.victor.base.data.source;

import com.victor.base.app.Injection;
import com.victor.base.data.entity.AllocateData;
import com.victor.base.data.entity.BusinessReminder;
import com.victor.base.data.entity.DeptData;
import com.victor.base.data.entity.DeviceData;
import com.victor.base.data.entity.HomeTotalData;
import com.victor.base.data.entity.IllegalTakeout;
import com.victor.base.data.entity.InboundData;
import com.victor.base.data.entity.InventoryData;
import com.victor.base.data.entity.MaterialBean;
import com.victor.base.data.entity.MaterialsData;
import com.victor.base.data.entity.MaterialsStatisticsData;
import com.victor.base.data.entity.MovementData;
import com.victor.base.data.entity.OutboundData;
import com.victor.base.data.entity.RfidsBean;
import com.victor.base.data.entity.StatisticsInfo;
import com.victor.base.data.entity.TokenBean;
import com.victor.base.data.entity.UserData;
import com.victor.base.data.entity.UserInfoBean;
import com.victor.base.data.http.HttpService;
import com.victor.base.data.http.ListResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.http.BaseResponse;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/9/9
 * 邮箱：jxfengmtx@gmail.com
 */
public class HttpDataSourceImpl implements HttpDataSource {
    private HttpService apiService;
    private volatile static HttpDataSourceImpl INSTANCE = null;

    public static HttpDataSourceImpl getInstance(HttpService apiService) {
        if (INSTANCE == null) {
            synchronized (HttpDataSourceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpDataSourceImpl(apiService);
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private HttpDataSourceImpl(HttpService apiService) {
        this.apiService = apiService;
    }


    @Override
    public Observable<BaseResponse<TokenBean>> login(String username, String pwd) {
        Map<String, String> map = Injection.getMapInstance();
        map.put("username", username);
        map.put("password", pwd);
        return apiService.login(map);
    }

    @Override
    public Observable<BaseResponse<UserInfoBean>> userInfo() {
        return apiService.userInfo();
    }

    @Override
    public Observable<ListResponse<List<MaterialsData>>> listMaterials(int pageNum, String materialStatus, String materialName, String rfidCode) {
        return apiService.listMaterials(pageNum, 10, materialStatus, materialName, rfidCode);
    }

    @Override
    public Observable<ListResponse<List<MaterialsData>>> listAllMaterials(int pageNum, int pageSize) {
        return apiService.listMaterials(pageNum, pageSize, null, null, null);
    }

    @Override
    public Observable<ListResponse<List<InventoryData>>> listInventory(int pageNum) {
        return apiService.listInventory(pageNum, 10);
    }

    @Override
    public Observable<ListResponse<List<InventoryData>>> listAllInventory(int pageNum) {
        return apiService.listInventory(pageNum, 100000);
    }

    @Override
    public Observable<ListResponse<List<InboundData>>> listInbound(int pageNum) {
        return apiService.listInbound(pageNum, 10);
    }

    @Override
    public Observable<ListResponse<List<InboundData>>> listAllInbound(int pageNum) {
        return apiService.listInbound(pageNum, 100000);
    }

    @Override
    public Observable<ListResponse<List<OutboundData>>> listOutbound(int pageNum) {
        return apiService.listOutbound(pageNum, 10);
    }

    @Override
    public Observable<ListResponse<List<OutboundData>>> listAllOutbound(int pageNum) {
        return apiService.listOutbound(pageNum, 100000);
    }

    @Override
    public Observable<ListResponse<List<MovementData>>> listMovement(int pageNum) {
        return apiService.listMovement(pageNum, 10);
    }

    @Override
    public Observable<ListResponse<List<MovementData>>> listAllMovement(int pageNum) {
        return apiService.listMovement(pageNum, 100000);
    }

    @Override
    public Observable<ListResponse<List<AllocateData>>> listAllocate(int pageNum) {
        return apiService.listAllocate(pageNum, 10);
    }

    @Override
    public Observable<ListResponse<List<AllocateData>>> listAllAllocate(int pageNum) {
        return apiService.listAllocate(pageNum, 100000);
    }

    @Override
    public Observable<BaseResponse<InventoryData>> selectByCheck(int checkId) {
        return apiService.selectByCheck(checkId);
    }

    @Override
    public Observable<BaseResponse<InboundData>> selectByInbound(int inId) {
        return apiService.selectByInbound(inId);
    }

    @Override
    public Observable<BaseResponse<OutboundData>> selectByOutbound(int outId) {
        return apiService.selectByOutbound(outId);
    }

    @Override
    public Observable<BaseResponse<MovementData>> selectByMovement(int movementId) {
        return apiService.selectByMovement(movementId);
    }

    @Override
    public Observable<BaseResponse<AllocateData>> selectByAllocate(int allocateId) {
        return apiService.selectByAllocate(allocateId);
    }

    @Override
    public Observable<BaseResponse> saveCheckedResult(InventoryData inventoryData) {
        return apiService.saveCheckedResult(inventoryData);
    }

    @Override
    public Observable<BaseResponse> saveInboundResult(InboundData inboundData) {
        return apiService.saveInboundResult(inboundData);
    }

    @Override
    public Observable<BaseResponse> saveOutboundResult(OutboundData outboundDetail) {
        return apiService.saveOutboundResult(outboundDetail);
    }

    @Override
    public Observable<BaseResponse> saveMovementResult(MovementData movementDetail) {
        return apiService.saveMovementResult(movementDetail);
    }

    @Override
    public Observable<BaseResponse> saveAllocateResult(AllocateData allocateData) {
        return apiService.saveAllocateResult(allocateData);
    }

    @Override
    public Observable<StatisticsInfo> getStatisticsInfo() {
        return apiService.getStatisticsInfo();
    }

    @Override
    public Observable<List<BusinessReminder>> getBusinessReminder() {
        return apiService.getBusinessReminder();
    }

    @Override
    public Observable<List<IllegalTakeout>> getIllegalTakeout() {
        return apiService.getIllegalTakeout();
    }

    @Override
    public Observable<List<HomeTotalData>> getTotalData() {
        return apiService.getTotalData();
    }

    @Override
    public Observable<List<DeviceData>> getDeviceData() {
        return apiService.getDeviceData();
    }

    @Override
    public Observable<BaseResponse<MaterialsStatisticsData>> getMaterialsStatistics() {
        return apiService.getMaterialsStatistics();
    }

    @Override
    public Observable<ListResponse<List<UserData>>> getUserList() {
        return apiService.getUserList();
    }

    @Override
    public Observable<BaseResponse<List<DeptData>>> getDeptList() {
        return apiService.getDeptList();
    }

    @Override
    public Observable<BaseResponse<List<MaterialBean>>> getMaterialListByRfids(RfidsBean rfidsBean) {
        return apiService.getMaterialListByRfids(rfidsBean);
    }
}
