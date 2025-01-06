package com.victor.base.data.entity;

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
public class AssetRepairData {

    /**
     * dataList : [{"repairDetailId":85,"batchNumber":"wx20092105380001","materialId":291,"customerId":24,"repairContent":null,"repairCosts":null,"remark":null,"createUserId":null,"createUserName":null,"createDate":null,"updateUserId":117,"updateUserName":"测试人员","updateDate":"2020-09-21 05:41:18","delflag":1,"repairDate":null,"reparirDepmName":null,"materialCode":"zc20091609580001","materialName":"华硕坦克","sortName":"电脑","materiaStatus":"0","locationName":"总部","specification":"","useDepmName":"销售部","useUserName":"补茹苟","rfidCode":"3230323030393138"},{"repairDetailId":86,"batchNumber":"wx20092105380001","materialId":292,"customerId":24,"repairContent":null,"repairCosts":null,"remark":null,"createUserId":null,"createUserName":null,"createDate":null,"updateUserId":117,"updateUserName":"测试人员","updateDate":"2020-09-21 05:41:18","delflag":1,"repairDate":null,"reparirDepmName":null,"materialCode":"zc20091609580002","materialName":"华硕坦克","sortName":"电脑","materiaStatus":"0","locationName":"总部","specification":"","useDepmName":"销售部","useUserName":"补茹苟","rfidCode":"3230323030393138"}]
     * mainInfo : {"repairId":36,"batchNumber":"wx20092105380001","repairTheme":"维修主题测试","reparirDepmCode":"001","reparirDepmName":"研发部","repairUserId":132,"repairUserName":"卢本伟","repairDate":"2020-09-21 00:00:00","repairDateStart":null,"repairDateEnd":null,"repairEndDate":"2020-09-24 00:00:00","repairEndDateStart":null,"repairEndDateEnd":null,"customerId":24,"remark":"","createDate":"2020-09-21 05:38:52","createUserId":117,"createUserName":"测试人员","updateDate":"2020-09-21 05:41:18","updateUserId":117,"updateUserName":"测试人员","status":0,"delflag":1,"useDepmCode":null,"useDepmName":null,"useUserName":null,"locationCode":null,"locationName":null,"materialCode":null,"materialName":null,"sortName":null,"materiaStatus":null,"specification":null,"detailDataIndex":"291,292","repairContent":null,"repairCosts":null}
     */

    private AssetRepairOdd mainInfo;
    private List<DataListBean> dataList;

    public AssetRepairOdd getMainInfo() {
        return mainInfo;
    }

    public void setMainInfo(AssetRepairOdd mainInfo) {
        this.mainInfo = mainInfo;
    }

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    @Entity(tableName = "AssetRepairDetail", primaryKeys = {"repairDetailId"})
    public static class DataListBean {
        /**
         * repairDetailId : 85
         * batchNumber : wx20092105380001
         * materialId : 291
         * customerId : 24
         * repairContent : null
         * repairCosts : null
         * remark : null
         * createUserId : null
         * createUserName : null
         * createDate : null
         * updateUserId : 117
         * updateUserName : 测试人员
         * updateDate : 2020-09-21 05:41:18
         * delflag : 1
         * repairDate : null
         * reparirDepmName : null
         * materialCode : zc20091609580001
         * materialName : 华硕坦克
         * sortName : 电脑
         * materiaStatus : 0
         * locationName : 总部
         * specification :
         * useDepmName : 销售部
         * useUserName : 补茹苟
         * rfidCode : 3230323030393138
         */
        private int repairDetailId;
        private String batchNumber;
        private int materialId;
        private int customerId;
        private String repairContent;
        private String repairCosts;
        private String remark;
        private String createUserId;
        private String createUserName;
        private String createDate;
        private int updateUserId;
        private String updateUserName;
        private String updateDate;
        private int delflag;
        private String repairDate;
        private String reparirDepmName;
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

        public int getRepairDetailId() {
            return repairDetailId;
        }

        public void setRepairDetailId(int repairDetailId) {
            this.repairDetailId = repairDetailId;
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

        public String getRepairContent() {
            return repairContent;
        }

        public void setRepairContent(String repairContent) {
            this.repairContent = repairContent;
        }

        public String getRepairCosts() {
            return repairCosts;
        }

        public void setRepairCosts(String repairCosts) {
            this.repairCosts = repairCosts;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(String createUserId) {
            this.createUserId = createUserId;
        }

        public String getCreateUserName() {
            return createUserName;
        }

        public void setCreateUserName(String createUserName) {
            this.createUserName = createUserName;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public int getUpdateUserId() {
            return updateUserId;
        }

        public void setUpdateUserId(int updateUserId) {
            this.updateUserId = updateUserId;
        }

        public String getUpdateUserName() {
            return updateUserName;
        }

        public void setUpdateUserName(String updateUserName) {
            this.updateUserName = updateUserName;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public int getDelflag() {
            return delflag;
        }

        public void setDelflag(int delflag) {
            this.delflag = delflag;
        }

        public String getRepairDate() {
            return repairDate;
        }

        public void setRepairDate(String repairDate) {
            this.repairDate = repairDate;
        }

        public String getReparirDepmName() {
            return reparirDepmName;
        }

        public void setReparirDepmName(String reparirDepmName) {
            this.reparirDepmName = reparirDepmName;
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
