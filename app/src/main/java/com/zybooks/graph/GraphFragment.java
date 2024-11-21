package com.zybooks.graph;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.NumberFormat;
import java.util.List;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GraphFragment extends Fragment {
    List<WeightData> wd;
    WeightDataDatabase weightDataDB;
    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[0]);
    GraphView chart;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graph, container, false);

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

        weightDataDB = Room.databaseBuilder(requireContext(), WeightDataDatabase.class, "WeightDataDB")
                .addCallback(myCallBack).build();
        chart = view.findViewById(R.id.chart);
        chart.addSeries(series);
        series.setTitle("Weight (lbs)");

        chart.getLegendRenderer().setVisible(true);
        chart.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMinimumIntegerDigits(2);
        numberFormat.setMaximumIntegerDigits(4);
        chart.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(numberFormat,numberFormat));
        series.setDrawDataPoints(true);
        chart.getGridLabelRenderer().setHorizontalAxisTitle("Earliest to Latest");
        chart.setBackgroundColor(getResources().getColor(R.color.white));



        chart.getViewport().setScalable(true);
        chart.getViewport().setScrollable(true);

        chart.getViewport().setXAxisBoundsManual(true);
        chart.getViewport().setMinX(0);
        chart.getViewport().setMaxX(4);


        getOrderedListInBackground();




        return view;
    }

    private void getOrderedListInBackground() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(() -> {
            //background task
            wd = weightDataDB.getWeightDataDAO().getDateASCWeightData();

            //on finishing task
            handler.post(() -> {

                series.resetData(getData());


                //Toast.makeText(getContext(), ""+printData, Toast.LENGTH_LONG).show();
            });
        });
    }


    private DataPoint[] getData() {
        DataPoint[] dp = new DataPoint[wd.size()];
        Integer a = wd.size();
        String a1 = a.toString();
        Toast.makeText(getContext(),a1,Toast.LENGTH_LONG).show();
        int index = 0;
        for (WeightData w : wd) {
            dp[index] = new DataPoint(index, (double) w.getWeight());
            index++;
        }

        return dp;
    }
}
