package com.example.zangliguang.gpstogd;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.zangliguang.gpstogd.dbs.DbHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DbInfoActivity extends Activity {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.daochu)
    Button mDaochu;
    private List<GpsDb> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_info);
        ButterKnife.bind(this);
        DbHelper dbHelper = new DbHelper(this);
        Cursor cursor = dbHelper.queryAll();
        mDatas = new ArrayList<>();
        int count = cursor.getCount();

        while (cursor.moveToNext()) {
            GpsDb gpsDb = new GpsDb();
            String id = cursor.getString(cursor.getColumnIndex(DbHelper.GPS_ID));

            String name = cursor.getString(cursor.getColumnIndex(DbHelper.GPS_NAME));
            String latitude = cursor.getString(cursor.getColumnIndex(DbHelper.LATITUDE));
            String longitude = cursor.getString(cursor.getColumnIndex(DbHelper.LONGITUDE));
            gpsDb.setName(name);
            gpsDb.setLati(latitude);
            gpsDb.setLongti(longitude);
            mDatas.add(gpsDb);
        }
        cursor.close();

        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setAdapter(new GpsAdapter(this, mDatas));


    }

    @OnClick(R.id.daochu)
    public void onClick() {
        DaoChu.daochu(mDatas);
    }
}
