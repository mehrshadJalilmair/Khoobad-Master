package com.mehrshad.khoobad.Util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.mehrshad.khoobad.Khoobad;
import com.mehrshad.khoobad.Model.VenueDetails;
import com.mehrshad.khoobad.Model.Venues;

import java.util.ArrayList;

public class GeneralFunctions {

    public static String persian_to_english(String text)
    {

        return text.replace("۰" , "0")
                .replace("۱" , "1")
                .replace("۲" , "2")
                .replace("۳" , "3")
                .replace("۴" , "4")
                .replace("۵" , "5")
                .replace("۶" , "6")
                .replace("۷" , "7")
                .replace("۸" , "8")
                .replace("۹" , "9");
    }

    public static String english_to_persian(String text)
    {
        return text.replace("0" , "۰")
                .replace("1" , "۱")
                .replace("2" , "۲")
                .replace("3" , "۳")
                .replace("4" , "۴")
                .replace("5" , "۵")
                .replace("6" , "۶")
                .replace("7" , "۷")
                .replace("8" , "۸")
                .replace("9" , "۹");
    }

    public static VenueDetails jsonOf(String json)
    {
        return json == null || json.equals("") ? null : new Gson().fromJson(json, VenueDetails.class);
    }

    public static String stringOf(VenueDetails venueDetails)
    {
        return venueDetails == null ? null : new Gson().toJson(venueDetails);
    }

    public static ArrayList<Integer> getSize()
    {
        WindowManager wm = (WindowManager) Khoobad.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = null;
        if (wm != null) {
            display = wm.getDefaultDisplay();
        }
        DisplayMetrics metrics = new DisplayMetrics();
        if (display != null) {
            display.getMetrics(metrics);
        }
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        ArrayList<Integer>arrayList = new ArrayList<>();
        arrayList.add(width);
        arrayList.add(height);
        return arrayList;
    }
}
