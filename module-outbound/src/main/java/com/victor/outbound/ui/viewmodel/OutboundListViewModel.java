package com.victor.outbound.ui.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;

import com.victor.base.data.Repository.AppRepository;
import com.victor.base.data.entity.ListData;
import com.victor.base.data.entity.OutboundData;
import com.victor.base.data.http.ApiListDisposableObserver;
import com.victor.base.event.Event;
import com.victor.base.event.MessageEvent;
import com.victor.base.event.MessageType;
import com.victor.base.utils.Constants;
import com.victor.outbound.R;
import com.victor.outbound.ui.viewmodel.itemviewmodel.OutboundItemViewModel;
import com.victor.workbench.ui.base.BaseOddViewModel;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.bus.RxSubscriptions;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class OutboundListViewModel extends BaseOddViewModel<OutboundItemViewModel> {
    public OutboundListViewModel(@NonNull Application application, AppRepository model) {
        super(application, model);
        uc.beginRefreshing.call();
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
        if (Constants.CONFIG.IS_OFFLINE) {
            model._listOutbound(page)
                    .compose(RxUtils.MaybeSchTransformer())
                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                    .subscribe((Consumer<List<OutboundData>>) outboundDatas -> {
                        if (outboundDatas == null || outboundDatas.size() == 0) {
                            if (page == 1) {
                                setNoDataVisibleObservable(View.VISIBLE);
                            } else {
                                setNoDataVisibleObservable(View.GONE);
                                canloadmore = false;
                                ToastUtils.showShort(R.string.app_no_more_data_text);
                            }
                        } else {
                            setNoDataVisibleObservable(View.GONE);
                            for (OutboundData outboundData : outboundDatas) {
                                OutboundItemViewModel itemViewModel = new OutboundItemViewModel(OutboundListViewModel.this, outboundData);
                                //双向绑定动态添加Item
                                observableList.add(itemViewModel);
                            }
                        }

                        uc.finishRefreshing.call();
                        uc.finishLoadmore.call();
                    });
        } else {
            model.listOutbound(page)
                    .compose(RxUtils.schedulersTransformer())
                    .compose(RxUtils.exceptionTransformer())
                    .subscribe(new ApiListDisposableObserver<List<OutboundData>>() {
                        @Override
                        public void onResult(ListData<List<OutboundData>> listData) {
                            if (listData == null || listData.getTotal() == 0) {
                                setNoDataVisibleObservable(View.VISIBLE);
                            } else if (listData != null) {
                                setNoDataVisibleObservable(View.GONE);
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

    //订阅者
    private Disposable mSubscriptionRefresh;

    //注册RxBus
    @Override
    public void registerRxBus() {
        super.registerRxBus();
        mSubscriptionRefresh = RxBus.getDefault().toObservable(Event.class)
                .subscribe(event -> {
                    if (event instanceof MessageEvent) {
                        switch (((MessageEvent<?>) event).getMessageType()) {
                            case MessageType.EVENT_TYPE_OUTBOUND_LIST_REFRESH:
                                uc.beginRefreshing.call();
                                break;
                        }
                    }
                });
        RxSubscriptions.add(mSubscriptionRefresh);
    }

    //移除RxBus
    @Override
    public void removeRxBus() {
        super.removeRxBus();
        //将订阅者从管理站中移除
        RxSubscriptions.remove(mSubscriptionRefresh);
    }
}
