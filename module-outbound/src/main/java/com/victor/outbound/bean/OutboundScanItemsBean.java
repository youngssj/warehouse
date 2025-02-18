package com.victor.outbound.bean;

import com.victor.base.data.entity.OutboundData;

import java.util.List;

public class OutboundScanItemsBean {

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private List<OutboundData.OutboundElecMaterial> elecMaterialList;

    public List<OutboundData.OutboundElecMaterial> getElecMaterialList() {
        return elecMaterialList;
    }

    public void setElecMaterialList(List<OutboundData.OutboundElecMaterial> elecMaterialList) {
        this.elecMaterialList = elecMaterialList;
    }

    @Override
    public String toString() {
        return "OutboundScanItemsBean{" +
                "position=" + position +
                ", elecMaterialList=" + elecMaterialList +
                '}';
    }
}
