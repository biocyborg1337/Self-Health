package com.zybooks.graph;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.Locale;

public class bmiCalculator extends Fragment {
    //private ImageView logo;
    private EditText poundsvalue, feetsvalue, bmiresult, aage;
    private RadioButton female, masculine, sed, sal, mal, al, va;
    private Double maleBmr, womanBmr, maleABmr, womanABmr, adultBmi;
    CalcBmrBmi cbb = new CalcBmrBmi();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bmi_calculator, container, false);

        // Initialize the UI elements
        poundsvalue = view.findViewById(R.id.weightvalue);
        feetsvalue = view.findViewById(R.id.heightvalue);
        aage = view.findViewById(R.id.AGEvalue);
        bmiresult = view.findViewById(R.id.etResult);
        Button bmi = view.findViewById(R.id.buttonbmi);
        Button idealweight = view.findViewById(R.id.buttonideal);
        Button erase = view.findViewById(R.id.buttonerase);
        female = view.findViewById(R.id.fem);
        masculine = view.findViewById(R.id.mas);
        sed = view.findViewById(R.id.sed);
        sal = view.findViewById(R.id.sal);
        mal = view.findViewById(R.id.mal);
        al = view.findViewById(R.id.al);
        va = view.findViewById(R.id.va);

        // BMI button click listener
        bmi.setOnClickListener(v -> {
            int height, age;
            Double weight;
            String pv = poundsvalue.getText().toString();
            String fs = feetsvalue.getText().toString();
            String ag = aage.getText().toString();
            poundsvalue.setText("");
            feetsvalue.setText("");
            aage.setText("");

            try {
                weight = Double.parseDouble(pv);
                height = Integer.parseInt(fs);
                age = Integer.parseInt(ag);
            } catch (Exception e) {
                Log.d("BMI button", "Invalid input");
                return; // exit on invalid input
            }

            // Common BMI calculation using pounds and inches
            adultBmi = cbb.bmiC(weight, height);

            if (female.isChecked()) {
                // Calculate BMR for female
                womanBmr = cbb.wbmrC(weight, height, age);
                // Adjust BMR based on activity
                womanABmr = adjustBmrByActivity(womanBmr);
                // Passing results to another fragment
                passResultsToFragment(womanABmr, adultBmi);
            } else if (masculine.isChecked()) {
                // Calculate BMR for male
                maleBmr = cbb.mbmrC(weight, height, age);
                // Adjust BMR based on activity
                maleABmr = adjustBmrByActivity(maleBmr);
                // Passing results to another fragment
                passResultsToFragment(maleABmr, adultBmi);
            }

            // Reset checkboxes after calculation
            resetCheckBoxes();
        });

        // Ideal Weight button click listener
        idealweight.setOnClickListener(v -> {
            int height = 0;
            String fs = feetsvalue.getText().toString();
            //feetsvalue.setText("");

            try {
                height = Integer.parseInt(fs);
            } catch (Exception e) {
                Log.d("IdealWeight button", "Invalid height");
            }

            if (height > 0) {
                String idealWeightRange = calculateIdealWeightBasedOnGenderAndActivity(height);
                bmiresult.setText(idealWeightRange);
            } else {
                bmiresult.setText("Please enter a valid height.");
            }
        });

        erase.setOnClickListener(v -> erase());

        return view;
    }

    private void erase() {
        resetCheckBoxes();
        poundsvalue.setText("");
        feetsvalue.setText("");
        aage.setText("");
        bmiresult.setText("");
    }

    // Method to adjust BMR based on the selected activity level
    private double adjustBmrByActivity(double baseBmr) {
        if (sed.isChecked()) {
            return Math.ceil(baseBmr * 1.2); // Sedentary
        } else if (sal.isChecked()) {
            return Math.ceil(baseBmr * 1.375); // Slightly active
        } else if (mal.isChecked()) {
            return Math.ceil(baseBmr * 1.55); // Moderately active
        } else if (al.isChecked()) {
            return Math.ceil(baseBmr * 1.725); // Active
        } else if (va.isChecked()) {
            return Math.ceil(baseBmr * 1.9); // Very active
        }
        return baseBmr; // Default if no activity level is checked
    }

    // Method to pass results to another fragment
    private void passResultsToFragment(double adjustedBmr, double bmi) {
        Bundle bundle = new Bundle();
        bundle.putDouble("br1", adjustedBmr);
        bundle.putDouble("abmi", bmi);

        TextBmrBmi textBmrBmi = new TextBmrBmi();
        textBmrBmi.setArguments(bundle);

        // Replace the current fragment with the result fragment
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, textBmrBmi).commit();
    }

    // Method to reset checkboxes after calculation
    private void resetCheckBoxes() {
        female.setChecked(false);
        masculine.setChecked(false);
        sed.setChecked(false);
        sal.setChecked(false);
        mal.setChecked(false);
        al.setChecked(false);
        va.setChecked(false);
        
    }

    // Method to calculate ideal weight range based on height (in inches)
    private String calculateIdealWeightBasedOnGenderAndActivity(int heightInInches) {
        // Calculate the minimum and maximum weight in pounds for BMI 18.5 to 24.9
        double minWeightLbs = 18.5 * heightInInches * heightInInches / 703; // Min weight for BMI 18.5 (in lbs)
        double maxWeightLbs = 24.9 * heightInInches * heightInInches / 703; // Max weight for BMI 24.9 (in lbs)

        // Adjust weight based on gender and activity level
        if (female.isChecked()) {
            // Female adjustments based on activity
            if (sed.isChecked()) {
                minWeightLbs *= 1.05;
                maxWeightLbs *= 1.05;
            } else if (sal.isChecked()) {
                minWeightLbs *= 1.1;
                maxWeightLbs *= 1.1;
            } else if (mal.isChecked()) {
                minWeightLbs *= 1.15;
                maxWeightLbs *= 1.15;
            } else if (al.isChecked()) {
                minWeightLbs *= 1.2;
                maxWeightLbs *= 1.2;
            } else if (va.isChecked()) {
                minWeightLbs *= 1.25;
                maxWeightLbs *= 1.25;
            }
        } else if (masculine.isChecked()) {
            // Male adjustments based on activity
            if (sed.isChecked()) {
                minWeightLbs *= 1.1;
                maxWeightLbs *= 1.1;
            } else if (sal.isChecked()) {
                minWeightLbs *= 1.15;
                maxWeightLbs *= 1.15;
            } else if (mal.isChecked()) {
                minWeightLbs *= 1.2;
                maxWeightLbs *= 1.2;
            } else if (al.isChecked()) {
                minWeightLbs *= 1.25;
                maxWeightLbs *= 1.25;
            } else if (va.isChecked()) {
                minWeightLbs *= 1.3;
                maxWeightLbs *= 1.3;
            }
        }

        return String.format(Locale.US, "%.1f - %.1f lbs", minWeightLbs, maxWeightLbs);
    }
}
