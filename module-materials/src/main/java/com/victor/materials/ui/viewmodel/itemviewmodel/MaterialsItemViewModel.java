package com.victor.materials.ui.viewmodel.itemviewmodel;

import androidx.annotation.NonNull;

import com.victor.base.data.entity.MaterialsData;
import com.victor.materials.ui.viewmodel.MaterialsViewModel;
import com.victor.workbench.ui.base.BaseRecycleItemViewModel;

public class MaterialsItemViewModel extends BaseRecycleItemViewModel<MaterialsViewModel, MaterialsData> {

    public MaterialsItemViewModel(@NonNull MaterialsViewModel viewModel, MaterialsData materialsData) {
        super(viewModel, materialsData);
    }

    @Override
    protected void itemClickCallback() {
        viewModel.muc.showCustomEvent.setValue(this);
    }
}
