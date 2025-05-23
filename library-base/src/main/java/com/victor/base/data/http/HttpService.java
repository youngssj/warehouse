package com.victor.base.data.http;

import com.victor.base.data.entity.AllocateData;
import com.victor.base.data.entity.BusinessReminder;
import com.victor.base.data.entity.DeptData;
import com.victor.base.data.entity.DeviceData;
import com.victor.base.data.entity.HomeTotalData;
import com.victor.base.data.entity.IllegalTakeout;
import com.victor.base.data.entity.InboundData;
import com.victor.base.data.entity.InventoryData;
import com.victor.base.data.entity.MaterialsData;
import com.victor.base.data.entity.MaterialsStatisticsData;
import com.victor.base.data.entity.MovementData;
import com.victor.base.data.entity.OutboundData;
import com.victor.base.data.entity.StatisticsInfo;
import com.victor.base.data.entity.TokenBean;
import com.victor.base.data.entity.UserData;
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
    @POST("/mobile/login")
    Observable<BaseResponse<TokenBean>> login(@Body Map<String, String> params);

    @GET("/system/user/profile")
    Observable<BaseResponse<UserInfoBean>> userInfo();

    /**
     * 物资查询列表
     *
     * @param materialStatus
     * @param materialName
     * @param rfidCode
     * @return
     */
    @GET("/mobile/storage/elecMaterial/list")
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
    @GET("/mobile/storage/ElecMaterialCheckinfo/list")
    Observable<ListResponse<List<InventoryData>>> listInventory(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /**
     * 入库列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GET("/mobile/storage/ElecMaterialIninfo/list")
    Observable<ListResponse<List<InboundData>>> listInbound(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /**
     * 出库列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GET("/mobile/storage/ElecMaterialOutinfo/list")
    Observable<ListResponse<List<OutboundData>>> listOutbound(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /**
     * 移库列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GET("/mobile/storage/moveinfo/list")
    Observable<ListResponse<List<MovementData>>> listMovement(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /**
     * 调拨列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GET("/mobile/storage/ElecExchangeInfo/list")
    Observable<ListResponse<List<AllocateData>>> listAllocate(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /**
     * 盘点单详情
     *
     * @param path
     * @return
     */
    @GET("/mobile/storage/ElecMaterialCheckinfo/{dynamicPath}")
    Observable<BaseResponse<InventoryData>> selectByCheck(@Path("dynamicPath") int path);

    /**
     * 入库单详情
     *
     * @param path
     * @return
     */
    @GET("/mobile/storage/ElecMaterialIninfo/{dynamicPath}")
    Observable<BaseResponse<InboundData>> selectByInbound(@Path("dynamicPath") int path);

    /**
     * 出库单详情
     *
     * @param path
     * @return
     */
    @GET("/mobile/storage/ElecMaterialOutinfo/{dynamicPath}")
    Observable<BaseResponse<OutboundData>> selectByOutbound(@Path("dynamicPath") int path);

    /**
     * 移库单详情
     *
     * @param path
     * @return
     */
    @GET("/mobile/storage/ElecMaterialMoveinfo/{dynamicPath}")
    Observable<BaseResponse<MovementData>> selectByMovement(@Path("dynamicPath") int path);

    /**
     * 调拨单详情
     *
     * @param path
     * @return
     */
    @GET("/mobile/storage/ElecExchangeInfo/{dynamicPath}")
    Observable<BaseResponse<AllocateData>> selectByAllocate(@Path("dynamicPath") int path);

    /**
     * 盘点完成
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("/mobile/storage/ElecMaterialCheckinfo/completeInfo")
    Observable<BaseResponse> saveCheckResult(@FieldMap Map<String, String> params);

    /**
     * 盘点完成
     *
     * @param inventoryData
     * @return
     */
    @POST("/mobile/storage/ElecMaterialCheckinfo/completeInfo")
    Observable<BaseResponse> saveCheckedResult(@Body InventoryData inventoryData);

    /**
     * 入库完成
     *
     * @param inboundData
     * @return
     */
    @POST("/mobile/storage/ElecMaterialIninfo/completeInfo")
    Observable<BaseResponse> saveInboundResult(@Body InboundData inboundData);

    /**
     * 出库完成
     *
     * @param outboundDetail
     * @return
     */
    @POST("/mobile/storage/ElecMaterialOutinfo/completeInfo")
    Observable<BaseResponse> saveOutboundResult(@Body OutboundData outboundDetail);

    /**
     * 移库完成
     *
     * @param movementDetail
     * @return
     */
    @POST("/mobile/storage/moveinfo/completeMove")
    Observable<BaseResponse> saveMovementResult(@Body MovementData movementDetail);

    /**
     * 调拨完成
     *
     * @param allocateData
     * @return
     */
    @POST("/mobile/storage/ElecExchangeInfo/completeExchange")
    Observable<BaseResponse> saveAllocateResult(@Body AllocateData allocateData);

    /**
     * 实时数据统计
     *
     * @return
     */
    @GET("/storage/homePage/dataStatistics")
    Observable<StatisticsInfo> getStatisticsInfo();

    /**
     * 业务提醒
     *
     * @return
     */
    @GET("/storage/homePage/businessReminder")
    Observable<List<BusinessReminder>> getBusinessReminder();

    /**
     * 违规带出
     *
     * @return
     */
    @GET("/storage/homePage/illegalTakeout")
    Observable<List<IllegalTakeout>> getIllegalTakeout();

    /**
     * 总体数据
     *
     * @return
     */
    @GET("/storage/homePage/totalData")
    Observable<List<HomeTotalData>> getTotalData();

    /**
     * 设备统计
     *
     * @return
     */
    @GET("/storage/homePage/deviceData")
    Observable<List<DeviceData>> getDeviceData();

    /**
     * 设备统计
     *
     * @return
     */
    @GET("/bas/basArea/queryStatisticsInfo")
    Observable<BaseResponse<MaterialsStatisticsData>> getMaterialsStatistics();

    /**
     * 用户列表
     *
     * @return
     */
    @GET("/system/user/list")
    Observable<ListResponse<List<UserData>>> getUserList();

    /**
     * 用户列表
     *
     * @return
     */
    @GET("/system/dept/list")
    Observable<BaseResponse<List<DeptData>>> getDeptList();
}
