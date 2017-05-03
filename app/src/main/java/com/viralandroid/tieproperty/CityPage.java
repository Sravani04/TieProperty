package com.viralandroid.tieproperty;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import static android.R.id.message;

/**
 * Created by T on 24-04-2017.
 */

public class CityPage extends Activity {
    CityPageAdapter cityPageAdapter;
    GridView gridView;
    ArrayList<String> cities;
    ImageView call_btn,email;
    TextView txtPhn,txtEmail;
    ArrayList<Cities> citiesfrom_api;
    ArrayList<Settings> settingsfrom_api;
    String logo,title,emails,phone,itunes_link,playstore_link,about;
    String city_id;


    @Override
    public void onCreate(Bundle savedinstanceState) {
        super.onCreate(savedinstanceState);
        setContentView(R.layout.city_list);
        gridView = (GridView) findViewById(R.id.city_list);
        call_btn = (ImageView) findViewById(R.id.call_btn);
        txtPhn = (TextView) findViewById(R.id.txtPhn);
        email = (ImageView) findViewById(R.id.email);
        txtEmail = (TextView) findViewById(R.id.txtEmail);

        call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (ActivityCompat.checkSelfPermission(CityPage.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Email = new Intent(Intent.ACTION_SEND);
                Email.setType("text/email");
                Email.putExtra(Intent.EXTRA_EMAIL,new String[]{emails});
                Email.putExtra(Intent.EXTRA_SUBJECT,"Add your Subject");
                Email.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(Email, "Send Feedback:"));
            }
        });
        cities = new ArrayList<>();
        citiesfrom_api = new ArrayList<>();
        settingsfrom_api = new ArrayList<>();


        cityPageAdapter = new CityPageAdapter(CityPage.this,citiesfrom_api);
        gridView.setAdapter(cityPageAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(CityPage.this,MainActivity.class);
                intent.putExtra("city",citiesfrom_api.get(i).title);
                intent.putExtra("id",citiesfrom_api.get(i).id);
                intent.putExtra("phone",phone);
                startActivity(intent);
            }
        });

        get_cities();
        get_details();

    }

    public void get_cities(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(this)
                .load(Session.SERVER_URL+"cities.php")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        try {
                            for (int i=0;i<result.size();i++){
                                Log.e("response",result.get(i).toString());
                                Cities cities = new Cities(result.get(i).getAsJsonObject(),CityPage.this);
                                citiesfrom_api.add(cities);
                            }

                            cityPageAdapter.notifyDataSetChanged();
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }

                    }
                });
    }

    public void get_details(){
        Ion.with(this)
                .load(Session.SERVER_URL+"settings.php")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try {
                            logo = result.get("logo").getAsString();
                            title = result.get("title").getAsString();
                            emails = result.get("email").getAsString();
                            phone = result.get("phone").getAsString();
                            itunes_link = result.get("itunes_link").getAsString();
                            playstore_link = result.get("playstore_link").getAsString();
                            about = result.get("about").getAsString();
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }

                    }
                });
    }








}
