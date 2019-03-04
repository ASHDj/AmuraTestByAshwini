package com.ashswini.amura;

import android.app.Activity;
import android.content.Context;

import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.text.TextUtils.isEmpty;

public class AppUtil {
    static Activity activity;

    public static Locale locale = Locale.US;

   /* public static boolean hasText(TextInputLayout inputLayout) {
        return !inputLayout.getEditText().getText().toString().trim().equals("");
    }

    public static String getText(TextInputLayout inputLayout) {
        return inputLayout.getEditText().getText().toString().trim();
    }*/

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static String FetchCurrentDate() {
        Calendar cal = Calendar.getInstance();
        return formatDateForDisplay(cal.getTime(), "yyyy-MM-dd");
    }
    public static Date toDate(String str, String format) {
        if (isEmpty(str)) {
            return null;
        }
        try {
            return new SimpleDateFormat(format, getAppLocale()).parse(str);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String GetSTRingFRomStringDate(String str, String format) {
        if (isEmpty(str)) {
            return null;
        }
        try {
           Date d= new SimpleDateFormat(format, getAppLocale()).parse(str);


           return formatDateForDisplay(d,format) ;
        } catch (ParseException e) {
            return null;
        }
    }
    public static String formatDateForDisplay(Date d, String format) {
        if (d == null) {
            return "";
        }
        return new SimpleDateFormat(format, getAppLocale()).format(d);

    }

    public static Locale getAppLocale() {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        return locale;
    }
}
