package com.victor.base.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.victor.base.data.entity.InventoryData;

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
public interface InventoryElecMaterialDao {
    @Query("SELECT * FROM InventoryElecMaterial where checkId=(:checkId)")
    List<InventoryData.InventoryElecMaterial> getAll(int checkId);

    @Query("DELETE FROM InventoryElecMaterial")
    void deleteAll();

    @Query("DELETE FROM InventoryElecMaterial where checkId=(:checkId)")
    void deleteByCheckId(int checkId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(InventoryData.InventoryElecMaterial... dataListBeans);
}
