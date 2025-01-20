package com.victor.materials.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.victor.materials.BR;
import com.victor.materials.R;
import com.victor.materials.ui.viewmodel.itemviewmodel.MaterialsItemViewModel;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class MaterialsViewModel extends BaseViewModel {
    public MaterialsViewModel(@NonNull Application application) {
        super(application);
        materialsList.add(new MaterialsItemViewModel(this, "001", "1111", "在库", "1111"));
        materialsList.add(new MaterialsItemViewModel(this, "002", "2222", "待入库", "2222"));
        materialsList.add(new MaterialsItemViewModel(this, "003", "3333", "待出库", "3333"));
        materialsList.add(new MaterialsItemViewModel(this, "004", "4444", "待移库", "4444"));
        materialsList.add(new MaterialsItemViewModel(this, "005", "5555", "待盘点", "5555"));
    }

    public class UIChangeObservable {
        public SingleLiveEvent<MaterialsItemViewModel> showCustomEvent = new SingleLiveEvent<>();
    }

    public UIChangeObservable uc = new UIChangeObservable();

    public ObservableList<MaterialsItemViewModel> materialsList = new ObservableArrayList<>();
    public ItemBinding<MaterialsItemViewModel> materialsItemBinding = ItemBinding.of(BR.viewModel, R.layout.materials_item);
}
