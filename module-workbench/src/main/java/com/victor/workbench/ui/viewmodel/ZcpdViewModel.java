package com.victor.workbench.ui.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import com.victor.base.data.Repository.AppRepository;
import com.victor.base.data.entity.AssetData;
import com.victor.base.data.entity.TakeStockDetail;
import com.victor.base.data.http.ApiDisposableObserver;
import com.victor.base.utils.Constants;
import com.victor.workbench.BR;
import com.victor.workbench.R;
import com.victor.workbench.ui.base.BaseTitleViewModel;
import com.victor.workbench.ui.viewmodel.itemViewmodel.ZcpdVpItemViewModel;
import com.victor.workbench.ui.viewmodel.itemViewmodel.ZcpdVpRvItemViewModel;

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

    public ObservableField<TakeStockDetail> entity = new ObservableField<>();
    public ObservableField<String> checkDataNum = new ObservableField<>("0/0");

    public ObservableField<Boolean> btnVisiable = new ObservableField<>(false);

    public ObservableList<ZcpdVpItemViewModel> items = new ObservableArrayList<>();
    //给ViewPager添加ItemBinding
    public ItemBinding<ZcpdVpItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.workbench_item_vp_zcpd);
    //给ViewPager添加PageTitle
    public final BindingViewPagerAdapter.PageTitles<ZcpdVpItemViewModel> pageTitles = (position, item) -> {
        String title = "";
        switch (position) {
            case 0:
                title = "全部";
                break;
            case 1:
                title = "盘点到";
                break;
            case 2:
                title = "未盘点到";
                break;

        }
        return title;
    };

    public BindingCommand pdFinishClickCommand = new BindingCommand(() -> {
        List<String> pddIds = new ArrayList<>();
        List<String> wPddIds = new ArrayList<>();
//        if (mPddList != null && mPddList.size() > 0) {
//            for (ZcpdVpRvItemViewModel zc : mPddList) {
//                if (mPyListId.indexOf(String.valueOf(zc.entity.get().getMaterialId())) == -1) {  //不在盘盈列表中
//                    pddIds.add(Objects.requireNonNull(zc.entity.get()).getCheckDetailId() + "");
//                }
//            }
//            for (ZcpdVpRvItemViewModel zc : mWpddList) {
//                wPddIds.add(Objects.requireNonNull(zc.entity.get()).getCheckDetailId() + "");
//            }
//
            TakeStockDetail mainInfo = entity.get();

            List<TakeStockDetail.ElecMaterialListDTO> elecMaterialListDTOS = new ArrayList<>();
            for (ZcpdVpRvItemViewModel zcpdVpRvItemViewModel : items.get(0).observableList) {
                TakeStockDetail.ElecMaterialListDTO elecMaterialListDTO = zcpdVpRvItemViewModel.entity.get();
                elecMaterialListDTOS.add(elecMaterialListDTO);
            }

            mainInfo.setElecMaterialList(elecMaterialListDTOS);


            if (Constants.CONFIG.IS_OFFLINE) {
                // 盘盈
                List<TakeStockDetail.ElecMaterialListDTO> pyDataList = new ArrayList<>();
//                for (ZcpdVpRvItemViewModel zcpdVpRvItemViewModel : mPddList) {
//                    if (zcpdVpRvItemViewModel.entity.get().getCheckResult().equals("盘盈")) {
//                        pyDataList.add(zcpdVpRvItemViewModel.entity.get());
//                    }
//                }
                Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
//                    model._saveCheckResult(mainInfo.getCheckId(),
//                            StringUtils.listToStr(pddIds, ","), StringUtils.listToStr(wPddIds, ","), mainInfo.getBatchNumber(), pyDataList);
                            emitter.onNext(true);
                        })
                        .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                        .compose(RxUtils.schedulersTransformer())
                        .subscribe(new DefaultObserver<Boolean>() {
                            @Override
                            public void onNext(Boolean b) {
                                btnVisiable.set(false);
                                ToastUtils.showShort("提交成功");
                                finish();
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            } else
                model.saveCheckedResult(mainInfo)
                        .compose(RxUtils.schedulersTransformer())
                        .compose(RxUtils.exceptionTransformer())
                        .doOnSubscribe(disposable -> {
                            showDialog();
                        }).subscribe(new ApiDisposableObserver() {
                            @Override
                            public void onResult(Object o) {
                                btnVisiable.set(false);
                                ToastUtils.showShort("提交成功");
                                finish();
                            }

                            @Override
                            public void onComplete() {
                                dismissDialog();
                            }
                        });
//        } else
//            ToastUtils.showShort("未盘到资产");
    });
    //ViewPager切换监听
    public BindingCommand<Integer> onPageSelectedCommand = new BindingCommand<>(index -> pagerIndex = index);

    public ZcpdViewModel(@NonNull Application application, AppRepository model) {
        super(application, model);
    }

    public int getCurrentPagerIndex() {
        return pagerIndex;
    }

    public void getNetData(int checkId) {
        if (Constants.CONFIG.IS_OFFLINE) {
            Observable.create((ObservableOnSubscribe<TakeStockDetail>) emitter -> {
//                TakeStockDetail data = model._selectOneCheck(checkId);
//                emitter.onNext(data);
                    })
                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                    .compose(RxUtils.schedulersTransformer())
                    .subscribe(new DefaultObserver<TakeStockDetail>() {
                        @Override
                        public void onNext(TakeStockDetail data) {
//                            if (data == null || data.getDataList().size() == 0) {
//                                setNoDataVisibleObservable(View.VISIBLE);
//                                return;
//                            }
//                            if (data != null) {
//                                entity.set(data);
//                                checkDataNum.set("0/" + data.getDataList().size());
//                                ZcpdVpItemViewModel itemViewModel = null;
//                                for (int i = 0; i < TAB_NUM; i++) {
//                                    itemViewModel = new ZcpdVpItemViewModel(ZcpdViewModel.this, i, data.getDataList());
//                                    items.add(itemViewModel);
//                                }
//                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {
                        }
                    });

        } else
            model.selectByCheck(checkId)
                    .compose(RxUtils.schedulersTransformer())
                    .compose(RxUtils.exceptionTransformer())
                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            showDialog("正在加载...");
                        }
                    })
                    .subscribe(new ApiDisposableObserver<TakeStockDetail>() {
                        @Override
                        public void onResult(TakeStockDetail data) {
                            if (data == null || data.getElecMaterialList().size() == 0) {
                                setNoDataVisibleObservable(View.VISIBLE);
                                return;
                            }

                            if (data != null) {
                                entity.set(data);
                                checkDataNum.set("0/" + data.getElecMaterialList().size());
                                for (int i = 0; i < TAB_NUM; i++) {
                                    ZcpdVpItemViewModel itemViewModel = new ZcpdVpItemViewModel(ZcpdViewModel.this, i, entity.get().getElecMaterialList());
                                    items.add(itemViewModel);
                                }
                            }
                        }

                        @Override
                        public void onComplete() {
                            dismissDialog();
                        }
                    });
    }

    public void updatePDItemModel(Set<String> sets) {
        btnVisiable.set(true);
        mPdAllList = items.get(0).observableList;
        mPddList = items.get(1).observableList;
        mWpddList = items.get(2).observableList;
        for (ZcpdVpRvItemViewModel viewModel : mPdAllList) {
            TakeStockDetail.ElecMaterialListDTO bean = viewModel.entity.get();
            if (sets.contains(bean.getRfidCode())) {
                bean.setBgColor(Utils.getContext().getDrawable(R.color.color_6684FF));
                bean.setCheckResult(1);
                bean.setCheckResultMessage("已盘点");
                sets.remove(bean.getRfidCode());
                rvSet.add(viewModel);  //防止添加的数据重复
            } else {
                bean.setCheckResult(2);
                bean.setCheckResultMessage("未盘点");
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

//        getPyData(sets);  //获取盘盈数据

        checkDataNum.set(mPddList.size() + "/" + entity.get().getElecMaterialList().size());
   /*     if (mPddList.size() > 0 || mPyListId.size() > 0) {
        }*/
        if (mPddList.size() == entity.get().getElecMaterialList().size() + mPyListId.size() && mWpddList.size() == 0) {  //全部盘点完成，盘点到的数据=已盘点+盘盈
            uc.scanFinishEvent.setValue(true);
        }
    }

    private void getPyData(Set<String> sets) {
        model.rfidToMaterialInfo(new ArrayList<>(sets))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                .compose(RxUtils.exceptionTransformer()).subscribe(new ApiDisposableObserver<List<AssetData>>() {
                    @Override
                    public void onResult(List<AssetData> beans) {
                        mPyListId.clear();
                        for (AssetData bean : beans) {
                            if (bean != null) {
                                mPyListId.add(String.valueOf(bean.getMaterialId()));

                                TakeStockDetail.ElecMaterialListDTO dataListBean = new TakeStockDetail.ElecMaterialListDTO();
//                        dataListBean.setBatchNumber(entity.get().getMainInfo().getBatchNumber());
                                dataListBean.setCheckResult(0);
                                dataListBean.setBgColor(Utils.getContext().getDrawable(R.color.color_81c480));
                                dataListBean.setMaterialId(bean.getMaterialId());
//                        dataListBean.setMaterialCode(bean.getMaterialCode());
//                        dataListBean.setMaterialName(bean.getMaterialName());
//                        dataListBean.setSpecification(bean.getSpecification());
//                        dataListBean.setSortName(bean.getSortName());
//                        dataListBean.setUseDepmName(bean.getUseDepmName());
//                        dataListBean.setLocationName(bean.getLocationName());
                                mPddList.add(new ZcpdVpRvItemViewModel(ZcpdViewModel.this, dataListBean));
                            }
                        }
                    }
                });
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
     /*   for (ZcpdVpRvItemViewModel itemViewModel : mPdAllList) {
            itemViewModel.entity.get().setBgColor(Utils.getContext().getDrawable(R.color.white));
            itemViewModel.entity.notifyChange();
        }*/
    }
}