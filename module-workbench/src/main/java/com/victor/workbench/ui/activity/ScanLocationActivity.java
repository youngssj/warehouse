package com.victor.workbench.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.victor.base.app.AppViewModelFactory;
import com.victor.base.router.RouterActivityPath;
import com.victor.workbench.BR;
import com.victor.workbench.R;
import com.victor.workbench.databinding.WorkbenchActivityScanLocationBinding;
import com.victor.workbench.ui.base.BaseUhfActivity;
import com.victor.workbench.ui.viewmodel.ScanLocationViewModel;

import java.util.Iterator;
import java.util.Set;

@Route(path = RouterActivityPath.WorkBench.PAGER_SCAN_LOCATION)
public class ScanLocationActivity extends BaseUhfActivity<WorkbenchActivityScanLocationBinding, ScanLocationViewModel> {
    @Override
    protected void readUhfCallback(Set epcSet) {
        if (epcSet != null && epcSet.size() > 0) {
            Iterator<String> iterator = epcSet.iterator();
            String firstValue = iterator.next();
            viewModel.handleBarCode(firstValue);
        }
    }

    @Override
    protected void scanBarCodeCallback(String barCode) {
        if (barCode != null) {
            viewModel.handleBarCode(barCode);
        }
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.workbench_activity_scan_location;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public ScanLocationViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return new ViewModelProvider(this, factory).get(ScanLocationViewModel.class);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        viewModel.setBackVisibleObservable(View.VISIBLE);
        viewModel.setPowerVisibleObservable(View.VISIBLE);
        viewModel.setTitleText(getResources().getString(R.string.workbench_location_scan_text));
        setRead(true);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.scanLocationEvent.observe(this, locationBean -> {
            Intent intent = new Intent();
            intent.putExtra("locationBean", locationBean);
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}
