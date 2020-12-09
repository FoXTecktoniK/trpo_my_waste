package com.example.mywaste;

import android.app.Application;

import androidx.room.Room;

import com.example.mywaste.room.AppDatabase;


public class MyApplication extends Application {
    private static MyApplication instance;
    public static MyApplication getInstance() {
        return instance;
    }

    private AppDatabase mAppDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mAppDatabase = Room.databaseBuilder(this,
                AppDatabase.class,
                "app-database")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    public AppDatabase getAppDatabase() {
        return mAppDatabase;
    }
}
