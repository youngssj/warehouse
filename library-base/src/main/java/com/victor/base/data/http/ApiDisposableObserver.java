package com.victor.base.data.http;

import android.text.TextUtils;

import io.reactivex.observers.DisposableObserver;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.http.NetworkUtil;
import me.goldze.mvvmhabit.http.ResponseThrowable;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.goldze.mvvmhabit.utils.Utils;

/**
 * Created by goldze on 2017/5/10.
 * 统一的Code封装处理。该类仅供参考，实际业务逻辑, 根据需求来定义，
 */

public abstract class ApiDisposableObserver<T> extends DisposableObserver<T> {
    public abstract void onResult(T t);

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
//            onComplete();
        }
    }

    @Override
    public void onNext(T o) {
        if (o instanceof BaseResponse) {
            BaseResponse baseResponse = (BaseResponse) o;
            String msg = "";
            if (TextUtils.isEmpty(baseResponse.getMsg())) {
                msg = "错误代码:" + baseResponse.getCode();
            } else {
                msg = baseResponse.getMsg();
            }
            switch (baseResponse.getCode()) {
                case CodeRule.CODE_0:
                    //请求成功, 正确的操作方式
                    onResult((T) baseResponse.getData());
                    break;
                case CodeRule.CODE_1:
                    //请求错误, 正确的操作方式
                    ToastUtils.showShort(msg);
                    break;
                case CodeRule.CODE_10:
                    //                onResult((T) baseResponse.getData());
                    break;
                case CodeRule.CODE_200:
                    // 请求成功, 正确的操作方式, 并消息提示
                    onResult((T) baseResponse.getData());
                    break;
                case CodeRule.CODE_300:
                    //请求失败，不打印Message
                    KLog.e("请求失败");
                    ToastUtils.showShort(msg);
                    break;
                case CodeRule.CODE_330:
                    //请求失败，打印Message
                    ToastUtils.showShort(msg);
                    break;
                case CodeRule.CODE_401:
                    // 无效的Token
                    break;
                case CodeRule.CODE_500:
                    //服务器内部异常
                    ToastUtils.showShort(msg);
                    break;
                case CodeRule.CODE_503:
                    //参数为空
                    KLog.e("参数为空");
                    break;
                case CodeRule.CODE_502:
                    //没有数据
                    KLog.e("没有数据");
                    break;
                case CodeRule.CODE_510:
                    break;
                case CodeRule.CODE_530:
                    ToastUtils.showShort("请先登录");
                    break;
                case CodeRule.CODE_551:
                    ToastUtils.showShort(msg);
                    break;
                default:
                    ToastUtils.showShort(msg);
                    break;
            }
        }else{
            onResult(o);
        }

//        onComplete();
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
        static final int CODE_401 = 401;
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