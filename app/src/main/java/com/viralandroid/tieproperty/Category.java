package com.viralandroid.tieproperty;

import android.content.Context;

import com.google.gson.JsonObject;

/**
 * Created by T on 27-04-2017.
 */

public class Category {
    public String id,title;
    public Category(JsonObject jsonObject, Context context){
        id = jsonObject.get("id").getAsString();
        title = jsonObject.get("title").getAsString();
    }
}
