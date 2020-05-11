package com.dailyrecorderteam.dailyrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import org.litepal.tablemanager.Connector;

import java.util.Calendar;

public class CalorieEditor extends AppCompatActivity {
    private EditText foodEdit;
    private EditText weightEdit;
    private EditText calorieEdit;
    private Button okButton;
    private boolean weightIsInt = true;
    private boolean calorieIsInt = true;
    private int weight = 0;
    private int calorie = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.calorieeditorlayout);
        foodEdit = (EditText) findViewById(R.id.edit_food);
        weightEdit = (EditText) findViewById(R.id.edit_weight);
        calorieEdit = (EditText) findViewById(R.id.edit_calorie);
        okButton = (Button) findViewById(R.id.calorie_ok);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String food=foodEdit.getText().toString();
                String strWeight=weightEdit.getText().toString();
                String strCalorie=calorieEdit.getText().toString();
                weightIsInt = true;
                calorieIsInt = true;

                try {
                    weight = Integer.valueOf(strWeight).intValue();
                }
                catch (NumberFormatException e) {
                    Toast.makeText(CalorieEditor.this,"重量值必须为整数",Toast.LENGTH_SHORT).show();
                    weightIsInt = false;
                }
                try {
                    calorie = Integer.valueOf(strCalorie).intValue();
                }
                catch (NumberFormatException e) {
                    Toast.makeText(CalorieEditor.this,"卡路里值必须为整数",Toast.LENGTH_SHORT).show();
                    calorieIsInt = false;
                }

                if(!food.isEmpty()&&weightIsInt&&calorieIsInt) {
                    Connector.getDatabase();
                    CalorieRecord calorieRecord = new CalorieRecord(food, weight, calorie);
                    calorieRecord.setType(MyRecord.CALORIE_RECORD);
                    Calendar ca = Calendar.getInstance();
                    int Year = ca.get(Calendar.YEAR);
                    int Month = ca.get(Calendar.MONTH);
                    int Day = ca.get(Calendar.DAY_OF_MONTH);
                    int Hour = ca.get(Calendar.HOUR_OF_DAY);
                    int Minute = ca.get(Calendar.MINUTE);
                    int Second = ca.get(Calendar.SECOND);
                    calorieRecord.setYear(Year);
                    calorieRecord.setMonth(Month);
                    calorieRecord.setDay(Day);
                    calorieRecord.setHour(Hour);
                    calorieRecord.setMinute(Minute);
                    calorieRecord.setSecond(Second);
                    calorieRecord.setTime(TimeOperator.toLongtime(Year,Month,Day,Hour,Minute,Second));
                    calorieRecord.save();
                    Toast.makeText(CalorieEditor.this,"已储存新纪录",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(CalorieEditor.this,"有值为空或不符合规范",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
