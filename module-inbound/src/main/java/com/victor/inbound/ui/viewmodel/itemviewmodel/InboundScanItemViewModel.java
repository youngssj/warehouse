package com.victor.inbound.ui.viewmodel.itemviewmodel;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.victor.base.data.entity.TakeStockDetail;
import com.victor.inbound.ui.viewmodel.InboundScanListViewModel;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class InboundScanItemViewModel extends ItemViewModel<InboundScanListViewModel> {

    public ObservableField<String> batchNumber = new ObservableField<>();
    public ObservableField<TakeStockDetail.ElecMaterialListDTO> entity = new ObservableField<>();

    public BindingCommand itemClick = new BindingCommand(() -> {
//        viewModel.uc.showCustomEvent.setValue(this);
    });

    public InboundScanItemViewModel(@NonNull InboundScanListViewModel viewModel, String batchNumber, TakeStockDetail.ElecMaterialListDTO bean) {
        super(viewModel);
        this.batchNumber.set(batchNumber);
        this.entity.set(bean);
    }
}
