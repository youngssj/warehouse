package com.victor.outbound.ui.viewmodel.itemviewmodel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;

import com.victor.base.data.entity.OutboundDetail;
import com.victor.outbound.ui.viewmodel.OutboundScanListViewModel;

import java.util.Objects;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class OutboundScanItemViewModel extends ItemViewModel<OutboundScanListViewModel> {

    public ObservableField<OutboundDetail.ElecMaterialList> entity = new ObservableField<>();

    public BindingCommand itemClick = new BindingCommand(() -> {
        viewModel.uc.showCustomEvent.setValue(this);
    });

    public OutboundScanItemViewModel(@NonNull OutboundScanListViewModel viewModel, OutboundDetail.ElecMaterialList bean) {
        super(viewModel);
        this.entity.set(bean);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof OutboundScanItemViewModel) {
            OutboundScanItemViewModel model = (OutboundScanItemViewModel) obj;
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
