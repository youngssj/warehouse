package com.victor.base.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.victor.base.data.entity.TakeStockDetail;

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
public interface ElecMateriallDao {
    @Query("SELECT * FROM ElecMaterialListDTO where checkId=(:checkId)")
    List<TakeStockDetail.ElecMaterialListDTO> getAll(String checkId);

    @Query("SELECT * FROM ElecMaterialListDTO where datetime(checkDate) between datetime((:lastDate)) and datetime(CURRENT_TIMESTAMP,'localtime')")
    List<TakeStockDetail.ElecMaterialListDTO> getAllByDate(String lastDate);

    @Query("SELECT * FROM ElecMaterialListDTO WHERE rfidCode in (:epcs)  COLLATE NOCASE ")
    List<TakeStockDetail.ElecMaterialListDTO> getAllByRfid(List<String> epcs);

    @Query("SELECT * FROM ElecMaterialListDTO WHERE materialCode = (:barCode)  COLLATE NOCASE ")
    List<TakeStockDetail.ElecMaterialListDTO> getAllByBarcode(String barCode);

    //更新部分字段
    @Query("Update ElecMaterialListDTO set checkResult=(:checkResult),checkDate =(:checkDate) where checkDetailId in (:ids)")
    void update(List<String> ids, String checkResult, String checkDate);

    @Query("DELETE FROM ElecMaterialListDTO")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(TakeStockDetail.ElecMaterialListDTO... dataListBeans);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TakeStockDetail.ElecMaterialListDTO> dataListBeans);

    @Delete
    void delete(TakeStockDetail.ElecMaterialListDTO dataListBean);

    @Update
    void update(TakeStockDetail.ElecMaterialListDTO... dataListBeans);

}
