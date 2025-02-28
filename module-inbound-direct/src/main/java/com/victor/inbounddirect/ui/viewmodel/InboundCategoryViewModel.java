package com.victor.inbounddirect.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.victor.base.base.BaseTitleViewModel;
import com.victor.base.data.entity.InboundCategory;
import com.victor.inbounddirect.BR;
import com.victor.inbounddirect.R;
import com.victor.inbounddirect.ui.viewmodel.itemviewmodel.InboundCategoryItemViewModel;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class InboundCategoryViewModel extends BaseTitleViewModel {

    public ObservableList<InboundCategoryItemViewModel> inboundCategoryList = new ObservableArrayList<>();
    public ItemBinding<InboundCategoryItemViewModel> inboundTypeItemBinding = ItemBinding.of(BR.viewModel, R.layout.inbounddirect_item_category);

    public InboundCategoryViewModel(@NonNull Application application) {
        super(application);

        inboundCategoryList.add(new InboundCategoryItemViewModel(this, new InboundCategory("1", "采购入库")));
        inboundCategoryList.add(new InboundCategoryItemViewModel(this, new InboundCategory("2", "生产入库")));
        inboundCategoryList.add(new InboundCategoryItemViewModel(this, new InboundCategory("3", "退料入库")));
        inboundCategoryList.add(new InboundCategoryItemViewModel(this, new InboundCategory("4", "退货入库")));
        inboundCategoryList.add(new InboundCategoryItemViewModel(this, new InboundCategory("5", "回货入库")));
        inboundCategoryList.add(new InboundCategoryItemViewModel(this, new InboundCategory("6", "其他入库")));
    }
}
