package com.victor.base.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.victor.base.data.entity.MaterialsData;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface MaterialsDataDao {
    @Query("SELECT * FROM MaterialsData " +
            "where (materialStatus=:materialStatus or (:materialStatus)='' COLLATE NOCASE)" +
            "and (materialName like '%' || :materialName || '%' or (:materialName)='' COLLATE NOCASE)" +
            "and (rfidCode like '%' || :rfidCode || '%' or (:rfidCode)='' COLLATE NOCASE)" +
            "limit :offset, :limit")
    Maybe<List<MaterialsData>> getAll(int offset, int limit, String materialStatus, String materialName, String rfidCode);

    @Query("SELECT * FROM MaterialsData WHERE materialId=(:materialId)")
    MaterialsData getOneById(int materialId);

    @Query("DELETE FROM MaterialsData")
    void deleteAll();

    @Query("DELETE FROM MaterialsData where materialId=(:materialId)")
    void deleteById(int materialId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(MaterialsData... materialsData);
}
