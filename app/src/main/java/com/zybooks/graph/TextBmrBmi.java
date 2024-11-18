package com.zybooks.graph;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**/
public class TextBmrBmi extends Fragment {
    private TextView text1, text2;
    private String concat;
    private StringBuilder sb = new StringBuilder();
    private double bmrAL;
    private double bmi;

    private String healthyBMI = "You are within the Healthy range of BMI! Alert: BMI cannot tell you about your body composition.";
    private String overweightBMI = "You are overweight if you have a normal build." +
            "If you are active, you may still be healthy. Alert: BMI cannot tell you about your body composition.";
    private String obeseBMI = "You are obese, try your best to make lifestyle changes. Alert: BMI cannot tell you about your body composition.";
    private String underweightBMI = "You are underweight, try your best to make lifestyle changes. Alert: BMI cannot tell you about your body composition.";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_text_bmr_bmi, container, false);
        text1 = view.findViewById(R.id.textView3);
        text2 = view.findViewById(R.id.textView2);


        Double a = getArguments().getDouble("br1");
        String b = a.toString();
        sb.append("Daily Calorie Need: " + b);
        sb.append(" cal.\n");

        Double a1 = getArguments().getDouble("abmi");
        String b1 = a1.toString();
        sb.append("Body Mass Index: " + b1 + " kg/m^2");

        concat = sb.toString();
        text1.setText(concat);
        if (a1 >= 18 && a1 <= 25) {
            text2.setText(healthyBMI);
        } else if (a1 < 18) {
            text2.setText(underweightBMI);
        } else if (a1 > 25 && a1 <= 30) {
            text2.setText(overweightBMI);
        } else {
            text2.setText(obeseBMI);
        }


        sb.setLength(0);
        return view;
    }
}