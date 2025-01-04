package com.victor.main.utils;

import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2021/1/22
 * 邮箱：jxfengmtx@gmail.com
 */
public class BaiDLocUtil {
    //采用Double CheckLock(DCL)实现单例
    private Context mContext;

    public LocationClient mLocationClient = null;

    public BaiDLocUtil(Context context) {
        mContext = context;
        mLocationClient = new LocationClient(context);
        initParams();
    }

    private void initParams() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(30000);//设置发起定位请求的间隔时间为5000ms
        mLocationClient.setLocOption(option);
    }


    public LocationClient addListener(BDAbstractLocationListener myListener) {
        mLocationClient.registerLocationListener(myListener);
        return mLocationClient;
    }

}
