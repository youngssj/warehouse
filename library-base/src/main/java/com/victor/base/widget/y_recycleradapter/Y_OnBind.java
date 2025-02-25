package com.victor.base.widget.y_recycleradapter;


import java.util.List;

/**
 * Created by mac on 16/6/18.
 */
public interface Y_OnBind {

    /**
     * @param holder
     * @param position
     */
    void onBindChildViewData(GeneralRecyclerViewHolder holder, Object itemData, int position, List<Object> payloads);
}
