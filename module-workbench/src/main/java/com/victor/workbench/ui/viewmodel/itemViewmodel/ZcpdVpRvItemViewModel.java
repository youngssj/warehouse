package com.victor.workbench.ui.viewmodel.itemViewmodel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;

import com.victor.base.data.entity.TakeStockDetail;
import com.victor.workbench.ui.viewmodel.ZcpdViewModel;

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

    public ObservableField<TakeStockDetail.ElecMaterialListDTO> entity = new ObservableField<>();


    public BindingCommand itemClick = new BindingCommand(() -> {
        viewModel.uc.showCustomEvent.setValue(this);
    });

    public ZcpdVpRvItemViewModel(@NonNull ZcpdViewModel viewModel, TakeStockDetail.ElecMaterialListDTO data) {
        super(viewModel);
        entity.set(data);
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
