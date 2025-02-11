package com.victor.base.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.victor.base.data.entity.AllocateData;
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
public interface AllocateMaterialDao {
    @Query("SELECT * FROM AllocateMaterial where allocateId=(:allocateId)")
    List<AllocateData.AllocateMaterial> getAll(int allocateId);

    @Query("DELETE FROM AllocateMaterial")
    void deleteAll();

    @Query("DELETE FROM AllocateMaterial where allocateId=(:allocateId)")
    void deleteByAllocateId(int allocateId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(AllocateData.AllocateMaterial... allocateMaterials);
}
