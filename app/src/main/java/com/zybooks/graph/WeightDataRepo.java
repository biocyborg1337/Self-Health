package com.zybooks.graph;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {WeightData.class}, version = 2
        //     autoMigrations= {@AutoMigration(from = 1,to = 2)},exportSchema = true
)
public abstract class WeightDataRepo extends RoomDatabase {

    public abstract WeightDataDAO getWeightDataDAO();

}


