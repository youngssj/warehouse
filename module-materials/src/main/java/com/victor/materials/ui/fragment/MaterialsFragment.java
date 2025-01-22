package com.victor.materials.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.victor.base.app.AppViewModelFactory;
import com.victor.base.router.RouterFragmentPath;
import com.victor.materials.BR;
import com.victor.materials.R;
import com.victor.materials.databinding.MaterialsFragmentBinding;
import com.victor.materials.databinding.MaterialsItemDetailBinding;
import com.victor.materials.ui.viewmodel.MaterialsQueryViewModel;
import com.victor.materials.ui.viewmodel.MaterialsViewModel;

import me.goldze.mvvmhabit.base.BaseFragment;

@Route(path = RouterFragmentPath.Materials.PAGER_MATERIALS_LIST)
public class MaterialsFragment extends BaseFragment<MaterialsFragmentBinding, MaterialsViewModel> {

    @Autowired(name = "materialStatus")
    String materialStatus;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.materials_fragment;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
        viewModel.muc.showCustomEvent.observe(getViewLifecycleOwner(), materialsItemViewModel -> {
            MaterialsItemDetailBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.materials_item_detail, null, false);
            binding.setViewModel(materialsItemViewModel);
            showCustomDialog(getResources().getString(R.string.workbench_materials_detail_text), binding, (dialog, which) -> {
            });
        });
        //监听下拉刷新完成
        viewModel.uc.finishRefreshing.observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                //结束刷新
                binding.bgaRefresh.endRefreshing();
            }
        });
        //监听上拉加载完成
        viewModel.uc.finishLoadmore.observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                //结束刷新
                binding.bgaRefresh.endLoadingMore();
            }
        });
    }

    @Override
    public MaterialsViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return new ViewModelProvider(this, factory).get(MaterialsViewModel.class);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        viewModel.setMaterialStatus(materialStatus);
    }
}
