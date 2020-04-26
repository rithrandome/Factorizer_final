package com.example.factorizer;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    TextView H_streak;
    TextView C_streak;
    Button game;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        C_streak=findViewById(R.id.current_streak);
        game=findViewById(R.id.new_game);
        H_streak=findViewById(R.id.high_streak);

        final MyApplication obj1=((MyApplication)getApplicationContext());

        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obj1.set_streak_zero();
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
                finish();
            }
        });

        load_H_streak(obj1);
        update_streak(obj1);

    }

    private long doubleBackToExitPressed;
    private Toast backToast;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressed + 2000 > System.currentTimeMillis() ) {
            backToast.cancel();
            super.onBackPressed();
            System.exit(0);
            return;
        }
        else {

            doubleBackToExitPressed = System.currentTimeMillis();
            backToast = Toast.makeText(this, "Please click Back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }


    }

    public void update_streak(MyApplication obj)
    {
        SharedPreferences sharePref = getSharedPreferences("SHARED_PREF",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharePref.edit();
        obj.update_H_streak();
        C_streak.setText(String.valueOf(obj.get_streak()));
        H_streak.setText(String.valueOf(obj.getH_streak()));

        editor.putInt("H_STREAK",obj.getH_streak());
        editor.apply();
    }

    public void load_H_streak(MyApplication obj)
    {
        SharedPreferences sh = getSharedPreferences("SHARED_PREF",MODE_PRIVATE);
        obj.setH_streak(sh.getInt("H_STREAK",0));
    }



}

