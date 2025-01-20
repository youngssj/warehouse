package com.victor.main.ui.activity;

import static me.goldze.mvvmhabit.utils.Utils.getContext;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.victor.base.app.AppViewModelFactory;
import com.victor.base.base.MBaseActivity;
import com.victor.base.router.RouterActivityPath;
import com.victor.main.BR;
import com.victor.main.R;
import com.victor.main.databinding.MainActivityLoginBinding;
import com.victor.main.databinding.MainViewSetIpBinding;
import com.victor.main.ui.viewmodel.LoginViewModel;

@Route(path = RouterActivityPath.Sign.PAGER_LOGIN)
public class LoginActivity extends MBaseActivity<MainActivityLoginBinding, LoginViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.main_activity_login;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public LoginViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return new ViewModelProvider(this, factory).get(LoginViewModel.class);
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.setIpEvent.observe(this, loginViewModel -> {
            MainViewSetIpBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.main_view_set_ip, null, false);
            binding.setViewModel(loginViewModel);
            showCustomDialog(getResources().getString(R.string.login_setting_text), binding, (dialog, which) -> {
                viewModel.saveIpAndPort();
            });
        });
    }
}