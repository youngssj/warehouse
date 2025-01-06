package com.victor.base.data.entity;

import android.graphics.drawable.Drawable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/9/17
 * 邮箱：jxfengmtx@gmail.com
 */
public class AssetCheckData {

    /**
     * dataList : [{"checkDetailId":156,"batchNumber":"pd20091605460001","materialId":289,"customerId":24,"checkResult":null,"handleResult":null,"checkDate":null,"remark":null,"createUserId":117,"createUserName":"测试人员","createDate":"2020-09-16 05:46:23","updateUserId":null,"updateUserName":null,"updateDate":null,"delflag":0,"materialCode":"zc20091506090001","materialName":"罗技G502","sortName":"鼠标","materiaStatus":"0","specification":"","useDepmName":"产品部"},{"checkDetailId":157,"batchNumber":"pd20091605460001","materialId":290,"customerId":24,"checkResult":null,"handleResult":null,"checkDate":null,"remark":null,"createUserId":117,"createUserName":"测试人员","createDate":"2020-09-16 05:46:23","updateUserId":null,"updateUserName":null,"updateDate":null,"delflag":0,"materialCode":"zc20091506100001","materialName":"罗技G302","sortName":"鼠标","materiaStatus":"0","specification":"","useDepmName":"销售部"}]
     * mainInfo : {"checkId":55,"batchNumber":"pd20091605460001","checkTheme":"盘点单","checkDepmCode":"002","checkDepmName":"销售部","checkUserId":129,"checkUserName":"补茹苟","checkDate":"2020-09-17 00:00:00","checkEndDate":"2020-09-20 00:00:00","checkStartDate":null,"customerId":24,"remark":"","createDate":"2020-09-16 05:46:23","createUserId":117,"createUserName":"测试人员","updateDate":null,"updateUserId":null,"updateUserName":null,"status":1,"delflag":0,"checkResult":null,"locationCode":null,"locationName":null,"materialCode":null,"materialName":null,"sortName":null,"materiaStatus":null,"assetvalue":0,"detailDataIndex":"289,290","detailLi":null}
     */

    private AssetCheckOdd mainInfo;
    private List<DataListBean> dataList;

    public AssetCheckOdd getMainInfo() {
        return mainInfo;
    }

    public void setMainInfo(AssetCheckOdd mainInfo) {
        this.mainInfo = mainInfo;
    }

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    @Entity(tableName = "AssetCheckDetail", primaryKeys = {"checkDetailId"})
    public static class DataListBean {


        /**
         * checkDetailId : 156
         * batchNumber : pd20091605460001
         * materialId : 289
         * customerId : 24
         * checkResult : null
         * handleResult : null
         * checkDate : null
         * remark : null
         * createUserId : 117
         * createUserName : 测试人员
         * createDate : 2020-09-16 05:46:23
         * updateUserId : null
         * updateUserName : null
         * updateDate : null
         * delflag : 0
         * materialCode : zc20091506090001
         * materialName : 罗技G502
         * sortName : 鼠标
         * materiaStatus : 0
         * specification :
         * useDepmName : 产品部
         */
        private int pagerIndex;
        private int checkDetailId;
        private String batchNumber;
        private int materialId;
        private int customerId;
        @ColumnInfo(collate = ColumnInfo.NOCASE)
        @SerializedName(value = "rfidCode", alternate = { "rfidcode"})
        private String rfidCode;
        private String checkResult;
        private String handleResult;
        private String checkDate;
        private String remark;
        private int createUserId;
        private String createUserName;
        private String createDate;
        private String updateUserId;
        private String updateUserName;
        private String updateDate;
        private int delflag;
        private String materialCode;
        private String materialName;
        private String sortName;
        private String materiaStatus;
        private String specification;
        private String useDepmName;
        private String locationName;
        @Ignore
        public Drawable bgColor;
        @Ignore
        public Drawable textColor;


        public String getRfidCode() {
            return rfidCode;
        }

        public void setRfidCode(String rfidCode) {
            this.rfidCode = rfidCode;
        }

        public int getPagerIndex() {
            return pagerIndex;
        }

        public void setPagerIndex(int pagerIndex) {
            this.pagerIndex = pagerIndex;
        }

        public int getCheckDetailId() {
            return checkDetailId;
        }

        public void setCheckDetailId(int checkDetailId) {
            this.checkDetailId = checkDetailId;
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

        public String getCheckResult() {
            return checkResult;
        }

        public void setCheckResult(String checkResult) {
            this.checkResult = checkResult;
        }

        public String getHandleResult() {
            return handleResult;
        }

        public void setHandleResult(String handleResult) {
            this.handleResult = handleResult;
        }

        public String getCheckDate() {
            return checkDate;
        }

        public void setCheckDate(String checkDate) {
            this.checkDate = checkDate;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
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

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
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

        public String getMaterialCode() {
            return materialCode == null ? "" : materialCode;
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

        public String getLocationName() {
            return locationName;
        }

        public void setLocationName(String locationName) {
            this.locationName = locationName;
        }

        public Drawable getBgColor() {
            return bgColor;
        }

        public void setBgColor(Drawable bgColor) {
            this.bgColor = bgColor;
        }

        public Drawable getTextColor() {
            return textColor;
        }

        public void setTextColor(Drawable textColor) {
            this.textColor = textColor;
        }
    }
}
