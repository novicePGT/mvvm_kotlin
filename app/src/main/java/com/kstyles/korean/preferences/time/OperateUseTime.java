package com.kstyles.korean.preferences.time;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.github.mikephil.charting.data.BarEntry;
import com.kstyles.korean.repository.FirebaseManager;
import com.kstyles.korean.repository.user.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OperateUseTime {

    private String TAG = "[OperateUseTime]";
    private String uid;
    private Activity activity;
    private long startTime = 0;
    private long endTime = 0;

    public OperateUseTime(Activity activity) {
        this.activity = activity;
        FirebaseManager firebaseManager = new FirebaseManager();
        User user = firebaseManager.getUser();
        uid = user.getUid();
    }

    public void onStart() {
        startTime = System.currentTimeMillis();
    }

    public void onStop() {
        endTime = System.currentTimeMillis();
        long useTime = endTime - startTime;
        int useTimeMin = conversionSecToMin(useTime);

        String date = isObtainDate();

        int beforeUseTime = loadUseTime(activity, date);

        int mergeUseTime = useTimeMin + beforeUseTime;
        saveUseTime(activity, date, mergeUseTime);

        Log.d(TAG, "앱 동작 시간: " + useTimeMin + " min");
        Log.d(TAG, "앱 이전 실행 시간: " + beforeUseTime + " min");
        Log.d(TAG, "앱 토탈 실행 시간: " + mergeUseTime + " min");
    }

    public ArrayList<BarEntry> getSpendingTime() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(uid, Context.MODE_PRIVATE);
        int[] useTimeArray = {
                sharedPreferences.getInt("Sun", 0),
                sharedPreferences.getInt("Mon", 0),
                sharedPreferences.getInt("Tue", 0),
                sharedPreferences.getInt("Wed", 0),
                sharedPreferences.getInt("Thu", 0),
                sharedPreferences.getInt("Fri", 0),
                sharedPreferences.getInt("Sat", 0)
        };

        ArrayList<BarEntry> spendingTime = IntStream.range(0, useTimeArray.length)
                .mapToObj(i -> new BarEntry((float) (i + 1), useTimeArray[i]))
                .collect(Collectors.toCollection(ArrayList::new));

        return spendingTime;
    }

    private void saveUseTime(Activity activity, String date, int useTimeMin) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(uid, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(date, useTimeMin);
        editor.apply();
    }

    private int loadUseTime(Activity activity, String date) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(uid, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(date, 0);
    }

    private String isObtainDate() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        String[] dayOfWeekName = {"Sun","Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        String date = dayOfWeekName[dayOfWeek - 1];

        calendar.add(Calendar.DAY_OF_MONTH, -7); // 일주일 전으로 이동
        Date weekAgo = calendar.getTime();
        String weekAgoDate = new SimpleDateFormat("EEE", Locale.ENGLISH).format(weekAgo);

        if (date.compareTo(weekAgoDate) < 0) {
            clearAllData();
            date = isObtainDate();
        }

        return date;
    }

    private int conversionSecToMin(long useTime) {
        int minute = (int) (useTime / (60 * 1000));
        return minute;
    }

    private void clearAllData() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(uid, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
