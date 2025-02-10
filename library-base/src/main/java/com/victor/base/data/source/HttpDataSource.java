package com.victor.base.data.source;

import com.victor.base.data.entity.AllocateData;
import com.victor.base.data.entity.InboundData;
import com.victor.base.data.entity.InventoryData;
import com.victor.base.data.entity.MaterialsData;
import com.victor.base.data.entity.MovementData;
import com.victor.base.data.entity.MovementData;
import com.victor.base.data.entity.OutboundData;
import com.victor.base.data.entity.OutboundData;
import com.victor.base.data.entity.TokenBean;
import com.victor.base.data.entity.UserInfoBean;
import com.victor.base.data.http.ListResponse;

import java.util.List;

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
public interface HttpDataSource {
    //登录
    Observable<BaseResponse<TokenBean>> login(String username, String pwd);

    Observable<BaseResponse<UserInfoBean>> userInfo();

    Observable<ListResponse<List<MaterialsData>>> listMaterials(int pageNum, String materialStatus, String materialName, String rfidCode);

    Observable<ListResponse<List<InventoryData>>> listInventory(int page);

    Observable<ListResponse<List<InventoryData>>> listAllInventory(int page);

    Observable<ListResponse<List<InboundData>>> listInbound(int page);

    Observable<ListResponse<List<InboundData>>> listAllInbound(int page);

    Observable<ListResponse<List<OutboundData>>> listOutbound(int page);

    Observable<ListResponse<List<OutboundData>>> listAllOutbound(int page);

    Observable<ListResponse<List<MovementData>>> listMovement(int page);

    Observable<ListResponse<List<MovementData>>> listAllMovement(int page);

    Observable<ListResponse<List<AllocateData>>> listAllocate(int page);

    Observable<BaseResponse<InventoryData>> selectByCheck(int checkId);

    Observable<BaseResponse<InboundData>> selectByInbound(int inId);

    Observable<BaseResponse<OutboundData>> selectByOutbound(int outId);

    Observable<BaseResponse<MovementData>> selectByMovement(int movementId);

    Observable<BaseResponse<AllocateData>> selectByAllocate(int allocateId);

    Observable<BaseResponse> saveCheckedResult(InventoryData inventoryData);

    Observable<BaseResponse> saveInboundResult(InboundData inboundData);

    Observable<BaseResponse> saveOutboundResult(OutboundData outboundDetail);

    Observable<BaseResponse> saveMovementResult(MovementData movementDetail);

    Observable<BaseResponse> saveAllocateResult(AllocateData allocateData);
}
