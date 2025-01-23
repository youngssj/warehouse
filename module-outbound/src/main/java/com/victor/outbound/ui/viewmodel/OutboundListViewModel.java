package com.victor.outbound.ui.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;

import com.victor.base.data.Repository.AppRepository;
import com.victor.base.data.entity.AssetCheckOdd;
import com.victor.base.data.entity.OutboundData;
import com.victor.base.data.entity.ListData;
import com.victor.base.data.http.ApiListDisposableObserver;
import com.victor.base.utils.Constants;
import com.victor.outbound.R;
import com.victor.outbound.ui.viewmodel.itemviewmodel.OutboundItemViewModel;
import com.victor.workbench.ui.base.BaseOddViewModel;

import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class OutboundListViewModel extends BaseOddViewModel<OutboundItemViewModel> {
    public OutboundListViewModel(@NonNull Application application, AppRepository model) {
        super(application, model);
    }

    @Override
    protected int initItemLayout() {
        return R.layout.outbound_list_item;
    }

    @Override
    protected void loadData(int page) {
        if (page == 1) {
            mMorePageNumber = 1;
            observableList.clear();
        }
        if (Constants.CONFIG.IS_OFFLINE)
            model._listCheck(page)
                    .compose(RxUtils.MaybeSchTransformer())
                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                    .subscribe((Consumer<List<AssetCheckOdd>>) assetCheckOdds -> {

                        uc.finishRefreshing.call();
                        uc.finishLoadmore.call();
                        if (assetCheckOdds == null || assetCheckOdds.size() == 0) {
                            if (page == 1)
                                setNoDataVisibleObservable(View.VISIBLE);
                            else ToastUtils.showShort(R.string.app_no_more_data_text);
                            return;
                        }
//                        for (AssetCheckOdd assetCheckOdd : assetCheckOdds) {
//                            PdOddItemViewModel itemViewModel = new PdOddItemViewModel(PdOddViewModel.this, assetCheckOdd);
//                            //双向绑定动态添加Item
//                            observableList.add(itemViewModel);
//                        }
                    });
        else {
            model.listOutbound(page)
                    .compose(RxUtils.schedulersTransformer())
                    .compose(RxUtils.exceptionTransformer())
                    .subscribe(new ApiListDisposableObserver<List<OutboundData>>() {
                        @Override
                        public void onResult(ListData<List<OutboundData>> listData) {
                            if (listData == null || listData.getTotal() == 0) {
                                setNoDataVisibleObservable(View.VISIBLE);
                            } else if (listData != null) {
                                if (observableList.size() == listData.getTotal()) {
                                    // 数据全部返回了
                                    canloadmore = false;
                                    ToastUtils.showShort(R.string.app_no_more_data_text);
                                } else {
                                    for (OutboundData outboundData : listData.getList()) {
                                        OutboundItemViewModel itemViewModel = new OutboundItemViewModel(OutboundListViewModel.this, outboundData);
                                        //双向绑定动态添加Item
                                        observableList.add(itemViewModel);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onComplete() {
                            uc.finishRefreshing.call();
                            uc.finishLoadmore.call();
                        }
                    });
        }
    }
}
