package com.victor.base.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.victor.base.data.entity.SyncInfo;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/9/29
 * 邮箱：jxfengmtx@gmail.com
 */
@Dao
public interface SyncInfoDao {
    @Query("SELECT * FROM SyncInfo where syncId = (:id)")
    SyncInfo getAllById(int id);

    @Query("DELETE FROM SyncInfo")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(SyncInfo... dataListBeans);

    @Delete
    void delete(SyncInfo dataListBean);
}
