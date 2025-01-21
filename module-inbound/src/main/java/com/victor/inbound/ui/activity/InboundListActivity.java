package com.victor.inbound.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.victor.base.app.AppViewModelFactory;
import com.victor.base.base.MBaseActivity;
import com.victor.base.router.RouterActivityPath;
import com.victor.inbound.BR;
import com.victor.inbound.R;
import com.victor.inbound.databinding.InboundListActivityBinding;
import com.victor.inbound.ui.viewmodel.InboundListViewModel;

@Route(path = RouterActivityPath.Inbound.PAGER_INBOUND_LIST)
public class InboundListActivity extends MBaseActivity<InboundListActivityBinding, InboundListViewModel> {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.inbound_list_activity;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        viewModel.setBackVisibleObservable(View.VISIBLE);
        viewModel.setTitleText(getResources().getString(R.string.workbench_inbound_list_title_text));
    }

    @Override
    public InboundListViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return new ViewModelProvider(this, factory).get(InboundListViewModel.class);
    }

    @Override
    public void initViewObservable() {
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
