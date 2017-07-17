package com.viralandroid.tieproperty;

import android.content.Context;

import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * Created by yellowsoft on 19/6/17.
 */

public class Services implements Serializable {
    public   String id,title,description;
    public Services(JsonObject jsonObject,Context context){
        id = jsonObject.get("id").getAsString();
        title = jsonObject.get("title").getAsString();
        description = jsonObject.get("description").getAsString();
    }
}
