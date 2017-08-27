package com.example.zangliguang.gpstogd;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zangliguang on 2017/8/27.
 */

public class GpsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.latitude)
    TextView mLatitude;
    @BindView(R.id.longtude)
    TextView mLongtude;

    public GpsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
    public void setData(GpsDb gpsDb)
    {
        mName.setText(gpsDb.getName());
        mLatitude.setText(gpsDb.getLati());
        mLongtude.setText(gpsDb.getLongti());
    }
}
