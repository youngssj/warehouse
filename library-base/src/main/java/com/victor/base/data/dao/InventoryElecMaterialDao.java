package com.victor.base.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

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
    List<InventoryData.InventoryElecMaterial> getAll(String checkId);

    @Query("SELECT * FROM InventoryElecMaterial where datetime(checkDate) between datetime((:lastDate)) and datetime(CURRENT_TIMESTAMP,'localtime')")
    List<InventoryData.InventoryElecMaterial> getAllByDate(String lastDate);

    @Query("SELECT * FROM InventoryElecMaterial WHERE rfidCode in (:epcs)  COLLATE NOCASE ")
    List<InventoryData.InventoryElecMaterial> getAllByRfid(List<String> epcs);

    @Query("SELECT * FROM InventoryElecMaterial WHERE materialCode = (:barCode)  COLLATE NOCASE ")
    List<InventoryData.InventoryElecMaterial> getAllByBarcode(String barCode);

    //更新部分字段
    @Query("Update InventoryElecMaterial set checkResult=(:checkResult),checkDate =(:checkDate) where checkDetailId in (:ids)")
    void update(List<String> ids, String checkResult, String checkDate);

    @Query("DELETE FROM InventoryElecMaterial")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(InventoryData.InventoryElecMaterial... dataListBeans);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<InventoryData.InventoryElecMaterial> dataListBeans);

    @Delete
    void delete(InventoryData.InventoryElecMaterial dataListBean);

    @Update
    void update(InventoryData.InventoryElecMaterial... dataListBeans);

}
