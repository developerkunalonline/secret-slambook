package com.qdat.secretslambook.Contract;

import android.net.Uri;

public class Contract {

    /* DataBase Constants */

    public static final String DATABASE_NAME = "Dairy";
    public static final int DATABASE_VERSION = 1;


    /* Common table fields */

    public static final String ID = "_id";

    /* Table Constants */

    // pages

    public static final String TABLE_PAGES = "pages";
    public static final String PAGE_TITLE = "title";
    public static final String PAGE_DATE = "date";
    public static final String PAGE_BODY = "body";
    public static final String PAGE_IMAGE = "images";


    /* Content Provider Constants */


    public static final String ATHORITY = "com.qdat.secretslambook";
    public static final Uri BASE_URI = Uri.parse("content://"+ATHORITY);

    public static final Uri PATH_PAGES = Uri.withAppendedPath(BASE_URI,TABLE_PAGES);


    /* Matching constants */

    public static final int DAIRY_ALL = 100;
    public static final int DAIRY_SPECIFIC = 101;
    public static final int DAIRY_INVALID = 102;

    /* Shared Prefrance Constants */

    public static final String PREFRANCE_NAME = "secretSlambook";
    public static final String BACKGROUND_IMAGE = "background_image";
    public static final String APP_PASSWORD = "password";
    public static final String IS_KNOWING = "isKnowing";




}
