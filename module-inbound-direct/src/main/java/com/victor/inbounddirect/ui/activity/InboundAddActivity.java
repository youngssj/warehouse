package com.victor.inbounddirect.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.victor.base.app.AppRequestCode;
import com.victor.base.app.AppViewModelFactory;
import com.victor.base.base.MBaseActivity;
import com.victor.base.data.entity.UserData;
import com.victor.base.router.RouterActivityPath;
import com.victor.base.utils.DateUtil;
import com.victor.base.utils.PopUtils;
import com.victor.inbounddirect.BR;
import com.victor.inbounddirect.R;
import com.victor.inbounddirect.databinding.InbounddirectActivityAddBinding;
import com.victor.inbounddirect.ui.viewmodel.InboundAddViewModel;

import java.util.Calendar;
import java.util.Date;

@Route(path = RouterActivityPath.Inbound.PAGER_INBOUND_ADD)
public class InboundAddActivity extends MBaseActivity<InbounddirectActivityAddBinding, InboundAddViewModel> {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.inbounddirect_activity_add;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public InboundAddViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return new ViewModelProvider(this, factory).get(InboundAddViewModel.class);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        viewModel.setBackVisibleObservable(View.VISIBLE);
        viewModel.setPowerVisibleObservable(View.GONE);
        viewModel.setTitleText(getResources().getString(R.string.workbench_inbound_add_text));
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.uesrClickEvent.observe(this, userDatas -> {
            new PopUtils().showBottomPops(this, userDatas, "", binding.rootView, binding.rootView, new PopUtils.OnPopItemClickListener() {
                @Override
                public boolean onItemClick(UserData item, int position) {
                    viewModel.currentUser.set(item);
                    return true;
                }
            });
        });
        viewModel.uc.timeClickEvent.observe(this, str -> {
            ARouter.getInstance().build(RouterActivityPath.Base.PAGER_BASE_CALENDAR)
                    .withBoolean("showTime", false)
                    .navigation(this, AppRequestCode.REQUEST_CODE_CALENDAR_SELECT);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppRequestCode.REQUEST_CODE_CALENDAR_SELECT && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Calendar calendar = (Calendar) data.getSerializableExtra("calendarDay");
                if (calendar != null) {
                    // 如果是卸车，校验装车时间
                    Date time = calendar.getTime();
                    String dateFormat = DateUtil.webFormat;
                    viewModel.planInDate.set(DateUtil.format(calendar.getTime(), dateFormat));
                }
            }
        }
    }
}
