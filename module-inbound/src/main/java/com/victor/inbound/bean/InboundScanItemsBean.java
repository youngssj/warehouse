package com.victor.inbound.bean;

import com.victor.base.data.entity.TakeStockDetail;

import java.util.List;

public class InboundScanItemsBean {

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private String batchNumber;
    private List<TakeStockDetail.ElecMaterialListDTO> elecMaterialList;

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public List<TakeStockDetail.ElecMaterialListDTO> getElecMaterialList() {
        return elecMaterialList;
    }

    public void setElecMaterialList(List<TakeStockDetail.ElecMaterialListDTO> elecMaterialList) {
        this.elecMaterialList = elecMaterialList;
    }
}
