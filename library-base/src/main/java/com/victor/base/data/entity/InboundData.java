package com.victor.base.data.entity;

import android.graphics.drawable.Drawable;

import androidx.room.Ignore;

import java.util.List;

public class InboundData {
    private String createBy;
    private String createTime;
    private String updateBy;
    private Object updateTime;
    private Object remark;
    private int inId;
    private String batchNumber;
    private String inTheme;
    private String planInDate;
    private int inUserId;
    private String auditStatus;
    private Object secondStatus;
    private int bussId;
    private String userName;
    private Object materialCategory;
    private String inStatus;
    private String inType;
    private String delFlag;
    private int deptId;
    private List<ElecMaterialList> elecMaterialList;

    public List<ElecMaterialList> getElecMaterialList() {
        return elecMaterialList;
    }

    public void setElecMaterialList(List<ElecMaterialList> elecMaterialList) {
        this.elecMaterialList = elecMaterialList;
    }

    public static class ElecMaterialList {
        private String createBy;
        private String createTime;
        private String updateBy;
        private Object updateTime;
        private String remark;
        private int indetailId;
        private int inId;
        private int materialId;
        private Object inDate;
        private String materialCode;
        private String materialName;
        private String materialStatusName;
        private String deptName;
        private String warehouseName;
        private String whAreaName;
        private String whLocationName;
        private String unitName;
        private String providerName;
        private String specifications;
        private String rfidCode;
        private int isIn; // 入库结果 0未入库 1已入库
        private transient String isInMessage; // 入库结果 0未入库 1已入库
        @Ignore
        public transient Drawable bgColor;
        @Ignore
        public transient Drawable textColor;

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

        public String getMaterialStatusName() {
            return materialStatusName;
        }

        public void setMaterialStatusName(String materialStatusName) {
            this.materialStatusName = materialStatusName;
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

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        public String getProviderName() {
            return providerName;
        }

        public void setProviderName(String providerName) {
            this.providerName = providerName;
        }

        public String getSpecifications() {
            return specifications;
        }

        public void setSpecifications(String specifications) {
            this.specifications = specifications;
        }

        public String getRfidCode() {
            return rfidCode;
        }

        public void setRfidCode(String rfidCode) {
            this.rfidCode = rfidCode;
        }

        public int getIsIn() {
            return isIn;
        }

        public void setIsIn(int isIn) {
            this.isIn = isIn;
        }

        public String getIsInMessage() {
            return isInMessage;
        }

        public void setIsInMessage(String isInMessage) {
            this.isInMessage = isInMessage;
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

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getIndetailId() {
            return indetailId;
        }

        public void setIndetailId(int indetailId) {
            this.indetailId = indetailId;
        }

        public int getInId() {
            return inId;
        }

        public void setInId(int inId) {
            this.inId = inId;
        }

        public int getMaterialId() {
            return materialId;
        }

        public void setMaterialId(int materialId) {
            this.materialId = materialId;
        }

        public Object getInDate() {
            return inDate;
        }

        public void setInDate(Object inDate) {
            this.inDate = inDate;
        }

        @Override
        public String toString() {
            return "ElecMaterialList{" +
                    "createBy='" + createBy + '\'' +
                    ", createTime='" + createTime + '\'' +
                    ", updateBy='" + updateBy + '\'' +
                    ", updateTime=" + updateTime +
                    ", remark='" + remark + '\'' +
                    ", indetailId=" + indetailId +
                    ", inId=" + inId +
                    ", materialId=" + materialId +
                    ", isIn='" + isIn + '\'' +
                    ", inDate=" + inDate +
                    ", materialCode='" + materialCode + '\'' +
                    ", materialName='" + materialName + '\'' +
                    ", materialStatusName='" + materialStatusName + '\'' +
                    ", deptName='" + deptName + '\'' +
                    ", warehouseName='" + warehouseName + '\'' +
                    ", whAreaName='" + whAreaName + '\'' +
                    ", whLocationName='" + whLocationName + '\'' +
                    ", unitName='" + unitName + '\'' +
                    ", providerName='" + providerName + '\'' +
                    ", specifications='" + specifications + '\'' +
                    ", rfidCode='" + rfidCode + '\'' +
                    ", isInMessage='" + isInMessage + '\'' +
                    ", bgColor=" + bgColor +
                    ", textColor=" + textColor +
                    '}';
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

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public int getInId() {
        return inId;
    }

    public void setInId(int inId) {
        this.inId = inId;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getInTheme() {
        return inTheme;
    }

    public void setInTheme(String inTheme) {
        this.inTheme = inTheme;
    }

    public String getPlanInDate() {
        return planInDate;
    }

    public void setPlanInDate(String planInDate) {
        this.planInDate = planInDate;
    }

    public int getInUserId() {
        return inUserId;
    }

    public void setInUserId(int inUserId) {
        this.inUserId = inUserId;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Object getSecondStatus() {
        return secondStatus;
    }

    public void setSecondStatus(Object secondStatus) {
        this.secondStatus = secondStatus;
    }

    public int getBussId() {
        return bussId;
    }

    public void setBussId(int bussId) {
        this.bussId = bussId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Object getMaterialCategory() {
        return materialCategory;
    }

    public void setMaterialCategory(Object materialCategory) {
        this.materialCategory = materialCategory;
    }

    public String getInStatus() {
        return inStatus;
    }

    public void setInStatus(String inStatus) {
        this.inStatus = inStatus;
    }

    public String getInType() {
        return inType;
    }

    public void setInType(String inType) {
        this.inType = inType;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }
}
