package com.victor.inventorydirect.ui.viewmodel.itemviewmodel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;

import com.victor.base.data.entity.InventoryData;
import com.victor.inventorydirect.ui.viewmodel.ZcpdViewModel;

import java.util.Objects;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/9/17
 * 邮箱：jxfengmtx@gmail.com
 */

public class ZcpdVpRvItemViewModel extends ItemViewModel<ZcpdViewModel> {

    public ObservableField<String> batchNumber = new ObservableField<>();

    public ObservableField<InventoryData.InventoryElecMaterial> entity = new ObservableField<>();

    public BindingCommand itemClick = new BindingCommand(() -> {
        viewModel.uc.showCustomEvent.setValue(this);
    });

    public ZcpdVpRvItemViewModel(@NonNull ZcpdViewModel viewModel, String batchNumber, InventoryData.InventoryElecMaterial data) {
        super(viewModel);
        entity.set(data);
        this.batchNumber.set(batchNumber);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ZcpdVpRvItemViewModel) {
            ZcpdVpRvItemViewModel model = (ZcpdVpRvItemViewModel) obj;
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
