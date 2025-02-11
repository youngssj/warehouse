package com.victor.allocate.ui.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;

import com.victor.allocate.R;
import com.victor.allocate.bean.AllocateListRefreshBean;
import com.victor.allocate.ui.viewmodel.itemviewmodel.AllocateItemViewModel;
import com.victor.base.data.Repository.AppRepository;
import com.victor.base.data.entity.AllocateData;
import com.victor.base.data.entity.InboundData;
import com.victor.base.data.entity.ListData;
import com.victor.base.data.http.ApiListDisposableObserver;
import com.victor.base.utils.Constants;
import com.victor.workbench.ui.base.BaseOddViewModel;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.bus.RxSubscriptions;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class AllocateListViewModel extends BaseOddViewModel<AllocateItemViewModel> {
    public AllocateListViewModel(@NonNull Application application, AppRepository model) {
        super(application, model);
        uc.beginRefreshing.call();
    }

    @Override
    protected int initItemLayout() {
        return R.layout.allocate_list_item;
    }

    @Override
    protected void loadData(int page) {
        if (page == 1) {
            mMorePageNumber = 1;
            observableList.clear();
        }
        if (Constants.CONFIG.IS_OFFLINE) {
            model._listAllocate(page)
                    .compose(RxUtils.MaybeSchTransformer())
                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                    .subscribe((Consumer<List<AllocateData>>) allocateDatas -> {
                        if (allocateDatas == null || allocateDatas.size() == 0) {
                            if (page == 1) {
                                setNoDataVisibleObservable(View.VISIBLE);
                            } else {
                                setNoDataVisibleObservable(View.GONE);
                                canloadmore = false;
                                ToastUtils.showShort(R.string.app_no_more_data_text);
                            }
                        } else {
                            setNoDataVisibleObservable(View.GONE);
                            for (AllocateData allocateData : allocateDatas) {
                                AllocateItemViewModel itemViewModel = new AllocateItemViewModel(AllocateListViewModel.this, allocateData);
                                //双向绑定动态添加Item
                                observableList.add(itemViewModel);
                            }
                        }

                        uc.finishRefreshing.call();
                        uc.finishLoadmore.call();
                    });
        } else {
            model.listAllocate(page)
                    .compose(RxUtils.schedulersTransformer())
                    .compose(RxUtils.exceptionTransformer())
                    .subscribe(new ApiListDisposableObserver<List<AllocateData>>() {
                        @Override
                        public void onResult(ListData<List<AllocateData>> listData) {
                            if (listData == null || listData.getTotal() == 0) {
                                setNoDataVisibleObservable(View.VISIBLE);
                            } else if (listData != null) {
                                setNoDataVisibleObservable(View.GONE);
                                if (observableList.size() == listData.getTotal()) {
                                    // 数据全部返回了
                                    canloadmore = false;
                                    ToastUtils.showShort(R.string.app_no_more_data_text);
                                } else {
                                    for (AllocateData allocateData : listData.getList()) {
                                        AllocateItemViewModel itemViewModel = new AllocateItemViewModel(AllocateListViewModel.this, allocateData);
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
        mSubscriptionRefresh = RxBus.getDefault().toObservable(AllocateListRefreshBean.class)
                .subscribe(new Consumer<AllocateListRefreshBean>() {
                    @Override
                    public void accept(AllocateListRefreshBean allocateListRefreshBean) throws Exception {
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
