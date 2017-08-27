package com.example.zangliguang.gpstogd.dbs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zangliguang on 2017/8/27.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "gps.db";
    public static final int oldVersion = 1;
    public static final int version = 3;
    public static final String GPS_NAME = "gps_name2";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String GPS_TABLE = "gps_table";
    public static final String GPS_ID = "id";

    public static final String CREATE_TABLE = "create table if not exists " + GPS_TABLE + "(" +
            "id integer primary key," +
            GPS_NAME + " varchar," +
            LATITUDE + " text," +
            LONGITUDE + " text)";
    private SQLiteDatabase mSqLiteDatabase;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, version);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion==version) {
            db.execSQL(CREATE_TABLE);
        }

    }

    public long insert(String gpsName, String latitude, String longitude) {
        mSqLiteDatabase = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(GPS_NAME, gpsName);
        values.put(LATITUDE, latitude);
        values.put(LONGITUDE, longitude);
        long insert = mSqLiteDatabase.insert(GPS_TABLE, null, values);
        mSqLiteDatabase.close();


        return insert;
    }

    public int delete(int id) {
        String whereClause = "id=?";
        String[] whereArgs = {String.valueOf(id)};
        mSqLiteDatabase = getWritableDatabase();
        int delete = mSqLiteDatabase.delete(GPS_TABLE, whereClause, whereArgs);

        mSqLiteDatabase.close();

        return delete;
    }


    public int update(String gpsName, double latitude, double longitude,int id) {
//实例化内容值
        ContentValues values = new ContentValues();

        mSqLiteDatabase = getWritableDatabase();
        values.put(GPS_NAME, gpsName);
        values.put(LATITUDE, latitude);
        values.put(LONGITUDE, longitude);
//在values中添加内容
//修改条件
        String whereClause = "id=?";
//修改添加参数
        String[] whereArgs = {String.valueOf(id)};
//修改
        int usertable = mSqLiteDatabase.update("usertable", values, whereClause, whereArgs);

        mSqLiteDatabase.close();
        return usertable;
    }

    public Cursor queryAll()
    {
        mSqLiteDatabase = getWritableDatabase();
        Cursor query = mSqLiteDatabase.query(GPS_TABLE, null, null, null, null, null, null);

        return query;
    }

}
