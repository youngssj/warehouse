package com.victor.main.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/9/21
 * 邮箱：jxfengmtx@gmail.com
 */
@Entity
public class AssetInspectionOdd {


    /**
     * inspectionId : 36
     * batchNumber : xj20092105440001
     * inspectionTheme : 巡检1
     * inspectionContent : null
     * inspectionResult : null
     * inspectionDepmCode : 002
     * inspectionDepmName : 销售部
     * inspectionUserId : 129
     * inspectionUserName : 补茹苟
     * inspectionDate : 2020-09-21 00:00:00
     * inspectionDateStart : null
     * inspectionDateEnd : null
     * inspectionEndDate : 2020-09-24 00:00:00
     * customerId : 24
     * remark :
     * createDate : 2020-09-21 05:44:44
     * createUserId : 117
     * createUserName : 测试人员
     * updateDate : 2020-09-21 05:45:06
     * updateUserId : null
     * updateUserName : null
     * status : 1
     * delflag : 0
     * materialCode : null
     * materialName : null
     * sortName : null
     * materiaStatus : null
     * locationName : null
     * assetvalue : null
     * inspectStatus : null
     * detailDataIndex : 206,207
     */

    @PrimaryKey
    private int inspectionId;
    private String batchNumber;
    private String inspectionTheme;
    private String inspectionContent;
    private int inspectionResult;
    private String inspectionDepmCode;
    private String inspectionDepmName;
    private int inspectionUserId;
    private String inspectionUserName;
    private String inspectionDate;
    private String inspectionDateStart;
    private String inspectionDateEnd;
    private String inspectionEndDate;
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
    private String materialCode;
    private String materialName;
    private String sortName;
    private String materiaStatus;
    private String locationName;
    private String assetvalue;
    private String inspectStatus;
    private String detailDataIndex;

    public int getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(int inspectionId) {
        this.inspectionId = inspectionId;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getInspectionTheme() {
        return inspectionTheme;
    }

    public void setInspectionTheme(String inspectionTheme) {
        this.inspectionTheme = inspectionTheme;
    }

    public String getInspectionContent() {
        return inspectionContent;
    }

    public void setInspectionContent(String inspectionContent) {
        this.inspectionContent = inspectionContent;
    }

    public int getInspectionResult() {
        return inspectionResult;
    }

    public void setInspectionResult(int inspectionResult) {
        this.inspectionResult = inspectionResult;
    }

    public String getInspectionDepmCode() {
        return inspectionDepmCode;
    }

    public void setInspectionDepmCode(String inspectionDepmCode) {
        this.inspectionDepmCode = inspectionDepmCode;
    }

    public String getInspectionDepmName() {
        return inspectionDepmName;
    }

    public void setInspectionDepmName(String inspectionDepmName) {
        this.inspectionDepmName = inspectionDepmName;
    }

    public int getInspectionUserId() {
        return inspectionUserId;
    }

    public void setInspectionUserId(int inspectionUserId) {
        this.inspectionUserId = inspectionUserId;
    }

    public String getInspectionUserName() {
        return inspectionUserName;
    }

    public void setInspectionUserName(String inspectionUserName) {
        this.inspectionUserName = inspectionUserName;
    }

    public String getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(String inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public String getInspectionDateStart() {
        return inspectionDateStart;
    }

    public void setInspectionDateStart(String inspectionDateStart) {
        this.inspectionDateStart = inspectionDateStart;
    }

    public String getInspectionDateEnd() {
        return inspectionDateEnd;
    }

    public void setInspectionDateEnd(String inspectionDateEnd) {
        this.inspectionDateEnd = inspectionDateEnd;
    }

    public String getInspectionEndDate() {
        return inspectionEndDate;
    }

    public void setInspectionEndDate(String inspectionEndDate) {
        this.inspectionEndDate = inspectionEndDate;
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

    public String getAssetvalue() {
        return assetvalue;
    }

    public void setAssetvalue(String assetvalue) {
        this.assetvalue = assetvalue;
    }

    public String getInspectStatus() {
        return inspectStatus;
    }

    public void setInspectStatus(String inspectStatus) {
        this.inspectStatus = inspectStatus;
    }

    public String getDetailDataIndex() {
        return detailDataIndex;
    }

    public void setDetailDataIndex(String detailDataIndex) {
        this.detailDataIndex = detailDataIndex;
    }
}
