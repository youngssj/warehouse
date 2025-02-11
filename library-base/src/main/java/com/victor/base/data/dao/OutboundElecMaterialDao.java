package com.victor.base.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.victor.base.data.entity.OutboundData;

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
public interface OutboundElecMaterialDao {
    @Query("SELECT * FROM OutboundElecMaterial where outId=(:outId)")
    List<OutboundData.OutboundElecMaterial> getAll(int outId);

    @Query("DELETE FROM OutboundElecMaterial")
    void deleteAll();

    @Query("DELETE FROM OutboundElecMaterial where outId=(:outId)")
    void deleteByOutId(int outId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(OutboundData.OutboundElecMaterial... dataListBeans);
}
