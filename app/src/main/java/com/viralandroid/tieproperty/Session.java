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

}
