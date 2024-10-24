Overview

HealthApp is a user-friendly calorie tracker designed to help users manage their weight goals. Whether you want to lose weight, gain weight, or maintain your current weight, HealthApp simplifies the process of tracking your daily calorie intake. The app provides a foundation for smart eating habits, with future enhancements planned to improve its functionality through food databases and personalized meal recommendations.
Features

    Calorie Goal Management
        Select your goal: Lose, Gain, or Maintain weight.
        Daily calorie target updates based on your goal and profile (age, gender, weight, height, and activity level).

    Manual Entry
        Enter foods manually along with their calorie count.

    Progress Tracker
        View your calorie intake vs. your daily target.
        Visualize weekly progress to help you stay on track.


Roadmap

Phase 1: Core Functionality (Current State)

    Users can manually input foods and calories.
    Track daily intake and monitor progress based on chosen weight goal.

Phase 2: Food Database Integration (Planned)

Integrate official APIs from food databases to pull detailed food information, including:

    USDA FoodData Central
        Comprehensive database with nutritional data on packaged and raw foods.
    Open Food Facts API
        User-contributed database with barcode scanning capabilities.
    Edamam Nutrition API
        Real-time nutrition analysis and meal recommendations.

Phase 3: Meal Recommendations and Tracking (Future)

    Provide meal recommendations based on user goals and preferences.
    Suggest foods to help users hit their calorie and macro goals.

Phase 4: Advanced Nutrition Tracking (Future)

    Track macronutrient and micronutrient intake, such as carbs, protein, fats, vitamins, and minerals.
    Ensure users meet nutritional balance for optimal health.
    Alerts if a nutrient is under or over the recommended amount.






*activity _main.xml*
Following Code:
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.zybooks.own_project.MainActivity">

    <TextView
        android:id="@+id/tvTitulo"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="18dp"
        android:layout_marginBottom="248dp"
        android:fontFamily="sans-serif-black"
        android:gravity="center"
        android:text="Ideal Weight"
        android:textColor="#8C9EFF"
        android:textSize="20dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.498"
        app:layout_constraintLeft_toLeftOf="Parent"
        app:layout_constraintRight_toRightOf="Parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.09"
        tools:ignore="MissingConstraints" />

<ImageView
        android:id="@+id/logo"
        android:layout_width="118dp"
        android:layout_height="110dp"
        android:layout_marginTop="20dp"
        android:foregroundGravity="center_horizontal|center_vertical"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.498"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/tvTitulo"
        app:srcCompat="@drawable/logo" />

    <TextView
        android:id="@+id/weight"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="35dp"
        android:layout_marginTop="55dp"
        android:fontFamily="sans-serif-black"
        android:text="Weight (lb):"
        android:textColor="#8C9EFF"
        android:textSize="20dp"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/logo" />
    <EditText
        android:id="@+id/weightvalue"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginStart="10dp"
        android:layout_marginTop="40dp"
        android:layout_marginEnd="20dp"
        android:ems="10"
        android:fontFamily="sans-serif-black"
        android:inputType="numberDecimal"
        android:minHeight="48dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toEndOf="@+id/weight"
        app:layout_constraintTop_toBottomOf="@+id/logo" />

    <TextView
        android:id="@+id/height"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="35dp"
        android:layout_marginTop="50dp"
        android:fontFamily="sans-serif-black"
        android:text="Height (fts):"
        android:textColor="#8C9EFF"
        android:textSize="20dp"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/weight" />

<EditText
        android:id="@+id/heightvalue"
        android:layout_width="wrap_content"
        android:layout_height="48dp"
        android:layout_marginTop="30dp"
        android:layout_marginEnd="15dp"
        android:ems="10"
        android:fontFamily="sans-serif-black"
        android:importantForAutofill="no"
        android:inputType="numberDecimal"
        android:minHeight="48dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="1.0"
        app:layout_constraintStart_toEndOf="@+id/weight"
        app:layout_constraintTop_toBottomOf="@+id/weightvalue"
        tools:ignore="NotSibling,SpeakableTextPresentCheck" />

    <TextView
        android:id="@+id/tvResult"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="35dp"
        android:layout_marginTop="108dp"
        android:fontFamily="sans-serif-black"
        android:text="Result:"
        android:textColor="#8C9EFF"
        android:textSize="20dp"
        app:layout_constraintEnd_toStartOf="@+id/etResult"
        app:layout_constraintHorizontal_bias="0.134"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/fem" />

<EditText
        android:id="@+id/etResult"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="96dp"
        android:layout_marginEnd="32dp"
        android:ems="11"
        android:enabled="false"
        android:fontFamily="sans-serif-black"
        android:importantForAutofill="no"
        android:inputType="number"
        android:minHeight="48dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@id/mas"
        tools:ignore="SpeakableTextPresentCheck" />

    <CheckBox
        android:id="@+id/fem"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="40dp"
        android:layout_marginEnd="200dp"
        android:onClick="onCheckboxClickedfem"
        android:text="Female"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintLeft_toLeftOf="@id/mas"
        app:layout_constraintTop_toBottomOf="@+id/height" />
<CheckBox
        android:id="@+id/mas"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="40dp"
        android:layout_marginEnd="45dp"
        android:onClick="onCheckboxClickedfem"
        android:text="Masculine"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintRight_toRightOf="@id/fem"
        app:layout_constraintTop_toBottomOf="@+id/height" />


    <Button
        android:id="@+id/buttoncm"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:layout_marginBottom="10dp"
        android:onClick="bmi"
        android:text="BMI"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.108"
        app:layout_constraintLeft_toLeftOf="@id/buttonideal"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/tvResult"
        app:layout_constraintVertical_bias="0.598" />

<Button
        android:id="@+id/buttonideal"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"
        android:layout_marginBottom="10dp"
        android:onClick="idealweight"
        android:text="Ideal Weight"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.55"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/etResult"
        app:layout_constraintVertical_bias="0.598" />

    <Button
        android:id="@+id/buttonerase"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="68dp"
        android:layout_marginBottom="10dp"
        android:onClick="erase"
        android:text="Erase"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.956"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/etResult"
        app:layout_constraintVertical_bias="0.0" />

</androidx.constraintlayout.widget.ConstraintLayout>

------------------------------------------------------------------------------
*MainActivity.java*
Following Code: (in process/not finish)
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.zybooks.own_project.R;

public class MainActivity extends AppCompatActivity {

    private TextView app, weight, height, result;
    private ImageView logo;
    private EditText kilovalue, feetsvalue, mcresult;
    private CheckBox female, masculine;
    private Button mc, idealweight, erase;
    double pi = 0, piup = 0, pidown = 0, f1 = 2.25, f2 = 45, m1 = 2.7, m2 = 47.75;
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main

        app = (TextView) findViewById(R.id.tvTitulo);
        weight = (TextView) findViewById(R.id.weight);
        height =  (TextView)
    }

}

