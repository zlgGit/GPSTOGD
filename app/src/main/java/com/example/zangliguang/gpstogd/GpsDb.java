package com.example.zangliguang.gpstogd;

/**
 * Created by zangliguang on 2017/8/27.
 */

public class GpsDb {
    public String name;
    public String lati;
    public String longti;
    public String tips;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLati() {
        return lati;
    }

    public void setLati(String lati) {
        this.lati = lati;
    }

    public String getLongti() {
        return longti;
    }

    public void setLongti(String longti) {
        this.longti = longti;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    @Override
    public String toString() {
        return  name+"   纬度："+lati+"   经度："+longti;
    }
}
