package com.victor.main.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.victor.main.data.entity.SyncInfo;

import java.util.List;

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

    @Query("SELECT * FROM SyncInfo where datetime(syncDate) between datetime(:lastDate) and datetime(CURRENT_TIMESTAMP,'localtime')")
    List<SyncInfo> getAllByDate(String lastDate);

    @Query("DELETE FROM SyncInfo")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(SyncInfo... dataListBeans);

    @Delete
    void delete(SyncInfo dataListBean);

    @Update
    void update(SyncInfo... dataListBeans);

}
