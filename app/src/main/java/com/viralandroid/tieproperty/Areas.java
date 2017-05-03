package com.viralandroid.tieproperty;

import android.content.Context;

import com.google.gson.JsonObject;

/**
 * Created by T on 27-04-2017.
 */

public class Areas {
    public String id,title,image;
    Boolean checked;
    public Areas(JsonObject jsonObject, Context context,Boolean checked){
        id = jsonObject.get("id").getAsString();
        title = jsonObject.get("title").getAsString();
        image = jsonObject.get("image").getAsString();
        this.checked = checked;
    }

    public Areas(String name, String id, Areas areas) {
        this.id = id;
        this.title = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getName() {
        return title;
    }
    public void setName(String title) {
        this.title = title;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
