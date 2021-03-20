package com.galab_rotemle.ex1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.sql.Timestamp;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{
    int moves = 0;
    String time = "00:00";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }


    @Override
    public void onClick(View v) {

    }
}