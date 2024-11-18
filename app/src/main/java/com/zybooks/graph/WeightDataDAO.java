package com.zybooks.graph;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WeightDataDAO {

    @Insert
    void addWeightData(WeightData weightData);

    @Update
    void updateWeightData(WeightData weightData);

    @Delete
    void deleteWeightData(WeightData weightData);

    @Query("select * from `weight chart`")
    List<WeightData> getAllWeightData();

    @Query("select * from `weight chart` Order by date ASC")
    List<WeightData> getOAllWeightData();

    @Query("DELETE FROM `weight chart`")
    void deleteAllwd();

    @Query("select * from `weight chart` where id ==:id")
    WeightData getWeightData(int id);


}
