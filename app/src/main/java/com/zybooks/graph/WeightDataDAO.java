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
    public void addWeightData(WeightData weightData);

    @Update
    public void updateWeightData(WeightData weightData);

    @Delete
    public void deleteWeightData(WeightData weightData);

    @Query("select * from `weight chart`")
    public List<WeightData> getAllWeightData();

    @Query("select * from `weight chart` Order by date ASC")
    public List<WeightData> getOAllWeightData();

    @Query("DELETE FROM `weight chart`")
    public void deleteAllwd();

    @Query("select * from `weight chart` where id ==:id")
    public WeightData getWeightData(int id);


}
