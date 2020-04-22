package com.example.factorizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView H_streak,C_streak,fact,T1;
    Button game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        H_streak=findViewById(R.id.h_streak);
        fact=findViewById(R.id.factorizer);
        T1=findViewById(R.id.t1);
        C_streak=findViewById(R.id.c_streak);
        game=findViewById(R.id.new_game);

        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

    }
}
