package com.victor.movement.ui.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;

import com.victor.base.data.Repository.AppRepository;
import com.victor.base.data.entity.AssetCheckOdd;
import com.victor.base.data.entity.ListData;
import com.victor.base.data.entity.MovementData;
import com.victor.base.data.http.ApiListDisposableObserver;
import com.victor.base.utils.Constants;
import com.victor.movement.R;
import com.victor.movement.bean.MovementListRefreshBean;
import com.victor.movement.ui.viewmodel.itemviewmodel.MovementItemViewModel;
import com.victor.workbench.ui.base.BaseOddViewModel;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.bus.RxSubscriptions;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class MovementListViewModel extends BaseOddViewModel<MovementItemViewModel> {
    public MovementListViewModel(@NonNull Application application, AppRepository model) {
        super(application, model);
        uc.beginRefreshing.call();
    }

    @Override
    protected int initItemLayout() {
        return R.layout.movement_list_item;
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
                        } else {
                            setNoDataVisibleObservable(View.GONE);
                        }
//                        for (AssetCheckOdd assetCheckOdd : assetCheckOdds) {
//                            PdOddItemViewModel itemViewModel = new PdOddItemViewModel(PdOddViewModel.this, assetCheckOdd);
//                            //双向绑定动态添加Item
//                            observableList.add(itemViewModel);
//                        }
                    });
        else {
            model.listMovement(page)
                    .compose(RxUtils.schedulersTransformer())
                    .compose(RxUtils.exceptionTransformer())
                    .subscribe(new ApiListDisposableObserver<List<MovementData>>() {
                        @Override
                        public void onResult(ListData<List<MovementData>> listData) {
                            if (listData == null || listData.getTotal() == 0) {
                                setNoDataVisibleObservable(View.VISIBLE);
                            } else if (listData != null) {
                                setNoDataVisibleObservable(View.GONE);
                                if (observableList.size() == listData.getTotal()) {
                                    // 数据全部返回了
                                    canloadmore = false;
                                    ToastUtils.showShort(R.string.app_no_more_data_text);
                                } else {
                                    for (MovementData MovementData : listData.getList()) {
                                        MovementItemViewModel itemViewModel = new MovementItemViewModel(MovementListViewModel.this, MovementData);
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
        mSubscriptionRefresh = RxBus.getDefault().toObservable(MovementListRefreshBean.class)
                .subscribe(new Consumer<MovementListRefreshBean>() {
                    @Override
                    public void accept(MovementListRefreshBean movementListRefreshBean) throws Exception {
                        uc.beginRefreshing.call();
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
