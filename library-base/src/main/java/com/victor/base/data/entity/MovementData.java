package com.victor.base.data.entity;

import android.graphics.drawable.Drawable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class MovementData {
    private String createBy;
    private String createTime;
    private String updateBy;
    private String updateTime;
    private String remark;
    @PrimaryKey
    private int id;
    private int bussId;
    private int oldAreaId;
    private String oldAreaName;
    private int newAreaId;
    private String newAreaName;
    private String oldLocationId;
    private String newLocationId;
    private String batchNumber;
    private int moveUserId;
    private String deptId;
    private String moveStatus;
    private String moveType;
    private String moveTheme;
    private String auditStatus;
    private String secondStatus;
    private String planMoveDate;
    private String delFlag;
    @Ignore
    private List<MovementElecMaterial> elecMaterialList;
    @Ignore
    private List<Materials> materials;
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
    public static class MovementElecMaterial {
        private String createBy;
        private String createTime;
        private String updateBy;
        private String updateTime;
        private String remark;
        @PrimaryKey
        private int id;
        private int moveId;
        private int materialId;
        private int oldLocationId;
        private int newLocationId;
        private String delFlag;
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
        private String isMove; // 入库结果 0未入库 1已入库
        private transient String isMoveMessage; // 入库结果 0未入库 1已入库
        @Ignore
        public transient Drawable bgColor;
        @Ignore
        public transient Drawable textColor;

        public String getIsMove() {
            return isMove;
        }

        public void setIsMove(String isMove) {
            this.isMove = isMove;
        }

        public String getIsMoveMessage() {
            return isMoveMessage;
        }

        public void setIsMoveMessage(String isMoveMessage) {
            this.isMoveMessage = isMoveMessage;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMoveId() {
            return moveId;
        }

        public void setMoveId(int moveId) {
            this.moveId = moveId;
        }

        public int getMaterialId() {
            return materialId;
        }

        public void setMaterialId(int materialId) {
            this.materialId = materialId;
        }

        public int getOldLocationId() {
            return oldLocationId;
        }

        public void setOldLocationId(int oldLocationId) {
            this.oldLocationId = oldLocationId;
        }

        public int getNewLocationId() {
            return newLocationId;
        }

        public void setNewLocationId(int newLocationId) {
            this.newLocationId = newLocationId;
        }

        public String getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(String delFlag) {
            this.delFlag = delFlag;
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
    }

    public static class Materials {
        private String createBy;
        private String createTime;
        private String updateBy;
        private String updateTime;
        private String remark;
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

    public int getOldAreaId() {
        return oldAreaId;
    }

    public void setOldAreaId(int oldAreaId) {
        this.oldAreaId = oldAreaId;
    }

    public String getOldAreaName() {
        return oldAreaName;
    }

    public void setOldAreaName(String oldAreaName) {
        this.oldAreaName = oldAreaName;
    }

    public int getNewAreaId() {
        return newAreaId;
    }

    public void setNewAreaId(int newAreaId) {
        this.newAreaId = newAreaId;
    }

    public String getNewAreaName() {
        return newAreaName;
    }

    public void setNewAreaName(String newAreaName) {
        this.newAreaName = newAreaName;
    }

    public String getOldLocationId() {
        return oldLocationId;
    }

    public void setOldLocationId(String oldLocationId) {
        this.oldLocationId = oldLocationId;
    }

    public String getNewLocationId() {
        return newLocationId;
    }

    public void setNewLocationId(String newLocationId) {
        this.newLocationId = newLocationId;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public int getMoveUserId() {
        return moveUserId;
    }

    public void setMoveUserId(int moveUserId) {
        this.moveUserId = moveUserId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getMoveStatus() {
        return moveStatus;
    }

    public void setMoveStatus(String moveStatus) {
        this.moveStatus = moveStatus;
    }

    public String getMoveType() {
        return moveType;
    }

    public void setMoveType(String moveType) {
        this.moveType = moveType;
    }

    public String getMoveTheme() {
        return moveTheme;
    }

    public void setMoveTheme(String moveTheme) {
        this.moveTheme = moveTheme;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getSecondStatus() {
        return secondStatus;
    }

    public void setSecondStatus(String secondStatus) {
        this.secondStatus = secondStatus;
    }

    public String getPlanMoveDate() {
        return planMoveDate;
    }

    public void setPlanMoveDate(String planMoveDate) {
        this.planMoveDate = planMoveDate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public List<MovementElecMaterial> getElecMaterialList() {
        return elecMaterialList;
    }

    public void setElecMaterialList(List<MovementElecMaterial> elecMaterialList) {
        this.elecMaterialList = elecMaterialList;
    }

    public List<Materials> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Materials> materials) {
        this.materials = materials;
    }
}
