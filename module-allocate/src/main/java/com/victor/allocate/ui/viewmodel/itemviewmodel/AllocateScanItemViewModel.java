package com.victor.allocate.ui.viewmodel.itemviewmodel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;

import com.victor.base.data.entity.AllocateDetail;
import com.victor.allocate.ui.viewmodel.AllocateScanListViewModel;

import java.util.Objects;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class AllocateScanItemViewModel extends ItemViewModel<AllocateScanListViewModel> {

    public ObservableField<AllocateDetail.ElecMaterialList> entity = new ObservableField<>();

    public BindingCommand itemClick = new BindingCommand(() -> {
        viewModel.uc.showCustomEvent.setValue(this);
    });

    public AllocateScanItemViewModel(@NonNull AllocateScanListViewModel viewModel, AllocateDetail.ElecMaterialList bean) {
        super(viewModel);
        this.entity.set(bean);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof AllocateScanItemViewModel) {
            AllocateScanItemViewModel model = (AllocateScanItemViewModel) obj;
            if (Objects.requireNonNull(entity.get()).getMaterialId() == model.entity.get().getMaterialId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.requireNonNull(entity.get()).getMaterialId();
    }
}
