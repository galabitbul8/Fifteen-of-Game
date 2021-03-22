package com.galab_rotemle.ex1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{
    TextView txtTime;
    Thread thread;
    boolean done = false;
    GameBoard board ;
    TextView [] boxesArray = new TextView[16];
    TextView txtMoves;
    int sec = 0;
    int m = 0;
    boolean pauseTimer = false;
    MediaPlayer mp;
    boolean musicOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        musicOn = sp.getBoolean("musicOn", false);

        setContentView(R.layout.activity_game);
        txtTime = (TextView) findViewById(R.id.time);
        startTime();
        board = new GameBoard();
        mp = MediaPlayer.create(this, R.raw.androidmusic);
        if(musicOn){
            mp.start();
        }

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });

        Button btnNewGame = (Button)findViewById(R.id.new_game);
        txtMoves =  findViewById(R.id.textView33);
        btnNewGame.setOnClickListener(this);

        for (int i = 0; i < 16; i++) {
            String str = "box" + (i+1) + "";
            boxesArray[i] = findViewById(getResources().getIdentifier(str, "id", getPackageName()));
            boxesArray[i].setOnClickListener(this);
        }
        insertData();
    }



    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseTimer = true;
        if(musicOn)
            mp.pause();
//        mp.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pauseTimer = false;
        if(musicOn){
            mp.start();
        }
    }

    private void insertData() {
        for (int i = 0; i < board.matrix.length; i++) {
            for (int j = 0; j < board.matrix.length; j++){
                if(board.matrix[i][j] == 16){
                    boxesArray[i * board.matrix.length + j].setText("");
                    boxesArray[i * board.matrix.length + j].setBackgroundResource(R.drawable.empty_box);
                }
                else {
                    boxesArray[i * board.matrix.length + j].setText(board.matrix[i][j] + "");
                    boxesArray[i * board.matrix.length + j].setBackgroundResource(R.drawable.box);
                }

            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.new_game){
            activateBoxes(true);
            done = false;
            board.shuffleMatrix();
            insertData();
            startTime();
            txtMoves.setText("Moves: "+"0000");
            sec = 0;
            m = 0;
            txtTime.setText("Time: 00:00");
        }
        else {
            TextView val = (TextView) findViewById(v.getId());
            if(val.getText() != ""){
            int value = Integer.parseInt(val.getText()+"");

                for (int i = 0; i < 16; i++) {
                    if(v.getId() == getResources().getIdentifier("box" + (i+1), "id", getPackageName())){
                        board.switchByPress(board.matrix, value);
                        insertData();
                        if(board.checkGameStatus()) {
                            done=true;
                            activateBoxes(false);
                            Toast.makeText(this, "Game Over - Puzzle solved", Toast.LENGTH_LONG*3).show();
                        }
                    }
                }
                txtMoves.setText(String.format( "Moves: %04d", board.count));
            }
        }
    }

    private void startTime(){
        if(thread == null) {
            thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    while (!done) {
                        if(!pauseTimer){
                            sec++; //count second
                            if (sec == 60) {
                                sec = 0;
                                m++; // count minutes
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    txtTime.setText("Time: "+String.format( "%02d", m) + ":" + String.format( "%02d", sec));
                                }
                            });
                            SystemClock.sleep(1000);
                        }
                    }
                    thread = null;
                }
            });
            thread.start();
        }
    }

    private void activateBoxes(Boolean status) {
        for (int i = 0; i <16 ; i++) {
            String str = "box" + (i+1) + "";
            boxesArray[i] = findViewById(getResources().getIdentifier(str, "id", getPackageName()));
            boxesArray[i].setClickable(status);
        }
    }

}