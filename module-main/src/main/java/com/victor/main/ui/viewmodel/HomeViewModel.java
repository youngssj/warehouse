package com.victor.main.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import com.victor.base.data.entity.StatisticsInfo;
import com.victor.main.BR;
import com.victor.main.R;
import com.victor.main.ui.viewmodel.itemviewmodel.BusinessNotificationItemViewModel;
import com.victor.main.ui.viewmodel.itemviewmodel.InBoundItemViewModel;
import com.victor.main.ui.viewmodel.itemviewmodel.OutBoundItemViewModel;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class HomeViewModel extends BaseViewModel {

    public ObservableField<StatisticsInfo> statisticsInfo = new ObservableField<>();
    // 业务提醒
    public ObservableList<BusinessNotificationItemViewModel> businessNotificationList = new ObservableArrayList<>();
    // 出库状态
    public ObservableList<OutBoundItemViewModel> outBoundList = new ObservableArrayList<>();
    // 入库状态
    public ObservableList<InBoundItemViewModel> inBoundList = new ObservableArrayList<>();

    public ItemBinding<BusinessNotificationItemViewModel> businessNotificationItemBinding = ItemBinding.of(BR.viewModel, R.layout.main_item_home_layout2);
    public ItemBinding<OutBoundItemViewModel> outBoundListItemBinding = ItemBinding.of(BR.viewModel, R.layout.main_item_home_layout5);
    public ItemBinding<InBoundItemViewModel> inBoundListItemBinding = ItemBinding.of(BR.viewModel, R.layout.main_item_home_layout6);

    public HomeViewModel(@NonNull Application application) {
        super(application);
        statisticsInfo.set(new StatisticsInfo());
        businessNotificationList.add(new BusinessNotificationItemViewModel(this, "xx原料", "入库"));
        businessNotificationList.add(new BusinessNotificationItemViewModel(this, "xx产品", "出库"));
        businessNotificationList.add(new BusinessNotificationItemViewModel(this, "xx配件", "入库"));
        businessNotificationList.add(new BusinessNotificationItemViewModel(this, "xx配件", "入库"));
        businessNotificationList.add(new BusinessNotificationItemViewModel(this, "xx配件", "入库"));

        outBoundList.add(new OutBoundItemViewModel(this, "xx配件", "入库", "2025-01-07", "admin"));
        outBoundList.add(new OutBoundItemViewModel(this, "xx配件", "入库", "2025-01-07", "admin"));
        outBoundList.add(new OutBoundItemViewModel(this, "xx配件", "入库", "2025-01-07", "admin"));
        outBoundList.add(new OutBoundItemViewModel(this, "xx配件", "入库", "2025-01-07", "admin"));

        inBoundList.add(new InBoundItemViewModel(this, "xx配件", "入库", "2025-01-07", "admin"));
        inBoundList.add(new InBoundItemViewModel(this, "xx配件", "入库", "2025-01-07", "admin"));
        inBoundList.add(new InBoundItemViewModel(this, "xx配件", "入库", "2025-01-07", "admin"));
        inBoundList.add(new InBoundItemViewModel(this, "xx配件", "入库", "2025-01-07", "admin"));
    }
}
