package com.tao.time.calendar.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.tao.time.calendar.SimpleMonthAdapter;
import com.victor.base.base.BaseTitleViewModel;

import java.util.Calendar;
import java.util.Observable;

import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

public class CalendarViewModel extends BaseTitleViewModel {

    public SimpleMonthAdapter.CalendarDay calendarDay;
    public ObservableField<Integer> confirmVisibility = new ObservableField();

    public class UIChangeObservable {
        public SingleLiveEvent<Calendar> confirmClickEvent = new SingleLiveEvent<>();
    }

    public UIChangeObservable uc = new UIChangeObservable();

    public CalendarViewModel(@NonNull Application application) {
        super(application);
    }

    public BindingCommand confirmClickCommand = new BindingCommand(() -> {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendarDay.year);
        calendar.set(Calendar.MONTH, calendarDay.month);
        calendar.set(Calendar.DAY_OF_MONTH, calendarDay.day);
        uc.confirmClickEvent.setValue(calendar);
    });
}
