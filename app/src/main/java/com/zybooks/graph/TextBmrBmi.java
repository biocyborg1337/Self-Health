package com.zybooks.graph;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class TextBmrBmi extends Fragment {
    private final StringBuilder sb = new StringBuilder();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_text_bmr_bmi, container, false);
        TextView text1 = view.findViewById(R.id.textView3);
        TextView text2 = view.findViewById(R.id.textView2);


        double a = getArguments() != null ? getArguments().getDouble("br1") : 0;
        String b = Double.toString(a);
        sb.append("Daily Calorie Need: ").append(b);
        sb.append(" cal.\n");

        double a1 = getArguments().getDouble("abmi");
        String b1 = Double.toString(a1);
        sb.append("Body Mass Index: ").append(b1).append(" kg/m^2");

        String concat = sb.toString();
        text1.setText(concat);
        if (a1 >= 18 && a1 <= 25) {
            String healthyBMI = "You are within the Healthy range of BMI!\n\n\n\n Warning: BMI isn't the only significant metric in health so be sure to refer to other metrics before making any changes.";
            text2.setText(healthyBMI);
        } else if (a1 < 18) {
            String underweightBMI = "You are underweight, try your best to make lifestyle changes.\n\n\n\n Warning: BMI isn't the only significant metric in health so be sure to refer to other metrics before making any changes.";
            text2.setText(underweightBMI);
        } else if (a1 > 25 && a1 <= 30) {
            String overweightBMI = "You are overweight but" +
                    " if you are active, you may still be healthy.\n\n\n\n Warning: BMI isn't the only significant metric in health so be sure to refer to other metrics before making any changes.";
            text2.setText(overweightBMI);
        } else {
            String obeseBMI = "You are obese, try to make lifestyle changes.\n\n\n\n Warning: BMI isn't the only significant metric in health so be sure to refer to other metrics before making any changes.";
            text2.setText(obeseBMI);
        }


        sb.setLength(0);
        return view;
    }
}