//package com.viralandroid.tieproperty;
//
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.google.gson.JsonArray;
//import com.koushikdutta.async.future.FutureCallback;
//import com.koushikdutta.ion.Ion;
//import com.koushikdutta.ion.Response;
//
//import java.util.ArrayList;
//
///**
// * Created by T on 12-05-2017.
// */
//
//public class AgentsProperties extends Activity {
//    ImageView back_btn,edit_profile;
//    TextView logout_btn;
//    String agent_id;
//    ListView listView;
//    Cities cities;
//    AgentsPropertiesAdapter agentsPropertiesAdapter;
//    ArrayList<Properties> propertiesfrom_api;
//    @Override
//    public void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.agents_acount);
//        back_btn = (ImageView) findViewById(R.id.back_btn);
//        logout_btn = (TextView) findViewById(R.id.logout_btn);
//        edit_profile = (ImageView) findViewById(R.id.edit_profile);
//        listView = (ListView) findViewById(R.id.properties_list);
//        propertiesfrom_api = new ArrayList<>();
//
//        if (getIntent()!=null && getIntent().hasExtra("agentId")){
//            agent_id = getIntent().getStringExtra("agentId");
//        }
//
//        agentsPropertiesAdapter = new AgentsPropertiesAdapter(this,cities,propertiesfrom_api);
//        listView.setAdapter(agentsPropertiesAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(AgentsProperties.this,PropertyDetailPage.class);
//                intent.putExtra("property",propertiesfrom_api.get(i));
//                startActivity(intent);
//            }
//        });
//        edit_profile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(AgentsProperties.this,AgentsEditProfile.class);
//                intent.putExtra("agentsId",agent_id);
//                startActivity(intent);
//            }
//        });
//
//
//        logout_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Session.SetUserId(AgentsProperties.this,"-1");
//                AgentsProperties.this.onBackPressed();
//            }
//        });
//
//        get_properties();
//
//
//    }
//
//    public void get_properties(){
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("please wait");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//        Ion.with(this)
//                .load(Session.SERVER_URL+"properties.php")
//                .setBodyParameter("city","1")
//                .asJsonArray()
//                .withResponse()
//                .setCallback(new FutureCallback<Response<JsonArray>>() {
//                    @Override
//                    public void onCompleted(Exception e, Response<JsonArray> result) {
//                        try {
//                            if (progressDialog != null)
//                                progressDialog.dismiss();
//                            Log.e("response", result.getServedFrom().toString());
//
//
//                            if (e != null) {
//                                e.printStackTrace();
//                                Log.e("error", e.getLocalizedMessage());
//
//                            } else
//                                try {
//                                    for (int i = 0; i < result.getResult().size(); i++) {
//                                        Properties properties = new Properties(result.getResult().get(i).getAsJsonObject(), AgentsProperties.this);
//                                        propertiesfrom_api.add(properties);
//                                    }
//                                    agentsPropertiesAdapter.notifyDataSetChanged();
//
//                                } catch (Exception e1) {
//                                    e1.printStackTrace();
//                                }
//                        }catch (Exception e1){
//                            e1.printStackTrace();
//                        }
//
//
//                    }
//                });
//    }
//}
