package com.qdat.secretslambook.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.qdat.secretslambook.Contract.Contract;

public class MyDatabase extends SQLiteOpenHelper {

    public MyDatabase(@Nullable Context context) {
        super(context, Contract.DATABASE_NAME, null, Contract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_QUERY = ""+
                "CREATE TABLE "+Contract.TABLE_PAGES+"(" +
                Contract.ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," +
                Contract.PAGE_TITLE+" TEXT NOT NULL , " +
                Contract.PAGE_BODY+" TEXT NOT NULL , " +
                Contract.PAGE_DATE+" TEXT NOT NULL , " +
                Contract.PAGE_IMAGE+" TEXT NOT NULL)";

        db.execSQL(CREATE_TABLE_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
