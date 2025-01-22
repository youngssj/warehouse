package com.victor.base.bindding.viewadapter.textview;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

import com.victor.base.widget.ClearableEditText;

/**
 * Created by goldze on 2017/6/16.
 */

public class ViewAdapter {

    @BindingAdapter(value = {"textColor"}, requireAll = false)
    public static void setText(TextView textView, int textColor) {
        textView.setTextColor(textView.getResources().getColor(textColor));
    }

    @BindingAdapter(value = {"background"}, requireAll = false)
    public static void setBackground(TextView textView, int background) {
        textView.setBackgroundResource(background);
    }
}
