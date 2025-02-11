package com.victor.base.data.entity;

import android.graphics.drawable.Drawable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class AllocateData {
    private String createBy;
    private String createTime;
    private String updateBy;
    private String updateTime;
    private String remark;
    @PrimaryKey
    private int id;
    private int bussId;
    private String exchangeName;
    private int applyDept;
    private int exchangeDept;
    @Ignore
    private List<AllocateMaterial> materials;
    private String exchangePerson;
    private String exchangeStatus;
    private String exchangeTel;
    private String auditStatus;
    private int deptId;
    private String handleStatus;
    private String exchangeNumber;
    private String handleDept;
    private String applyDeptName;
    private String excDeptName;
    private String delFlag;
    private String planExchangeDate;
    private String secondStatus;
    private int finished;
    private String checkDate;

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
    public static class AllocateMaterial {

        private int allocateId;
        private String createBy;
        private String createTime;
        private String updateBy;
        private String updateTime;
        private String remark;
        @PrimaryKey
        private int materialId;
        private String materialCode;
        private String materialName;
        private String materialStatus;
        private String materialStatusName;
        private String deptName;
        private String warehouseName;
        private String whAreaName;
        private String whLocationName;
        private String providerName;
        private String unitName;
        private String materialValue;
        private String deptId;
        private int warehouseId;
        private int whAreaId;
        private int whLocationId;
        private int unitId;
        private int providerId;
        private String specifications;
        private String purchaseDate;
        private String inDate;
        private String preRepairDate;
        private int repairCycle;
        private int serviceLife;
        private String rfidCode;
        private String delFlag;
        private String isAllocate; // 入库结果 0未入库 1已入库
        private transient String isAllocateMessage; // 入库结果 0未入库 1已入库
        @Ignore
        public transient Drawable bgColor;
        @Ignore
        public transient Drawable textColor;

        public int getAllocateId() {
            return allocateId;
        }

        public void setAllocateId(int allocateId) {
            this.allocateId = allocateId;
        }

        public String getIsAllocate() {
            return isAllocate;
        }

        public void setIsAllocate(String isAllocate) {
            this.isAllocate = isAllocate;
        }

        public String getIsAllocateMessage() {
            return isAllocateMessage;
        }

        public void setIsAllocateMessage(String isAllocateMessage) {
            this.isAllocateMessage = isAllocateMessage;
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

        public int getMaterialId() {
            return materialId;
        }

        public void setMaterialId(int materialId) {
            this.materialId = materialId;
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

        public String getMaterialStatus() {
            return materialStatus;
        }

        public void setMaterialStatus(String materialStatus) {
            this.materialStatus = materialStatus;
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

        public String getProviderName() {
            return providerName;
        }

        public void setProviderName(String providerName) {
            this.providerName = providerName;
        }

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        public String getMaterialValue() {
            return materialValue;
        }

        public void setMaterialValue(String materialValue) {
            this.materialValue = materialValue;
        }

        public String getDeptId() {
            return deptId;
        }

        public void setDeptId(String deptId) {
            this.deptId = deptId;
        }

        public int getWarehouseId() {
            return warehouseId;
        }

        public void setWarehouseId(int warehouseId) {
            this.warehouseId = warehouseId;
        }

        public int getWhAreaId() {
            return whAreaId;
        }

        public void setWhAreaId(int whAreaId) {
            this.whAreaId = whAreaId;
        }

        public int getWhLocationId() {
            return whLocationId;
        }

        public void setWhLocationId(int whLocationId) {
            this.whLocationId = whLocationId;
        }

        public int getUnitId() {
            return unitId;
        }

        public void setUnitId(int unitId) {
            this.unitId = unitId;
        }

        public int getProviderId() {
            return providerId;
        }

        public void setProviderId(int providerId) {
            this.providerId = providerId;
        }

        public String getSpecifications() {
            return specifications;
        }

        public void setSpecifications(String specifications) {
            this.specifications = specifications;
        }

        public String getPurchaseDate() {
            return purchaseDate;
        }

        public void setPurchaseDate(String purchaseDate) {
            this.purchaseDate = purchaseDate;
        }

        public String getInDate() {
            return inDate;
        }

        public void setInDate(String inDate) {
            this.inDate = inDate;
        }

        public String getPreRepairDate() {
            return preRepairDate;
        }

        public void setPreRepairDate(String preRepairDate) {
            this.preRepairDate = preRepairDate;
        }

        public int getRepairCycle() {
            return repairCycle;
        }

        public void setRepairCycle(int repairCycle) {
            this.repairCycle = repairCycle;
        }

        public int getServiceLife() {
            return serviceLife;
        }

        public void setServiceLife(int serviceLife) {
            this.serviceLife = serviceLife;
        }

        public String getRfidCode() {
            return rfidCode;
        }

        public void setRfidCode(String rfidCode) {
            this.rfidCode = rfidCode;
        }

        public String getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(String delFlag) {
            this.delFlag = delFlag;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBussId() {
        return bussId;
    }

    public void setBussId(int bussId) {
        this.bussId = bussId;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public int getApplyDept() {
        return applyDept;
    }

    public void setApplyDept(int applyDept) {
        this.applyDept = applyDept;
    }

    public int getExchangeDept() {
        return exchangeDept;
    }

    public void setExchangeDept(int exchangeDept) {
        this.exchangeDept = exchangeDept;
    }

    public List<AllocateMaterial> getMaterials() {
        return materials;
    }

    public void setMaterials(List<AllocateMaterial> materials) {
        this.materials = materials;
    }

    public String getExchangePerson() {
        return exchangePerson;
    }

    public void setExchangePerson(String exchangePerson) {
        this.exchangePerson = exchangePerson;
    }

    public String getExchangeStatus() {
        return exchangeStatus;
    }

    public void setExchangeStatus(String exchangeStatus) {
        this.exchangeStatus = exchangeStatus;
    }

    public String getExchangeTel() {
        return exchangeTel;
    }

    public void setExchangeTel(String exchangeTel) {
        this.exchangeTel = exchangeTel;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(String handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getExchangeNumber() {
        return exchangeNumber;
    }

    public void setExchangeNumber(String exchangeNumber) {
        this.exchangeNumber = exchangeNumber;
    }

    public String getHandleDept() {
        return handleDept;
    }

    public void setHandleDept(String handleDept) {
        this.handleDept = handleDept;
    }

    public String getApplyDeptName() {
        return applyDeptName;
    }

    public void setApplyDeptName(String applyDeptName) {
        this.applyDeptName = applyDeptName;
    }

    public String getExcDeptName() {
        return excDeptName;
    }

    public void setExcDeptName(String excDeptName) {
        this.excDeptName = excDeptName;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getPlanExchangeDate() {
        return planExchangeDate;
    }

    public void setPlanExchangeDate(String planExchangeDate) {
        this.planExchangeDate = planExchangeDate;
    }

    public String getSecondStatus() {
        return secondStatus;
    }

    public void setSecondStatus(String secondStatus) {
        this.secondStatus = secondStatus;
    }
}
