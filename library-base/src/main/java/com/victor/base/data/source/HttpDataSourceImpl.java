package com.victor.base.data.source;

import com.victor.base.app.Injection;
import com.victor.base.data.entity.AllocateData;
import com.victor.base.data.entity.AllocateDetail;
import com.victor.base.data.entity.InboundData;
import com.victor.base.data.entity.InboundDetail;
import com.victor.base.data.entity.InventoryData;
import com.victor.base.data.entity.MaterialsData;
import com.victor.base.data.entity.MovementData;
import com.victor.base.data.entity.MovementDetail;
import com.victor.base.data.entity.OutboundData;
import com.victor.base.data.entity.OutboundDetail;
import com.victor.base.data.entity.InventoryData;
import com.victor.base.data.entity.TokenBean;
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
    public Observable<ListResponse<List<InventoryData>>> listTakeStock(int pageNum) {
        return apiService.listTakeStock(pageNum, 10);
    }

    @Override
    public Observable<ListResponse<List<InventoryData>>> listAllTakeStock(int pageNum) {
        return apiService.listTakeStock(pageNum, 100000);
    }

    @Override
    public Observable<ListResponse<List<InboundData>>> listInbound(int pageNum) {
        return apiService.listInbound(pageNum, 10);
    }

    @Override
    public Observable<ListResponse<List<OutboundData>>> listOutbound(int pageNum) {
        return apiService.listOutbound(pageNum, 10);
    }

    @Override
    public Observable<ListResponse<List<MovementData>>> listMovement(int pageNum) {
        return apiService.listMovement(pageNum, 10);
    }

    @Override
    public Observable<ListResponse<List<AllocateData>>> listAllocate(int pageNum) {
        return apiService.listAllocate(pageNum, 10);
    }

    @Override
    public Observable<BaseResponse<InventoryData>> selectByCheck(int checkId) {
        return apiService.selectByCheck(checkId);
    }

    @Override
    public Observable<BaseResponse<InboundDetail>> selectByInbound(int inId) {
        return apiService.selectByInbound(inId);
    }

    @Override
    public Observable<BaseResponse<OutboundDetail>> selectByOutbound(int outId) {
        return apiService.selectByOutbound(outId);
    }

    @Override
    public Observable<BaseResponse<MovementDetail>> selectByMovement(int movementId) {
        return apiService.selectByMovement(movementId);
    }

    @Override
    public Observable<BaseResponse<AllocateDetail>> selectByAllocate(int allocateId) {
        return apiService.selectByAllocate(allocateId);
    }

    @Override
    public Observable<BaseResponse> saveCheckedResult(InventoryData mainInfo) {
        return apiService.saveCheckedResult(mainInfo);
    }

    @Override
    public Observable<BaseResponse> saveInboundResult(InboundDetail inboundDetail) {
        return apiService.saveInboundResult(inboundDetail);
    }

    @Override
    public Observable<BaseResponse> saveOutboundResult(OutboundDetail outboundDetail) {
        return apiService.saveOutboundResult(outboundDetail);
    }

    @Override
    public Observable<BaseResponse> saveMovementResult(MovementDetail movementDetail) {
        return apiService.saveMovementResult(movementDetail);
    }

    @Override
    public Observable<BaseResponse> saveAllocateResult(AllocateDetail allocateDetail) {
        return apiService.saveAllocateResult(allocateDetail);
    }
}
