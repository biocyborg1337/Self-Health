package com.zybooks.graph;

import android.app.DatePickerDialog;
import android.os.Build;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class NumberFragment extends Fragment {
    private EditText mWeightData;
    private ImageView mDateData;
    String sDate;
    LocalDate lDate;
    TextView printDB;
    WeightDataRepo weightDataDB;
    //LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[0]);
    List<WeightData> weightDataList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_number, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle saveInstanceState) {
        mWeightData = view.findViewById(R.id.numberfragment_weight);
        mDateData = view.findViewById(R.id.nf_calendar);
        Button nfSave = view.findViewById(R.id.numberfragment_save);
        Button nfPrint = view.findViewById(R.id.numberfragment_print);
        Button nfClear = view.findViewById(R.id.numberfragment_clear);
        printDB = view.findViewById(R.id.numberfragment_show);
        /*Button nfSort = getView().findViewById(R.id.numberfragment_sort);
         chart = (GraphView) view.findViewById(R.id.chart);
         chart.addSeries(series);*/


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
                .addCallback(myCallBack).fallbackToDestructiveMigration().build();

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        /*
        mDateData.setOnClickListener(view-> {

            DatePickerDialog datePickerDialog = new DatePickerDialog(mDateData.getContext(),(datePicker, year1, month1, day1) -> {
                month1 = month1 + 1;
                String mo = String.format("%32d", month1);
                String da = String.format("%32d",day1);
                sDate = year1+"-"+mo+"-"+da;

            },year,month,day);
            datePickerDialog.show();
        });*/

        mDateData.setOnClickListener(view1 -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(mDateData.getContext(), (datePicker, year1, month1, day1) -> {
                String mo, da, ye;
                month1 = month1+1;
                ye = String.format(Locale.US, "%d", year1);
                mo = String.format(Locale.US, "%d", month1);
                da = String.format(Locale.US, "%d", day1);

                if (day1 < 10) {
                    da = String.format(Locale.US, "%2d", day1).replace(' ', '0');
                }
                if (month1 < 10) {
                    mo = String.format(Locale.US, "%2d", month1).replace(' ', '0');
                }
                sDate = ye + "-" + mo + "-" + da;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    lDate = LocalDate.parse(sDate, dtf);
                }
            }, year, month, day);
            datePickerDialog.show();

        });
        /*database logic here*/


        nfSave.setOnClickListener(v -> {
            String weightStr = mWeightData.getText().toString();
            int numWeight = 0;
            try {
                numWeight = Integer.parseInt(weightStr);
            } catch (Exception e) {
                Log.d("Weight Data", "Not Numeric");
            }
            if(lDate == null) if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                lDate = LocalDate.now();
            }
            WeightData wd1 = new WeightData(lDate, numWeight);

            addWeightDataInBackground(wd1);
            mWeightData.setText("");
        });
        nfPrint.setOnClickListener(v -> {
                    LogText logText = new LogText();
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction().replace(R.id.fragmentContainerView, logText)
                            .commit();
                });
        nfClear.setOnClickListener(v -> clearWeightDataListInBackground());
        /*
        nfSort.setOnClickListener(v->{
            getOWeightDataListInBackground();
        });*/


    }

    public void addWeightDataInBackground(WeightData weightData) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(() -> {
            //background task
            weightDataDB.getWeightDataDAO().addWeightData(weightData);

            //on finishing task
            handler.post(() -> Toast.makeText(getContext(), "Added to Database", Toast.LENGTH_SHORT).show());
        });

    }

    public void getWeightDataListInBackground() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(() -> {
            //background task
            weightDataList = weightDataDB.getWeightDataDAO().getOAllWeightData();

            //on finishing task
            handler.post(() -> {
                StringBuilder sb = new StringBuilder();
                for (WeightData w : weightDataList) {
                    sb.append(" ").append(w.getDate()).append(":").append(w.getWeight()).append(" lbs");
                    sb.append("\n");
                }
                String printData = sb.toString();
                printDB.setText(printData);
                //Toast.makeText(getContext(), ""+printData, Toast.LENGTH_LONG).show();
            });
        });

    }

    public void clearWeightDataListInBackground() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(() -> {
            //background task

            weightDataDB.getWeightDataDAO().deleteAllwd();
            /*weightDataDB = Room.databaseBuilder(getContext(),
                            WeightDataRepo.class, "WeightDataDB")
                    .fallbackToDestructiveMigration()
                    .build(); */

            //on finishing task
            handler.post(() -> Toast.makeText(getContext(), "Database cleared!", Toast.LENGTH_SHORT).show());
        });

    }/*
    public void getOWeightDataListInBackground() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                //background task
                weightDataList = weightDataDB.getWeightDataDAO().getOAllWeightData();



                //on finishing task
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        series.resetData(getData());
                        //Toast.makeText(getContext(), ""+printData, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
    private DataPoint[] getData(){

        DataPoint[] dp = new DataPoint[weightDataList.size()];
        int index = 0;
        for(WeightData w: weightDataList){
            dp[index] = new DataPoint((double)index,(double)w.getWeight());
            index++;
        }

        return dp;
    }*/

}

