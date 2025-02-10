package com.victor.base.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.victor.base.data.entity.OutboundData;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface OutboundDataDao {
    @Query("SELECT * FROM OutboundData limit (:offset),(:limit)")
    Maybe<List<OutboundData>> getAll(int offset, int limit);

    @Query("SELECT * FROM OutboundData where finished=1 and datetime(checkDate) between datetime((:lastDate)) and datetime(CURRENT_TIMESTAMP,'localtime')")
    List<OutboundData> getFinishedByDate(String lastDate);

    @Query("SELECT * FROM OutboundData WHERE outId=(:outId)")
    OutboundData getOneById(int outId);

    @Query("DELETE FROM OutboundData")
    void deleteAll();

    @Query("DELETE FROM OutboundData where outId=(:outId)")
    void deleteById(int outId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(OutboundData... outboundDatas);
}
