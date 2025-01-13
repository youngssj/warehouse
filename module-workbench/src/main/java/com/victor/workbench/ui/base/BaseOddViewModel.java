package com.victor.workbench.ui.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.victor.base.data.Repository.AppRepository;
import com.victor.workbench.BR;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * 版权：heihei
 * h
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/9/27
 * 邮箱：jxfengmtx@gmail.com
 */
public abstract class BaseOddViewModel<T extends BaseRecycleItemViewModel> extends BaseTitleViewModel<AppRepository> {

    public int mMorePageNumber = 1;

    public boolean canloadmore = true;

    //给RecyclerView添加ObservableList
    public ObservableList<BaseRecycleItemViewModel> observableList = new ObservableArrayList<>();
    //给RecyclerView添加ItemBinding
    public ItemBinding<BaseRecycleItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, initItemLayout());

    public BaseOddViewModel mOddViewModel;

    public BaseOddViewModel(@NonNull Application application) {
        this(application, null);
    }

    public BaseOddViewModel(@NonNull Application application, AppRepository model) {
        super(application, model);
        mOddViewModel = this;
    }

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        //下拉刷新完成
        public SingleLiveEvent finishRefreshing = new SingleLiveEvent<>();
        //上拉加载完成
        public SingleLiveEvent finishLoadmore = new SingleLiveEvent<>();
    }

    protected abstract int initItemLayout();

    protected abstract void loadData(int page);


    /**
     * 获取条目下标
     *
     * @param oddItemViewModel
     * @return
     */
    public int getItemPosition(T oddItemViewModel) {
        return observableList.indexOf(oddItemViewModel);
    }

    //下拉刷新
    public BindingCommand onRefreshCommand = new BindingCommand(() -> {
//            ToastUtils.showShort("下拉刷新");
        loadData(1);
    });

    public BindingCommand autoRefreshCommand = new BindingCommand(() -> {});
    //上拉加载
    public BindingCommand onLoadMoreCommand = new BindingCommand(() -> {
//        if (observableList.size() > 50) {
//            ToastUtils.showLong("兄dei，没有太多数据啦");
//            uc.finishLoadmore.call();
//            return;
//        }
        if(canloadmore) {
            loadData(++mMorePageNumber);
        }else {
            //模拟网络上拉加载更多
            Observable.timer(200, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Object>() {
                        @Override
                        public void onSubscribe(Disposable disposable) {
                        }

                        @Override
                        public void onNext(Object o) {
                            uc.finishRefreshing.call();
                            uc.finishLoadmore.call();
                            dismissDialog();
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        }
    });

}
