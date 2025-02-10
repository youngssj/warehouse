package com.victor.base.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.victor.base.data.entity.MovementData;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface MovementDataDao {
    @Query("SELECT * FROM MovementData limit (:offset),(:limit)")
    Maybe<List<MovementData>> getAll(int offset, int limit);

    @Query("SELECT * FROM MovementData where finished=1 and datetime(checkDate) between datetime((:lastDate)) and datetime(CURRENT_TIMESTAMP,'localtime')")
    List<MovementData> getFinishedByDate(String lastDate);

    @Query("SELECT * FROM MovementData WHERE id=(:moveId)")
    MovementData getOneById(int moveId);

    @Query("DELETE FROM MovementData")
    void deleteAll();

    @Query("DELETE FROM MovementData where id=(:moveId)")
    void deleteById(int moveId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(MovementData... movementDatas);
}
