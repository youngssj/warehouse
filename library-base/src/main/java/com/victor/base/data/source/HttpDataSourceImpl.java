package com.victor.base.data.source;

import com.victor.base.app.Injection;
import com.victor.base.data.entity.AssetApproveOdd;
import com.victor.base.data.entity.AssetCheckData;
import com.victor.base.data.entity.AssetCheckOdd;
import com.victor.base.data.entity.AssetData;
import com.victor.base.data.entity.AssetInspectionData;
import com.victor.base.data.entity.AssetInspectionOdd;
import com.victor.base.data.entity.AssetLocation;
import com.victor.base.data.entity.AssetRepairData;
import com.victor.base.data.entity.AssetRepairOdd;
import com.victor.base.data.entity.LocationInfo;
import com.victor.base.data.entity.MaterialsData;
import com.victor.base.data.entity.OddNum;
import com.victor.base.data.entity.TakeStockData;
import com.victor.base.data.entity.TakeStockDetail;
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
    public Observable<BaseResponse<List<AssetCheckOdd>>> listCheck(int page) {
        Map<String, String> map = Injection.getMapInstance();
        map.put("page", String.valueOf(page));
        return apiService.listCheck(map);
    }

    @Override
    public Observable<ListResponse<List<TakeStockData>>> listTakeStock(int pageNum) {
        return apiService.listTakeStock(pageNum, 10);
    }

    @Override
    public Observable<ListResponse<List<TakeStockData>>> listAllTakeStock(int pageNum) {
        return apiService.listTakeStock(pageNum, 10000);
    }

    @Override
    public Observable<BaseResponse<AssetCheckData>> selectOneCheck(int checkId) {
        Map<String, String> map = Injection.getMapInstance();
        map.put("checkId", String.valueOf(checkId));
        return apiService.selectOneCheck(map);
    }

    @Override
    public Observable<BaseResponse<TakeStockDetail>> selectByCheck(int checkId) {
//        Map<String, String> map = Injection.getMapInstance();
//        map.put("checkId", String.valueOf(checkId));
        return apiService.selectByCheck(checkId);
    }

    @Override
    public Observable<BaseResponse> saveCheckResult(int checkId, String checkPks, String noCheckPks, String batchNumber, String surplusCheckPks) {
        Map<String, String> map = Injection.getMapInstance();
        map.put("checkId", String.valueOf(checkId));
        map.put("checkPks", String.valueOf(checkPks));
        map.put("noCheckPks", String.valueOf(noCheckPks));
        map.put("batchNumber", batchNumber);
        map.put("surplusCheckPks", surplusCheckPks);
        return apiService.saveCheckResult(map);
    }

    @Override
    public Observable<BaseResponse> saveCheckedResult(TakeStockDetail mainInfo) {
        return apiService.saveCheckedResult(mainInfo);
    }

    @Override
    public Observable<BaseResponse<List<AssetInspectionOdd>>> listInspection(int page) {
        Map<String, String> map = Injection.getMapInstance();
        map.put("page", String.valueOf(page));
        return apiService.listInspection(map);
    }

    @Override
    public Observable<BaseResponse<AssetInspectionData>> selectOneInspection(int inspectionId) {
        Map<String, String> map = Injection.getMapInstance();
        map.put("inspectionId", String.valueOf(inspectionId));
        return apiService.selectOneInspection(map);
    }

    @Override
    public Observable<BaseResponse> saveInspection(int inspectionId, String batchNumber, String inspectionDate) {
        Map<String, String> map = Injection.getMapInstance();
        map.put("inspectionId", String.valueOf(inspectionId));
        map.put("batchNumber", String.valueOf(batchNumber));
        map.put("inspectionDate", String.valueOf(inspectionDate));
        return apiService.saveInspection(map);
    }

    @Override
    public Observable<BaseResponse> saveInspectionResult(int inspectionDetailsId, int inspectionResult, String inspectionContent) {
        Map<String, String> map = Injection.getMapInstance();
        map.put("inspectionDetailsId", String.valueOf(inspectionDetailsId));
        map.put("inspectionResult", String.valueOf(inspectionResult));
        map.put("inspectionContent", String.valueOf(inspectionContent));
        return apiService.saveInspectionResult(map);
    }

    @Override
    public Observable<BaseResponse<List<AssetRepairOdd>>> listRepair(int page) {
        Map<String, String> map = Injection.getMapInstance();
        map.put("page", String.valueOf(page));
        return apiService.listRepair(map);
    }

    @Override
    public Observable<BaseResponse<AssetRepairData>> selectOneRepair(int repairId) {
        Map<String, String> map = Injection.getMapInstance();
        map.put("repairId", String.valueOf(repairId));
        return apiService.selectOneRepair(map);
    }

    @Override
    public Observable<BaseResponse> saveRepair(int repairId, String batchNumber) {
        Map<String, String> map = Injection.getMapInstance();
        map.put("repairId", String.valueOf(repairId));
        map.put("batchNumber", String.valueOf(batchNumber));
        return apiService.saveRepair(map);
    }

    @Override
    public Observable<BaseResponse> saveRepairResult(int repairDetailId, int materialId, double repairCosts, String inspectionContent) {
        Map<String, String> map = Injection.getMapInstance();
        map.put("repairDetailId", String.valueOf(repairDetailId));
        map.put("materialId", String.valueOf(materialId));
        map.put("repairCosts", String.valueOf(repairCosts));
        map.put("repairContent", String.valueOf(inspectionContent));
        return apiService.saveRepairResult(map);
    }

    @Override
    public Observable<BaseResponse<List<OddNum>>> selectNum() {
        return apiService.selectNum();
    }

    @Override
    public Observable<BaseResponse<List<AssetLocation>>> materialLocation(List<LocationInfo> locationInfoList) {
        return apiService.materialLocation(locationInfoList);
    }

    @Override
    public Observable<BaseResponse<List<AssetData>>> rfidToMaterialInfo(List<String> epcs) {
        return apiService.rfidToMaterialInfo(epcs);
    }

    @Override
    public Observable<BaseResponse<List<AssetData>>> barCodeToMaterialInfo(String barCode) {
        Map<String, String> map = Injection.getMapInstance();
        map.put("materialCode", String.valueOf(barCode));
        return apiService.queryMaterialList(map);
    }

    @Override
    public Observable<BaseResponse<List<AssetApproveOdd>>> listPage(int approveLevel) {
        Map<String, String> map = Injection.getMapInstance();
        map.put("approveLevel", String.valueOf(approveLevel));
        return apiService.listPage(map);
    }

    @Override
    public Observable<BaseResponse<AssetApproveOdd>> selectOne(String pk) {
        return apiService.selectOne(pk);
    }

    @Override
    public Observable<BaseResponse<String>> borrowMaterialStr(String batchNumber) {
        Map<String, String> map = Injection.getMapInstance();
        map.put("batchNumber", String.valueOf(batchNumber));
        return apiService.borrowMaterialStr(map);
    }

    @Override
    public Observable<BaseResponse<List<AssetCheckData.DataListBean>>> selectMany(String detailDataIndex) {
        Map<String, String> map = Injection.getMapInstance();
        map.put("detailDataIndex", String.valueOf(detailDataIndex));
        return apiService.selectMany(map);
    }

    @Override
    public Observable<BaseResponse> assAgree(String pks, String approveNote) {
        Map<String, String> map = Injection.getMapInstance();
        map.put("pks", String.valueOf(pks));
        map.put("approveNote", String.valueOf(approveNote));
        return apiService.assAgree(map);
    }

    @Override
    public Observable<BaseResponse> assReject(String pks, String approveNote) {
        Map<String, String> map = Injection.getMapInstance();
        map.put("pks", String.valueOf(pks));
        map.put("approveNote", String.valueOf(approveNote));
        return apiService.assReject(map);
    }

    @Override
    public Observable<BaseResponse<List<AssetData>>> queryMaterialList() {
        Map<String, String> map = Injection.getMapInstance();
        map.put("delflag", String.valueOf(0));
        return apiService.queryMaterialList(map);
    }

    @Override
    public Observable<ListResponse<List<MaterialsData>>> listMaterials(int pageNum, String materialStatus, String materialName, String rfidCode) {
        return apiService.listMaterials(pageNum, 10, materialStatus, materialName, rfidCode);
    }

}
