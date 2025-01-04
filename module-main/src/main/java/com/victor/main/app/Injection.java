package com.victor.main.app;

import com.victor.main.data.Repository.AppRepository;
import com.victor.main.data.http.HttpService;
import com.victor.main.data.http.RetrofitClient;
import com.victor.main.data.source.HttpDataSource;
import com.victor.main.data.source.HttpDataSourceImpl;
import com.victor.main.data.source.LocalDataSource;
import com.victor.main.data.source.LocalDataSourceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/9/9
 * 邮箱：jxfengmtx@gmail.com
 */
public class Injection {
    public static Map<String, String> getMapInstance() {
        return new HashMap<>();
    }

    public static AppRepository provideAppRepository() {
        //网络API服务
        HttpService apiService = RetrofitClient.getInstance().create(HttpService.class);
        //网络数据源
        HttpDataSource httpDataSource = HttpDataSourceImpl.getInstance(apiService);
        //创建数据库
        AppDatabase appDatabase = AppDatabase.getInstance();
        //本地数据源
        LocalDataSource localDataSource = LocalDataSourceImpl.getInstance(appDatabase);
        //两条分支组成一个数据仓库
        return AppRepository.getInstance(httpDataSource, localDataSource);
    }
}
