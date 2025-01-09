package me.goldze.mvvmhabit.binding.viewadapter.imageview;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

public final class ViewAdapter {

    @BindingAdapter(value = {"src"}, requireAll = false)
    public static void setSrc(ImageView view, Bitmap bitmap) {
        view.setImageBitmap(bitmap);
    }

    @BindingAdapter(value = {"src"}, requireAll = false)
    public static void setSrc(ImageView view, int resId) {
        view.setImageResource(resId);
    }

    @BindingAdapter(value = {"background"}, requireAll = false)
    public static void setBackground(ImageView view, Drawable drawable) {
        view.setBackground(drawable);
    }

    @BindingAdapter(value = {"background"}, requireAll = false)
    public static void setBackground(ImageView view, int backgroundId) {
        view.setBackgroundResource(backgroundId);
    }
}
