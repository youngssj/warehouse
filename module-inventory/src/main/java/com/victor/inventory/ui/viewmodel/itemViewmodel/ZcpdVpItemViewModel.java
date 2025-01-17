package com.victor.inventory.ui.viewmodel.itemViewmodel;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.victor.base.data.entity.TakeStockDetail;
import com.victor.inventory.BR;
import com.victor.inventory.R;
import com.victor.inventory.ui.viewmodel.ZcpdViewModel;

import java.util.List;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/9/17
 * 邮箱：jxfengmtx@gmail.com
 */

public class ZcpdVpItemViewModel extends ItemViewModel<ZcpdViewModel> {

    public ObservableList<ZcpdVpRvItemViewModel> observableList = new ObservableArrayList<>();

    public ItemBinding<ZcpdVpRvItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.inventory_item_vp_rv_zcpd);

    public ZcpdVpItemViewModel(@NonNull ZcpdViewModel viewModel, int pagerIndex, String batchNumber, List<TakeStockDetail.ElecMaterialListDTO> data) {
        super(viewModel);
        switch (pagerIndex) {
            case 0:
            case 2:
                for (TakeStockDetail.ElecMaterialListDTO bean : data) {
                    observableList.add(new ZcpdVpRvItemViewModel(viewModel, batchNumber, bean));
                }
                break;
            case 1:
            default:
                break;
        }
    }

}
