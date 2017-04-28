package com.viralandroid.tieproperty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by T on 28-04-2017.
 */

public class CategoryPageAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    ArrayList<Category> categories;

    public CategoryPageAdapter(Context context,ArrayList<Category> categories){
        this.context = context;
        this.categories = categories;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return categories.size();
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
        View item_view = inflater.inflate(R.layout.category_items,null);
        TextView category_name = (TextView) item_view.findViewById(R.id.category_name);
        category_name.setText(categories.get(i).title);
        return item_view;
    }
}
