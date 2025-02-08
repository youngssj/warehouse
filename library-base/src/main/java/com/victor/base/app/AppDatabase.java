package com.victor.base.app;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.victor.base.data.dao.InventoryElecMaterialDao;
import com.victor.base.data.dao.SyncInfoDao;
import com.victor.base.data.dao.InventoryDataDao;
import com.victor.base.data.dao.UserTokenDao;
import com.victor.base.data.entity.InventoryData;
import com.victor.base.data.entity.InventoryData;
import com.victor.base.data.entity.SyncInfo;
import com.victor.base.data.entity.UserToken;
import com.victor.base.data.source.LocalDataSourceImpl;

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
        SyncInfo.class,
        InventoryData.class,
        InventoryData.InventoryElecMaterial.class,
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

    // 盘点单
    public abstract InventoryDataDao takeStockDataDao();

    // 盘点单详情
    public abstract InventoryElecMaterialDao elecMaterialDao();
}
