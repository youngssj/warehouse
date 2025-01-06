package com.victor.base.data.http.Interceptor;

import com.victor.base.utils.Constants;

import java.io.IOException;
import java.util.List;

import me.goldze.mvvmhabit.utils.KLog;
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

        List<String> loginHeaders = request.headers("login");
        HttpUrl.Builder urlBuilder = oldHttpUrl
                .newBuilder()
                // 更换主机名
                .host(Constants.CONFIG.DEFAULT_IP)
                // 更换端口
                .port(Integer.parseInt(Constants.CONFIG.DEFAULT_PORT));
      /*  String sPath = newBaseUrl.encodedPath();
        if (loginHeaders != null && Constants.CONFIG.IS_INTRANET) {// 重建新的HttpUrl，修改需要修改的url部分
            sPath = sPath.replace("api/sys/sysAs", "arcArc");
        } else if (Constants.CONFIG.IS_INTRANET) {
            sPath = sPath.replace("api/arc/arcArc", "arcArc");
        }
        urlBuilder.encodedPath(sPath);*/
        newBaseUrl = urlBuilder.build();
        KLog.i("Url", "intercept: " + newBaseUrl.toString());
        return chain.proceed(builder.url(newBaseUrl).build());
    }
}