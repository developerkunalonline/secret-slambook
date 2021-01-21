package com.qdat.secretslambook.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qdat.secretslambook.Contract.Contract;

public class DairyDataProvider extends ContentProvider {

    MyDatabase database;
    static UriMatcher matcher;

    static {
        matcher = new UriMatcher(Contract.DAIRY_INVALID);
        matcher.addURI(Contract.ATHORITY,Contract.TABLE_PAGES,Contract.DAIRY_ALL);
        matcher.addURI(Contract.ATHORITY,Contract.TABLE_PAGES+"#",Contract.DAIRY_SPECIFIC);
    }


    @Override
    public boolean onCreate() {

        database = new MyDatabase(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        int status = matcher.match(uri);
        SQLiteDatabase db = database.getReadableDatabase();
        if (status == Contract.DAIRY_ALL){

            return db.query(Contract.TABLE_PAGES,projection,selection,selectionArgs,null,null,Contract.ID+" DESC");

        }
        else if (status == Contract.DAIRY_SPECIFIC){

            long id = ContentUris.parseId(uri);

            return db.query(Contract.TABLE_PAGES,projection,Contract.ID+"=?"
                    ,new String[]{""+id},null,null,sortOrder);


        }
        else{
            return null;
        }

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        int status = matcher.match(uri);

        if (status == Contract.DAIRY_ALL){

            SQLiteDatabase db = database.getWritableDatabase();
            long id  = db.insert(Contract.TABLE_PAGES,null,values);

            return ContentUris.withAppendedId(uri,id);

        }

        return ContentUris.withAppendedId(uri,-1);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        int status = matcher.match(uri);
        SQLiteDatabase db = database.getWritableDatabase();

        if (status == Contract.DAIRY_ALL){

            return db.delete(Contract.TABLE_PAGES,null,null);

        }
        else if (status == Contract.DAIRY_SPECIFIC){

            long id = ContentUris.parseId(uri);
            return db.delete(Contract.TABLE_PAGES,Contract.ID+"=?",new String[]{""+id});

        }


        return -2;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        int status = matcher.match(uri);


        if (status == Contract.DAIRY_SPECIFIC) {
            SQLiteDatabase db = database.getWritableDatabase();
            long id = ContentUris.parseId(uri);

            return db.update(Contract.TABLE_PAGES,values,Contract.ID+"=?",new String[]{""+id});
        }
        return -2;
    }
}
