package com.zybooks.graph;

import android.content.Context;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import java.util.ArrayList;
import java.util.Date;
import  java.util.List;
/* , exportSchema = false*/
@Database(entities = {WeightData.class},version = 2,
        exportSchema = false
)
public abstract class WeightDataRepo extends RoomDatabase {

    public abstract WeightDataDAO getWeightDataDAO();

}


