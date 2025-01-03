package com.victor.main.ui.activity;

import static me.goldze.mvvmhabit.utils.Utils.getContext;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.victor.main.BR;
import com.victor.main.R;
import com.victor.main.databinding.ActivityLoginBinding;
import com.victor.main.databinding.ViewSetIpBinding;
import com.victor.main.ui.viewmodel.LoginViewModel;

import me.goldze.mvvmhabit.base.BaseActivity;

public class LoginActivity  extends BaseActivity<ActivityLoginBinding, LoginViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
        //监听ViewModel中pSwitchObservable的变化, 当ViewModel中执行【uc.pSwitchObservable.set(!uc.pSwitchObservable.get());】时会回调该方法
//        viewModel.getUC().pSwitchEvent.observe(this, aBoolean -> {
//            //pSwitchObservable是boolean类型的观察者,所以可以直接使用它的值改变密码开关的图标
//            if (viewModel.uc.pSwitchEvent.getValue()) {
//                //密码可见
//                //在xml中定义id后,使用binding可以直接拿到这个view的引用,不再需要findViewById去找控件了
//                binding.ivSwichPasswrod.setImageResource(R.mipmap.show_psw);
//                binding.etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//            } else {
//                //密码不可见
//                binding.ivSwichPasswrod.setImageResource(R.mipmap.show_psw_press);
//                binding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
//            }
//        });


        viewModel.uc.setIpEvent.observe(this, loginViewModel -> {
            ViewSetIpBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.view_set_ip, null, false);
            binding.setViewModel(loginViewModel);
//            showCustomDialog("设置", binding, (dialog, which) -> {
//                viewModel.saveIpAndPort();
//            });
        });
    }
}