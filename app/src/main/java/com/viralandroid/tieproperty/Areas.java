package com.viralandroid.tieproperty;

import android.content.Context;

import com.google.gson.JsonObject;

/**
 * Created by T on 27-04-2017.
 */

public class Areas {
    public String id,title,image;
    public Areas(JsonObject jsonObject, Context context){
        id = jsonObject.get("id").getAsString();
        title = jsonObject.get("title").getAsString();
        image = jsonObject.get("image").getAsString();
    }
}
