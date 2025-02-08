package com.victor.inbound.bean;

import com.victor.base.data.entity.InboundData;

import java.util.List;

public class InboundScanUpdateItemsBean {

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private List<InboundData.ElecMaterialList> elecMaterialList;

    public List<InboundData.ElecMaterialList> getElecMaterialList() {
        return elecMaterialList;
    }

    public void setElecMaterialList(List<InboundData.ElecMaterialList> elecMaterialList) {
        this.elecMaterialList = elecMaterialList;
    }

    @Override
    public String toString() {
        return "InboundScanAddItemsBean{" +
                "position=" + position +
                ", elecMaterialList=" + elecMaterialList +
                '}';
    }
}
