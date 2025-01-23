
package com.victor.movement.bean;

import com.victor.base.data.entity.MovementDetail;

import java.util.List;

public class MovementScanRemoveItemsBean {

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private List<MovementDetail.ElecMaterialList> elecMaterialList;

    public List<MovementDetail.ElecMaterialList> getElecMaterialList() {
        return elecMaterialList;
    }

    public void setElecMaterialList(List<MovementDetail.ElecMaterialList> elecMaterialList) {
        this.elecMaterialList = elecMaterialList;
    }
}
