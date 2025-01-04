package com.victor.main.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/9/21
 * 邮箱：jxfengmtx@gmail.com
 */
public class AssetInspectionData {

    /**
     * dataList : [{"inspectionDetailsId":79,"batchNumber":"xj20092105440001","materialId":206,"customerId":24,"inspectionResult":null,"inspectionDate":null,"remark":null,"inspectionContent":null,"createDate":"2020-09-21 05:44:44","createUserId":117,"createUserName":"测试人员","updateDate":null,"updateUserId":null,"updateUserName":null,"status":0,"delflag":0,"materialCode":"zc20091115190001","materialName":"华硕飞行堡垒","sortName":"电脑","materiaStatus":"1","locationName":"总部","specification":"05-2T","useDepmName":"研发部","useUserName":"卢本伟","rfidCode":"3230323030393138"},{"inspectionDetailsId":80,"batchNumber":"xj20092105440001","materialId":207,"customerId":24,"inspectionResult":null,"inspectionDate":null,"remark":null,"inspectionContent":null,"createDate":"2020-09-21 05:44:44","createUserId":117,"createUserName":"测试人员","updateDate":null,"updateUserId":null,"updateUserName":null,"status":0,"delflag":0,"materialCode":"zc20091115580001","materialName":"华硕飞行堡垒","sortName":"电脑","materiaStatus":"1","locationName":"总部","specification":"05-2T","useDepmName":"研发部","useUserName":"卢本伟","rfidCode":"3230323030393138"}]
     * mainInfo : {"inspectionId":36,"batchNumber":"xj20092105440001","inspectionTheme":"巡检1","inspectionContent":null,"inspectionResult":null,"inspectionDepmCode":"002","inspectionDepmName":"销售部","inspectionUserId":129,"inspectionUserName":"补茹苟","inspectionDate":"2020-09-21 00:00:00","inspectionDateStart":null,"inspectionDateEnd":null,"inspectionEndDate":"2020-09-24 00:00:00","customerId":24,"remark":"","createDate":"2020-09-21 05:44:44","createUserId":117,"createUserName":"测试人员","updateDate":"2020-09-21 05:45:06","updateUserId":null,"updateUserName":null,"status":1,"delflag":0,"materialCode":null,"materialName":null,"sortName":null,"materiaStatus":null,"locationName":null,"assetvalue":null,"inspectStatus":null,"detailDataIndex":"206,207"}
     */

    private AssetInspectionOdd mainInfo;
    private List<DataListBean> dataList;

    public AssetInspectionOdd getMainInfo() {
        return mainInfo;
    }

    public void setMainInfo(AssetInspectionOdd mainInfo) {
        this.mainInfo = mainInfo;
    }

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    @Entity(tableName = "AssetInspectionDetail", primaryKeys = {"inspectionDetailsId"})
    public static class DataListBean {
        /**
         * inspectionDetailsId : 79
         * batchNumber : xj20092105440001
         * materialId : 206
         * customerId : 24
         * inspectionResult : null
         * inspectionDate : null
         * remark : null
         * inspectionContent : null
         * createDate : 2020-09-21 05:44:44
         * createUserId : 117
         * createUserName : 测试人员
         * updateDate : null
         * updateUserId : null
         * updateUserName : null
         * status : 0
         * delflag : 0
         * materialCode : zc20091115190001
         * materialName : 华硕飞行堡垒
         * sortName : 电脑
         * materiaStatus : 1
         * locationName : 总部
         * specification : 05-2T
         * useDepmName : 研发部
         * useUserName : 卢本伟
         * rfidCode : 3230323030393138
         */

        private int inspectionDetailsId;
        private String batchNumber;
        private int materialId;
        private int customerId;
        private int inspectionResult;
        private String inspectionDate;
        private String remark;
        private String inspectionContent;
        private String createDate;
        private int createUserId;
        private String createUserName;
        private String updateDate;
        private String updateUserId;
        private String updateUserName;
        private int status;
        private int delflag;
        private String materialCode;
        private String materialName;
        private String sortName;
        private String materiaStatus;
        private String locationName;
        private String specification;
        private String useDepmName;
        private String useUserName;
        @ColumnInfo(collate = ColumnInfo.NOCASE)
        @SerializedName(value = "rfidCode", alternate = { "rfidcode"})
        private String rfidCode;

        public int getInspectionDetailsId() {
            return inspectionDetailsId;
        }

        public void setInspectionDetailsId(int inspectionDetailsId) {
            this.inspectionDetailsId = inspectionDetailsId;
        }

        public String getBatchNumber() {
            return batchNumber;
        }

        public void setBatchNumber(String batchNumber) {
            this.batchNumber = batchNumber;
        }

        public int getMaterialId() {
            return materialId;
        }

        public void setMaterialId(int materialId) {
            this.materialId = materialId;
        }

        public int getCustomerId() {
            return customerId;
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public int getInspectionResult() {
            return inspectionResult;
        }

        public void setInspectionResult(int inspectionResult) {
            this.inspectionResult = inspectionResult;
        }

        public String getInspectionDate() {
            return inspectionDate;
        }

        public void setInspectionDate(String inspectionDate) {
            this.inspectionDate = inspectionDate;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getInspectionContent() {
            return inspectionContent;
        }

        public void setInspectionContent(String inspectionContent) {
            this.inspectionContent = inspectionContent;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public int getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(int createUserId) {
            this.createUserId = createUserId;
        }

        public String getCreateUserName() {
            return createUserName;
        }

        public void setCreateUserName(String createUserName) {
            this.createUserName = createUserName;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public String getUpdateUserId() {
            return updateUserId;
        }

        public void setUpdateUserId(String updateUserId) {
            this.updateUserId = updateUserId;
        }

        public String getUpdateUserName() {
            return updateUserName;
        }

        public void setUpdateUserName(String updateUserName) {
            this.updateUserName = updateUserName;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getDelflag() {
            return delflag;
        }

        public void setDelflag(int delflag) {
            this.delflag = delflag;
        }

        public String getMaterialCode() {
            return materialCode;
        }

        public void setMaterialCode(String materialCode) {
            this.materialCode = materialCode;
        }

        public String getMaterialName() {
            return materialName;
        }

        public void setMaterialName(String materialName) {
            this.materialName = materialName;
        }

        public String getSortName() {
            return sortName;
        }

        public void setSortName(String sortName) {
            this.sortName = sortName;
        }

        public String getMateriaStatus() {
            return materiaStatus;
        }

        public void setMateriaStatus(String materiaStatus) {
            this.materiaStatus = materiaStatus;
        }

        public String getLocationName() {
            return locationName;
        }

        public void setLocationName(String locationName) {
            this.locationName = locationName;
        }

        public String getSpecification() {
            return specification;
        }

        public void setSpecification(String specification) {
            this.specification = specification;
        }

        public String getUseDepmName() {
            return useDepmName;
        }

        public void setUseDepmName(String useDepmName) {
            this.useDepmName = useDepmName;
        }

        public String getUseUserName() {
            return useUserName;
        }

        public void setUseUserName(String useUserName) {
            this.useUserName = useUserName;
        }

        public String getRfidCode() {
            return rfidCode;
        }

        public void setRfidCode(String rfidCode) {
            this.rfidCode = rfidCode;
        }
    }
}
