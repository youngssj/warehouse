package com.victor.inventory.ui.adapter;

import android.view.ViewGroup;

import androidx.databinding.ViewDataBinding;

import com.victor.inventory.databinding.InventoryItemVpZcpdBinding;
import com.victor.inventory.ui.viewmodel.itemViewmodel.ZcpdVpItemViewModel;

import me.tatarka.bindingcollectionadapter2.BindingViewPagerAdapter;


/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/9/17
 * 邮箱：jxfengmtx@gmail.com
 */

public class ZcpdVpBindingAdapter extends BindingViewPagerAdapter<ZcpdVpItemViewModel> {

    @Override
    public void onBindBinding(final ViewDataBinding binding, int variableId, int layoutRes, final int position, ZcpdVpItemViewModel item) {
        super.onBindBinding(binding, variableId, layoutRes, position, item);
        //这里可以强转成ViewPagerItemViewModel对应的ViewDataBinding，
        InventoryItemVpZcpdBinding _binding = (InventoryItemVpZcpdBinding) binding;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
