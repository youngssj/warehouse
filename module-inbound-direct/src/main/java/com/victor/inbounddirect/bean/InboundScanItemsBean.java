package com.victor.inbounddirect.bean;

import com.victor.base.data.entity.InboundData;

import java.util.List;

public class InboundScanItemsBean {

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private List<InboundData.InboundElecMaterial> inboundElecMaterial;

    public List<InboundData.InboundElecMaterial> getElecMaterialList() {
        return inboundElecMaterial;
    }

    public void setElecMaterialList(List<InboundData.InboundElecMaterial> inboundElecMaterial) {
        this.inboundElecMaterial = inboundElecMaterial;
    }

    @Override
    public String toString() {
        return "InboundScanItemsBean{" +
                "position=" + position +
                ", elecMaterialList=" + inboundElecMaterial +
                '}';
    }
}
