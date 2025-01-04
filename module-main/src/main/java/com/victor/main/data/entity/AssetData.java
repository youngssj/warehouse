package com.victor.main.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2021/12/16
 * 邮箱：jxfengmtx@gmail.com
 */

@Entity
public class AssetData {

    @PrimaryKey
    private int materialId;
    private String materialCode;
    private String mdefCode;
    private String statusName;
    private String materialName;
    private String rfidcode;
    private String specification;
    private String sortName;
    private String locationName;
    private String brand;
    private String manageDepmName;
    private String manageUserName;
    private String useDepmName;
    private String useUserName;
    private String inDate;
    private String recordDate;
    private String handleDate;
    private String productName;
    private String providerName;
    private Double assetvalue;
    private String sortCode;
    private String sn;
    private String unit;
    private String locationCode;
    private String manageDepmCode;
    private String useDepmCode;
    private int useUserId;
    private String purchaseDate;
    private String productDate;
    private String enableDate;
    private String remark;
    private String createDate;
    private int createUserId;
    private String createUserName;
    private String updatedate;
    private int updateUserId;
    private String updateUserName;
    private int customId;
    private int status;
    private int delflag;


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

    public String getMdefCode() {
        return mdefCode;
    }

    public void setMdefCode(String mdefCode) {
        this.mdefCode = mdefCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getRfidcode() {
        return rfidcode;
    }

    public void setRfidcode(String rfidcode) {
        this.rfidcode = rfidcode;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getManageDepmName() {
        return manageDepmName;
    }

    public void setManageDepmName(String manageDepmName) {
        this.manageDepmName = manageDepmName;
    }

    public String getManageUserName() {
        return manageUserName;
    }

    public void setManageUserName(String manageUserName) {
        this.manageUserName = manageUserName;
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

    public String getInDate() {
        return inDate;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getHandleDate() {
        return handleDate;
    }

    public void setHandleDate(String handleDate) {
        this.handleDate = handleDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public Double getAssetvalue() {
        return assetvalue;
    }

    public void setAssetvalue(Double assetvalue) {
        this.assetvalue = assetvalue;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getManageDepmCode() {
        return manageDepmCode;
    }

    public void setManageDepmCode(String manageDepmCode) {
        this.manageDepmCode = manageDepmCode;
    }

    public String getUseDepmCode() {
        return useDepmCode;
    }

    public void setUseDepmCode(String useDepmCode) {
        this.useDepmCode = useDepmCode;
    }

    public int getUseUserId() {
        return useUserId;
    }

    public void setUseUserId(int useUserId) {
        this.useUserId = useUserId;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getProductDate() {
        return productDate;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }

    public String getEnableDate() {
        return enableDate;
    }

    public void setEnableDate(String enableDate) {
        this.enableDate = enableDate;
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

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }

    public int getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(int updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public int getCustomId() {
        return customId;
    }

    public void setCustomId(int customId) {
        this.customId = customId;
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

}
