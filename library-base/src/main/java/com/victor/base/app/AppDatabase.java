package com.victor.base.app;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.victor.base.data.dao.AllocateDataDao;
import com.victor.base.data.dao.AllocateMaterialDao;
import com.victor.base.data.dao.InboundDataDao;
import com.victor.base.data.dao.InboundElecMaterialDao;
import com.victor.base.data.dao.InventoryDataDao;
import com.victor.base.data.dao.InventoryElecMaterialDao;
import com.victor.base.data.dao.MovementDataDao;
import com.victor.base.data.dao.MovementElecMaterialDao;
import com.victor.base.data.dao.OutboundDataDao;
import com.victor.base.data.dao.OutboundElecMaterialDao;
import com.victor.base.data.dao.SyncInfoDao;
import com.victor.base.data.dao.UserTokenDao;
import com.victor.base.data.entity.AllocateData;
import com.victor.base.data.entity.InboundData;
import com.victor.base.data.entity.InventoryData;
import com.victor.base.data.entity.MovementData;
import com.victor.base.data.entity.OutboundData;
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
@Database(entities = {
        UserToken.class,
        SyncInfo.class,
        InventoryData.class,
        InventoryData.InventoryElecMaterial.class,
        InboundData.class,
        InboundData.InboundElecMaterial.class,
        OutboundData.class,
        OutboundData.OutboundElecMaterial.class,
        MovementData.class,
        MovementData.MovementElecMaterial.class,
        AllocateData.class,
        AllocateData.AllocateMaterial.class,
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
    public abstract InventoryDataDao inventoryDataDao();

    // 盘点单详情
    public abstract InventoryElecMaterialDao inventoryElecMaterialDao();

    // 入库单
    public abstract InboundDataDao inboundDataDao();

    // 入库单详情
    public abstract InboundElecMaterialDao inboundElecMaterialDao();

    // 出库单
    public abstract OutboundDataDao outboundDataDao();

    // 出库单详情
    public abstract OutboundElecMaterialDao outboundElecMaterialDao();

    // 移库单
    public abstract MovementDataDao movementDataDao();

    // 移库单详情
    public abstract MovementElecMaterialDao movementElecMaterialDao();

    // 调拨单
    public abstract AllocateDataDao allocateDataDao();

    // 调拨单详情
    public abstract AllocateMaterialDao allocateMaterialDao();
}
