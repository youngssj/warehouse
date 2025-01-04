package com.victor.main.data.http;

import com.victor.main.data.entity.ListData;

import io.reactivex.observers.DisposableObserver;
import me.goldze.mvvmhabit.http.NetworkUtil;
import me.goldze.mvvmhabit.http.ResponseThrowable;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.goldze.mvvmhabit.utils.Utils;

/**
 * Created by goldze on 2017/5/10.
 * 统一的Code封装处理。该类仅供参考，实际业务逻辑, 根据需求来定义，
 */

public abstract class ApiListDisposableObserver<T> extends DisposableObserver<T> {
    public abstract void onResult(ListData<T> listData);

    @Override
    public void onComplete() {

    }


    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        onComplete();

        if (e instanceof ResponseThrowable) {
            ResponseThrowable rError = (ResponseThrowable) e;
            ToastUtils.showShort(rError.message);
            return;
        }
        //其他全部甩锅网络异常
        ToastUtils.showShort("网络异常");
    }

    @Override
    public void onStart() {
        super.onStart();
        KLog.i("http is start");
        // if  NetworkAvailable no !   must to call onCompleted
        if (!NetworkUtil.isNetworkAvailable(Utils.getContext())) {
            KLog.d("无网络，读取缓存数据");
            onComplete();
        }
    }

    @Override
    public void onNext(T o) {
       if(o instanceof ListResponse){
            ListResponse listResponse = (ListResponse) o;
            onResult(new ListData<T>(listResponse.getTotal(), (T)listResponse.getRows()));
        }
        onComplete();
    }

    public static final class CodeRule {
        static final int CODE_0 = 0;
        static final int CODE_1 = 1;
        static final int CODE_10 = 10;
        static final int CODE_11 = 11;

        //请求成功, 正确的操作方式
        static final int CODE_200 = 200;
        //请求成功, 消息提示
        static final int CODE_220 = 220;
        //请求失败，不打印Message
        static final int CODE_300 = 300;
        //请求失败，打印Message
        static final int CODE_330 = 330;
        //服务器内部异常
        static final int CODE_500 = 500;
        //参数为空
        static final int CODE_503 = 503;
        //没有数据
        static final int CODE_502 = 502;
        //无效的Token
        static final int CODE_510 = 510;
        //未登录
        static final int CODE_530 = 530;
        //请求的操作异常终止：未知的页面类型
        static final int CODE_551 = 551;
    }
}