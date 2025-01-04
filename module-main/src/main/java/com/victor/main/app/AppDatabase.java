package com.victor.main.app;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.victor.main.data.dao.AssetCheckOddDao;
import com.victor.main.data.dao.AssetCheckOddDetailDao;
import com.victor.main.data.dao.AssetDataDao;
import com.victor.main.data.dao.AssetInspectionOddDao;
import com.victor.main.data.dao.AssetInspectionOddDetailDao;
import com.victor.main.data.dao.AssetRepairOddDao;
import com.victor.main.data.dao.AssetRepairOddDetailDao;
import com.victor.main.data.dao.ElecMateriallDao;
import com.victor.main.data.dao.SyncInfoDao;
import com.victor.main.data.dao.TakeStockDataDao;
import com.victor.main.data.dao.UserTokenDao;
import com.victor.main.data.entity.AssetCheckData;
import com.victor.main.data.entity.AssetCheckOdd;
import com.victor.main.data.entity.AssetData;
import com.victor.main.data.entity.AssetInspectionData;
import com.victor.main.data.entity.AssetInspectionOdd;
import com.victor.main.data.entity.AssetRepairData;
import com.victor.main.data.entity.AssetRepairOdd;
import com.victor.main.data.entity.SyncInfo;
import com.victor.main.data.entity.TakeStockData;
import com.victor.main.data.entity.TakeStockDetail;
import com.victor.main.data.entity.UserToken;
import com.victor.main.data.source.LocalDataSourceImpl;

import me.goldze.mvvmhabit.utils.SDCardUtils;
import me.goldze.mvvmhabit.utils.Utils;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/9/9
 * 邮箱：jxfengmtx@gmail.com
 */
@Database(entities = {UserToken.class,
        AssetCheckOdd.class,
        AssetCheckData.DataListBean.class,
        AssetInspectionOdd.class,
        AssetInspectionData.DataListBean.class,
        AssetRepairOdd.class,
        AssetRepairData.DataListBean.class,
        AssetData.class,
        SyncInfo.class,
        TakeStockData.class,
        TakeStockDetail.ElecMaterialListDTO.class,
}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase DB;  //创建单例

    public static AppDatabase getInstance() {
        if (DB == null) {
            synchronized (LocalDataSourceImpl.class) {
                if (DB == null) {
                    String path = SDCardUtils.getFileDataPath();
                    DB = Room.databaseBuilder(Utils.getContext(),
                            AppDatabase.class, path + "/appdata.db")
                            .fallbackToDestructiveMigration()    //版本更改直接删除之前的数据库，重新再创建新的数据库
                            .allowMainThreadQueries()   //允许在主线程查询数据
                            .build();
                }
            }
        }
        return DB;
    }

    public abstract UserTokenDao userTokenDao();

    public abstract SyncInfoDao syncInfoDao();

    public abstract AssetCheckOddDao assetCheckOddDao();

    public abstract AssetCheckOddDetailDao assetCheckDetailDao();
    public abstract AssetDataDao assetDataDao();
    // 巡检
    public abstract AssetInspectionOddDao assetInspectionOddDao();
    public abstract AssetInspectionOddDetailDao assetInspectionOddDetailDao();
    //维修
    public abstract AssetRepairOddDao assetRepairOddDao();
    public abstract AssetRepairOddDetailDao assetRepairOddDetailDao();
    // 盘点单
    public abstract TakeStockDataDao takeStockDataDao();
    // 盘点单详情
    public abstract ElecMateriallDao elecMaterialDao();
}
