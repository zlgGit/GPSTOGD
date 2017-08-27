package com.example.zangliguang.gpstogd;

import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zangliguang on 2017/8/27.
 */

public class DaoChu {

    public static String filepath=Environment.getExternalStorageDirectory().getAbsolutePath();
    public static String fileName="/经纬度坐标.txt";


    public DaoChu(){


    }
    public static void daochu(List<GpsDb> gpsDbs)
    {
        FileOutputStream outputStream=null;
        try {
             outputStream = new FileOutputStream(new File(filepath+fileName));

            Iterator<GpsDb> iterator = gpsDbs.iterator();
            while (iterator.hasNext())
            {
                GpsDb next = iterator.next();
                StringBuilder stringBuilder = new StringBuilder(next.toString());
                stringBuilder.append("\r\n");

                outputStream.write(stringBuilder.toString().getBytes());
                outputStream.flush();
            }




        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (outputStream!=null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
