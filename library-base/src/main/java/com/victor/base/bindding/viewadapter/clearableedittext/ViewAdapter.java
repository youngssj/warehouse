package com.victor.base.bindding.viewadapter.clearableedittext;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

import com.victor.base.widget.ClearableEditText;

/**
 * Created by goldze on 2017/6/16.
 */

public class ViewAdapter {

    @BindingAdapter(value = {"text"}, requireAll = false)
    public static void setText(ClearableEditText editText, String text) {
        if(!editText.getText().equals(text)){
            editText.setText(text);
        }
    }

    @InverseBindingAdapter(attribute = "text", event = "onTextChanged")
    public static String getText(ClearableEditText editText) {
        return editText.getText();
    }

    @BindingAdapter("onTextChanged")
    public static void setListeners(ClearableEditText editText, InverseBindingListener onTextChanged) {
        editText.setTextChangeListener(new ClearableEditText.TextChangeListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onTextChanged.onChange();
            }
        });
    }
}
