package com.victor.base.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.victor.base.data.entity.AssetRepairData;

import java.util.List;

/**
 *
 * @author tutu
 * 版本：1.0
 * 创建日期：2022/5/23
 *
 * 巡检详情数据库操作类
 */
@Dao
public interface AssetRepairOddDetailDao {
    @Query("SELECT * FROM AssetRepairDetail where batchNumber=(:batchNumber)")
    List<AssetRepairData.DataListBean> getAll(String batchNumber);

    @Query("SELECT * FROM AssetRepairDetail where datetime(RepairDate) between datetime((:lastDate)) and datetime(CURRENT_TIMESTAMP,'localtime')")
    List<AssetRepairData.DataListBean> getAllByDate(String lastDate);

    @Query("SELECT * FROM AssetRepairDetail WHERE rfidCode in (:epcs)  COLLATE NOCASE ")
    List<AssetRepairData.DataListBean> getAllByRfid(List<String> epcs);

    @Query("SELECT * FROM AssetRepairDetail WHERE materialCode = (:barCode)  COLLATE NOCASE ")
    List<AssetRepairData.DataListBean> getAllByBarcode(String barCode);

    @Query("SELECT * FROM AssetRepairDetail WHERE repairDetailId=(:repairDetailId)")
    AssetRepairData.DataListBean getOneByIds(int repairDetailId);

    //更新部分字段
    @Query("Update AssetRepairDetail set repairContent=(:repairContent),RepairDate =(:RepairDate) where repairDetailId in (:ids)")
    void update(List<String> ids, String repairContent, String RepairDate);

    @Query("DELETE FROM AssetRepairDetail")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<AssetRepairData.DataListBean> dataListBeans);

    @Delete
    void delete(AssetRepairData.DataListBean dataListBean);

    @Update
    void update(AssetRepairData.DataListBean... dataListBeans);

}
