package com.victor.base.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.victor.base.data.entity.AllocateData;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface AllocateDataDao {
    @Query("SELECT * FROM AllocateData limit (:offset),(:limit)")
    Maybe<List<AllocateData>> getAll(int offset, int limit);

    @Query("SELECT * FROM AllocateData where finished=1 and datetime(checkDate) between datetime((:lastDate)) and datetime(CURRENT_TIMESTAMP,'localtime')")
    List<AllocateData> getFinishedByDate(String lastDate);

    @Query("SELECT * FROM AllocateData WHERE id=(:allocateId)")
    AllocateData getOneById(int allocateId);

    @Query("DELETE FROM AllocateData")
    void deleteAll();

    @Query("DELETE FROM AllocateData where id=(:allocateId)")
    void deleteById(int allocateId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(AllocateData... allocateDatas);
}
