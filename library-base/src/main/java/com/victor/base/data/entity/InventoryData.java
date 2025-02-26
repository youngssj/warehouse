package com.victor.base.data.entity;

import android.graphics.drawable.Drawable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class InventoryData {
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
    @Ignore
    private List<InventoryElecMaterial> elecMaterialList;
    private int finished;
    private String checkDate;

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    @Entity
    public static class InventoryElecMaterial {

        private String createBy;
        private String createTime;
        private String updateBy;
        private String updateTime;
        private String remark;
        @PrimaryKey
        private int checkDetailId;
        private int checkId;
        private int materialId;
        private String rfidCode;
        private String materialName; // 资产名称
        private String specifications; // 资产规格
        private String materialCode; // 资产编码
        private String deptName; // 使用部门
        private String warehouseName; // 存放地点
        private String whAreaName; // 库区
        private String whLocationName; // 库位
        private int checkResult; // 盘点结果 1正常（已盘点） 2亏 3盈
        private transient String checkResultMessage; // 盘点结果 1正常（已盘点） 2亏 3盈
        private String positionNo;
        private String checkDate;

        @Ignore
        public transient Drawable bgColor;
        @Ignore
        public transient Drawable textColor;

        public String getCheckDate() {
            return checkDate;
        }

        public void setCheckDate(String checkDate) {
            this.checkDate = checkDate;
        }

        public String getCheckResultMessage() {
            return checkResultMessage;
        }

        public void setCheckResultMessage(String checkResultMessage) {
            this.checkResultMessage = checkResultMessage;
        }

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

        public int getCheckDetailId() {
            return checkDetailId;
        }

        public void setCheckDetailId(int checkDetailId) {
            this.checkDetailId = checkDetailId;
        }

        public int getCheckId() {
            return checkId;
        }

        public void setCheckId(int checkId) {
            this.checkId = checkId;
        }

        public int getMaterialId() {
            return materialId;
        }

        public void setMaterialId(int materialId) {
            this.materialId = materialId;
        }

        public String getRfidCode() {
            return rfidCode;
        }

        public void setRfidCode(String rfidCode) {
            this.rfidCode = rfidCode;
        }

        public String getMaterialName() {
            return materialName;
        }

        public void setMaterialName(String materialName) {
            this.materialName = materialName;
        }

        public String getSpecifications() {
            return specifications;
        }

        public void setSpecifications(String specifications) {
            this.specifications = specifications;
        }

        public String getMaterialCode() {
            return materialCode;
        }

        public void setMaterialCode(String materialCode) {
            this.materialCode = materialCode;
        }

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }

        public String getWarehouseName() {
            return warehouseName;
        }

        public void setWarehouseName(String warehouseName) {
            this.warehouseName = warehouseName;
        }

        public String getWhAreaName() {
            return whAreaName;
        }

        public void setWhAreaName(String whAreaName) {
            this.whAreaName = whAreaName;
        }

        public String getWhLocationName() {
            return whLocationName;
        }

        public void setWhLocationName(String whLocationName) {
            this.whLocationName = whLocationName;
        }

        public int getCheckResult() {
            return checkResult;
        }

        public void setCheckResult(int checkResult) {
            this.checkResult = checkResult;
        }

        public String getPositionNo() {
            return positionNo;
        }

        public void setPositionNo(String positionNo) {
            this.positionNo = positionNo;
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

    public List<InventoryData.InventoryElecMaterial> getElecMaterialList() {
        return elecMaterialList;
    }

    public void setElecMaterialList(List<InventoryData.InventoryElecMaterial> elecMaterialList) {
        this.elecMaterialList = elecMaterialList;
    }
}
