package com.victor.base.data.entity;

import java.util.List;

public class MaterialsStatisticsData {
    private List<String> areaArr;
    private List<Integer> areaNum;

    public List<String> getAreaArr() {
        return areaArr;
    }

    public void setAreaArr(List<String> areaArr) {
        this.areaArr = areaArr;
    }

    public List<Integer> getAreaNum() {
        return areaNum;
    }

    public void setAreaNum(List<Integer> areaNum) {
        this.areaNum = areaNum;
    }
}
