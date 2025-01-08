package com.victor.workbench.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.victor.base.router.RouterFragmentPath;
import com.victor.workbench.BR;
import com.victor.workbench.R;
import com.victor.workbench.databinding.MainFragmentWordbenchBinding;
import com.victor.workbench.ui.viewmodel.WorkBenchViewModel;

import me.goldze.mvvmhabit.base.BaseFragment;

@Route(path = RouterFragmentPath.WorkBench.PAGER_WorkBench)
public class WorkBenchFragment extends BaseFragment<MainFragmentWordbenchBinding, WorkBenchViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.main_fragment_wordbench;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}
