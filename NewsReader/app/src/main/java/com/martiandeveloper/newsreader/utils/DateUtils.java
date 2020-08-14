package com.martiandeveloper.newsreader.utils;

import android.annotation.SuppressLint;
import android.util.Log;

public class DateUtils {

    @SuppressLint("SimpleDateFormat")
    public static String formatNewsApiDate(String inputDate) {
        if(inputDate == null){
            return null;
        }

        String LOG = "MartianDeveloper";
        try {
            Log.d(LOG, inputDate);
            String[] array = inputDate.split("T");

            return array[0];
        } catch (Exception e) {
            assert e.getLocalizedMessage() != null;
            Log.d(LOG, e.getLocalizedMessage());
            //FirebaseCrash.report(e);
        }

        return inputDate;
    }
}
