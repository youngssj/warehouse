package com.victor.base.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.victor.base.data.entity.InventoryData;

import java.util.List;

import io.reactivex.Maybe;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/9/29
 * 邮箱：jxfengmtx@gmail.com
 */
@Dao
public interface InventoryDataDao {
    @Query("SELECT * FROM InventoryData limit (:offset),(:limit)")
    Maybe<List<InventoryData>> getAll(int offset, int limit);

    @Query("SELECT COUNT(*) FROM InventoryData")
    int getCount();

    @Query("SELECT * FROM InventoryData WHERE checkId=(:checkId)")
    InventoryData getOneByIds(int checkId);

    @Query("DELETE FROM InventoryData")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(InventoryData... inventoryDatas);

    @Update
    void update(InventoryData... inventoryDatas);

    @Delete
    void delete(InventoryData inventoryData);

}
