
package com.zybooks.graph;

import android.os.Build;

import androidx.room.TypeConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class DateTypeConverter {
    @TypeConverter
    public static LocalDate toDate(Integer dateInteger) {
        LocalDate dt = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dt = LocalDate.now();
            int Year = dt.getYear();
            return dateInteger == null ? null : LocalDate.ofYearDay(Year, dateInteger);
        }
        return null;
    }

    @TypeConverter
    public static Integer fromDate(LocalDate date) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return date == null ? null : date.getDayOfYear();
        }
        return -1;
    }
}
