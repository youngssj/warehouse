package com.victor.outbounddirect.ui.viewmodel.itemviewmodel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;

import com.victor.base.data.entity.MaterialBean;
import com.victor.outbounddirect.ui.viewmodel.OutboundScanViewModel;

import java.util.Objects;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class OutboundScanItemViewModel extends ItemViewModel<OutboundScanViewModel> {

    public ObservableField<MaterialBean> entity = new ObservableField<>();

    public BindingCommand itemClick = new BindingCommand(() -> {
        viewModel.uc.showCustomEvent.setValue(this);
    });

    public OutboundScanItemViewModel(@NonNull OutboundScanViewModel viewModel, MaterialBean bean) {
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
