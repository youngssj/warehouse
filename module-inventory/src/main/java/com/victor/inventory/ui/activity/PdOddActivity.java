package com.victor.inventory.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.victor.base.app.AppViewModelFactory;
import com.victor.base.base.MBaseActivity;
import com.victor.base.router.RouterActivityPath;
import com.victor.inventory.BR;
import com.victor.inventory.R;
import com.victor.inventory.databinding.InventoryFragmentPdOddBinding;
import com.victor.inventory.ui.viewmodel.PdOddViewModel;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/9/16
 * 邮箱：jxfengmtx@gmail.com
 */
@Route(path = RouterActivityPath.Inventory.PAGER_INVENTORY_CHECK_LIST)
public class PdOddActivity extends MBaseActivity<InventoryFragmentPdOddBinding, PdOddViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.inventory_fragment_pd_odd;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        viewModel.setBackVisibleObservable(View.VISIBLE);
        viewModel.setTitleText(getResources().getString(R.string.workbench_check_list_title_text));
    }

    @Override
    public void initViewObservable() {
        //监听下拉刷新开始
        viewModel.uc.beginRefreshing.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                //结束刷新
                binding.include.bgaRefresh.beginRefreshing();
            }
        });
        //监听下拉刷新完成
        viewModel.uc.finishRefreshing.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                //结束刷新
                binding.include.bgaRefresh.endRefreshing();
            }
        });
        //监听上拉加载完成
        viewModel.uc.finishLoadmore.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                //结束刷新
                binding.include.bgaRefresh.endLoadingMore();
            }
        });
    }

    @Override
    public PdOddViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return new ViewModelProvider(this, factory).get(PdOddViewModel.class);
    }
}
