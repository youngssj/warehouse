package com.victor.base.data.entity;

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
public class AssetRepairOdd {

    /**
     * repairId : 34
     * batchNumber : wx20092105270001
     * repairTheme : 维修主题
     * reparirDepmCode : 002
     * reparirDepmName : 销售部
     * repairUserId : 129
     * repairUserName : 补茹苟
     * repairDate : 2020-09-21 00:00:00
     * repairDateStart : null
     * repairDateEnd : null
     * repairEndDate : 2020-09-24 00:00:00
     * repairEndDateStart : null
     * repairEndDateEnd : null
     * customerId : 24
     * remark :
     * createDate : 2020-09-21 05:27:51
     * createUserId : 117
     * createUserName : 测试人员
     * updateDate : 2020-09-21 05:28:38
     * updateUserId : null
     * updateUserName : null
     * status : 1
     * delflag : 0
     * useDepmCode : null
     * useDepmName : null
     * useUserName : null
     * locationCode : null
     * locationName : null
     * materialCode : null
     * materialName : null
     * sortName : null
     * materiaStatus : null
     * specification : null
     * detailDataIndex : 226,288
     * repairContent : null
     * repairCosts : null
     */

    @PrimaryKey
    private int repairId;
    private String batchNumber;
    private String repairTheme;
    private String reparirDepmCode;
    private String reparirDepmName;
    private int repairUserId;
    private String repairUserName;
    private String repairDate;
    private String repairDateStart;
    private String repairDateEnd;
    private String repairEndDate;
    private String repairEndDateStart;
    private String repairEndDateEnd;
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
    private String useDepmCode;
    private String useDepmName;
    private String useUserName;
    private String locationCode;
    private String locationName;
    private String materialCode;
    private String materialName;
    private String sortName;
    private String materiaStatus;
    private String specification;
    private String detailDataIndex;
    private String repairContent;
    private String repairCosts;

    public int getRepairId() {
        return repairId;
    }

    public void setRepairId(int repairId) {
        this.repairId = repairId;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getRepairTheme() {
        return repairTheme;
    }

    public void setRepairTheme(String repairTheme) {
        this.repairTheme = repairTheme;
    }

    public String getReparirDepmCode() {
        return reparirDepmCode;
    }

    public void setReparirDepmCode(String reparirDepmCode) {
        this.reparirDepmCode = reparirDepmCode;
    }

    public String getReparirDepmName() {
        return reparirDepmName;
    }

    public void setReparirDepmName(String reparirDepmName) {
        this.reparirDepmName = reparirDepmName;
    }

    public int getRepairUserId() {
        return repairUserId;
    }

    public void setRepairUserId(int repairUserId) {
        this.repairUserId = repairUserId;
    }

    public String getRepairUserName() {
        return repairUserName;
    }

    public void setRepairUserName(String repairUserName) {
        this.repairUserName = repairUserName;
    }

    public String getRepairDate() {
        return repairDate;
    }

    public void setRepairDate(String repairDate) {
        this.repairDate = repairDate;
    }

    public String getRepairDateStart() {
        return repairDateStart;
    }

    public void setRepairDateStart(String repairDateStart) {
        this.repairDateStart = repairDateStart;
    }

    public String getRepairDateEnd() {
        return repairDateEnd;
    }

    public void setRepairDateEnd(String repairDateEnd) {
        this.repairDateEnd = repairDateEnd;
    }

    public String getRepairEndDate() {
        return repairEndDate;
    }

    public void setRepairEndDate(String repairEndDate) {
        this.repairEndDate = repairEndDate;
    }

    public String getRepairEndDateStart() {
        return repairEndDateStart;
    }

    public void setRepairEndDateStart(String repairEndDateStart) {
        this.repairEndDateStart = repairEndDateStart;
    }

    public String getRepairEndDateEnd() {
        return repairEndDateEnd;
    }

    public void setRepairEndDateEnd(String repairEndDateEnd) {
        this.repairEndDateEnd = repairEndDateEnd;
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

    public String getUseDepmCode() {
        return useDepmCode;
    }

    public void setUseDepmCode(String useDepmCode) {
        this.useDepmCode = useDepmCode;
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

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getDetailDataIndex() {
        return detailDataIndex;
    }

    public void setDetailDataIndex(String detailDataIndex) {
        this.detailDataIndex = detailDataIndex;
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
}
