package com.victor.base.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.victor.base.data.entity.AssetCheckOdd;

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
public interface AssetCheckOddDao {
    @Query("SELECT * FROM AssetCheckOdd WHERE status = 1 limit (:offset),(:limit)")
    Maybe<List<AssetCheckOdd>> getAll(int offset, int limit);

    @Query("SELECT * FROM AssetCheckOdd where datetime(checkDate) between datetime((:lastDate)) and datetime(CURRENT_TIMESTAMP,'localtime')")
    List<AssetCheckOdd> getAllByDate(String lastDate);


    @Query("SELECT COUNT(*) FROM AssetCheckOdd WHERE status = 1")
    int getCount();

    @Query("SELECT * FROM AssetCheckOdd WHERE status = 1 AND checkId=(:checkId)")
    AssetCheckOdd getOneByIds(int checkId);

    @Query("DELETE FROM AssetCheckOdd")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(AssetCheckOdd... assetCheckOdds);

    @Update
    void update(AssetCheckOdd... AssetCheckOdd);

    @Delete
    void delete(AssetCheckOdd assetCheckOdd);

}
