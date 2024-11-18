package com.zybooks.graph;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Managing Fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        //Weight Info Button fragment manager logic
        Button btnwi = findViewById(R.id.btnWI);
        Button btnchart = findViewById(R.id.btnChart);
        Button btnBmiBmr = findViewById(R.id.lcalbutton);
        Button faq_button = findViewById(R.id.faq_button);


        btnwi.setOnClickListener(view -> fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, NumberFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("name")
                .commit());
        //Chart Button fragment manager logic

        btnchart.setOnClickListener(view -> fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, GraphFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("name")
                .commit());
        btnBmiBmr.setOnClickListener(view -> fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, bmiCalculator.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("name")
                .commit());

        faq_button.setOnClickListener(view -> fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, QAFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("name")
                .commit()
        );
    }
}