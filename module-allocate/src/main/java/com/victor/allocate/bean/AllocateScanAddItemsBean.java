package com.victor.allocate.bean;

import com.victor.base.data.entity.AllocateData;

import java.util.List;

public class AllocateScanAddItemsBean {

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private List<AllocateData.AllocateMaterial> materials;

    public List<AllocateData.AllocateMaterial> getMaterials() {
        return materials;
    }

    public void setMaterials(List<AllocateData.AllocateMaterial> materials) {
        this.materials = materials;
    }

    @Override
    public String toString() {
        return "AllocateScanAddItemsBean{" +
                "position=" + position +
                ", materials=" + materials +
                '}';
    }
}
