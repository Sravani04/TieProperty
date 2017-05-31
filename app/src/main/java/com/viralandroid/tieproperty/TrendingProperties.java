package com.viralandroid.tieproperty;

import android.content.Context;

import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * Created by T on 31-05-2017.
 */

public class TrendingProperties implements Serializable {
    public String id,title,image,link,property_id;
    public TrendingProperties(JsonObject jsonObject, Context context){
        id = jsonObject.get("id").getAsString();
        title = jsonObject.get("title").getAsString();
        image = jsonObject.get("image").getAsString();
        link = jsonObject.get("link").getAsString();
        property_id = jsonObject.get("property_id").getAsString();
    }
}
