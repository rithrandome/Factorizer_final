package com.example.factorizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.text.TextUtils;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    EditText in;
    Button ent;
    TextView num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);

        in=findViewById(R.id.number);
        ent=findViewById(R.id.enter);
        num=findViewById(R.id.input);

        ent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(in.getText())) {
                    in.setError("Number required !!");
                } else if (Integer.parseInt(in.getText().toString()) == 0 || Integer.parseInt(in.getText().toString()) == 1) {
                    in.setError("Invalid entry !!");
                } else {
                    Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                    intent.putExtra("in_number", Integer.parseInt(in.getText().toString()));
                    startActivity(intent);
                    finish();
                }
            }


        });
    }

    private long doubleBackToExitPressed;
    private Toast backToast;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressed + 2000 > System.currentTimeMillis() ) {
            backToast.cancel();
            super.onBackPressed();
            Intent intent = new Intent(MainActivity2.this,MainActivity.class);
            startActivity(intent);
        }
        else {

            doubleBackToExitPressed = System.currentTimeMillis();
            backToast = Toast.makeText(this, "Press again to go to Main menu", Toast.LENGTH_SHORT);
            backToast.show();
        }


    }
}
