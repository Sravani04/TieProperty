package com.viralandroid.tieproperty;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by T on 02-05-2017.
 */

public class AreaFragmentAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
     ArrayList<Areas> areas;


    public AreaFragmentAdapter(Context context, ArrayList<Areas> areas) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.areas = areas;

    }

    public class ViewHolder {
        TextView name;
        CheckBox checkBox;
    }


    @Override
    public int getCount() {
        return areas.size();
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
    public View getView(final int position, View view, ViewGroup viewGroup) {
        final AreaFragmentAdapter.ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.localities_list_items, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.area_name);
            holder.checkBox = (CheckBox) view.findViewById(R.id.check);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(areas.get(position).title);
        holder.checkBox.setChecked(areas.get(position).getChecked());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                areas.get(position).setChecked(isChecked);
//                    Log.e("checkd","checked true");
                Log.e("print",areas.get(position).id);
            }
        });


        return view;
    }





}
