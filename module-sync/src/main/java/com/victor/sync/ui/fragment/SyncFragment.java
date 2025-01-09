package com.victor.sync.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.victor.base.app.AppViewModelFactory;
import com.victor.base.router.RouterFragmentPath;
import com.victor.sync.BR;
import com.victor.sync.R;
import com.victor.sync.databinding.SyncFragmentSyncBinding;
import com.victor.sync.ui.viewmodel.SyncViewModel;

import me.goldze.mvvmhabit.base.BaseFragment;

@Route(path = RouterFragmentPath.Sync.PAGER_SYNC)
public class SyncFragment extends BaseFragment<SyncFragmentSyncBinding, SyncViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.sync_fragment_sync;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public SyncViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return new ViewModelProvider(this, factory).get(SyncViewModel.class);
    }

}
