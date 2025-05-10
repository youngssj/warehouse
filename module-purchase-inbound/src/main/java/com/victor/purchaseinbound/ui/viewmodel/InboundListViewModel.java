package com.victor.purchaseinbound.ui.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;

import com.victor.base.data.Repository.AppRepository;
import com.victor.base.data.entity.InboundData;
import com.victor.base.data.entity.ListData;
import com.victor.base.data.http.ApiListDisposableObserver;
import com.victor.base.event.Event;
import com.victor.base.event.MessageEvent;
import com.victor.base.event.MessageType;
import com.victor.base.utils.Constants;
import com.victor.purchaseinbound.ui.viewmodel.itemviewmodel.InboundItemViewModel;
import com.victor.purchaseinbound.R;
import com.victor.workbench.ui.base.BaseOddViewModel;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class InboundListViewModel extends BaseOddViewModel<InboundItemViewModel> {
    public InboundListViewModel(@NonNull Application application, AppRepository model) {
        super(application, model);
        uc.beginRefreshing.call();
    }

    @Override
    protected int initItemLayout() {
        return R.layout.purchaseinbound_list_item;
    }

    @Override
    protected void loadData(int page) {
        if (page == 1) {
            mMorePageNumber = 1;
            observableList.clear();
        }
        if (Constants.CONFIG.IS_OFFLINE) {
            model._listInbound(page)
                    .compose(RxUtils.MaybeSchTransformer())
                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                    .subscribe((Consumer<List<InboundData>>) inboundDatas -> {
                        if (inboundDatas == null || inboundDatas.size() == 0) {
                            if (page == 1) {
                                setNoDataVisibleObservable(View.VISIBLE);
                            } else {
                                setNoDataVisibleObservable(View.GONE);
                                canloadmore = false;
                                ToastUtils.showShort(R.string.app_no_more_data_text);
                            }
                        } else {
                            setNoDataVisibleObservable(View.GONE);
                            for (InboundData inboundData : inboundDatas) {
                                InboundItemViewModel itemViewModel = new InboundItemViewModel(InboundListViewModel.this, inboundData);
                                //双向绑定动态添加Item
                                observableList.add(itemViewModel);
                            }
                        }

                        uc.finishRefreshing.call();
                        uc.finishLoadmore.call();
                    });
        } else {
            model.listInbound(page)
                    .compose(RxUtils.schedulersTransformer())
                    .compose(RxUtils.exceptionTransformer())
                    .subscribe(new ApiListDisposableObserver<List<InboundData>>() {
                        @Override
                        public void onResult(ListData<List<InboundData>> listData) {
                            if (listData == null || listData.getTotal() == 0) {
                                setNoDataVisibleObservable(View.VISIBLE);
                            } else if (listData != null) {
                                setNoDataVisibleObservable(View.GONE);
                                if (observableList.size() == listData.getTotal()) {
                                    // 数据全部返回了
                                    canloadmore = false;
                                    ToastUtils.showShort(R.string.app_no_more_data_text);
                                } else {
                                    for (InboundData inboundData : listData.getList()) {
                                        InboundItemViewModel itemViewModel = new InboundItemViewModel(InboundListViewModel.this, inboundData);
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

    //注册RxBus
    @Override
    public void registerRxBus() {
        super.registerRxBus();
        Disposable mSubscriptionRefresh = RxBus.getDefault().toObservable(Event.class)
                .subscribe(event -> {
                    if (event instanceof MessageEvent) {
                        switch (((MessageEvent<?>) event).getMessageType()) {
                            case MessageType.EVENT_TYPE_INBOUND_LIST_REFRESH:
                                uc.beginRefreshing.call();
                                break;
                        }
                    }
                });
        addSubscribe(mSubscriptionRefresh);
    }
}
