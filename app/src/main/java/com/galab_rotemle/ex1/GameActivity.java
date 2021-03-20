package com.galab_rotemle.ex1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.Locale;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{
    String time = "00:00";
    TextView txtTime;
    Thread thread;
    boolean done = false;
    GameBoard board ;
    TextView [] boxesArray = new TextView[16];
    TextView txtMoves;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        txtTime = (TextView) findViewById(R.id.time);
        startTime();
        board = new GameBoard();

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

    private void insertData() {
        for (int i = 0; i < board.matrix.length; i++) {
            for (int j = 0; j < board.matrix.length; j++){
                if(board.matrix[i][j] == 16)
                    boxesArray[i * board.matrix.length + j].setText("");
                else
                    boxesArray[i * board.matrix.length + j].setText(board.matrix[i][j] + "");

            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.new_game){
            Log.d("myLog", "new game pressed: ");
            board.shuffleMatrix();
            insertData();
            txtMoves.setText("Moves: "+"0000");

        }
        else {
            TextView val = (TextView) findViewById(v.getId());;
            if(val.getText() != ""){
            int value = Integer.parseInt(val.getText()+"");

                for (int i = 0; i < 16; i++) {
                    if(v.getId() == getResources().getIdentifier("box" + (i+1), "id", getPackageName())){
                        board.switchByPress(board.matrix, value);
                        insertData();
                        if(board.checkGameStatus()) {
                            Log.d("myLog", "Completed!!!: ");
                        }
                    }
                }
                int length =(int) (Math.log10(board.count) + 1);

                String zeros="000";
                if(length  == 2)
                    zeros="00";
                else if(length == 3)
                    zeros="0";
                else if(length ==4)
                    zeros="";
                txtMoves.setText("Moves: "+zeros +board.count);
            }
        }
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