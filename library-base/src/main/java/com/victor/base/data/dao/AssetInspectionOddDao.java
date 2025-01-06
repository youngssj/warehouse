package com.victor.base.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.victor.base.data.entity.AssetInspectionOdd;

import java.util.List;

import io.reactivex.Maybe;

/**
 *
 * @author tutu
 * 版本：1.0
 * 创建日期：2022/5/20
 * 巡检数据库操作类
 */
@Dao
public interface AssetInspectionOddDao {
    @Query("SELECT * FROM AssetInspectionOdd WHERE status = 1 limit (:offset),(:limit)")
    Maybe<List<AssetInspectionOdd>> getAll(int offset, int limit);

    @Query("SELECT * FROM AssetInspectionOdd where datetime(inspectionDate) between datetime((:lastDate)) and datetime(CURRENT_TIMESTAMP,'localtime')")
    List<AssetInspectionOdd> getAllByDate(String lastDate);

    @Query("SELECT COUNT(*) FROM AssetInspectionOdd WHERE status = 1")
    int getCount();

    @Query("SELECT * FROM AssetInspectionOdd WHERE status = 1 AND inspectionId=(:inspectionId)")
    AssetInspectionOdd getOneByIds(int inspectionId);

    @Query("DELETE FROM AssetInspectionOdd")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<AssetInspectionOdd> assetInspectionOddList);

    @Update
    void update(AssetInspectionOdd... assetInspectionOdd);

    @Delete
    void delete(AssetInspectionOdd assetInspectionOdd);

}
