package com.victor.base.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.victor.base.data.entity.MovementData;

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
public interface MovementElecMaterialDao {
    @Query("SELECT * FROM MovementElecMaterial where moveId=(:moveId)")
    List<MovementData.MovementElecMaterial> getAll(int moveId);

    @Query("DELETE FROM MovementElecMaterial")
    void deleteAll();

    @Query("DELETE FROM MovementElecMaterial where moveId=(:moveId)")
    void deleteByMoveId(int moveId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(MovementData.MovementElecMaterial... dataListBeans);
}
