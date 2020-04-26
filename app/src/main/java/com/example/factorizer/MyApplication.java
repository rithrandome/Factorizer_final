package com.example.factorizer;

import android.app.Application;

public class MyApplication extends Application {
    private int streak;
    private int h_streak ;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";

    public void set_streak()
    {
        this.streak+=1;
    }

    public void set_streak_zero()
    {
        this.streak=0;
    }

    public void update_H_streak()
    {
        if(this.h_streak<this.streak)
        {
            this.h_streak=this.streak;
        }
    }
    public int getH_streak()
    {
        return this.h_streak;
    }

    public void setH_streak(int h_streak)
    {
        this.h_streak=h_streak;
    }

    public int get_streak()
    {
        return this.streak;
    }

}
