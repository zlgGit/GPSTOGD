package com.example.zangliguang.gpstogd;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by zangliguang on 2017/8/27.
 */

public class GpsAdapter extends RecyclerView.Adapter<GpsViewHolder> {


    private Context mContext;
    private List<GpsDb> mList;

    public GpsAdapter(Context context, List<GpsDb> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public GpsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.gps_item, parent, false);
        GpsViewHolder gpsViewHolder = new GpsViewHolder(view);
        return gpsViewHolder;
    }

    @Override
    public void onBindViewHolder(GpsViewHolder holder, int position) {

        GpsDb gpsDb = mList.get(position);
        holder.setData(gpsDb);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
