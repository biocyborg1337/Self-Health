package com.zybooks.graph;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/* , exportSchema = false*/
@Database(entities = {WeightData.class},version = 3,
        exportSchema = false
)
public abstract class WeightDataDatabase extends RoomDatabase {

    public abstract WeightDataDAO getWeightDataDAO();

}


