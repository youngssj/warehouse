package com.victor.main.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.victor.main.data.entity.AssetData;

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
public interface AssetDataDao {

    @Query("SELECT * FROM AssetData WHERE rfidcode in (:epcs)  COLLATE NOCASE ")
    List<AssetData> getAllByRfid(List<String> epcs);

    @Query("SELECT * FROM AssetData WHERE materialCode = (:barCode)  COLLATE NOCASE ")
    List<AssetData> getAllByBarcode(String barCode);

    @Query("DELETE FROM AssetData")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(AssetData... assetDatas);

    @Delete
    void delete(AssetData dataListBean);

    @Update
    void update(AssetData... assetDatas);

}
