package com.victor.base.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.victor.base.data.entity.AssetInspectionData;

import java.util.List;

/**
 *
 * @author tutu
 * 版本：1.0
 * 创建日期：2022/5/20
 *
 * 巡检详情数据库操作类
 */
@Dao
public interface AssetInspectionOddDetailDao {
    @Query("SELECT * FROM AssetInspectionDetail where batchNumber=(:batchNumber)")
    List<AssetInspectionData.DataListBean> getAll(String batchNumber);

    @Query("SELECT * FROM AssetInspectionDetail where datetime(inspectionDate) between datetime((:lastDate)) and datetime(CURRENT_TIMESTAMP,'localtime')")
    List<AssetInspectionData.DataListBean> getAllByDate(String lastDate);

    @Query("SELECT * FROM AssetInspectionDetail WHERE rfidCode in (:epcs)  COLLATE NOCASE ")
    List<AssetInspectionData.DataListBean> getAllByRfid(List<String> epcs);

    @Query("SELECT * FROM AssetInspectionDetail WHERE materialCode = (:barCode)  COLLATE NOCASE ")
    List<AssetInspectionData.DataListBean> getAllByBarcode(String barCode);

    @Query("SELECT * FROM AssetInspectionDetail WHERE inspectionDetailsId=(:inspectionDetailsId)")
    AssetInspectionData.DataListBean getOneByIds(int inspectionDetailsId);

    //更新部分字段
    @Query("Update AssetInspectionDetail set inspectionResult=(:inspectionResult),inspectionDate =(:inspectionDate) where inspectionDetailsId in (:ids)")
    void update(List<String> ids, String inspectionResult, String inspectionDate);

    @Query("DELETE FROM AssetInspectionDetail")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<AssetInspectionData.DataListBean> dataListBeans);

    @Delete
    void delete(AssetInspectionData.DataListBean dataListBean);

    @Update
    void update(AssetInspectionData.DataListBean... dataListBeans);

}
