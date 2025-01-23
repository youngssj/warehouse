
package com.victor.inbound.bean;

import com.victor.base.data.entity.InboundDetail;

import java.util.List;

public class InboundScanRemoveItemsBean {

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private List<InboundDetail.ElecMaterialList> elecMaterialList;

    public List<InboundDetail.ElecMaterialList> getElecMaterialList() {
        return elecMaterialList;
    }

    public void setElecMaterialList(List<InboundDetail.ElecMaterialList> elecMaterialList) {
        this.elecMaterialList = elecMaterialList;
    }
}
