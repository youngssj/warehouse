package com.victor.base.bindding.viewadapter.numberprogressbar;

import androidx.databinding.BindingAdapter;

import com.daimajia.numberprogressbar.NumberProgressBar;

public class ViewAdapter {
    @BindingAdapter(value = {"progress_current"}, requireAll = false)
    public static void setCurrentProgress(NumberProgressBar bar, int value) {
        bar.setProgress(value);
    }
}
