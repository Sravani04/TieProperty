package com.viralandroid.tieproperty;

import android.content.Context;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by T on 28-04-2017.
 */

public class AreasListAdapter  extends BaseAdapter {

    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private ArrayList<Areas> arraylist;
    private Geocoder mGeocoder;


    public AreasListAdapter(Context context, ArrayList<Areas> arraylist) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = arraylist;

    }


    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return arraylist.size();
    }

    @Override
    public Areas getItem(int position) {
        return arraylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.areas_items, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.area_name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(arraylist.get(position).title);
        return view;
    }
}
