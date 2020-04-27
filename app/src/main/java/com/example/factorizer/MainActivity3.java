package com.example.factorizer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.*;


public class MainActivity3 extends AppCompatActivity {

    private static final long START_TIME_IN_MILLIS = 10000;
    private boolean timerRunning, option_selected;
    private long timeLeftInMillis = START_TIME_IN_MILLIS;
    private long endTime;

    List<Integer> F = new ArrayList<Integer>();
    List<Integer> new_list = new ArrayList<Integer>();
    TextView n, textViewTimer;
    RadioGroup group;
    RadioButton opt1, opt2, opt3, selected_opt;
    Button start_timer;
    ConstraintLayout currentLayout;
    MediaPlayer c,w;


    int selected_id, inputNumber;
    int flag;

    CountDownTimer countDownTimer;

    public List<Integer> factorize(int number) {

        Random r = new Random();
        int x;
        List<Integer> fact = new ArrayList<Integer>();
        List<Integer> not_fact = new ArrayList<Integer>();
        List<Integer> options = new ArrayList<Integer>();

        for (int i = 1; i <= number; i++) {
            if (number % i == 0)
                fact.add(i);
            else
                not_fact.add(i);
        }
        F.addAll(fact);
        if (number > 4) {
            Collections.shuffle(not_fact);
            for (int k = 0; k < 2; k++)
                options.add(not_fact.get(k));
        }
        else if(number == 2)
        {
            for(int i=0;i<2;i++)
            {
                x=r.nextInt(50);
                if(x!=2&&x!=1)
                options.add(x);
            }

        }

        else if(number == 3)
        {
            for(int i=0;i<2;i++)
            {
                x=r.nextInt(50);
                if(x!=3&&x!=1)
                    options.add(x);
            }

        }

        else if(number == 4)
        {
            for(int i=0;i<2;i++)
            {
                x=r.nextInt(50);
                if(x!=2&&x!=1&&x!=4)
                    options.add(x);
            }

        }

        Collections.shuffle(fact);
        options.add(fact.get(0));
        Collections.shuffle(options);

        return options;
    }



    public int show_correct_ans(RadioButton rb, int selected_id) {
        if (F.contains(Integer.parseInt(rb.getText().toString()))) {
            return rb.getId();
        } else if (rb.getId() == selected_id)
            return 0;
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        n = findViewById(R.id.textView2);
        group = findViewById(R.id.group1);
        opt1 = findViewById(R.id.option1);
        opt2 = findViewById(R.id.option2);
        opt3 = findViewById(R.id.option3);
        start_timer = findViewById(R.id.start);
        textViewTimer = findViewById(R.id.timer);
        currentLayout = findViewById(R.id.layout_activity3);

        final Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        final MyApplication obj = ((MyApplication) getApplication());

        Intent intent = getIntent();
        inputNumber = intent.getIntExtra("in_number", 0);
        n.setText(String.valueOf(inputNumber));


        new_list.addAll(factorize(inputNumber));

        opt1.setText(String.valueOf(new_list.get(0)));
        opt2.setText(String.valueOf(new_list.get(1)));
        opt3.setText(String.valueOf(new_list.get(2)));


        group.setBackgroundColor(Color.WHITE);

        group.clearCheck();
        start_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                flag = 1;

                group.setVisibility(View.VISIBLE);

                startTimer();

                start_timer.setText("SUBMIT");
                start_timer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        flag = 2;
                        selected_id = group.getCheckedRadioButtonId();
                        selected_opt = findViewById(selected_id);
                        if (selected_id == -1) {
                            Toast.makeText(MainActivity3.this,
                                    "Select an option !!", Toast.LENGTH_SHORT).show();

                        } else {
                            timerRunning = false;
                            countDownTimer.cancel();
                            String str = selected_opt.getText().toString();
                            if (F.contains(Integer.parseInt(str))) {

                                correct_sound();
                                Toast.makeText(MainActivity3.this,
                                        "Correct Answer !!", Toast.LENGTH_SHORT).show();
                                correct_ans(obj);

                            } else {

                                wrong_sound();
                                Toast.makeText(MainActivity3.this,
                                        "Wrong Answer !!", Toast.LENGTH_SHORT).show();
                                wrong_answer(selected_id);
                                final Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                assert v != null;
                                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                            }

                        }
                    }
                });
            }
        });

        updateCountDownText();

    }

    private void startTimer() {
        endTime = System.currentTimeMillis() + timeLeftInMillis;
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }


            @Override
            public void onFinish() {
                timerRunning = false;
                //Toast.makeText(MainActivity3.this,
                 //"Time Out !!", Toast.LENGTH_SHORT).show();

                wrong_answer(selected_id);
                //final Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            }
        }.start();

        timerRunning = true;
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        textViewTimer.setText(timeLeftFormatted);
    }

    public void wrong_answer(int selected_id) {


        currentLayout = findViewById(R.id.layout_activity3);
        currentLayout.setBackgroundResource(R.drawable.redbackground);
        if (opt1.getId() == show_correct_ans(opt1, selected_id))
            opt1.setBackgroundColor(Color.parseColor("#FF4CAF50"));
        else if (opt2.getId() == show_correct_ans(opt2, selected_id))
            opt2.setBackgroundColor(Color.parseColor("#FF4CAF50"));
        else if (opt3.getId() == show_correct_ans(opt3, selected_id))
            opt3.setBackgroundColor(Color.parseColor("#FF4CAF50"));


        start_timer.setText("NEW GAME");

        start_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 4;
                Intent intent = new Intent(MainActivity3.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    public void correct_ans(final MyApplication obj1)
    {
        currentLayout.setBackgroundResource(R.drawable.greenbackground);
        selected_opt.setBackgroundColor(Color.parseColor("#FF4CAF50"));

        start_timer.setText("NEXT");
        start_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=3;
                obj1.set_streak();

                Intent intent = new Intent(MainActivity3.this, MainActivity2.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("timerState", timerRunning);
        outState.putInt("visibility", group.getVisibility());
        outState.putIntegerArrayList("options",(ArrayList<Integer>) new_list);
        outState.putLong("timeLeftInTimer", timeLeftInMillis);
        outState.putString("buttonName", start_timer.getText().toString());
        outState.putInt("button_status", selected_id);
        outState.putLong("endTime", endTime);
        outState.putInt("flag_start", flag);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        final MyApplication obj = ((MyApplication)getApplication());

        timeLeftInMillis = savedInstanceState.getLong("timeLeftInTimer");
        timerRunning = savedInstanceState.getBoolean("timerState");
        group.setVisibility(savedInstanceState.getInt("visibility"));
        new_list = savedInstanceState.getIntegerArrayList("options");
        start_timer.setText(savedInstanceState.getString("buttonName"));
        selected_id = savedInstanceState.getInt("button_status");
        flag = savedInstanceState.getInt("flag_start");

        updateCountDownText();

        if (timerRunning) {
            endTime = savedInstanceState.getLong("endTime");
            timeLeftInMillis = endTime - System.currentTimeMillis();
        }

        if (flag==1) {

            opt1.setText(String.valueOf(new_list.get(0)));
            opt2.setText(String.valueOf(new_list.get(1)));
            opt3.setText(String.valueOf(new_list.get(2)));

            group.setVisibility(View.VISIBLE);

            startTimer();

            start_timer.setText("SUBMIT");
            start_timer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    flag = 2;
                    selected_id=group.getCheckedRadioButtonId();
                    selected_opt=findViewById(selected_id);
                    if(selected_id==-1)
                    {
                        Toast.makeText(MainActivity3.this,
                                "Select an option !!", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        timerRunning=false;
                        countDownTimer.cancel();
                        String str = selected_opt.getText().toString();
                        if (F.contains(Integer.parseInt(str))) {

                            correct_sound();
                            Toast.makeText(MainActivity3.this,
                                    "Correct Answer !!", Toast.LENGTH_SHORT).show();
                            correct_ans(obj);
                        } else {

                            wrong_sound();
                            Toast.makeText(MainActivity3.this,
                                    "Wrong Answer !!", Toast.LENGTH_SHORT).show();
                            wrong_answer(selected_id);
                            final Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            assert v != null;
                            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                        }

                    }
                }
            });

        }

        else if(flag==2)
        {
            opt1.setText(String.valueOf(new_list.get(0)));
            opt2.setText(String.valueOf(new_list.get(1)));
            opt3.setText(String.valueOf(new_list.get(2)));

            selected_id=group.getCheckedRadioButtonId();
            selected_opt=findViewById(selected_id);
            if(selected_id==-1)
            {
                Toast.makeText(MainActivity3.this,
                        "Select an option !!", Toast.LENGTH_SHORT).show();

            }
            else
            {
                timerRunning=false;
                // countDownTimer.cancel();
                String str = selected_opt.getText().toString();
                if (F.contains(Integer.parseInt(str))) {

                    correct_ans(obj);

                } else {

                    wrong_answer(selected_id);

                }

            }
        }
        
        else if(flag == 3)
        {
            obj.set_streak();

            Intent intent = new Intent(MainActivity3.this, MainActivity2.class);
            startActivity(intent);
            finish();
        }

        else if(flag == 4)
        {
            Intent intent = new Intent(MainActivity3.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private long doubleBackToExitPressed;
    private Toast backToast;


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressed + 2000 > System.currentTimeMillis() ) {
            backToast.cancel();
            super.onBackPressed();
            Intent intent = new Intent(MainActivity3.this,MainActivity2.class);
            startActivity(intent);
        }
        else {

            doubleBackToExitPressed = System.currentTimeMillis();
            backToast = Toast.makeText(this, "Press again to enter the number", Toast.LENGTH_SHORT);
            backToast.show();
        }


    }

    public void correct_sound()
    {
        c = MediaPlayer.create(this,R.raw.correct_answer);

        c.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                c.start();
            }
        });

        c.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                c.release();
            }
        });

    }

    public void wrong_sound()
    {
        w = MediaPlayer.create(this,R.raw.wrong_answer);

        w.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                w.start();
            }
        });

        w.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                w.release();
            }
        });

    }




}


