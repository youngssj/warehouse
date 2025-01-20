package com.victor.materials.ui.viewmodel.itemviewmodel;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.victor.materials.ui.viewmodel.MaterialsViewModel;

import me.goldze.mvvmhabit.base.ItemViewModel;

public class MaterialsItemViewModel extends ItemViewModel<MaterialsViewModel> {

    public ObservableField<String> materialsCode = new ObservableField<>();
    public ObservableField<String> materialsName = new ObservableField<>();
    public ObservableField<String> materialsStatus = new ObservableField<>();
    public ObservableField<String> rfid = new ObservableField<>();

    public MaterialsItemViewModel(@NonNull MaterialsViewModel viewModel, String materialsCode, String materialsName, String materialsStatus, String rfid) {
        super(viewModel);
        this.materialsCode.set(materialsCode);
        this.materialsName.set(materialsName);
        this.materialsStatus.set(materialsStatus);
        this.rfid.set(rfid);
    }
}
