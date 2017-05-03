package com.viralandroid.tieproperty;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by T on 02-05-2017.
 */

public class AreaFragmentAdapter extends BaseAdapter implements Filterable {
    Context mContext;
    LayoutInflater inflater;
    private ArrayList<Areas> arraylist;
    ArrayList<Areas> mStringFilterList;
    ValueFilter valueFilter;

    public AreaFragmentAdapter(Context context, ArrayList<Areas> arraylist) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = arraylist;
        mStringFilterList = arraylist;

    }

    public class ViewHolder {
        TextView name;
        CheckBox checkBox;
    }


    @Override
    public int getCount() {
        return arraylist.size();
    }

    @Override
    public Object getItem(int i) {
        return arraylist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
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
            final Areas areas = arraylist.get(position);

            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    areas.setChecked(isChecked);
                    Log.e("checkd","checked true");
                    Log.e("print",areas.getId());
                }
            });

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(arraylist.get(position).title);
        holder.checkBox.setChecked(arraylist.get(position).getChecked());
        Log.e("message",arraylist.get(position).getChecked().toString());
        Log.e("message1",arraylist.get(position).getName());
        Log.e("id",arraylist.get(position).getId());


        return view;
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }


    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            Log.e("results",results.toString());

            if (constraint != null && constraint.length() > 0) {
                ArrayList<Areas> filterList = new ArrayList<Areas>();
                for (int i = 0; i < mStringFilterList.size(); i++) {
                    if ((mStringFilterList.get(i).getName().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {

                        Areas areas = new Areas(mStringFilterList.get(i)
                                .getName(), mStringFilterList.get(i)
                                .getId(), mStringFilterList.get(i));

                        filterList.add(areas);
                        Log.e("results",results.toString());
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
                Log.e("results",filterList.toString());
            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            arraylist = (ArrayList<Areas>) results.values;
            notifyDataSetChanged();
        }
    }
}
