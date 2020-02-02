package com.example.alarmclock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class AlarmHub extends AppCompatActivity {

    TextClock currentTime;
    Boolean stopped = false;
    Boolean been_set = false;
    private final int REQ_CODE = 100;
    TextView textView;
    TextView defText;

    public static List<String> words = Arrays.asList("abstract", "aesthetic", "timorous");
    public static List<String> defs = Arrays.asList("not representing or imitating external reality or the objects of nature", "having to do with the appreciation of beauty", "timid; fearful");

    Random random = new Random();
    int random_number = random.nextInt(3);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_hub);
        textView = findViewById(R.id.text);
        defText = findViewById(R.id.defText);

        //Button speak = findViewById(R.id.backButton);
        configureBackButton();


        currentTime = findViewById(R.id.homeClock);

        final Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));

        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(currentTime.getText().toString().equals(MainActivity.alarm_time) && !stopped && been_set)
                {
                    playTone(r);
                    System.out.println("here");
                }
                else
                {
                    r.stop();
                }
                configureStopButton(r);
            }
        }, 0, 1000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    textView.setText((CharSequence) result.get(0));
                    CharSequence word = (CharSequence) result.get(0);
                    if (((String) word).equals(words.get(random_number)))
                    {
                        stopped = true;
                    }
                    else
                    {
                        stopped = false;
                    }
                }
                //break;
            }
        }
    }
    public void playTone(Ringtone r)
    {
        r.play();
    }

    public void stopTone(Ringtone r)
    {
        r.stop();
    }

    private void configureBackButton()
    {
        System.out.println(MainActivity.alarm_time);
        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Clicked");
                stopped = false;
                been_set = true;
                random_number = random.nextInt(words.size());
                startActivity(new Intent(AlarmHub.this, MainActivity.class));
            }
        });
    }

    public void configureStopButton(final Ringtone r)
    {
        final Button stopButton = (Button) findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopped = true;
                stopTone(r);
                defText.setText((CharSequence) defs.get(random_number));

                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");

                try {
                    System.out.println("speak to this");
                    startActivityForResult(intent, REQ_CODE);

                } catch (ActivityNotFoundException a) {
                    System.out.println("Stopped");
                    Toast.makeText(getApplicationContext(),
                            "Sorry your device not supported",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}