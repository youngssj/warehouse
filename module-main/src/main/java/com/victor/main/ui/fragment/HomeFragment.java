package com.victor.main.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.Fill;
import com.victor.base.app.AppViewModelFactory;
import com.victor.base.data.entity.HomeTotalData;
import com.victor.base.data.entity.MaterialsStatisticsData;
import com.victor.base.router.RouterFragmentPath;
import com.victor.main.BR;
import com.victor.main.R;
import com.victor.main.databinding.MainFragmentHomeBinding;
import com.victor.main.ui.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseFragment;

@Route(path = RouterFragmentPath.Home.PAGER_HOME)
public class HomeFragment extends BaseFragment<MainFragmentHomeBinding, HomeViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.main_fragment_home;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public HomeViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return new ViewModelProvider(this, factory).get(HomeViewModel.class);
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.totalDataEvent.observe(this, this::setPieChart);
        viewModel.uc.materialsStatisticsDataEvent.observe(this, this::setBarChart);
    }

    private void setBarChart(MaterialsStatisticsData materialsStatisticsData) {
        BarChart barChart = binding.barChart.chart;

        barChart.setDescription(null);
        barChart.getLegend().setEnabled(false);
        barChart.setDoubleTapToZoomEnabled(false);
        barChart.setScaleEnabled(false);
        barChart.setExtraOffsets(10, 10, 10, 10);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setAxisLineColor(ContextCompat.getColor(getContext(), R.color.color_333333));
        xAxis.setAxisLineWidth(1f);
        xAxis.setTextColor(ContextCompat.getColor(getContext(), R.color.color_333333));
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setValueFormatter(new IndexAxisValueFormatter() {
            @Override
            public String getFormattedValue(float index, AxisBase axis) {
                return materialsStatisticsData.getAreaArr().get((int) index);
            }
        });

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.valueOf((int) Math.abs(value));
            }
        });
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f);

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);

        ArrayList<BarEntry> values = new ArrayList<>();
        if (materialsStatisticsData != null && materialsStatisticsData.getAreaArr() != null) {
            for (int i = 0; i < materialsStatisticsData.getAreaArr().size(); i++) {
                values.add(new BarEntry(i, materialsStatisticsData.getAreaNum().get(i)));
            }
        }

        BarDataSet set = new BarDataSet(values, "");
        set.setDrawIcons(false);
        int startColor = ContextCompat.getColor(getContext(), R.color.color_15CBDF);
        int endColor = ContextCompat.getColor(getContext(), R.color.color_258DFF);
        List<Fill> gradientFills = new ArrayList<>();
        gradientFills.add(new Fill(startColor, endColor));
        set.setFills(gradientFills);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set);

        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        data.setBarWidth(0.6f);

        barChart.setData(data);
        barChart.notifyDataSetChanged();
        barChart.invalidate();
    }

    private void setPieChart(List<HomeTotalData> homeTotalDatas) {
        PieChart pieChart = binding.pieChart.chart;

        pieChart.setDescription(null);
        pieChart.getLegend().setEnabled(false);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.setExtraOffsets(10, 10, 10, 10); //饼形图上下左右边距
        pieChart.setDragDecelerationFrictionCoef(0.95f); //设置pieChart图表转动阻力摩擦系数[0,1]
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setEntryLabelTextSize(12f);

        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();
        if (homeTotalDatas != null) {
            for (HomeTotalData homeTotalData : homeTotalDatas) {
                entries.add(new PieEntry(homeTotalData.getValue(), homeTotalData.getName()));
//                if (homeTotalData.getItemStyle() != null && homeTotalData.getItemStyle().length() > 16) {
//                    String colorStr = homeTotalData.getItemStyle().substring(9, 15);
//                    try {
//                        colors.add((int) Long.parseLong(colorStr, 16) | 0xFF000000);
//                    } catch (NumberFormatException e) {
//                        e.printStackTrace();
//                    }
//                }
            }

            colors.add(getResources().getColor(R.color.color_FF3366));
            colors.add(getResources().getColor(R.color.color_9187FF));
            colors.add(getResources().getColor(R.color.color_017BFF));
            colors.add(getResources().getColor(R.color.color_15CBDF));
            colors.add(getResources().getColor(R.color.color_DF15B7));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setColors(colors);
        dataSet.setSliceSpace(5f);
        dataSet.setSelectionShift(5f);//设置饼状Item被选中时变化的距离(为0f时，选中的不会弹起来)

        PieData data = new PieData(dataSet);

        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(16f);

        pieChart.setData(data);
        pieChart.notifyDataSetChanged();
        pieChart.invalidate();
    }
}