package com.zybooks.graph;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.time.LocalDate;

@Entity(tableName = "Weight Chart")
public class WeightData {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "date")
    String date;

    @ColumnInfo(name = "weight")
    Integer weight;

    public WeightData(String date, Integer weight) {
        this.date = date;
        this.weight = weight;
        this.id = 0;
    }





    public String getDate() {
        return date;
    }

    public Integer getWeight() {
        return weight;
    }

    /*
    public void setDate(String date) {
        this.date = date;
    }
    */

    public void setWeight(Integer weight) {
        this.weight = weight;
    }


}