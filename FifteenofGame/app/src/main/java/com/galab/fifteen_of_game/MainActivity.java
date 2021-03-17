package com.galab.fifteen_of_game;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnStart = (Button)findViewById(R.id.start);
        btnStart.setOnClickListener(this);

    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        MenuItem about = menu.add("About");
        MenuItem exit = menu.add("Exit");

        about.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });
                dialog.setIcon(R.mipmap.ic_launcher);
                dialog.setTitle("About App");
                dialog.setMessage("Puzzle 15 ("+getPackageName()+")\n\nBy Rotem Levi & Gal David Abitbul");

                dialog.show();
                return true;
            }
        });
        exit.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        finish(); // kill the app
                    }
                });
                dialog.setIcon(R.drawable.exit);
                dialog.setTitle("Exit App");
                dialog.setMessage("Do you really want to exit Puzzle 15");
                dialog.setNegativeButton("NO",new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    { }
                });
                dialog.show();
                return true;
            }
        });

        return true;
    }

    @Override
    public void onClick(View v) {
        if(R.id.start == v.getId()){
            Intent intent = new Intent(MainActivity.this,GameActivity.class);
            startActivity(intent);
        }
    }
}