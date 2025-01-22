package com.victor.base.data.entity;

import com.victor.base.R;

public class MaterialsData {
    private String createBy;
    private String createTime;
    private String updateBy;
    private String updateTime;
    private Object remark;
    private int materialId;
    private String materialCode;
    private String materialName;
    private String materialStatus;
    private int materialStatusColor;
    private int materialStatusBackground;
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
    private Object specifications;
    private Object purchaseDate;
    private Object inDate;
    private Object preRepairDate;
    private int repairCycle;
    private Object serviceLife;
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

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
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
        if ("0".equals(materialStatus)) {
            return "录入";
        } else if ("1".equals(materialStatus)) {
            return "在库";
        } else if ("2".equals(materialStatus)) {
            return "出库";
        } else if ("11".equals(materialStatus)) {
            return "待入库";
        } else if ("12".equals(materialStatus)) {
            return "待出库";
        } else if ("13".equals(materialStatus)) {
            return "待盘点";
        } else if ("14".equals(materialStatus)) {
            return "待移库";
        } else if ("15".equals(materialStatus)) {
            return "待调拨";
        }
        return materialStatus;
    }

    public int getMaterialStatusColor() {
        if ("0".equals(materialStatus)) {
            return R.color.color_258DFF;
        } else if ("1".equals(materialStatus)) {
            return R.color.color_4BFF00;
        } else if ("2".equals(materialStatus)) {
            return R.color.color_FF0000;
        } else if ("11".equals(materialStatus)) {
            return R.color.color_FF9100;
        } else if ("12".equals(materialStatus)) {
            return R.color.color_FF9100;
        } else if ("13".equals(materialStatus)) {
            return R.color.color_FF9100;
        } else if ("14".equals(materialStatus)) {
            return R.color.color_FF9100;
        } else if ("15".equals(materialStatus)) {
            return R.color.color_FF9100;
        }
        return R.color.color_FF9100;
    }

    public void setMaterialStatusColor(int materialStatusColor) {
        this.materialStatusColor = materialStatusColor;
    }

    public int getMaterialStatusBackground() {
        if ("0".equals(materialStatus)) {
            return R.drawable.materials_shape_status_1;
        } else if ("1".equals(materialStatus)) {
            return R.drawable.materials_shape_status_2;
        } else if ("2".equals(materialStatus)) {
            return R.drawable.materials_shape_status_3;
        } else if ("11".equals(materialStatus)) {
            return R.drawable.materials_shape_status_4;
        } else if ("12".equals(materialStatus)) {
            return R.drawable.materials_shape_status_4;
        } else if ("13".equals(materialStatus)) {
            return R.drawable.materials_shape_status_4;
        } else if ("14".equals(materialStatus)) {
            return R.drawable.materials_shape_status_4;
        } else if ("15".equals(materialStatus)) {
            return R.drawable.materials_shape_status_4;
        }
        return R.drawable.materials_shape_status_4;
    }

    public void setMaterialStatusBackground(int materialStatusBackground) {
        this.materialStatusBackground = materialStatusBackground;
    }

    public void setMaterialStatus(String materialStatus) {
        this.materialStatus = materialStatus;
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

    public Object getSpecifications() {
        return specifications;
    }

    public void setSpecifications(Object specifications) {
        this.specifications = specifications;
    }

    public Object getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Object purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Object getInDate() {
        return inDate;
    }

    public void setInDate(Object inDate) {
        this.inDate = inDate;
    }

    public Object getPreRepairDate() {
        return preRepairDate;
    }

    public void setPreRepairDate(Object preRepairDate) {
        this.preRepairDate = preRepairDate;
    }

    public int getRepairCycle() {
        return repairCycle;
    }

    public void setRepairCycle(int repairCycle) {
        this.repairCycle = repairCycle;
    }

    public Object getServiceLife() {
        return serviceLife;
    }

    public void setServiceLife(Object serviceLife) {
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
