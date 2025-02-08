package com.victor.inbound.ui.viewmodel.itemviewmodel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;

import com.victor.base.data.entity.InboundDetail;
import com.victor.inbound.ui.viewmodel.InboundScanListViewModel;

import java.util.Objects;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class InboundScanItemViewModel extends ItemViewModel<InboundScanListViewModel> {

    public ObservableField<InboundDetail.ElecMaterialList> entity = new ObservableField<>();

    public BindingCommand itemClick = new BindingCommand(() -> {
        viewModel.uc.showCustomEvent.setValue(this);
    });

    public InboundScanItemViewModel(@NonNull InboundScanListViewModel viewModel, InboundDetail.ElecMaterialList bean) {
        super(viewModel);
        this.entity.set(bean);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof InboundScanItemViewModel) {
            InboundScanItemViewModel model = (InboundScanItemViewModel) obj;
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
