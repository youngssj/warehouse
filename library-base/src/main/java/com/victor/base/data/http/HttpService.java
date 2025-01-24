package com.victor.base.data.http;

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

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.http.BaseResponse;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/9/9
 * 邮箱：jxfengmtx@gmail.com
 */
public interface HttpService {
    /**
     * 登录
     *
     * @param params
     * @return
     */
//    @POST("/sysAs/login")
    @POST("/dev-api/mobile/login")
    Observable<BaseResponse<TokenBean>> login(@Body Map<String, String> params);

    @GET("/dev-api/system/user/profile")
    Observable<BaseResponse<UserInfoBean>> userInfo();

    /**
     * 盘点单
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("/assAs/listCheck")
    Observable<BaseResponse<List<AssetCheckOdd>>> listCheck(@FieldMap Map<String, String> params);

    /**
     * 盘点单
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GET("/dev-api/mobile/storage/ElecMaterialCheckinfo/list")
    Observable<ListResponse<List<TakeStockData>>> listTakeStock(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /**
     * 入库列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GET("/dev-api/mobile/storage/ElecMaterialIninfo/list")
    Observable<ListResponse<List<InboundData>>> listInbound(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /**
     * 出库列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GET("/dev-api/mobile/storage/ElecMaterialIninfo/list")
    Observable<ListResponse<List<OutboundData>>> listOutbound(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /**
     * 移库列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GET("/dev-api/mobile/storage/ElecMaterialIninfo/list")
    Observable<ListResponse<List<MovementData>>> listMovement(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /**
     * 调拨列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GET("/dev-api/mobile/storage/ElecMaterialIninfo/list")
    Observable<ListResponse<List<AllocateData>>> listAllocate(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /**
     * 盘盈
     *
     * @param params
     * @return
     */
    @POST("/assAs/rfidToMaterialInfo")
    Observable<BaseResponse<List<AssetData>>> rfidToMaterialInfo(@Body List<String> params);

    /**
     * 盘点单详情
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("/assAs/selectOneCheck")
    Observable<BaseResponse<AssetCheckData>> selectOneCheck(@FieldMap Map<String, String> params);

    /**
     * 盘点单详情
     *
     * @param path
     * @return
     */
    @GET("/dev-api/mobile/storage/ElecMaterialCheckinfo/{dynamicPath}")
    Observable<BaseResponse<TakeStockDetail>> selectByCheck(@Path("dynamicPath") int path);

    /**
     * 入库单详情
     *
     * @param path
     * @return
     */
    @GET("/dev-api/mobile/storage/ElecMaterialIninfo/{dynamicPath}")
    Observable<BaseResponse<InboundDetail>> selectByInbound(@Path("dynamicPath") int path);

    /**
     * 出库单详情
     *
     * @param path
     * @return
     */
    @GET("/dev-api/mobile/storage/ElecMaterialIninfo/{dynamicPath}")
    Observable<BaseResponse<InboundDetail>> selectByOutbound(@Path("dynamicPath") int path);

    /**
     * 移库单详情
     *
     * @param path
     * @return
     */
    @GET("/dev-api/mobile/storage/ElecMaterialIninfo/{dynamicPath}")
    Observable<BaseResponse<MovementDetail>> selectByMovement(@Path("dynamicPath") int path);

    /**
     * 调拨单详情
     *
     * @param path
     * @return
     */
    @GET("/dev-api/mobile/storage/ElecMaterialIninfo/{dynamicPath}")
    Observable<BaseResponse<AllocateDetail>> selectByAllocate(@Path("dynamicPath") int path);

    /**
     * 盘点完成
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("/dev-api/mobile/storage/ElecMaterialCheckinfo/completeInfo")
    Observable<BaseResponse> saveCheckResult(@FieldMap Map<String, String> params);

    /**
     * 盘点完成
     *
     * @param mainInfo
     * @return
     */
    @POST("/dev-api/mobile/storage/ElecMaterialCheckinfo/completeInfo")
    Observable<BaseResponse> saveCheckedResult(@Body TakeStockDetail mainInfo);

    /**
     * 入库完成
     *
     * @param inboundDetail
     * @return
     */
    @POST("/dev-api/mobile/storage/ElecMaterialIninfo/completeInfo")
    Observable<BaseResponse> saveInboundResult(@Body InboundDetail inboundDetail);

    /**
     * 出库完成
     *
     * @param outboundDetail
     * @return
     */
    @POST("/dev-api/mobile/storage/ElecMaterialIninfo/completeInfo")
    Observable<BaseResponse> saveOutboundResult(@Body OutboundDetail outboundDetail);

    /**
     * 移库完成
     *
     * @param movementDetail
     * @return
     */
    @POST("/dev-api/mobile/storage/ElecMaterialIninfo/completeInfo")
    Observable<BaseResponse> saveMovementResult(@Body MovementDetail movementDetail);

    /**
     * 调拨完成
     *
     * @param movementDetail
     * @return
     */
    @POST("/dev-api/mobile/storage/ElecMaterialIninfo/completeInfo")
    Observable<BaseResponse> saveAllocateResult(@Body AllocateDetail allocateDetail);

    /**
     * 资产列表
     *
     * @param params
     * @return
     */
    @POST("/assAs/queryMaterialList")
    Observable<BaseResponse<List<AssetData>>> queryMaterialList(@Body Map<String, String> params);


    /**
     * 巡检单
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("/assAs/listInspection")
    Observable<BaseResponse<List<AssetInspectionOdd>>> listInspection(@FieldMap Map<String, String> params);

    /**
     * 巡检单详情
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("/assAs/selectOneInspection")
    Observable<BaseResponse<AssetInspectionData>> selectOneInspection(@FieldMap Map<String, String> params);

    /**
     * 巡检完成
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("/assAs/saveInspection")
    Observable<BaseResponse> saveInspection(@FieldMap Map<String, String> params);

    /**
     * 保存巡检结果
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("/assAs/saveInspectionResult")
    Observable<BaseResponse> saveInspectionResult(@FieldMap Map<String, String> params);

    /**
     * 维修单
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("/assAs/listRepair")
    Observable<BaseResponse<List<AssetRepairOdd>>> listRepair(@FieldMap Map<String, String> params);

    /**
     * 巡检单详情
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("/assAs/selectOneRepair")
    Observable<BaseResponse<AssetRepairData>> selectOneRepair(@FieldMap Map<String, String> params);

    /**
     * 维修完成
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("/assAs/saveRepairResult")
    Observable<BaseResponse> saveRepairResult(@FieldMap Map<String, String> params);


    /**
     * 保存维修结果
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("/assAs/saveRepair")
    Observable<BaseResponse> saveRepair(@FieldMap Map<String, String> params);

    /**
     * 单子数量
     *
     * @return
     */
    @POST("/assAs/selectNum")
    Observable<BaseResponse<List<OddNum>>> selectNum();

    /**
     * 资产定位
     *
     * @return
     */
    @POST("/assAs/materialLocation")
    Observable<BaseResponse<List<AssetLocation>>> materialLocation(@Body List<LocationInfo> locationInfoList);


    /**
     * 资产审批
     *
     * @return
     */
    @FormUrlEncoded
    @POST("/api/sys/SysApproveActivity/listPage")
    Observable<BaseResponse<List<AssetApproveOdd>>> listPage(@FieldMap Map<String, String> params);


    /**
     * 资产审批上半部分
     *
     * @return
     */
    @GET("/assBorrow/selectOne")
    Observable<BaseResponse<AssetApproveOdd>> selectOne(@Query("pk") String params);

    /**
     * 资产审批列表id
     *
     * @return
     */
    @FormUrlEncoded
    @POST("/assBorrow/borrowMaterialStr")
    Observable<BaseResponse<String>> borrowMaterialStr(@FieldMap Map<String, String> params);


    /**
     * 资产审批列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST("/assMaterial/selectMany")
    Observable<BaseResponse<List<AssetCheckData.DataListBean>>> selectMany(@FieldMap Map<String, String> params);


    /**
     * 资产审批列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST("/SysApproveActivity/assAgree")
    Observable<BaseResponse> assAgree(@FieldMap Map<String, String> params);


    /**
     * 资产审批列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST("/SysApproveActivity/assReject")
    Observable<BaseResponse> assReject(@FieldMap Map<String, String> params);

    /**
     * 物资查询列表
     * @param materialStatus
     * @param materialName
     * @param rfidCode
     * @return
     */
    @GET("/dev-api/mobile/storage/elecMaterial/list")
    Observable<ListResponse<List<MaterialsData>>> listMaterials(@Query("pageNum") int pageNum,
                                                                @Query("pageSize") int pageSize,
                                                                @Query("materialStatus") String materialStatus,
                                                                @Query("materialName") String materialName,
                                                                @Query("rfidCode") String rfidCode);
}
