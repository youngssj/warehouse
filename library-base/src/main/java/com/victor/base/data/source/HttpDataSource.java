package com.victor.base.data.source;

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
import com.victor.base.data.entity.TakeStockData;
import com.victor.base.data.entity.TakeStockDetail;
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

    Observable<BaseResponse<List<AssetCheckOdd>>> listCheck(int page);

    Observable<ListResponse<List<TakeStockData>>> listTakeStock(int page);

    Observable<ListResponse<List<InboundData>>> listInbound(int page);

    Observable<ListResponse<List<OutboundData>>> listOutbound(int page);

    Observable<ListResponse<List<MovementData>>> listMovement(int page);

    Observable<ListResponse<List<AllocateData>>> listAllocate(int page);

    Observable<ListResponse<List<TakeStockData>>> listAllTakeStock(int page);

    Observable<BaseResponse<AssetCheckData>> selectOneCheck(int checkId);

    Observable<BaseResponse<TakeStockDetail>> selectByCheck(int checkId);

    Observable<BaseResponse<InboundDetail>> selectByInbound(int inId);

    Observable<BaseResponse<OutboundDetail>> selectByOutbound(int outId);

    Observable<BaseResponse<MovementDetail>> selectByMovement(int movementId);

    Observable<BaseResponse<AllocateDetail>> selectByAllocate(int allocateId);

    Observable<BaseResponse> saveCheckResult(int checkId, String checkPks, String noCheckPks, String batchNumber, String surplusCheckPks);

    Observable<BaseResponse> saveCheckedResult(TakeStockDetail mainInfo);

    Observable<BaseResponse> saveInboundResult(InboundDetail inboundDetail);

    Observable<BaseResponse> saveOutboundResult(OutboundDetail outboundDetail);

    Observable<BaseResponse> saveMovementResult(MovementDetail movementDetail);

    Observable<BaseResponse> saveAllocateResult(AllocateDetail allocateDetail);

    Observable<BaseResponse<List<AssetInspectionOdd>>> listInspection(int page);

    Observable<BaseResponse<AssetInspectionData>> selectOneInspection(int inspectionId);

    Observable<BaseResponse> saveInspection(int inspectionId, String batchNumber, String inspectionDate);

    Observable<BaseResponse> saveInspectionResult(int inspectionDetailsId, int inspectionResult, String inspectionContent);

    Observable<BaseResponse<List<AssetRepairOdd>>> listRepair(int page);

    Observable<BaseResponse<AssetRepairData>> selectOneRepair(int repairId);

    Observable<BaseResponse> saveRepair(int repairId, String batchNumber);

    Observable<BaseResponse> saveRepairResult(int repairDetailId, int materialId, double repairCosts, String inspectionContent);

    Observable<BaseResponse<List<OddNum>>> selectNum();

    Observable<BaseResponse<List<AssetLocation>>> materialLocation(List<LocationInfo> locationInfoList);

    Observable<BaseResponse<List<AssetData>>> rfidToMaterialInfo(List<String> epcs);

    Observable<BaseResponse<List<AssetData>>> barCodeToMaterialInfo(String barCode);

    Observable<BaseResponse<List<AssetApproveOdd>>> listPage(int approveLevel);

    Observable<BaseResponse<AssetApproveOdd>> selectOne(String batchNumber);

    Observable<BaseResponse<String>> borrowMaterialStr(String batchNumber);

    Observable<BaseResponse<List<AssetCheckData.DataListBean>>> selectMany(String detailDataIndex);

    Observable<BaseResponse> assAgree(String pks, String approveNote);

    Observable<BaseResponse> assReject(String pks, String approveNote);

    Observable<BaseResponse<List<AssetData>>> queryMaterialList();

    Observable<ListResponse<List<MaterialsData>>> listMaterials(int page, String materialStatus, String materialName, String rfidCode);

}
