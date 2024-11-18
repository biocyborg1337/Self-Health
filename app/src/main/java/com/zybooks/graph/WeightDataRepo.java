package com.zybooks.graph;

import android.content.Context;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Database(entities = {WeightData.class}, version = 2
        //     autoMigrations= {@AutoMigration(from = 1,to = 2)},exportSchema = true
)
public abstract class WeightDataRepo extends RoomDatabase {

    public abstract WeightDataDAO getWeightDataDAO();

}


