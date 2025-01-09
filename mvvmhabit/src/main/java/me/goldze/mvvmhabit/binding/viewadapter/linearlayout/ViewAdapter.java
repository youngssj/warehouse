package me.goldze.mvvmhabit.binding.viewadapter.linearlayout;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.databinding.BindingAdapter;

public final class ViewAdapter {

    @BindingAdapter(value = {"background"}, requireAll = false)
    public static void setBackground(LinearLayout view, Drawable drawable) {
        view.setBackground(drawable);
    }

    @BindingAdapter(value = {"background"}, requireAll = false)
    public static void setBackground(LinearLayout view, int backgroundId) {
        view.setBackgroundResource(backgroundId);
    }
}
