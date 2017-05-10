package com.viralandroid.tieproperty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by T on 05-05-2017.
 */

public class SpecificationsAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    Properties properties;


    public SpecificationsAdapter(Context context, Properties properties) {
        this.context = context;
        this.properties = properties;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return properties.specifications.size();
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
        View item_view = inflater.inflate(R.layout.specifications_items, null);
        TextView title = (TextView) item_view.findViewById(R.id.title);
        TextView description = (TextView) item_view.findViewById(R.id.description);

        if (properties.specifications!=null){
            title.setText(properties.specifications.get(i).title);
            description.setText(properties.specifications.get(i).description);

        }

        return item_view;
    }


}
