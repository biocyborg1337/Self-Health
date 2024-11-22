package com.zybooks.graph;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class WeightHistory extends Fragment {
    private EditText mWeightData;
    private ImageView mDateData;
    String sDate;
    TextView printDB;
    WeightDataDatabase weightDataDB;
    List<WeightData> weightDataList;
    String year2,month2,day2;
    int year, month, day;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_weighthistory, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle saveInstanceState) {
        mWeightData = view.findViewById(R.id.weighthistory_weight);
        mDateData = view.findViewById(R.id.nf_calendar);
        Button whSave = view.findViewById(R.id.weighthistory_save);
        Button whPrint = view.findViewById(R.id.weighthistory_print);
        Button whClear = view.findViewById(R.id.weighthistory_clear);
        printDB = view.findViewById(R.id.weighthistory_show);
        Button whDelete = view.findViewById(R.id.weighthistory_delete);
        Button whHelp = view.findViewById(R.id.weighthistory_help);


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

        InitCalendar();



        mDateData.setOnClickListener(view1 -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(mDateData.getContext(), (datePicker, year1, month1, day1) -> {
                String mo, da, ye;
                month1++;

                ye = String.format(Locale.US, "%d", year1);
                mo = String.format(Locale.US, "%2d", month1);
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
                Log.d("Weight Data", "Not Numeric");
            }

            WeightData wd1 = new WeightData(sDate, numWeight);

            addWeightDataInBackground(wd1);
            mWeightData.setText("");
            InitCalendar();
        });
        whPrint.setOnClickListener(v -> getWeightDataListInBackground());
        whClear.setOnClickListener(v -> clearWeightDataListInBackground());
        whDelete.setOnClickListener(v->deleteSpecifiedDateInBackground(sDate));
        whHelp.setOnClickListener(v->makeDialog());


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



    }
    private void deleteSpecifiedDateInBackground(String date){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(()->{
            weightDataDB.getWeightDataDAO().deleteSpecificDate(sDate);
            handler.post(() -> Toast.makeText(getContext(),sDate+" removed!", Toast.LENGTH_SHORT).show());
        });
    }
    private void InitCalendar(){
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        year2 = String.format(Locale.US, "%d", year);
        month2= String.format(Locale.US, "%2d", month+1);
        day2 = String.format(Locale.US, "%2d", day);

        if(day < 10){
            day2 = String.format(Locale.US, "%2d",day).replace(' ','0');
        }
        if(month < 10){
            month2 = String.format(Locale.US, "%2d",month).replace(' ','0');
        }
        sDate = year2+"-"+month2+"-"+day2;
    }
    private void makeDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Instructions");
        alertDialog.setMessage(getResources().getString(R.string.weightHistory_instruction));
        alertDialog.setIcon(R.drawable.logo);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog.show();
    }


}

