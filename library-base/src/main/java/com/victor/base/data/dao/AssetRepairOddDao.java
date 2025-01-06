package com.victor.base.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.victor.base.data.entity.AssetRepairOdd;

import java.util.List;

import io.reactivex.Maybe;

/**
 *
 * @author tutu
 * 版本：1.0
 * 创建日期：2022/5/23
 * 维修数据库操作类
 */
@Dao
public interface AssetRepairOddDao {
    @Query("SELECT * FROM AssetRepairOdd WHERE status = 1 limit (:offset),(:limit)")
    Maybe<List<AssetRepairOdd>> getAll(int offset, int limit);

    @Query("SELECT * FROM AssetRepairOdd where datetime(repairDate) between datetime((:lastDate)) and datetime(CURRENT_TIMESTAMP,'localtime')")
    List<AssetRepairOdd> getAllByDate(String lastDate);


    @Query("SELECT COUNT(*) FROM AssetRepairOdd WHERE status = 1")
    int getCount();

    @Query("SELECT * FROM AssetRepairOdd WHERE status = 1 AND RepairId=(:RepairId)")
    AssetRepairOdd getOneByIds(int RepairId);

    @Query("DELETE FROM AssetRepairOdd")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<AssetRepairOdd> assetRepairOddList);

    @Update
    void update(AssetRepairOdd... assetRepairOdd);

    @Delete
    void delete(AssetRepairOdd assetRepairOdd);

}
