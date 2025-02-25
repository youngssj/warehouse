package com.victor.base.widget.y_recycleradapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import me.goldze.mvvmhabit.base.BaseApplication;

/**
 * Created by mac on 15/12/19.
 */
public class GeneralRecyclerViewHolder extends RecyclerView.ViewHolder {

    private final View mItemView;
    private final SparseArray<View> childViews;

    public GeneralRecyclerViewHolder(View itemView) {
        super(itemView);
        this.mItemView = itemView;
        this.childViews = new SparseArray<>(20);
    }

    public <T extends View> T getChildView(int childViewId) {
        View view = childViews.get(childViewId);
        if (view == null) {
            view = mItemView.findViewById(childViewId);
            childViews.put(childViewId, view);
        }
        return (T) view;
    }

    public GeneralRecyclerViewHolder setText(int childViewId, String text) {
        TextView textView = getChildView(childViewId);
        textView.setText(text);
        return this;
    }

    public GeneralRecyclerViewHolder setText(int childViewId, int textRes) {
        TextView textView = getChildView(childViewId);
        textView.setText(BaseApplication.getInstance().getResources().getString(textRes));
        return this;
    }

    public GeneralRecyclerViewHolder setImageView(int childViewId) {
        ImageView imageView = getChildView(childViewId);
        imageView.setVisibility(View.VISIBLE);
        return this;
    }

    public GeneralRecyclerViewHolder setImg(int childViewId, String text, Context context, int rId) {
        TextView textView = getChildView(childViewId);
        textView.setText(text);
        Drawable top = BaseApplication.getInstance().getDrawable(rId);// 获取res下的图片drawable
        top.setBounds(0, 0, top.getMinimumWidth(), top.getMinimumHeight());// 一定要设置setBounds();
        textView.setCompoundDrawables(null, top, null, null);
        return this;
    }

}
