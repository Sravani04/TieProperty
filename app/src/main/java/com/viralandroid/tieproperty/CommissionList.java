package com.viralandroid.tieproperty;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by T on 17-05-2017.
 */

public class CommissionList extends Activity {
    ImageView close_btn;
    ListView listView;
    CommissionsPageAdapter adapter;
    Commissions commissions;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commission_list);
        close_btn = (ImageView) findViewById(R.id.close_btn);
        listView = (ListView) findViewById(R.id.commissions_list);
        adapter = new CommissionsPageAdapter(CommissionList.this,commissions);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
