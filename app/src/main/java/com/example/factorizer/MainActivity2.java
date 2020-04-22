package com.example.factorizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    EditText in;
    Button ent;
    TextView num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        in=findViewById(R.id.input);
        ent=findViewById(R.id.enter);
        num=findViewById(R.id.number);

        ent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                String inputNumber = in.getText().toString();
                intent.putExtra("in_number", inputNumber);
                startActivity(intent);
            }
        });
    }
}
