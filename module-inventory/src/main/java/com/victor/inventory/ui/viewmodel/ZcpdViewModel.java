package com.victor.inventory.ui.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import com.victor.base.data.Repository.AppRepository;
import com.victor.base.data.entity.InventoryData;
import com.victor.base.data.http.ApiDisposableObserver;
import com.victor.base.utils.Constants;
import com.victor.base.utils.DateUtil;
import com.victor.inventory.BR;
import com.victor.inventory.R;
import com.victor.inventory.bean.InventoryListRefreshBean;
import com.victor.inventory.ui.viewmodel.itemviewmodel.ZcpdVpItemViewModel;
import com.victor.inventory.ui.viewmodel.itemviewmodel.ZcpdVpRvItemViewModel;
import com.victor.workbench.ui.base.BaseTitleViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DefaultObserver;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.goldze.mvvmhabit.utils.Utils;
import me.tatarka.bindingcollectionadapter2.BindingViewPagerAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;


/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/9/17
 * 邮箱：jxfengmtx@gmail.com
 */
public class ZcpdViewModel extends BaseTitleViewModel<AppRepository> {

    public UIChangeObservable uc = new UIChangeObservable();

    private Set<ZcpdVpRvItemViewModel> rvSet = new HashSet<>();  //盘点到单子集合


    private final int TAB_NUM = 3;
    private int pagerIndex = 0;
    private ObservableList<ZcpdVpRvItemViewModel> mPddList; // 盘点到
    private List<String> mPyListId = new ArrayList<>();
    private ObservableList<ZcpdVpRvItemViewModel> mWpddList; // 未盘点到
    private ObservableList<ZcpdVpRvItemViewModel> mPdAllList; // 全部

    public class UIChangeObservable {
        public SingleLiveEvent<Boolean> scanFinishEvent = new SingleLiveEvent<>();
        public SingleLiveEvent<String> itemClickEvent = new SingleLiveEvent<>();
        public SingleLiveEvent<Integer> pageSelectEvent = new SingleLiveEvent<>();
        public SingleLiveEvent<ZcpdVpRvItemViewModel> showCustomEvent = new SingleLiveEvent<>();
    }

    public ObservableField<InventoryData> entity = new ObservableField<>();
    public ObservableField<String> checkDataNum = new ObservableField<>("0/0");

    public ObservableField<Boolean> btnVisiable = new ObservableField<>(false);

    public ObservableList<ZcpdVpItemViewModel> items = new ObservableArrayList<>();
    //给ViewPager添加ItemBinding
    public ItemBinding<ZcpdVpItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.inventory_item_vp_zcpd);
    //给ViewPager添加PageTitle
    public final BindingViewPagerAdapter.PageTitles<ZcpdVpItemViewModel> pageTitles = (position, item) -> {
        String title = "";
        switch (position) {
            case 0:
                title = getApplication().getResources().getString(R.string.workbench_check_tab1_text);
                break;
            case 1:
                title = getApplication().getResources().getString(R.string.workbench_check_tab2_text);
                break;
            case 2:
                title = getApplication().getResources().getString(R.string.workbench_check_tab3_text);
                break;

        }
        return title;
    };

    public BindingCommand pdFinishClickCommand = new BindingCommand(() -> {
        InventoryData inventoryData = entity.get();

        List<InventoryData.InventoryElecMaterial> inventoryElecMaterialS = new ArrayList<>();
        for (ZcpdVpRvItemViewModel zcpdVpRvItemViewModel : items.get(0).observableList) {
            InventoryData.InventoryElecMaterial inventoryElecMaterial = zcpdVpRvItemViewModel.entity.get();
            inventoryElecMaterialS.add(inventoryElecMaterial);
        }

        inventoryData.setElecMaterialList(inventoryElecMaterialS);
        inventoryData.setFinished(1);
        inventoryData.setCheckDate(DateUtil.getNowTime());

        if (Constants.CONFIG.IS_OFFLINE) {
            Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
                    model._saveInventoryResult(inventoryData);
                        emitter.onNext(true);
                    })
                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                    .compose(RxUtils.schedulersTransformer())
                    .subscribe(new DefaultObserver<Boolean>() {
                        @Override
                        public void onNext(Boolean b) {
                            btnVisiable.set(false);
                            ToastUtils.showShort(R.string.workbench_check_submit_success_text);
                            RxBus.getDefault().post(new InventoryListRefreshBean());
                            finish();
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            model.saveCheckedResult(inventoryData)
                    .compose(RxUtils.schedulersTransformer())
                    .compose(RxUtils.exceptionTransformer())
                    .doOnSubscribe(disposable -> {
                        showProgress();
                    }).subscribe(new ApiDisposableObserver() {
                        @Override
                        public void onResult(Object o) {
                            btnVisiable.set(false);
                            ToastUtils.showShort(R.string.workbench_check_submit_success_text);
                            RxBus.getDefault().post(new InventoryListRefreshBean());
                            finish();
                        }

                        @Override
                        public void onComplete() {
                            dismissProgress();
                        }
                    });
        }
    });

    public BindingCommand<Integer> onPageSelectedCommand = new BindingCommand<>(index -> pagerIndex = index);

    public ZcpdViewModel(@NonNull Application application, AppRepository model) {
        super(application, model);
    }

    public int getCurrentPagerIndex() {
        return pagerIndex;
    }

    public void getNetData(int checkId) {
        if (Constants.CONFIG.IS_OFFLINE) {
            Observable.create((ObservableOnSubscribe<InventoryData>) emitter -> {
                        InventoryData data = model._selectOneInventory(checkId);
                        emitter.onNext(data);
                    })
                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                    .compose(RxUtils.schedulersTransformer())
                    .subscribe(new DefaultObserver<InventoryData>() {
                        @Override
                        public void onNext(InventoryData data) {
                            handleInventoryData(data);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        } else {
            model.selectByCheck(checkId)
                    .compose(RxUtils.schedulersTransformer())
                    .compose(RxUtils.exceptionTransformer())
                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            showProgress();
                        }
                    })
                    .subscribe(new ApiDisposableObserver<InventoryData>() {
                        @Override
                        public void onResult(InventoryData data) {
                            handleInventoryData(data);
                        }

                        @Override
                        public void onComplete() {
                            dismissProgress();
                        }
                    });
        }
    }

    private void handleInventoryData(InventoryData data) {
        if (data == null || data.getElecMaterialList().size() == 0) {
            setNoDataVisibleObservable(View.VISIBLE);
            return;
        } else {
            setNoDataVisibleObservable(View.GONE);
        }

        entity.set(data);
        checkDataNum.set("0/" + data.getElecMaterialList().size());
        for (int i = 0; i < TAB_NUM; i++) {
            ZcpdVpItemViewModel itemViewModel = new ZcpdVpItemViewModel(ZcpdViewModel.this, i, entity.get().getBatchNumber(), entity.get().getElecMaterialList());
            items.add(itemViewModel);
        }
    }

    public void updatePDItemModel(Set<String> sets) {
        btnVisiable.set(true);
        mPdAllList = items.get(0).observableList;
        mPddList = items.get(1).observableList;
        mWpddList = items.get(2).observableList;
        for (ZcpdVpRvItemViewModel viewModel : mPdAllList) {
            InventoryData.InventoryElecMaterial bean = viewModel.entity.get();
            if (sets.contains(bean.getRfidCode())) {
                bean.setBgColor(Utils.getContext().getDrawable(R.color.color_6684FF));
                bean.setCheckResult(1);
                bean.setCheckResultMessage(getApplication().getResources().getString(R.string.workbench_check_success_text));
                sets.remove(bean.getRfidCode());
                rvSet.add(viewModel);  //防止添加的数据重复
            } else {
                bean.setCheckResult(2);
                bean.setCheckResultMessage(getApplication().getResources().getString(R.string.workbench_check_failure_text));
                bean.setBgColor(Utils.getContext().getDrawable(R.color.color_fc6666));
            }
            viewModel.entity.notifyChange();
        }

        KLog.i("sets:" + rvSet.size() + "--pddList:" + mPddList.size() + "--wpddList:" + mWpddList.size());
        if (rvSet.size() != 0)
            uc.pageSelectEvent.setValue(1);  //选中盘点到 页面

        mPddList.clear();
        for (ZcpdVpRvItemViewModel model : rvSet) {
            if (rvSet.size() <= entity.get().getElecMaterialList().size()) {
                mPddList.add(model);  //盘点到的数据添加
                mWpddList.remove(model);  //未盘点到的数据
            }
        }

        setBgColor();

        checkDataNum.set(mPddList.size() + "/" + entity.get().getElecMaterialList().size());

        if (mPddList.size() == entity.get().getElecMaterialList().size() + mPyListId.size() && mWpddList.size() == 0) {  //全部盘点完成，盘点到的数据=已盘点+盘盈
            uc.scanFinishEvent.setValue(true);
        }
    }

    private void setBgColor() {
        for (ZcpdVpRvItemViewModel itemViewModel : mPddList) {
            itemViewModel.entity.get().setBgColor(Utils.getContext().getDrawable(R.color.color_6684FF));
            itemViewModel.entity.get().setCheckResult(1);
            itemViewModel.entity.get().setCheckResultMessage("已盘点");
            itemViewModel.entity.notifyChange();
        }
        for (ZcpdVpRvItemViewModel itemViewModel : mWpddList) {
            itemViewModel.entity.get().setBgColor(Utils.getContext().getDrawable(R.color.color_fc6666));
            itemViewModel.entity.get().setCheckResult(2);
            itemViewModel.entity.get().setCheckResultMessage("未盘点");
            itemViewModel.entity.notifyChange();
        }
    }
}