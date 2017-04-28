package com.viralandroid.tieproperty;

import android.content.Context;

import com.google.gson.JsonObject;

/**
 * Created by T on 28-04-2017.
 */

public class Settings {
    public String logo,title,emails,phone,itunes_link,playstore_link,about;
    public Settings(JsonObject jsonObject, Context context){
        logo = jsonObject.get("logo").getAsString();
        title = jsonObject.get("title").getAsString();
        emails = jsonObject.get("email").getAsString();
        phone = jsonObject.get("phone").getAsString();
        itunes_link = jsonObject.get("itunes_link").getAsString();
        playstore_link = jsonObject.get("playstore_link").getAsString();
        about = jsonObject.get("about").getAsString();
    }
}
