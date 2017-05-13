package com.viralandroid.tieproperty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by T on 13-05-2017.
 */

public class AgentsPropertiesAdapter  extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    ArrayList<Properties> properties;
    Cities cities;

    public AgentsPropertiesAdapter(Context context,Cities cities,ArrayList<Properties> properties){
        this.context = context;
        this.cities  = cities;
        this.properties = properties;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return properties.size();
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        View item_view = inflater.inflate(R.layout.agents_properties_list_items,null);
        ImageView property_image = (ImageView) item_view.findViewById(R.id.property_image);
        TextView property_title = (TextView) item_view.findViewById(R.id.property_title);
        TextView property_address = (TextView) item_view.findViewById(R.id.property_address);
        TextView property_city = (TextView) item_view.findViewById(R.id.property_city);
        TextView category_title = (TextView) item_view.findViewById(R.id.category_title);

        Ion.with(context)
                .load(properties.get(position).images.get(0).image)
                .withBitmap()
                .intoImageView(property_image);

        property_title.setText(properties.get(position).title);
        property_address.setText(properties.get(position).area_title);
        category_title.setText(properties.get(position).category_title);
        return item_view;
    }
}
