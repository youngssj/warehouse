package com.victor.materials.ui.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.victor.base.data.Repository.AppRepository;
import com.victor.base.data.entity.ListData;
import com.victor.base.data.entity.MaterialsData;
import com.victor.base.data.http.ApiListDisposableObserver;
import com.victor.materials.BR;
import com.victor.materials.R;
import com.victor.materials.bean.MaterialsQueryConditionBean;
import com.victor.materials.ui.viewmodel.itemviewmodel.MaterialsItemViewModel;
import com.victor.workbench.ui.base.BaseOddViewModel;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.bus.RxSubscriptions;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class MaterialsViewModel extends BaseOddViewModel<MaterialsItemViewModel> {

    private MaterialsQueryConditionBean materialsQueryConditionBean;
    private String materialStatus;

    public void setMaterialStatus(String materialStatus) {
        this.materialStatus = materialStatus;
    }

    public MaterialsViewModel(@NonNull Application application, AppRepository model) {
        super(application, model);
        uc.beginRefreshing.call();
    }

    @Override
    protected int initItemLayout() {
        return R.layout.materials_item;
    }

    @Override
    protected void loadData(int page) {
        if (materialsQueryConditionBean == null) {
            materialsQueryConditionBean = new MaterialsQueryConditionBean(null, null);
        }
        query(page, materialsQueryConditionBean);
    }

    public class UIChangeObservable {
        public SingleLiveEvent<MaterialsItemViewModel> showCustomEvent = new SingleLiveEvent<>();
    }

    public UIChangeObservable muc = new UIChangeObservable();

    private void query(int page, MaterialsQueryConditionBean materialsQueryConditionBean) {
        if (page == 1) {
            mMorePageNumber = 1;
            observableList.clear();
        }
        model.listMaterials(page, materialStatus, materialsQueryConditionBean.getMaterialName(), materialsQueryConditionBean.getRfidCode())
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new ApiListDisposableObserver<List<MaterialsData>>() {
                    @Override
                    public void onResult(ListData<List<MaterialsData>> listData) {
                        if (listData == null || listData.getTotal() == 0) {
                            setNoDataVisibleObservable(View.VISIBLE);
                        } else {
                            setNoDataVisibleObservable(View.GONE);
                            if (observableList.size() == listData.getTotal()) {
                                // 数据全部返回了
                                canloadmore = false;
                                ToastUtils.showShort(R.string.app_no_more_data_text);
                            } else {
                                for (MaterialsData materialsData : listData.getList()) {
                                    MaterialsItemViewModel itemViewModel = new MaterialsItemViewModel(MaterialsViewModel.this, materialsData);
                                    // 双向绑定动态添加Item
                                    observableList.add(itemViewModel);
                                }
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        uc.finishRefreshing.call();
                        uc.finishLoadmore.call();
                    }
                });
    }

    //订阅者
    private Disposable mSubscription;

    //注册RxBus
    @Override
    public void registerRxBus() {
        super.registerRxBus();
        mSubscription = RxBus.getDefault().toObservable(MaterialsQueryConditionBean.class)
                .subscribe(new Consumer<MaterialsQueryConditionBean>() {
                    @Override
                    public void accept(MaterialsQueryConditionBean materialsQueryConditionBean) throws Exception {
                        MaterialsViewModel.this.materialsQueryConditionBean = materialsQueryConditionBean;
                        query(1, materialsQueryConditionBean);
                    }
                });
        //将订阅者加入管理站
        RxSubscriptions.add(mSubscription);
    }

    //移除RxBus
    @Override
    public void removeRxBus() {
        super.removeRxBus();
        //将订阅者从管理站中移除
        RxSubscriptions.remove(mSubscription);
    }
}
