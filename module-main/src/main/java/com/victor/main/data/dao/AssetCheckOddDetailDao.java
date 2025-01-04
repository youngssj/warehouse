package com.victor.main.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.victor.main.data.entity.AssetCheckData;

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
public interface AssetCheckOddDetailDao {
    @Query("SELECT * FROM AssetCheckDetail where batchNumber=(:batchNumber)")
    List<AssetCheckData.DataListBean> getAll(String batchNumber);

    @Query("SELECT * FROM AssetCheckDetail where datetime(checkDate) between datetime((:lastDate)) and datetime(CURRENT_TIMESTAMP,'localtime')")
    List<AssetCheckData.DataListBean> getAllByDate(String lastDate);

    @Query("SELECT * FROM AssetCheckDetail WHERE rfidCode in (:epcs)  COLLATE NOCASE ")
    List<AssetCheckData.DataListBean> getAllByRfid(List<String> epcs);

    @Query("SELECT * FROM AssetCheckDetail WHERE materialCode = (:barCode)  COLLATE NOCASE ")
    List<AssetCheckData.DataListBean> getAllByBarcode(String barCode);

    //更新部分字段
    @Query("Update AssetCheckDetail set checkResult=(:checkResult),checkDate =(:checkDate) where checkDetailId in (:ids)")
    void update(List<String> ids, String checkResult, String checkDate);

    @Query("DELETE FROM AssetCheckDetail")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(AssetCheckData.DataListBean... dataListBeans);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<AssetCheckData.DataListBean> dataListBeans);

    @Delete
    void delete(AssetCheckData.DataListBean dataListBean);

    @Update
    void update(AssetCheckData.DataListBean... dataListBeans);

}
