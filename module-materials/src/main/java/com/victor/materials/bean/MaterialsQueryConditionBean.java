package com.victor.materials.bean;

public class MaterialsQueryConditionBean {
    public MaterialsQueryConditionBean(String materialName, String rfidCode) {
        this.materialName = materialName;
        this.rfidCode = rfidCode;
    }

    private String materialName;
    private String rfidCode;

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getRfidCode() {
        return rfidCode;
    }

    public void setRfidCode(String rfidCode) {
        this.rfidCode = rfidCode;
    }
}
