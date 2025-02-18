package com.victor.base.data.http.Interceptor;

import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.victor.base.event.MessageEvent;
import com.victor.base.event.MessageType;
import com.victor.base.router.RouterActivityPath;
import com.victor.base.utils.Constants;

import java.io.IOException;
import java.util.List;

import me.goldze.mvvmhabit.base.AppManager;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/11/9
 * 邮箱：jxfengmtx@gmail.com
 */
public class BaseUrlInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //获取request
        Request request = chain.request();
        //从request中获取原有的HttpUrl实例oldHttpUrl
        HttpUrl oldHttpUrl = request.url();
        //获取request的创建者builder
        Request.Builder builder = request.newBuilder();
        HttpUrl newBaseUrl = oldHttpUrl;

        String token = SPUtils.getInstance().getString("token");
        if (!TextUtils.isEmpty(token)) {
            builder.addHeader("Authorization", "Bearer " + token);
        }

        HttpUrl.Builder urlBuilder = oldHttpUrl
                .newBuilder()
                // 更换主机名
                .host(Constants.CONFIG.DEFAULT_IP)
                // 更换端口
                .port(Integer.parseInt(Constants.CONFIG.DEFAULT_PORT));
        newBaseUrl = urlBuilder.build();
        KLog.i("Url", "intercept: " + newBaseUrl.toString());

        Response response = chain.proceed(builder.url(newBaseUrl).build());
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        try {
            BaseResponse baseResponse = new Gson().fromJson(content, BaseResponse.class);
            if (baseResponse.getCode() == 401) {
                RxBus.getDefault().post(new MessageEvent<>(MessageType.EVENT_TYPE_TOKEN_INVALID, null));
                return response.newBuilder()
                        .body(okhttp3.ResponseBody.create(mediaType, content))
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }
}