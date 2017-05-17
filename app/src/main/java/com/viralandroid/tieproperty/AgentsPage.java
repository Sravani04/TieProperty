package com.viralandroid.tieproperty;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by T on 17-05-2017.
 */

public class AgentsPage extends Activity{
    ImageView back_btn;
    ListView listView;
    AgentsHomePageAdapter adapter;
    ArrayList<Agents> agentsfrom_api;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agents_list_items);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AgentsPage.this.onBackPressed();
            }
        });
        listView = (ListView) findViewById(R.id.agents_list);
        agentsfrom_api = new ArrayList<>();
        adapter = new AgentsHomePageAdapter(this,agentsfrom_api);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        get_agents();



    }

    public void get_agents(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(this)
                .load(Session.SERVER_URL+"agents.php")
                .setBodyParameter("agent_id",Session.GetUserId(getApplicationContext()))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        for (int i=0;i<result.size();i++){
                            Agents agents = new Agents(result.get(i).getAsJsonObject(),getApplicationContext());
                            agentsfrom_api.add(agents);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

    }




}
