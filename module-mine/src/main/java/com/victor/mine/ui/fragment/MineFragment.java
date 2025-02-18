package com.victor.mine.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.victor.base.app.AppViewModelFactory;
import com.victor.base.router.RouterActivityPath;
import com.victor.base.router.RouterFragmentPath;
import com.victor.base.utils.Constants;
import com.victor.mine.BR;
import com.victor.mine.R;
import com.victor.mine.databinding.MineFragmentMineBinding;
import com.victor.mine.ui.viewmodel.MineViewModel;

import me.goldze.mvvmhabit.base.AppManager;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.MaterialDialogUtils;
import me.goldze.mvvmhabit.utils.SPUtils;

@Route(path = RouterFragmentPath.User.PAGER_MINE)
public class MineFragment extends BaseFragment<MineFragmentMineBinding, MineViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.mine_fragment_mine;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public MineViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return new ViewModelProvider(this, factory).get(MineViewModel.class);
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.exitEvent.observe(getViewLifecycleOwner(), mineViewModel -> {
            MaterialDialogUtils.showBasicDialog(getContext(), getResources().getString(R.string.mine_exit_hint_text))
                    .onPositive((dialog, which) -> {
                        //关闭所有页面
                        AppManager.getAppManager().finishAllActivity();
                        SPUtils.getInstance().put(Constants.SP.TOKEN, "");
                        //跳入登录界面
                        ARouter.getInstance().build(RouterActivityPath.Sign.PAGER_LOGIN).navigation();
                    })
                    .show();
        });
    }
}
