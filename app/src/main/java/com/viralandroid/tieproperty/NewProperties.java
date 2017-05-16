package com.viralandroid.tieproperty;

import android.content.Context;

import com.google.gson.JsonObject;

/**
 * Created by T on 16-05-2017.
 */

public class NewProperties {
    public String id,title,type_id,type_title,name,phone,address,city_id,city_title,area_id,area_title;
    public NewProperties(JsonObject jsonObject, Context context){
        id = jsonObject.get("id").getAsString();
        title = jsonObject.get("title").getAsString();
        type_id = jsonObject.get("type").getAsJsonObject().get("type_id").getAsString();
        type_title = jsonObject.get("type").getAsJsonObject().get("title").getAsString();
        name = jsonObject.get("name").getAsString();
        phone = jsonObject.get("phone").getAsString();
        address = jsonObject.get("address").getAsString();
        city_id = jsonObject.get("city").getAsJsonObject().get("city_id").getAsString();
        city_title = jsonObject.get("city").getAsJsonObject().get("title").getAsString();
        area_id = jsonObject.get("area").getAsJsonObject().get("area_id").getAsString();
        area_title = jsonObject.get("area").getAsJsonObject().get("title").getAsString();
    }
}
