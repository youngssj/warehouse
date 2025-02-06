package com.victor.allocate.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.victor.base.app.AppViewModelFactory;
import com.victor.base.base.MBaseActivity;
import com.victor.base.router.RouterActivityPath;
import com.victor.allocate.BR;
import com.victor.allocate.R;
import com.victor.allocate.databinding.AllocateListActivityBinding;
import com.victor.allocate.ui.viewmodel.AllocateListViewModel;

@Route(path = RouterActivityPath.Allocate.PAGER_ALLOCATE_LIST)
public class AllocateListActivity extends MBaseActivity<AllocateListActivityBinding, AllocateListViewModel> {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.allocate_list_activity;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        viewModel.setBackVisibleObservable(View.VISIBLE);
        viewModel.setTitleText(getResources().getString(R.string.workbench_allocate_list_title_text));
    }

    @Override
    public AllocateListViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return new ViewModelProvider(this, factory).get(AllocateListViewModel.class);
    }

    @Override
    public void initViewObservable() {
        //监听下拉刷新开始
        viewModel.uc.beginRefreshing.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                //结束刷新
                binding.bgaRefresh.beginRefreshing();
            }
        });
        //监听下拉刷新完成
        viewModel.uc.finishRefreshing.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                //结束刷新
                binding.bgaRefresh.endRefreshing();
            }
        });
        //监听上拉加载完成
        viewModel.uc.finishLoadmore.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                //结束刷新
                binding.bgaRefresh.endLoadingMore();
            }
        });
    }
}
