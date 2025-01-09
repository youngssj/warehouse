package me.goldze.mvvmhabit.binding.viewadapter.image;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import androidx.databinding.BindingAdapter;

/**
 * Created by goldze on 2017/6/18.
 */
public final class ViewAdapter {
    @BindingAdapter(value = {"url", "placeholderRes"}, requireAll = false)
    public static void setImageUri(ImageView imageView, String url, int placeholderRes) {
        if (!TextUtils.isEmpty(url)) {
            //使用Glide框架加载图片
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(new RequestOptions().placeholder(placeholderRes))
                    .into(imageView);
        }
    }

    @BindingAdapter(value = {"url_round"})
    public static void setImageUri(ImageView imageView, String url_round) {
        if (!TextUtils.isEmpty(url_round)) {
            //使用Glide框架加载图片
            Glide.with(imageView.getContext())
                    .load(url_round)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(1000)))
                    .into(imageView);
        }
    }
}

