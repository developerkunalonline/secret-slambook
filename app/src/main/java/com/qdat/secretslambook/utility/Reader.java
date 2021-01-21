package com.qdat.secretslambook.utility;

import android.database.Cursor;

import com.qdat.secretslambook.Contract.Contract;

import java.util.ArrayList;
import java.util.List;

public class Reader {

    public static List<Dairy> extractDateFromCursor(Cursor cursor){
        List<Dairy> list = new ArrayList<>();
        if (!cursor.moveToNext()){
            return list;
        }
        do {
            list.add(new Dairy(cursor.getInt(cursor.getColumnIndex(Contract.ID)),
                            cursor.getString(cursor.getColumnIndex(Contract.PAGE_TITLE)),
                            new MyDate(cursor.getString(cursor.getColumnIndex(Contract.PAGE_DATE))),
                                    cursor.getString(cursor.getColumnIndex(Contract.PAGE_BODY)),
                                    cursor.getString(cursor.getColumnIndex(Contract.PAGE_IMAGE))));
        }while (cursor.moveToNext());


        return list;
    }
}
