package com.victor.movement.bean;

import com.victor.base.data.entity.MovementData;

import java.util.List;

public class MovementScanUpdateItemsBean {

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private List<MovementData.MovementElecMaterial> elecMaterialList;

    public List<MovementData.MovementElecMaterial> getElecMaterialList() {
        return elecMaterialList;
    }

    public void setElecMaterialList(List<MovementData.MovementElecMaterial> elecMaterialList) {
        this.elecMaterialList = elecMaterialList;
    }

    @Override
    public String toString() {
        return "MovementScanAddItemsBean{" +
                "position=" + position +
                ", elecMaterialList=" + elecMaterialList +
                '}';
    }
}
