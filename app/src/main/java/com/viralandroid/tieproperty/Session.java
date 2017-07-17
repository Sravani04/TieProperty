package com.viralandroid.tieproperty;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by T on 27-04-2017.
 */

public class Session {
    public static String SERVER_URL = "https://tieproperty.in/api/";

    public  static  final String mem_id="mem_id";
    public  static  final String mem_name="mem_name";
    public static final String service = "services";
    public static  final String setting = "settings";
    public static  final String city = "cities";

    public  static void SetUserId(Context context, String id){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(mem_id,id);
        editor.commit();
    }

    public  static String GetUserId(Context context) {
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(mem_id,"-1");
    }

    public static void SetServices(Context context,String services){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(service,services);
        editor.commit();
    }

    public static String GetServices(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(service,"-1");
    }

    public static void SetSettings(Context context,String settings){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(setting,settings);
        editor.commit();
    }

    public static String GetSettings(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(setting,"-1");
    }

    public static void SetCities(Context context,String cities){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(city,cities);
        editor.commit();
    }

    public static String GetCities(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(city,"-1");
    }

}
