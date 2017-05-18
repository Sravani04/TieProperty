package com.viralandroid.tieproperty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by T on 17-05-2017.
 */

public class CommissionsPageAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    Commissions commissions;

    public CommissionsPageAdapter(Context context,Commissions commissions){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.commissions = commissions;
    }
    @Override
    public int getCount() {
        return 1;
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
        View item_view = inflater.inflate(R.layout.commissions_list_items,null);
        return item_view;
    }
}
