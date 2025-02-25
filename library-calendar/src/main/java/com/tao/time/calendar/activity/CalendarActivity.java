package com.tao.time.calendar.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.view.TimePickerView;
import com.tao.time.calendar.BR;
import com.tao.time.calendar.DatePickerController;
import com.tao.time.calendar.DayPickerView;
import com.tao.time.calendar.R;
import com.tao.time.calendar.SimpleMonthAdapter;
import com.tao.time.calendar.databinding.CalendarActivityCalendarBinding;
import com.tao.time.calendar.timePicker.OnTimeSelectListener;
import com.tao.time.calendar.timePicker.TimePicker;
import com.tao.time.calendar.viewmodel.CalendarViewModel;
import com.victor.base.base.MBaseActivity;
import com.victor.base.router.RouterActivityPath;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Route(path = RouterActivityPath.Base.PAGER_BASE_CALENDAR)
public class CalendarActivity extends MBaseActivity<CalendarActivityCalendarBinding, CalendarViewModel> {

    private TimePickerView pvCustomTime;

    @Autowired(name = "showTime")
    boolean showTime = true;

    private TimePicker timePicker;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.calendar_activity_calendar;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.confirmClickEvent.observe(this, calendar -> {
            if (viewModel.calendarDay != null) {
                Intent intent = new Intent();
                intent.putExtra("calendarDay", calendar);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        viewModel.setBackVisibleObservable(View.VISIBLE);
        viewModel.setTitleText(getResources().getString(R.string.calendar_select));

        if (showTime) {
            viewModel.confirmVisibility.set(View.GONE);
        } else {
            viewModel.confirmVisibility.set(View.VISIBLE);
        }

        Calendar c = Calendar.getInstance();//首先要获取日历对象

        DayPickerView.DataModel dataModel = new DayPickerView.DataModel();

        viewModel.calendarDay = new SimpleMonthAdapter.CalendarDay(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        // 默认选择的日期
//        dataModel.selectedDays = new SimpleMonthAdapter.SelectedDays<>(calendarDay, null);

        //TYPE_MULTI：多选，TYPE_RANGE：范围选，TYPE_DAY_NUMBER：单选，TYPE_CONTINUOUS 连续选择
        dataModel.mTimeType = DayPickerView.DataModel.TYPE.TYPE_DAY_NUMBER;//选择
        dataModel.yearStart = c.get(Calendar.YEAR) - 1;//开始年份

        dataModel.monthStart = 0;//开始的月份
        dataModel.monthCount = 36;//要显示几个月
//        dataModel.leastDaysNum = 2;//最小选择天数
//        dataModel.mostDaysNum = 4;//最大选择天数
//        dataModel.defTag = "标2";//默认显示的标签
        dataModel.isToDayOperation = true;//今天日期能否被操作
//        dataModel.numberOfDays = 2; //根据天数起始日期选择

        binding.dpv.setParameter(dataModel, new DatePickerController() {

            @Override
            public void onDateSelected(List<SimpleMonthAdapter.CalendarDay> selectedDays) {
                if (selectedDays != null) {
                    viewModel.calendarDay = selectedDays.get(0);
                    if (showTime) {
                        //时间选择器 ，自定义布局
//                        pvCustomTime = new TimePickerBuilder(activity, new OnTimeSelectListener() {
//                            @Override
//                            public void onTimeSelect(Date date, View v) {
//                                Calendar calendar = Calendar.getInstance();
//                                calendar.setTime(date);
//                                calendar.set(Calendar.YEAR, calendarDay.year);
//                                calendar.set(Calendar.MONTH, calendarDay.month);
//                                calendar.set(Calendar.DAY_OF_MONTH, calendarDay.day);
//                                Intent intent = new Intent();
//                                intent.putExtra("calendarDay", calendar);
//                                setResult(Activity.RESULT_OK, intent);
//                                finish();
//                            }
//                        })
//                                .setLayoutRes(R.layout.logis_pickerview_custom_time, new CustomListener() {
//                                    @Override
//                                    public void customLayout(View v) {
//                                        TextView tvNext = v.findViewById(R.id.tvNext);
//                                        tvNext.setOnClickListener(new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View v) {
//                                                if (pvCustomTime != null) {
//                                                    pvCustomTime.returnData();
//                                                    pvCustomTime.dismiss();
//                                                }
//                                            }
//                                        });
//                                    }
//                                })
//                                .setDividerColor(Color.TRANSPARENT)//设置分割线的颜色
//                                .setLineSpacingMultiplier(1.6f)//设置两横线之间的间隔倍数
//                                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
//                                .setContentTextSize(20)
//                                .setTextColorCenter(0xFF4977fc)
//                                .setTextColorOut(0xFFA6A6A6)
//                                .setType(new boolean[]{false, false, false, true, true, true})
//                                .setLabel("年", "月", "日", "时  ", "分  ", "秒  ")
//                                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                                .isDialog(false)
//                                .build();
//                        pvCustomTime.show();
                        timePicker = new TimePicker().pickTime(CalendarActivity.this, new OnTimeSelectListener() {
                            @Override
                            public void onTimeSelect(Date date) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(date);
                                calendar.set(Calendar.YEAR, viewModel.calendarDay.year);
                                calendar.set(Calendar.MONTH, viewModel.calendarDay.month);
                                calendar.set(Calendar.DAY_OF_MONTH, viewModel.calendarDay.day);
                                Intent intent = new Intent();
                                intent.putExtra("calendarDay", calendar);
                                setResult(Activity.RESULT_OK, intent);
                                finish();
                            }
                        });
                    }
                } else {
                    viewModel.calendarDay = null;
                }
            }

            @Override
            public void alertSelected(int error) {
                Log.i("aaa", "错误代号=" + error);
            }
        });
        binding.dpv.scrollToSelectedPosition(new SimpleMonthAdapter.SelectedDays<>(viewModel.calendarDay, null), dataModel.monthStart, dataModel.yearStart);
    }

    @Override
    protected void onDestroy() {
        if (timePicker != null) {
            timePicker.dismiss();
        }
        super.onDestroy();
    }
}
