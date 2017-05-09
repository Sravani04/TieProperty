package com.viralandroid.tieproperty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by T on 28-04-2017.
 */

public class CategoryPageAdapter extends BaseAdapter{
    LayoutInflater inflater;
    Context context;
    ArrayList<Category> categories;

    public CategoryPageAdapter(Context context,ArrayList<Category> categories){
        this.context = context;
        this.categories = categories;
        inflater = LayoutInflater.from(context);
    }



    public class ViewHolder {
        TextView name;
        CheckBox checkBox;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final CategoryPageAdapter.ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.category_items, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.category_name);
            holder.checkBox = (CheckBox) view.findViewById(R.id.check_items);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }


        holder.name.setText(categories.get(i).title);
        holder.checkBox.setChecked(categories.get(i).getChecked());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                categories.get(i).setChecked(isChecked);
            }
        });
        return view;
    }




    }
