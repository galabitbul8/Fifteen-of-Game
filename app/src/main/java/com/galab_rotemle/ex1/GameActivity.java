package com.galab_rotemle.ex1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.Locale;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{
    int moves = 0;
    String time = "00:00";
    TextView txtTime;
    Thread thread;
    boolean done = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        txtTime = (TextView) findViewById(R.id.time);
        startTime();
    }


    @Override
    public void onClick(View v) {

    }

    private void startTime(){
        if(thread == null) {
            thread = new Thread(new Runnable() {
                int sec = 0;
                int m = 0;
                @Override
                public void run() {
                    while (!done) {
                        //count second
                        sec++;
                        if (sec == 60) {
                            sec = 0;
                            m++; // count minutes
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txtTime.setText(String.format(Locale.getDefault(), "%02d", m) + ":" + String.format(Locale.getDefault(), "%02d", sec));
                            }
                        });
                        SystemClock.sleep(1000);
                    }
                    thread = null;
                }
            });
            thread.start();
        }
    }
}