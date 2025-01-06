package com.victor.base.data.entity;

public class ListData<T> {

    public ListData(int total, T list){
        this.total = total;
        this.list = list;
    }

    private int total;

    private T list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public T getList() {
        return list;
    }

    public void setList(T list) {
        this.list = list;
    }
}
