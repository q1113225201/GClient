package com.sjl.gankapp;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.sjl.platform.base.db.DBManager;

import net.tsz.afinal.FinalDb;

/**
 * App
 *
 * @author SJL
 * @date 2017/12/4
 */

public class App extends Application {
    FinalDb.DbUpdateListener dbUpdateListener = new FinalDb.DbUpdateListener() {
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    };
    @Override
    public void onCreate() {
        super.onCreate();
        FinalDb finalDb = FinalDb.create(this,"gank.db",BuildConfig.DEBUG,BuildConfig.VERSION_CODE,dbUpdateListener);
        DBManager.init(finalDb);
    }
}
