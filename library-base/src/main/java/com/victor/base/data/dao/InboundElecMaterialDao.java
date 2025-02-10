package com.victor.base.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.victor.base.data.entity.InboundData;

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
public interface InboundElecMaterialDao {
    @Query("SELECT * FROM InboundElecMaterial where inId=(:inId)")
    List<InboundData.InboundElecMaterial> getAll(int inId);

    @Query("DELETE FROM InboundElecMaterial")
    void deleteAll();

    @Query("DELETE FROM InboundElecMaterial where inId=(:inId)")
    void deleteByInId(int inId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(InboundData.InboundElecMaterial... dataListBeans);
}
