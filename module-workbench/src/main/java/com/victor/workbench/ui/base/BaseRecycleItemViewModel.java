package com.victor.workbench.ui.base;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

/**
 * 版权：heihei
 * h
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/9/27
 * 邮箱：jxfengmtx@gmail.com
 */
public abstract class BaseRecycleItemViewModel<M extends BaseOddViewModel, T> extends ItemViewModel<M> {

    public ObservableField<T> entity = new ObservableField<>();

    public BaseRecycleItemViewModel(@NonNull M viewModel, T data) {
        super(viewModel);
        entity.set(data);
    }

    public BindingCommand itemClick = new BindingCommand(() -> {
        itemClickCallback();
    });

    protected abstract void itemClickCallback();

    /**
     * 获取position的方式有很多种,indexOf是其中一种，常见的还有在Adapter中、ItemBinding.of回调里
     *
     * @return
     */
    public int getPosition() {
        return viewModel.getItemPosition(this);
    }


}
