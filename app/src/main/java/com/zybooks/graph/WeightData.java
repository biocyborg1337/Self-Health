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
public class WeightData implements Parcelable {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "date")
    @TypeConverters({DateTypeConverter.class})
    LocalDate date;

    @ColumnInfo(name = "weight")
    Integer weight;

    public WeightData(LocalDate date, Integer weight) {
        this.date = date;
        this.weight = weight;
        this.id = 0;
    }

    protected WeightData(Parcel in) {
        id = in.readInt();
        if (in.readByte() == 0) {
            weight = null;
        } else {
            weight = in.readInt();
        }
    }

    public static final Creator<WeightData> CREATOR = new Creator<WeightData>() {
        @Override
        public WeightData createFromParcel(Parcel in) {
            return new WeightData(in);
        }

        @Override
        public WeightData[] newArray(int size) {
            return new WeightData[size];
        }
    };

    public LocalDate getDate() {
        return date;
    }

    public Integer getWeight() {
        return weight;
    }

    /*
    public void setDate(LocalDate date) {
        this.date = date;
    }
    */

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        if (weight == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(weight);
        }
    }
}