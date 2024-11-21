package com.zybooks.graph;

import android.app.DatePickerDialog;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class WeightHistory extends Fragment {
    private EditText mWeightData;
    private ImageView mDateData;
    String sDate;

    TextView printDB;
    WeightDataDatabase weightDataDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight_history, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle saveInstanceState) {
        mWeightData = view.findViewById(R.id.weightHistory_weight);
        mDateData = view.findViewById(R.id.nf_calendar);
        Button whSave = view.findViewById(R.id.weightHistory_save);
        Button whPrint = view.findViewById(R.id.weightHistory_print);
        Button whClear = view.findViewById(R.id.weightHistory_clear);
        printDB = view.findViewById(R.id.weightHistory_show);
        Button whDelete = view.findViewById(R.id.weightHistory_delete);

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
                .addCallback(myCallBack).fallbackToDestructiveMigration().build();

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String year2,month2,day2;
        year2 = String.format(Locale.US, "%d", year);
        month2 = String.format(Locale.US, "%2d", month+1);
        day2 = String.format(Locale.US, "%2d", day);
       if (day < 10) {
            day2= String.format(Locale.US, "%2d", day).replace(' ', '0');
        }
        if (month < 10) {
            month2 = String.format(Locale.US, "%2d", month).replace(' ', '0');
        }
        sDate = year2+"-"+month2+"-"+day2;


        mDateData.setOnClickListener(view1 -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(mDateData.getContext(), (datePicker, year1, month1, day1) -> {
                String mo, da, ye;
                ye = String.format(Locale.US, "%d", year1);
                mo = String.format(Locale.US, "%2d", month1+1);
                da = String.format(Locale.US, "%2d", day1);

                if (day1 < 10) {
                    da = String.format(Locale.US, "%2d", day1).replace(' ', '0');
                }
                if (month1 < 10) {
                    mo = String.format(Locale.US, "%2d", month1).replace(' ', '0');
                }
                sDate = ye + "-" + mo + "-" + da;
            }, year, month, day);
            datePickerDialog.show();

        });


        whSave.setOnClickListener(v -> {
            String weightStr = mWeightData.getText().toString();

            int numWeight = 0;
            try {
                numWeight = Integer.parseInt(weightStr);
            } catch (Exception e) {
                Log.d("Weight Info Fragment", "Somehow not Numeric");
            }
                WeightData wd1 = new WeightData(sDate, numWeight);
                addWeightDataInBackground(wd1);

            mWeightData.setText("");
        });
        whPrint.setOnClickListener(v -> {
                    LogText logText = new LogText();
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction().replace(R.id.fragmentContainerView, logText)
                            .commit();
                });
        whClear.setOnClickListener(v -> clearWeightDataListInBackground());

        whDelete.setOnClickListener(v -> deleteSpecifiedDateInBackground());


    }

    private void addWeightDataInBackground(WeightData weightData) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(() -> {
            //background task
            weightDataDB.getWeightDataDAO().addWeightData(weightData);

            //on finishing task
            handler.post(() -> Toast.makeText(getContext(), "Added to Database", Toast.LENGTH_SHORT).show());
        });

    }



    private void clearWeightDataListInBackground() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(() -> {
            //background task

            weightDataDB.getWeightDataDAO().deleteAllwd();


            //on finishing task
            handler.post(() -> Toast.makeText(getContext(), "Database cleared!", Toast.LENGTH_SHORT).show());
        });

    }
    private void deleteSpecifiedDateInBackground(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(()->{
            weightDataDB.getWeightDataDAO().deleteSpecificDate(sDate);

            handler.post(() -> Toast.makeText(getContext(),sDate+" removed!", Toast.LENGTH_SHORT).show());
        });
    }


}

