package com.victor.main.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.victor.main.data.entity.TakeStockData;

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
public interface TakeStockDataDao {
    @Query("SELECT * FROM TakeStockData limit (:offset),(:limit)")
    Maybe<List<TakeStockData>> getAll(int offset, int limit);

    @Query("SELECT COUNT(*) FROM TakeStockData")
    int getCount();

    @Query("SELECT * FROM TakeStockData WHERE checkId=(:checkId)")
    TakeStockData getOneByIds(int checkId);

    @Query("DELETE FROM TakeStockData")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(TakeStockData... takeStockDatas);

    @Update
    void update(TakeStockData... takeStockDatas);

    @Delete
    void delete(TakeStockData takeStockData);

}
