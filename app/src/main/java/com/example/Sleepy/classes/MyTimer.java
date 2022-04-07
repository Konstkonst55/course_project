package com.example.Sleepy.classes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.AlarmClock;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;

import kotlin.text.Regex;

public class MyTimer {

    public static String calcRemainingTimeMinute(Date currentDate){
        String RemTime;
        long timeUp = currentDate.getTime();
        long diff = timeUp - new Date().getTime();

        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;

        if (diffMinutes == 0){
            RemTime = "" + diffHours + "ч";
        }else if(diffHours == 0){
            RemTime = "" + diffMinutes + "м";
        }else{
            RemTime = "" + diffHours + "ч" + diffMinutes + "м";
        }
        return RemTime;
    }

    public static String getFormatTime(long minute){
        String RemTime;
        long h, m;
        h = minute / 60;
        m = minute - h * 60;
        if (m == 0){
            RemTime = "" + h + "ч";
        }else if(h != 0 && m != 0){
            RemTime = "" + h + "ч" + m + "м";
        }else{
            RemTime = "" + m + "м";
        }
        return RemTime;
    }

    public static void clearTime(TimePicker tp, Context context){
        MyVibrator.vibrate(30, context);
        tp.setHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        tp.setMinute(Calendar.getInstance().get(Calendar.MINUTE));
    }

    public static Calendar getCurrentTime(TimePicker tp){
        Calendar timeAlarm = Calendar.getInstance();
        timeAlarm.set(Calendar.MINUTE, tp.getMinute());
        timeAlarm.set(Calendar.HOUR_OF_DAY, tp.getHour());
        return timeAlarm;
    }

    public static void setAlarmInApp(Calendar time, Context context, View view){
        try{
            context.startActivity(new Intent(AlarmClock.ACTION_SET_ALARM)
                    .putExtra(AlarmClock.EXTRA_MESSAGE, "New Alarm")
                    .putExtra(AlarmClock.EXTRA_HOUR, time.get(Calendar.HOUR_OF_DAY))
                    .putExtra(AlarmClock.EXTRA_MINUTES, time.get(Calendar.MINUTE)));
        }catch (Exception ex){
            Snackbar.make(view, "Не удалось открыть будильник :(", Snackbar.LENGTH_LONG).show();
        }
    }

    public static void getAsleepText(int asleepTime, TextView tv) {
        if (asleepTime != 0){
            tv.setVisibility(View.VISIBLE);
            tv.setText(new StringBuilder()
                    .append("Расчитано с учетом времени засыпания - ")
                    .append(asleepTime)
                    .append(" мин."));
        }else {
            tv.setVisibility(View.GONE);
        }
    }

    public static Calendar getTimeFromText(CharSequence timeStr, String remTime){
        Calendar c = Calendar.getInstance();
        //int rem = Integer.parseInt(remTime.replaceAll("[^0-9.]",""));
        try {
            c.set(Calendar.HOUR, Objects.requireNonNull(new SimpleDateFormat("HH:mm", Locale.getDefault()).parse((String) timeStr)).getHours());
            c.set(Calendar.MINUTE, Objects.requireNonNull(new SimpleDateFormat("HH:mm", Locale.getDefault()).parse((String) timeStr)).getMinutes());
            //c.add(Calendar.DATE, rem%24);
            //Log.i("timer", "time parse:" + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + "-" + c.get(Calendar.DATE) + " rem: " + rem%24);
        } catch (ParseException ex) {
            Log.i("timer", "parse ex " + ex);
        }
        return c;
    }  //todo прибавлять сутки если осталось больше 24
}
