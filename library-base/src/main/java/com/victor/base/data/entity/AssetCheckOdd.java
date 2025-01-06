package com.victor.base.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/9/16
 * 邮箱：jxfengmtx@gmail.com
 */
@Entity
public class AssetCheckOdd {

    /**
     * checkId : 55
     * batchNumber : pd20091605460001
     * checkTheme : 盘点单
     * checkDepmCode : 002
     * checkDepmName : 销售部
     * checkUserId : 129
     * checkUserName : 补茹苟
     * checkDate : 2020-09-17 00:00:00
     * checkEndDate : 2020-09-20 00:00:00
     * checkStartDate : null
     * customerId : 24
     * remark :
     * createDate : 2020-09-16 05:46:23
     * createUserId : 117
     * createUserName : 测试人员
     * updateDate : null
     * updateUserId : null
     * updateUserName : null
     * status : 1
     * delflag : 0
     * checkResult : null
     * locationCode : null
     * locationName : null
     * materialCode : null
     * materialName : null
     * sortName : null
     * materiaStatus : null
     * assetvalue : 0
     * detailDataIndex : 289,290
     * detailLi : null
     */
    @PrimaryKey
    private int checkId;
    private String batchNumber;
    private String checkTheme;
    private String checkDepmCode;
    private String checkDepmName;
    private int checkUserId;
    private String checkUserName;
    private String checkDate;
    private String checkEndDate;
    private String checkStartDate;
    private int customerId;
    private String remark;
    private String createDate;
    private int createUserId;
    private String createUserName;
    private String updateDate;
    private String updateUserId;
    private String updateUserName;
    private int status;
    private int delflag;
    private String checkResult;
    private String locationCode;
    private String locationName;
    private String materialCode;
    private String materialName;
    private String sortName;
    private String materiaStatus;
    private int assetvalue;
    private String detailDataIndex;
    private String detailLi;

    public int getCheckId() {
        return checkId;
    }

    public void setCheckId(int checkId) {
        this.checkId = checkId;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getCheckTheme() {
        return checkTheme;
    }

    public void setCheckTheme(String checkTheme) {
        this.checkTheme = checkTheme;
    }

    public String getCheckDepmCode() {
        return checkDepmCode;
    }

    public void setCheckDepmCode(String checkDepmCode) {
        this.checkDepmCode = checkDepmCode;
    }

    public String getCheckDepmName() {
        return checkDepmName;
    }

    public void setCheckDepmName(String checkDepmName) {
        this.checkDepmName = checkDepmName;
    }

    public int getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(int checkUserId) {
        this.checkUserId = checkUserId;
    }

    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getCheckEndDate() {
        return checkEndDate;
    }

    public void setCheckEndDate(String checkEndDate) {
        this.checkEndDate = checkEndDate;
    }

    public String getCheckStartDate() {
        return checkStartDate;
    }

    public void setCheckStartDate(String checkStartDate) {
        this.checkStartDate = checkStartDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
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

    public int getAssetvalue() {
        return assetvalue;
    }

    public void setAssetvalue(int assetvalue) {
        this.assetvalue = assetvalue;
    }

    public String getDetailDataIndex() {
        return detailDataIndex;
    }

    public void setDetailDataIndex(String detailDataIndex) {
        this.detailDataIndex = detailDataIndex;
    }

    public String getDetailLi() {
        return detailLi;
    }

    public void setDetailLi(String detailLi) {
        this.detailLi = detailLi;
    }
}
