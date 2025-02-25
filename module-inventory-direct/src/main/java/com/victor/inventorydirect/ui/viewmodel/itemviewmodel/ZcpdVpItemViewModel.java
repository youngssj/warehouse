package com.victor.inventorydirect.ui.viewmodel.itemviewmodel;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.victor.base.data.entity.InventoryData;
import com.victor.inventorydirect.BR;
import com.victor.inventorydirect.R;
import com.victor.inventorydirect.ui.viewmodel.ZcpdViewModel;

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

    public ItemBinding<ZcpdVpRvItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.inventorydirect_item_vp_rv_zcpd);

    public ZcpdVpItemViewModel(@NonNull ZcpdViewModel viewModel, int pagerIndex, String batchNumber, List<InventoryData.InventoryElecMaterial> data) {
        super(viewModel);
        switch (pagerIndex) {
            case 0:
            case 2:
                for (InventoryData.InventoryElecMaterial bean : data) {
                    observableList.add(new ZcpdVpRvItemViewModel(viewModel, batchNumber, bean));
                }
                break;
            case 1:
            default:
                break;
        }
    }

}
