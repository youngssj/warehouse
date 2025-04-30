package com.victor.base.data.entity;

import android.graphics.drawable.Drawable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class OutboundData {
    private String createBy;
    private String createTime;
    private String updateBy;
    private String updateTime;
    private String remark;
    @PrimaryKey
    private int outId;
    private String auditStatus;
    private int bussId;
    private String batchNumber;
    private String outTheme;
    private String planOutDate;
    private int outUserId;
    private String userName;
    private String materialCategory;
    private String outStatus;
    private String outType;
    private String delFlag;
    private int deptId;
    private String secondStatus;
    @Ignore
    private List<OutboundElecMaterial> elecMaterialList;
    private int finished;
    private String checkDate;
    private String outCategory;
    private Long bussJindieId;

    public String getOutCategory() {
        return outCategory;
    }

    public void setOutCategory(String outCategory) {
        this.outCategory = outCategory;
    }

    public Long getBussJindieId() {
        return bussJindieId;
    }

    public void setBussJindieId(Long bussJindieId) {
        this.bussJindieId = bussJindieId;
    }

    public List<OutboundElecMaterial> getElecMaterialList() {
        return elecMaterialList;
    }

    public void setElecMaterialList(List<OutboundElecMaterial> elecMaterialList) {
        this.elecMaterialList = elecMaterialList;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    @Entity
    public static class OutboundElecMaterial {
        private String createBy;
        private String createTime;
        private String updateBy;
        private String updateTime;
        private String remark;
        @PrimaryKey
        private int outdetailId;
        private int outId;
        private int materialId;
        private String outDate;
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
        private String isOut; // 入库结果 0未入库 1已入库
        private transient String isOutMessage; // 入库结果 0未入库 1已入库
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

        public String getIsOutMessage() {
            return isOutMessage;
        }

        public void setIsOutMessage(String isOutMessage) {
            this.isOutMessage = isOutMessage;
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

        public int getOutdetailId() {
            return outdetailId;
        }

        public void setOutdetailId(int outdetailId) {
            this.outdetailId = outdetailId;
        }

        public int getOutId() {
            return outId;
        }

        public void setOutId(int outId) {
            this.outId = outId;
        }

        public int getMaterialId() {
            return materialId;
        }

        public void setMaterialId(int materialId) {
            this.materialId = materialId;
        }

        public String getIsOut() {
            return isOut;
        }

        public void setIsOut(String isOut) {
            this.isOut = isOut;
        }

        public String getOutDate() {
            return outDate;
        }

        public void setOutDate(String outDate) {
            this.outDate = outDate;
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

    public int getOutId() {
        return outId;
    }

    public void setOutId(int outId) {
        this.outId = outId;
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

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getOutTheme() {
        return outTheme;
    }

    public void setOutTheme(String outTheme) {
        this.outTheme = outTheme;
    }

    public String getPlanOutDate() {
        return planOutDate;
    }

    public void setPlanOutDate(String planOutDate) {
        this.planOutDate = planOutDate;
    }

    public int getOutUserId() {
        return outUserId;
    }

    public void setOutUserId(int outUserId) {
        this.outUserId = outUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMaterialCategory() {
        return materialCategory;
    }

    public void setMaterialCategory(String materialCategory) {
        this.materialCategory = materialCategory;
    }

    public String getOutStatus() {
        return outStatus;
    }

    public void setOutStatus(String outStatus) {
        this.outStatus = outStatus;
    }

    public String getOutType() {
        return outType;
    }

    public void setOutType(String outType) {
        this.outType = outType;
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

    public String getSecondStatus() {
        return secondStatus;
    }

    public void setSecondStatus(String secondStatus) {
        this.secondStatus = secondStatus;
    }
}
