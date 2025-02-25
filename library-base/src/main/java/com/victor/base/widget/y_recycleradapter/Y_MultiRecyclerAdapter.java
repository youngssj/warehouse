package com.victor.base.widget.y_recycleradapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


/**
 * Created by mac on 16/1/6.
 */
public class Y_MultiRecyclerAdapter extends RecyclerView.Adapter<GeneralRecyclerViewHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private Y_ItemEntityList itemList;


    public Y_MultiRecyclerAdapter(Context ct, Y_ItemEntityList itemList) {
        this.mContext = ct;
        this.itemList = itemList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public GeneralRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GeneralRecyclerViewHolder(mLayoutInflater.inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GeneralRecyclerViewHolder holder, int position) {

    }

    @Override
    public void onBindViewHolder(GeneralRecyclerViewHolder holder, int position, List<Object> payloads) {
        if (itemList.getOnBind(position) != null) {
            itemList.getOnBind(position).onBindChildViewData(holder, itemList.getItemData(position), position, payloads);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.getItemLayoutId(position);
    }

    @Override
    public int getItemCount() {
        return itemList.getItemCount();
    }

}
