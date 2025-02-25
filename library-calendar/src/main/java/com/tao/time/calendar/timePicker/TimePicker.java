package com.tao.time.calendar.timePicker;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.contrarywind.view.WheelView;
import com.tao.time.calendar.R;
import com.zyyoona7.popup.EasyPopup;

import java.lang.ref.WeakReference;
import java.util.Calendar;

public class TimePicker {

    private EasyPopup easyPopup;

    private WheelView wv_hours;
    private WheelView wv_minutes;
    private WheelView wv_seconds;
    private WeakReference<Activity> activityWeakReference;

    public TimePicker pickTime(Activity activity, final OnTimeSelectListener onTimeSelectListener) {
        activityWeakReference = new WeakReference<>(activity);
        easyPopup = EasyPopup.create()
                .setContentView(activityWeakReference.get(), R.layout.calendar_pickerview_custom_time, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.CalendarBottomPopAnim)
                //是否允许点击PopupWindow之外的地方消失
                .setFocusAndOutsideEnable(true)
                //允许背景变暗
                .setBackgroundDimEnable(true)
                //变暗的透明度(0-1)，0为完全透明
                .setDimValue(0.4f)
                //变暗的背景颜色
                .setDimColor(Color.BLACK)
                //指定任意 ViewGroup 背景变暗
                .setDimView((ViewGroup) activityWeakReference.get().findViewById(android.R.id.content))
                .apply();
        easyPopup.showAtLocation(activityWeakReference.get().findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);

        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);

        wv_hours = (WheelView) easyPopup.findViewById(R.id.hour);
        wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
        wv_hours.setLabel(activity.getString(R.string.calendar_hour));// 添加文字
        wv_hours.setCurrentItem(hours);
        wv_hours.setGravity(Gravity.CENTER);

        wv_minutes = (WheelView) easyPopup.findViewById(R.id.min);
        wv_minutes.setAdapter(new NumericWheelAdapter(0, 59));
        wv_minutes.setLabel(activity.getString(R.string.calendar_minute));// 添加文字
        wv_minutes.setCurrentItem(minute);
        wv_minutes.setGravity(Gravity.CENTER);

        wv_seconds = (WheelView) easyPopup.findViewById(R.id.second);
        wv_seconds.setAdapter(new NumericWheelAdapter(0, 59));
        wv_seconds.setLabel(activity.getString(R.string.calendar_second));// 添加文字
        wv_seconds.setCurrentItem(seconds);
        wv_seconds.setGravity(Gravity.CENTER);
        // 设置是否循环滚动
        wv_hours.setCyclic(false);
        wv_minutes.setCyclic(false);
        wv_seconds.setCyclic(false);
        // 设置间距倍数
        wv_hours.setLineSpacingMultiplier(1.6f);
        wv_minutes.setLineSpacingMultiplier(1.6f);
        wv_seconds.setLineSpacingMultiplier(1.6f);
        // 设置分割线颜色
        wv_hours.setDividerColor(Color.TRANSPARENT);
        wv_minutes.setDividerColor(Color.TRANSPARENT);
        wv_seconds.setDividerColor(Color.TRANSPARENT);
        // 设置分割线以外的颜色
        wv_hours.setTextColorOut(0xFFA6A6A6);
        wv_minutes.setTextColorOut(0xFFA6A6A6);
        wv_seconds.setTextColorOut(0xFFA6A6A6);
        // 设置中间的颜色
        wv_hours.setTextColorCenter(0xFF1465ff);
        wv_minutes.setTextColorCenter(0xFF1465ff);
        wv_seconds.setTextColorCenter(0xFF1465ff);
        // 是否只显示中间选中项的
        wv_hours.isCenterLabel(false);
        wv_minutes.isCenterLabel(false);
        wv_seconds.isCenterLabel(false);
        // 字体大小
        wv_hours.setTextSize(20);
        wv_minutes.setTextSize(20);
        wv_seconds.setTextSize(20);

        easyPopup.findViewById(R.id.tvNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onTimeSelectListener != null) {
                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.HOUR_OF_DAY, wv_hours.getCurrentItem());
                    cal.set(Calendar.MINUTE, wv_minutes.getCurrentItem());
                    cal.set(Calendar.SECOND, wv_seconds.getCurrentItem());
                    onTimeSelectListener.onTimeSelect(cal.getTime());
                }
            }
        });
        return this;
    }

    public void dismiss() {
        if (easyPopup != null && easyPopup.isShowing()) {
            easyPopup.dismiss();
        }
    }
}
