package com.victor.outbound.bean;

import com.victor.base.data.entity.OutboundDetail;

import java.util.List;

public class OutboundScanAddItemsBean {

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private List<OutboundDetail.ElecMaterialList> elecMaterialList;

    public List<OutboundDetail.ElecMaterialList> getElecMaterialList() {
        return elecMaterialList;
    }

    public void setElecMaterialList(List<OutboundDetail.ElecMaterialList> elecMaterialList) {
        this.elecMaterialList = elecMaterialList;
    }

    @Override
    public String toString() {
        return "OutboundScanAddItemsBean{" +
                "position=" + position +
                ", elecMaterialList=" + elecMaterialList +
                '}';
    }
}
