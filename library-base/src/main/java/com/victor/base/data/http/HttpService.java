package com.victor.base.data.http;

import com.victor.base.data.entity.AllocateData;
import com.victor.base.data.entity.AllocateDetail;
import com.victor.base.data.entity.InboundData;
import com.victor.base.data.entity.InboundDetail;
import com.victor.base.data.entity.InventoryData;
import com.victor.base.data.entity.InventoryData;
import com.victor.base.data.entity.MaterialsData;
import com.victor.base.data.entity.MovementData;
import com.victor.base.data.entity.MovementDetail;
import com.victor.base.data.entity.OutboundData;
import com.victor.base.data.entity.OutboundDetail;
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
    @POST("/dev-api/mobile/login")
    Observable<BaseResponse<TokenBean>> login(@Body Map<String, String> params);

    @GET("/dev-api/system/user/profile")
    Observable<BaseResponse<UserInfoBean>> userInfo();

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

    /**
     * 盘点单
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GET("/dev-api/mobile/storage/ElecMaterialCheckinfo/list")
    Observable<ListResponse<List<InventoryData>>> listTakeStock(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

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
    @GET("/dev-api/mobile/storage/ElecMaterialOutinfo/list")
    Observable<ListResponse<List<OutboundData>>> listOutbound(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /**
     * 移库列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GET("/dev-api/mobile/storage/moveinfo/list")
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
     * 盘点单详情
     *
     * @param path
     * @return
     */
    @GET("/dev-api/mobile/storage/ElecMaterialCheckinfo/{dynamicPath}")
    Observable<BaseResponse<InventoryData>> selectByCheck(@Path("dynamicPath") int path);

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
    @GET("/dev-api/mobile/storage/ElecMaterialOutinfo/{dynamicPath}")
    Observable<BaseResponse<OutboundDetail>> selectByOutbound(@Path("dynamicPath") int path);

    /**
     * 移库单详情
     *
     * @param path
     * @return
     */
    @GET("/dev-api/mobile/storage/ElecMaterialMoveinfo/{dynamicPath}")
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
    Observable<BaseResponse> saveCheckedResult(@Body InventoryData mainInfo);

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
    @POST("/dev-api/mobile/storage/ElecMaterialOutinfo/completeInfo")
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
     * @param allocateDetail
     * @return
     */
    @POST("/dev-api/mobile/storage/ElecMaterialIninfo/completeInfo")
    Observable<BaseResponse> saveAllocateResult(@Body AllocateDetail allocateDetail);
}
