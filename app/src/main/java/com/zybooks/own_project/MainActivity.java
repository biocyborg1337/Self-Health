package com.zybooks.own_project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    private TextView app, weight, height, result;
    private ImageView logo;
    private EditText poundsvalue, feetsvalue, bmiresult;
    private CheckBox female, masculine;
    private Button bmi, idealweight, erase;
    double pi = 0, piup = 0, pidown = 0, f1 = 2.25, f2 = 45, m1 = 2.7, m2 = 47.75;
    boolean flag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = (TextView) findViewById(R.id.tvTitulo);
        weight = (TextView) findViewById(R.id.weight);
        height =  (TextView) findViewById(R.id.height);
        result = (TextView)  findViewById(R.id.tvResult);
        logo = (ImageView) findViewById(R.id.logo);
        poundsvalue = (EditText) findViewById(R.id.weightvalue);
        feetsvalue = (EditText) findViewById(R.id.heightvalue);
        bmiresult = (EditText) findViewById(R.id.etResult);
        female = (CheckBox) findViewById(R.id.fem);
        masculine = (CheckBox) findViewById(R.id.mas);
        bmi = (Button) findViewById(R.id.buttonbmi);
        idealweight = (Button) findViewById(R.id.buttonideal);
        erase = (Button) findViewById(R.id.buttonerase);
    }


    public  void  bmi(View view) {

        if (poundsvalue.getText().toString().trim().length() == 0) {
            poundsvalue.requestFocus();

            Toast.makeText(this, "Please add the weight.", Toast.LENGTH_LONG).show();

        } else if (feetsvalue.getText().toString().trim().length() == 0) {
            feetsvalue.requestFocus();

            Toast.makeText(this, "Please add the height.", Toast.LENGTH_LONG).show();
        } else {

            float kg = Float.parseFloat(poundsvalue.getText().toString());
            float m = Float.parseFloat(feetsvalue.getText().toString());
            float bm = (kg / (m * m));

            if (bm < 18.5) {
                Toast.makeText(this,"Your bmi is:" + bm + "\n" + "Under weight", Toast.LENGTH_LONG).show();
                bmiresult.setText(String.valueOf((bm)));

            } else {
                if (bm < 24.9) {
                    Toast.makeText(this,"Your bmi is:" + bm + "\n" + "Healthy weight", Toast.LENGTH_LONG).show();
                    bmiresult.setText(String.valueOf((bm)));

                }else {
                    if (bm < 29.9) {
                        Toast.makeText(this,"Your bmi is:" + bm + "\n" + "Over weight", Toast.LENGTH_LONG).show();
                        bmiresult.setText(String.valueOf((bm)));

                    }else {
                        if (bm <34.9){
                            Toast.makeText(this,"Your bmi is:" + bm + "\n" + "Obesity", Toast.LENGTH_LONG).show();
                            bmiresult.setText(String.valueOf((bm)));
                        }


                    }
                }
            }

        }
    }

    public void erase(View view) {
        poundsvalue.setText("");
        poundsvalue.requestFocus();
        feetsvalue.setText("");
        bmiresult.setText("");
        pi = 0;
        piup = 0;
        pidown = 0;
        female.setEnabled(true);
        masculine.setEnabled(true);
        female.setChecked(false);
        masculine.setChecked(false);
        flag=true;



    }


    public void idealweight (View view){

        float kg = Float.parseFloat(poundsvalue.getText().toString());
        float m = Float.parseFloat(feetsvalue.getText().toString());

        if (poundsvalue.getText().toString().trim().length() == 0){
            poundsvalue.requestFocus();

            Toast.makeText(this, "Please add the weight:", Toast.LENGTH_LONG).show();
            flag = false;

        } else if(feetsvalue.getText().toString().trim().length() == 0){
            feetsvalue.requestFocus();

            Toast.makeText(this,"Please add the Height:", Toast.LENGTH_LONG).show();
            flag = false;

        } else if (masculine.isChecked() == false && female.isChecked() == false){
            Toast.makeText(this, "Please select a gender:", Toast.LENGTH_LONG).show();
            flag = false;

        }
        if(female.isChecked() == true){

            masculine.setEnabled(false);
            pi = ((((100 * m ) - 152.4) / 2.54) * f1) + f2;

        }else  if(masculine.isChecked() == true) {

            female.setEnabled(false);
            pi = ((((100 * m ) - 152.4) / 2.54) * m1) + m2;
        }
        if (flag) {
            piup = (pi * 0.10) + pi;
            Toast.makeText(this, "Ideal weight result" + pi + "\n", Toast.LENGTH_LONG).show();
            bmiresult.setText(String.valueOf((pi)));
            pidown = (pi - (pi * 0.10));

            if (kg > piup) {
                Toast.makeText(this, "You are over your ideal weight", Toast.LENGTH_LONG).show();

            } else if (kg <= piup && kg >= pidown) {

                Toast.makeText(this, "You are at your ideal weight", Toast.LENGTH_LONG).show();

            } else if (kg < pidown) {

                Toast.makeText(this, "You are below your ideal weight", Toast.LENGTH_LONG).show();


            }
        }

    }

    public void  onCheckboxClickedfem(View view) {

        female.setChecked(true);
        masculine.setChecked(false);
        Toast.makeText(this, "Female.", Toast.LENGTH_LONG).show();
    }

    public void  onCheckboxClickedmas(View view) {

        masculine.setChecked(true);
        female.setChecked(false);
        Toast.makeText(this, "Please select a gender:", Toast.LENGTH_LONG).show();
    }
}



