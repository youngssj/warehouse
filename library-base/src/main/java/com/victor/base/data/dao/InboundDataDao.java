package com.victor.base.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.victor.base.data.entity.InboundData;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface InboundDataDao {
    @Query("SELECT * FROM InboundData limit (:offset),(:limit)")
    Maybe<List<InboundData>> getAll(int offset, int limit);

    @Query("SELECT * FROM InboundData where finished=1 and datetime(checkDate) between datetime((:lastDate)) and datetime(CURRENT_TIMESTAMP,'localtime')")
    List<InboundData> getFinishedByDate(String lastDate);

    @Query("SELECT * FROM InboundData WHERE inId=(:inId)")
    InboundData getOneById(int inId);

    @Query("DELETE FROM InboundData")
    void deleteAll();

    @Query("DELETE FROM InboundData where inId=(:inId)")
    void deleteById(int inId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(InboundData... inventoryDatas);
}
