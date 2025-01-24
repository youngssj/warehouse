package com.victor.allocate.bean;

import com.victor.base.data.entity.AllocateDetail;

import java.util.List;

public class AllocateScanUpdateItemsBean {

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private List<AllocateDetail.ElecMaterialList> elecMaterialList;

    public List<AllocateDetail.ElecMaterialList> getElecMaterialList() {
        return elecMaterialList;
    }

    public void setElecMaterialList(List<AllocateDetail.ElecMaterialList> elecMaterialList) {
        this.elecMaterialList = elecMaterialList;
    }

    @Override
    public String toString() {
        return "AllocateScanAddItemsBean{" +
                "position=" + position +
                ", elecMaterialList=" + elecMaterialList +
                '}';
    }
}
