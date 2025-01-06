package com.victor.main.ui.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.victor.base.router.RouterActivityPath;
import com.victor.main.BR;
import com.victor.main.R;
import com.victor.main.databinding.ActivityMainBinding;
import com.victor.main.ui.viewmodel.MainViewModel;

import me.goldze.mvvmhabit.base.BaseActivity;

@Route(path = RouterActivityPath.Main.PAGER_MAIN)
public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}
