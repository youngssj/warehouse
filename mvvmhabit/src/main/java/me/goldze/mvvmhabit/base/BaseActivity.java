package me.goldze.mvvmhabit.base;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.launcher.ARouter;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import me.goldze.mvvmhabit.base.BaseViewModel.ParameterField;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.MaterialDialogUtils;
import me.goldze.mvvmhabit.utils.ProgressDialogManager;
import me.jessyan.autosize.AutoSizeCompat;


/**
 * Created by goldze on 2017/6/15.
 * 一个拥有DataBinding框架的基Activity
 * 这里根据项目业务可以换成你自己熟悉的BaseActivity, 但是需要继承RxAppCompatActivity,方便LifecycleProvider管理生命周期
 */
public abstract class BaseActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends RxAppCompatActivity implements IBaseView {
    protected V binding;
    protected VM viewModel;
    private int viewModelId;
    private MaterialDialog dialog;
    private ProgressDialogManager mProgressDialogManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (restartApp()) {
            return;
        }

        ARouter.getInstance().inject(this);

        //页面接受的参数方法
        initParam();
        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding(savedInstanceState);
        //私有的ViewModel与View的契约事件回调逻辑
        registorUIChangeLiveDataCallBack();
        //页面数据初始化方法
        initData(savedInstanceState);
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();
        //注册RxBus
        viewModel.registerRxBus();

        //设置状态栏透明
//        StatusBarUtil.setTranslucentStatus(this);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        // 设置使用刘海屏区域
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        this.getWindow().setAttributes(lp);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

//        getWindow().getDecorView().setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.INVISIBLE);
        //设置状态栏和导航栏颜色为透明
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);

        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
//        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
//            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
//            //这样半透明+白=灰, 状态栏的文字能看得清
//            StatusBarUtil.setStatusBarColor(this, 0x55000000);
//        }
    }

    /**
     * 处理系统被后台回收，被回收后重走启动流程
     *
     * @return
     */
    public abstract boolean restartApp();

    public static boolean[] isSystemUiVisible(Window window) {
        boolean[] result = new boolean[]{false, false};
        if (window == null) {
            return result;
        }
        WindowManager.LayoutParams attributes = window.getAttributes();
        if (attributes != null) {
            result[0] = (attributes.flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) != WindowManager.LayoutParams.FLAG_FULLSCREEN;
            //
            ViewGroup decorView = (ViewGroup) window.getDecorView();
            result[1] = (((attributes.systemUiVisibility | decorView.getWindowSystemUiVisibility()) &
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0) && (attributes.flags & WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS) != 0;
        }
        //
        Object decorViewObj = window.getDecorView();
        Class<?> clazz = decorViewObj.getClass();
        int mLastBottomInset = 0, mLastRightInset = 0, mLastLeftInset = 0;
        try {
            Field mLastBottomInsetField = clazz.getDeclaredField("mLastBottomInset");
            mLastBottomInsetField.setAccessible(true);
            mLastBottomInset = mLastBottomInsetField.getInt(decorViewObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Field mLastRightInsetField = clazz.getDeclaredField("mLastRightInset");
            mLastRightInsetField.setAccessible(true);
            mLastRightInset = mLastRightInsetField.getInt(decorViewObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Field mLastLeftInsetField = clazz.getDeclaredField("mLastLeftInset");
            mLastLeftInsetField.setAccessible(true);
            mLastLeftInset = mLastLeftInsetField.getInt(decorViewObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean isNavBarToRightEdge = mLastBottomInset == 0 && mLastRightInset > 0;
        int size = isNavBarToRightEdge ? mLastRightInset : (mLastBottomInset == 0 && mLastLeftInset > 0 ? mLastLeftInset : mLastBottomInset);
        result[1] = result[1] && size > 0;
        return result;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除Messenger注册
        Messenger.getDefault().unregister(viewModel);
        if (viewModel != null) {
            viewModel.removeRxBus();
        }
        if (binding != null) {
            binding.unbind();
        }
    }

    /**
     * 注入绑定
     */
    private void initViewDataBinding(Bundle savedInstanceState) {
        //DataBindingUtil类需要在project的build中配置 dataBinding {enabled true }, 同步后会自动关联android.databinding包
        binding = DataBindingUtil.setContentView(this, initContentView(savedInstanceState));
        viewModelId = initVariableId();
        viewModel = initViewModel();
        if (viewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            viewModel = (VM) createViewModel(this, modelClass);
        }
        //关联ViewModel
        binding.setVariable(viewModelId, viewModel);
        //支持LiveData绑定xml，数据改变，UI自动会更新
        binding.setLifecycleOwner(this);
        //让ViewModel拥有View的生命周期感应
        getLifecycle().addObserver(viewModel);
        //注入RxLifecycle生命周期
        viewModel.injectLifecycleProvider(this);
    }

    //刷新布局
    public void refreshLayout() {
        if (viewModel != null) {
            binding.setVariable(viewModelId, viewModel);
        }
    }


    /**
     * =====================================================================
     **/
    //注册ViewModel与View的契约UI回调事件
    protected void registorUIChangeLiveDataCallBack() {
        //加载对话框显示
        viewModel.getUC().getShowDialogEvent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String title) {
                showDialog(title);
            }
        });
        viewModel.getUC().getShowProgressEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                showProgress();
            }
        });
        viewModel.getUC().getDismissProgressEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                dismissProgress();
            }
        });
        //加载对话框消失
        viewModel.getUC().getDismissDialogEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                dismissDialog();
            }
        });
        //跳入新页面
        viewModel.getUC().getStartActivityEvent().observe(this, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
                Class<?> clz = (Class<?>) params.get(ParameterField.CLASS);
                Bundle bundle = (Bundle) params.get(ParameterField.BUNDLE);
                startActivity(clz, bundle);
            }
        });
        //跳入ContainerActivity
        viewModel.getUC().getStartContainerActivityEvent().observe(this, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
                String canonicalName = (String) params.get(ParameterField.CANONICAL_NAME);
                Bundle bundle = (Bundle) params.get(ParameterField.BUNDLE);
                startContainerActivity(canonicalName, bundle);
            }
        });
        //关闭界面
        viewModel.getUC().getFinishEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                finish();
            }
        });
        //关闭上一层
        viewModel.getUC().getOnBackPressedEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                onBackPressed();
            }
        });
    }

    public void showDialog(String title) {
//        if (dialog != null) {
//            dialog = dialog.getBuilder().title(title).build();
//            dialog.show();
//        } else {
        MaterialDialog.Builder builder = MaterialDialogUtils.showIndeterminateProgressDialog(this, title, true);
        dialog = builder.show();
//        }
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void showCustomDialog(String title, ViewDataBinding vdb, MaterialDialog.SingleButtonCallback callback) {
        dismissDialog();
        vdb.setLifecycleOwner(this);
        dialog = MaterialDialogUtils.showCustomDialog(this, title, vdb, callback);
    }

    public void showProgress() {
        if (mProgressDialogManager == null) {
            mProgressDialogManager = new ProgressDialogManager(this);
        }
        if (!mProgressDialogManager.isShow()) {
            mProgressDialogManager.setCanceledOnTouchOutside(false);
            mProgressDialogManager.setCancelable(true);
            mProgressDialogManager.showWaiteDialog();
        }
    }

    public void dismissProgress() {
        if (mProgressDialogManager != null) {
            mProgressDialogManager.cancelWaiteDialog();
        }
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(this, clz));
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     */
    public void startContainerActivity(String canonicalName) {
        startContainerActivity(canonicalName, null);
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     * @param bundle        跳转所携带的信息
     */
    public void startContainerActivity(String canonicalName, Bundle bundle) {
        Intent intent = new Intent(this, ContainerActivity.class);
        intent.putExtra(ContainerActivity.FRAGMENT, canonicalName);
        if (bundle != null) {
            intent.putExtra(ContainerActivity.BUNDLE, bundle);
        }
        startActivity(intent);
    }

    /**
     * =====================================================================
     **/
    @Override
    public void initParam() {

    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    public abstract int initContentView(Bundle savedInstanceState);

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    public abstract int initVariableId();

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    public VM initViewModel() {
        return null;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initViewObservable() {

    }

    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(FragmentActivity activity, Class<T> cls) {
        return ViewModelProviders.of(activity).get(cls);
    }

    public String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }
}
