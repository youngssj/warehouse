package me.goldze.mvvmhabit.base;

import android.os.Bundle;

/**
 * Created by goldze on 2017/6/15.
 */

public interface IBaseView {
    /**
     * 初始化界面传递参数
     */
    void initParam();
    /**
     * 初始化数据
     */
    void initData(Bundle savedInstanceState);

    /**
     * 初始化界面观察者的监听
     */
    void initViewObservable();
}
