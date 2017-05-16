package com.viralandroid.tieproperty;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;


/**
 * Created by T on 15-05-2017.
 */

public class AgentHomePage extends Activity {
    ImageView menu_btn;
    SlidingPaneLayout slidingPaneLayout;
    ListView listView;
    String agent_id;
    ArrayList<Agents> agentsfrom_api;
    AgentsHomePageAdapter agentsHomePageAdapter;
    TextView callback_customers,commissions,new_property,properties,site_visits,my_account,logout_btn;


    @Override
    public void onCreate(Bundle savedinstanceState) {
        super.onCreate(savedinstanceState);
        setContentView(R.layout.agents_home_page);
        menu_btn = (ImageView) findViewById(R.id.menu_btn);
        listView = (ListView) findViewById(R.id.agents_list);
        slidingPaneLayout = (SlidingPaneLayout) findViewById(R.id.sliding_pane_layout);
        callback_customers = (TextView) findViewById(R.id.callback_customers);
        commissions = (TextView) findViewById(R.id.commissions);
        new_property = (TextView) findViewById(R.id.new_property);
        properties = (TextView) findViewById(R.id.properties);
        site_visits = (TextView) findViewById(R.id.site_visits);
        my_account = (TextView) findViewById(R.id.my_account);
        logout_btn = (TextView) findViewById(R.id.logout_btn);
        agentsfrom_api = new ArrayList<>();

        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingPaneLayout.openPane();
            }
        });

        if (getIntent()!=null && getIntent().hasExtra("agentId")){
            agent_id = getIntent().getStringExtra("agentId");
        }

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Session.SetUserId(AgentHomePage.this,"-1");
                AgentHomePage.this.onBackPressed();
            }
        });

        agentsHomePageAdapter = new AgentsHomePageAdapter(this,agentsfrom_api);
        listView.setAdapter(agentsHomePageAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        callback_customers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback_customers.setBackgroundColor(Color.parseColor("#CCCCCC"));
                callback_customers.setTextColor(Color.parseColor("#09366C"));
                new_property.setBackgroundColor(Color.parseColor("#ffffff"));
                new_property.setTextColor(Color.parseColor("#000000"));
                site_visits.setBackgroundColor(Color.parseColor("#ffffff"));
                site_visits.setTextColor(Color.parseColor("#000000"));
                Intent intent = new Intent(AgentHomePage.this, CallbackCustomersPage.class);
                intent.putExtra("agentId",agent_id);
                startActivity(intent);
                slidingPaneLayout.closePane();
            }
        });

        new_property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_property.setBackgroundColor(Color.parseColor("#CCCCCC"));
                new_property.setTextColor(Color.parseColor("#09366C"));
                callback_customers.setBackgroundColor(Color.parseColor("#ffffff"));
                callback_customers.setTextColor(Color.parseColor("#000000"));
                site_visits.setBackgroundColor(Color.parseColor("#ffffff"));
                site_visits.setTextColor(Color.parseColor("#000000"));
                Intent intent = new Intent(getApplicationContext(),NewPropertyScreen.class);
                startActivity(intent);
                slidingPaneLayout.closePane();
            }
        });

        site_visits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                site_visits.setBackgroundColor(Color.parseColor("#CCCCCC"));
                site_visits.setTextColor(Color.parseColor("#09366C"));
                new_property.setBackgroundColor(Color.parseColor("#ffffff"));
                new_property.setTextColor(Color.parseColor("#000000"));
                callback_customers.setBackgroundColor(Color.parseColor("#ffffff"));
                callback_customers.setTextColor(Color.parseColor("#000000"));
                Intent intent = new Intent(getApplicationContext(),SiteVisitScreen.class);
                startActivity(intent);
                slidingPaneLayout.closePane();
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
                .setBodyParameter("agent_id",Session.GetUserId(AgentHomePage.this))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        for (int i=0;i<result.size();i++){
                            Agents agents = new Agents(result.get(i).getAsJsonObject(),AgentHomePage.this);
                            agentsfrom_api.add(agents);
                        }
                        agentsHomePageAdapter.notifyDataSetChanged();
                    }
                });

    }
}
