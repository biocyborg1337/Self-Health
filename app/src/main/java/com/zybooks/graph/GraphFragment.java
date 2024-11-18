package com.zybooks.graph;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GraphFragment extends Fragment {
    List<WeightData> wd;
    WeightDataRepo weightDataDB;
    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[0]);
    GraphView chart;
    Button gfSort;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graph, container, false);
        chart = view.findViewById(R.id.chart);
        gfSort = view.findViewById(R.id.gf_sort);
        chart.addSeries(series);
        RoomDatabase.Callback myCallBack = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
            }
        };

        weightDataDB = Room.databaseBuilder(requireContext(), WeightDataRepo.class, "WeightDataDB")
                .addCallback(myCallBack).build();

        gfSort.setOnClickListener(v -> getOWeightDataListInBackground());

        return view;
    }

    public void getOWeightDataListInBackground() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(() -> {
            //background task
            wd = weightDataDB.getWeightDataDAO().getOAllWeightData();


            //on finishing task
            handler.post(() -> {

                series.resetData(getData());
                //Toast.makeText(getContext(), ""+printData, Toast.LENGTH_LONG).show();
            });
        });
    }

    private DataPoint[] getData() {

        DataPoint[] dp = new DataPoint[wd.size()];
        int index = 0;
        for (WeightData w : wd) {
            dp[index] = new DataPoint(index, (double) w.getWeight());
            index++;
        }

        return dp;
    }
}
