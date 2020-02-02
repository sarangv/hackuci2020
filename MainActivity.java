package com.example.alarmclock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TimePicker;

import java.util.Timer;
import java.util.TimerTask;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    TimePicker alarmTime;
    Random rand;
    //int randomindex =0;
    public static int MY_VALUE = 4;
    public static String alarm_time = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //configureRinger();
        configureNextButton();
    }

    public TimePicker getAlarmTime()
    {
        return alarmTime;
    }




    public String AlarmTime()
    {
        Integer alarmHours = alarmTime.getCurrentHour();
        Integer alarmMinutes = alarmTime.getCurrentMinute();
        String stringAlarmMinutes;

        if(alarmMinutes < 10)
        {
            stringAlarmMinutes = "0";
            stringAlarmMinutes = stringAlarmMinutes.concat(alarmMinutes.toString());
        }
        else
        {
            stringAlarmMinutes = alarmMinutes.toString();
        }

        String stringAlarmTime;

        if(alarmHours > 12)
        {
            alarmHours = alarmHours - 12;
            stringAlarmTime = alarmHours.toString().concat(":").concat(stringAlarmMinutes).concat(" PM");
        }
        else
        {
            stringAlarmTime = alarmHours.toString().concat(":").concat(stringAlarmMinutes).concat(" AM");
        }

        return stringAlarmTime;
    }

    private void configureNextButton()
    {
        Button nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(" First Clicked");
                alarmTime = findViewById(R.id.timePicker);
                alarm_time = AlarmTime();
                //randomindex = rand.nextInt(AlarmHub.words.size());
                finish();
            }
        });
    }
}