package com.victor.outbounddirect.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.victor.base.base.BaseTitleViewModel;
import com.victor.base.data.entity.OperateCategory;
import com.victor.outbounddirect.BR;
import com.victor.outbounddirect.R;
import com.victor.outbounddirect.ui.viewmodel.itemviewmodel.OutboundCategoryItemViewModel;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class OutboundCategoryViewModel extends BaseTitleViewModel {

    public ObservableList<OutboundCategoryItemViewModel> outboundCategoryList = new ObservableArrayList<>();
    public ItemBinding<OutboundCategoryItemViewModel> outboundTypeItemBinding = ItemBinding.of(BR.viewModel, R.layout.outbounddirect_item_category);


    public OutboundCategoryViewModel(@NonNull Application application) {
        super(application);

        outboundCategoryList.add(new OutboundCategoryItemViewModel(this, new OperateCategory("1", "领料出库")));
        outboundCategoryList.add(new OutboundCategoryItemViewModel(this, new OperateCategory("2", "销售出库")));
        outboundCategoryList.add(new OutboundCategoryItemViewModel(this, new OperateCategory("3", "调拨出库")));
        outboundCategoryList.add(new OutboundCategoryItemViewModel(this, new OperateCategory("4", "其他出库")));
    }
}
