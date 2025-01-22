package com.victor.workbench.ui.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.afollestad.materialdialogs.MaterialDialog;
import com.hiultra.c72.BarCodeHelper;
import com.hiultra.c72.UhfC72Utils;
import com.hiultra.hiu_961.Uhf961Utils;
import com.victor.base.base.MBaseActivity;
import com.victor.base.utils.Constants;
import com.victor.base.utils.SystemUtil;
import com.victor.workbench.BR;
import com.victor.workbench.R;
import com.victor.workbench.databinding.WorkbenchViewTopSetPowerBinding;

import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.observers.DefaultObserver;
import me.goldze.mvvmhabit.utils.MaterialDialogUtils;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.
 * 创建日期：2020/9/22
 * 邮箱：jxfengmtx@gmail.com
 * <p>
 * 所有rfitd扫描页面的基类
 */
public abstract class BaseUhfActivity<V extends ViewDataBinding, VM extends BaseTitleViewModel> extends MBaseActivity<V, VM> {


    protected UhfC72Utils mUhfC72Utils;
    protected Uhf961Utils mUhf961Utils;
    protected BarCodeHelper mBarCodeHelper;

    protected abstract void readUhfCallback(Set<String> epcSet);

    protected abstract void scanBarCodeCallback(String barCode);

    private boolean isReadFinish = false;
    protected boolean isRead = false;
    protected boolean isSingle = false;
    private boolean keyUpFlag = true;
    private long startTime = 0;

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
        isSingle = false;
    }

    public void setRead(boolean read, boolean single) {
        isRead = read;
        isSingle = single;
    }

    private BroadcastReceiver keyReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            int keyCode = intent.getIntExtra("keyCode", 0);
            boolean keyDown = intent.getBooleanExtra("keydown", false);
            if (keyUpFlag && keyDown && System.currentTimeMillis() - startTime > 500) {
                keyUpFlag = false;
                startTime = System.currentTimeMillis();
                if ((keyCode == KeyEvent.KEYCODE_F3 || keyCode == KeyEvent.KEYCODE_F4)) {
                    if (mUhf961Utils == null)
                        return;
                    if (isReadFinish) {
                        ToastUtils.showShort("已全部扫描完，请点击完成");
                        return;
                    }
                    if (isSingle) {
                        mUhf961Utils.readSingleTag(epcSet -> readUhfCallback(epcSet));
                    } else {
                        mUhf961Utils.startRead(epcSet -> readUhfCallback(epcSet));
                    }
                }
                return;
            } else if (keyDown) {
                startTime = System.currentTimeMillis();
            } else {
                keyUpFlag = true;
            }
        }
    };

    public void setReadFinish(boolean readFinish) {
        isReadFinish = readFinish;
        if (mUhfC72Utils != null) {//close uhf module.
            mUhfC72Utils.closeUhf();
        }
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

        viewModel.uc.showSetDialogEvent.observe(this, i -> {

            showCustomDialog(getResources().getString(R.string.workbench_check_power_title_text), (dialog, which) -> {
                switch (SystemUtil.getSystemModel()) {
                    case Constants.DEVICE.C72_AND11:
                    case Constants.DEVICE.C72_AND11_1:
                    case Constants.DEVICE.C72:
                    case Constants.DEVICE.C725X:
                        if (mUhfC72Utils == null) {
                            mUhfC72Utils = new UhfC72Utils(this);
                        }
                        mUhfC72Utils.setPower(Integer.parseInt((String) viewModel.power.get()));
                        break;

                    case Constants.DEVICE.K71V1_64_BSP:
                        if (mUhf961Utils == null) {
                            mUhf961Utils = new Uhf961Utils(BaseUhfActivity.this);
                        }
                        mUhf961Utils.setPower(Integer.parseInt((String) viewModel.power.get()));
                        break;

                }

            });
        });
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.workbench_view_top_title;
    }


    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initUHF();
        /*if (mUhfUtils != null) {
            int iPower = mUhfUtils.getPower();
            if (iPower > -1) {
                viewModel.uc.power = String.valueOf(iPower);
            }
        }*/
    }

    protected void initUHF() {
        switch (SystemUtil.getSystemModel()) {
            case Constants.DEVICE.C725X:
            case Constants.DEVICE.C72_AND11:
            case Constants.DEVICE.C72_AND11_1:
            case Constants.DEVICE.C72:
                if (isRead) {
                    mUhfC72Utils = new UhfC72Utils(this);
                    mUhfC72Utils.initUHF(this);
//                    return;
                }
                mBarCodeHelper = BarCodeHelper.getInstance(this);
                mBarCodeHelper.init();
                break;

            case Constants.DEVICE.K71V1_64_BSP:
                mUhf961Utils = new Uhf961Utils(BaseUhfActivity.this);
                IntentFilter filter = new IntentFilter();
                filter.addAction("android.rfid.FUN_KEY");
                registerReceiver(keyReceiver, filter);
//                mUhfPdaUtils.initUHF();
                break;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        switch (SystemUtil.getSystemModel()) {
            case Constants.DEVICE.C72_AND11:
            case Constants.DEVICE.C72_AND11_1:
            case Constants.DEVICE.C72:
            case Constants.DEVICE.C725X:
                if (mBarCodeHelper != null)
                    mBarCodeHelper.closeScan();
                break;
            case Constants.DEVICE.K71V1_64_BSP:
                unregisterReceiver(keyReceiver);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        switch (SystemUtil.getSystemModel()) {
            case Constants.DEVICE.C72_AND11:
            case Constants.DEVICE.C72_AND11_1:
            case Constants.DEVICE.C72:
            case Constants.DEVICE.C725X:
                if (!isRead)
                    return;
                if (mUhfC72Utils != null) {//close uhf module.
                    mUhfC72Utils.closeUhf();
                }
                break;

            case Constants.DEVICE.K71V1_64_BSP:
                if (mUhf961Utils != null) {
                    mUhf961Utils.closeUhf();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        switch (SystemUtil.getSystemModel()) {
            case Constants.DEVICE.C72_AND11:
            case Constants.DEVICE.C72_AND11_1:
            case Constants.DEVICE.C72:
            case Constants.DEVICE.C725X:
                break;
        }
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {  //长按监听
        /*if ((keyCode == 293 || keyCode == 280)) {  //扫RFID
            if (!isRead)
                return false;
            if (mUhfC72Utils == null)
                return false;
            if (isReadFinish) {
                ToastUtils.showShort("已全部扫描完，请点击完成");
                return false;
            }
            mUhfC72Utils.startRead(epcSet -> readUhfCallback(epcSet));
            return true;
        }
        if (keyCode == 139) {
            mBarCodeHelper.ScanBarcode(this::scanBarCodeCallback);
        }*/
        return super.onKeyLongPress(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (SystemUtil.getSystemModel()) {
            case Constants.DEVICE.C72_AND11:
            case Constants.DEVICE.C72_AND11_1:
            case Constants.DEVICE.C72:
            case Constants.DEVICE.C725X:
                if ((keyCode == 293 || keyCode == 280)) {  //扫RFID
                    if (!isRead)
                        return false;
                    if (mUhfC72Utils == null)
                        return false;
                    if (isReadFinish) {
                        ToastUtils.showShort("已全部扫描完，请点击完成");
                        return false;
                    }
                    if (mUhfC72Utils != null) {
                        if (isSingle) {
                            mUhfC72Utils.startSingleRead(BaseUhfActivity.this, epcData -> readUhfCallback(epcData));
                        }else{
                            mUhfC72Utils.startRead(BaseUhfActivity.this, epcData -> readUhfCallback(epcData));
                        }
                    }
                    return true;
                }
                if (keyCode == 139) {  //扫码
                    if (mBarCodeHelper != null)
                        mBarCodeHelper.ScanBarcode(this::scanBarCodeCallback);
                }
                break;


        }
        return super.onKeyDown(keyCode, event);
    }

    public void showCustomDialog(String title, MaterialDialog.SingleButtonCallback callback) {
        showProgress();
        Observable.create((ObservableOnSubscribe<String>) emitter -> {
                    emitter.onNext(String.valueOf(mUhfC72Utils.getPower()));
                })
                .compose(RxUtils.bindToLifecycle(viewModel.getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .subscribe(new DefaultObserver<String>() {
                    @Override
                    public void onNext(String power) {
                        viewModel.power.set(power);
                        dismissProgress();
                        WorkbenchViewTopSetPowerBinding binding = DataBindingUtil.inflate(LayoutInflater.from(BaseUhfActivity.this), R.layout.workbench_view_top_set_power, null, false);
                        binding.setViewModel(viewModel);
                        binding.setLifecycleOwner(BaseUhfActivity.this);
                        MaterialDialogUtils.showCustomDialog(BaseUhfActivity.this, title, binding, callback);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
