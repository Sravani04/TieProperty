package com.viralandroid.tieproperty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by T on 24-04-2017.
 */

public class CityPageAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
//    ArrayList<String> cities;
    ArrayList<Cities> cities;
    public CityPageAdapter(Context context,ArrayList<Cities>cities){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.cities = cities;
    }
    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final View item_view = inflater.inflate(R.layout.city_list_items,null);
        TextView city_name = (TextView) item_view.findViewById(R.id.city_name);
        city_name.setText(cities.get(i).title);
        return item_view;
    }
}
