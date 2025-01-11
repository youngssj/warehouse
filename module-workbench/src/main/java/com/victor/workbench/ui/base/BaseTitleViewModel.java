package com.victor.workbench.ui.base;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.victor.base.data.entity.PowerItemData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import me.goldze.mvvmhabit.base.BaseModel;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.viewadapter.spinner.IKeyAndValue;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.bus.RxSubscriptions;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/9/14
 * 邮箱：jxfengmtx@gmail.com
 */
public class BaseTitleViewModel<M extends BaseModel> extends BaseViewModel<M> {

    public ObservableField<String> titleText = new ObservableField<>("标题");
    //返回按钮的观察者
    public ObservableInt backVisibleObservable = new ObservableInt(View.GONE);
    public ObservableInt powerVisibleObservable = new ObservableInt(View.GONE);
    public ObservableInt noDataVisibleObservable = new ObservableInt(View.GONE);
    //兼容databinding，去泛型化
    public BaseTitleViewModel mBaseTitleViewModel;

    public List<IKeyAndValue> powerItemDatas;


    public UIChangeObservable uc = new UIChangeObservable();
    private Disposable mShowSetDialogSubscription;

    public class UIChangeObservable {
        public String power;
        public SingleLiveEvent<Integer> showSetDialogEvent = new SingleLiveEvent<>();
    }


    public BaseTitleViewModel(@NonNull Application application) {
        this(application, null);
    }

    public BaseTitleViewModel(@NonNull Application application, M model) {
        super(application, model);
        powerItemDatas = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            powerItemDatas.add(new PowerItemData(i + 5 + "", String.valueOf(i + 5)));
        }

        mBaseTitleViewModel = this;
    }

    public BindingCommand<IKeyAndValue> onPowerSelectorCommand = new BindingCommand<>(iKeyAndValue -> uc.power = iKeyAndValue.getValue());

    public BindingCommand<Void> backOnClick = new BindingCommand<>(() -> finish());

    public BindingCommand setPowerOnClick = new BindingCommand(() -> {
        uc.showSetDialogEvent.call();
    });

    @Override
    public void registerRxBus() {
        super.registerRxBus();
        mShowSetDialogSubscription = RxBus.getDefault().toObservable(String.class)
                .subscribe(s -> {

                });
        //将订阅者加入管理站
        RxSubscriptions.add(mShowSetDialogSubscription);
    }

    @Override
    public void removeRxBus() {
        super.removeRxBus();
        RxSubscriptions.remove(mShowSetDialogSubscription);
    }

    /**
     * 设置左边按钮显隐性
     *
     * @param visibility
     */
    public void setBackVisibleObservable(int visibility) {
        backVisibleObservable.set(visibility);
    }

    /**
     * 设置功率按钮显隐性
     *
     * @param visibility
     */
    public void setPowerVisibleObservable(int visibility) {
        powerVisibleObservable.set(visibility);
    }

    /**
     * 设置无数据按钮显隐性
     *
     * @param visibility
     */
    public void setNoDataVisibleObservable(int visibility) {
        noDataVisibleObservable.set(visibility);
    }

    /**
     * 设置标题
     *
     * @param text 标题文字
     */
    public void setTitleText(String text) {
        titleText.set(text);
    }

}
