package com.victor.main.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TakeStockData {
    private String createBy;
    private String createTime;
    private String updateBy;
    private String updateTime;
    private String remark;
    @PrimaryKey
    private int checkId;
    private String auditStatus;
    private int bussId;
    private int deptId;
    private String batchNumber;
    private String checkTheme;
    private String checkType;
    private String checkStatus;
    private String planCheckDate;
    private String materialCategory;
    private String delFlag;
    private int checkUserId;
    private String userName;
    private String elecMaterialList;

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getCheckId() {
        return checkId;
    }

    public void setCheckId(int checkId) {
        this.checkId = checkId;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public int getBussId() {
        return bussId;
    }

    public void setBussId(int bussId) {
        this.bussId = bussId;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
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

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getPlanCheckDate() {
        return planCheckDate;
    }

    public void setPlanCheckDate(String planCheckDate) {
        this.planCheckDate = planCheckDate;
    }

    public String getMaterialCategory() {
        return materialCategory;
    }

    public void setMaterialCategory(String materialCategory) {
        this.materialCategory = materialCategory;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public int getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(int checkUserId) {
        this.checkUserId = checkUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getElecMaterialList() {
        return elecMaterialList;
    }

    public void setElecMaterialList(String elecMaterialList) {
        this.elecMaterialList = elecMaterialList;
    }
}
