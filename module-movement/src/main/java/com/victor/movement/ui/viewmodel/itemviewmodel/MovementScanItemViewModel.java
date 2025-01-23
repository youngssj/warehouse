package com.victor.movement.ui.viewmodel.itemviewmodel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;

import com.victor.base.data.entity.MovementDetail;
import com.victor.movement.ui.viewmodel.MovementScanListViewModel;

import java.util.Objects;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class MovementScanItemViewModel extends ItemViewModel<MovementScanListViewModel> {

    public ObservableField<MovementDetail.ElecMaterialList> entity = new ObservableField<>();

    public BindingCommand itemClick = new BindingCommand(() -> {
        viewModel.uc.showCustomEvent.setValue(this);
    });

    public MovementScanItemViewModel(@NonNull MovementScanListViewModel viewModel, MovementDetail.ElecMaterialList bean) {
        super(viewModel);
        this.entity.set(bean);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof MovementScanItemViewModel) {
            MovementScanItemViewModel model = (MovementScanItemViewModel) obj;
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
