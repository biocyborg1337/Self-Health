package com.zybooks.graph;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.os.Handler;
import android.os.Looper;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class LogText extends Fragment {
    TextView text;
    List<WeightData> wd;
    WeightDataRepo weightDataDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_text, container, false);

       text = view.findViewById(R.id.fragLogText);
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

        getWeightDataListInBackground();
        text.setMovementMethod(new ScrollingMovementMethod());


        // Inflate the layout for this fragment
        return view;
    }
    public void getWeightDataListInBackground() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(() -> {
            //background task
            wd = weightDataDB.getWeightDataDAO().getOAllWeightData();

            //on finishing task
            handler.post(() -> {
                StringBuilder sb = new StringBuilder();
                for (WeightData w : wd) {
                    sb.append(" ").append(w.getDate()).append(":").append(w.getWeight()).append(" lbs");
                    sb.append("\n");
                }
                String printData = sb.toString();
                text.setText(printData);
                //Toast.makeText(getContext(), ""+printData, Toast.LENGTH_LONG).show();
            });
        });

    }
}