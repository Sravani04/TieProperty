package com.viralandroid.tieproperty;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import java.util.ArrayList;

public class MainActivity extends Activity {
    ListView listView;
    PropertyListAdapter propertyListAdapter;
    TextView select_city;
    String cities,mobile,city_id,cat_id,areas_id,price_from,price_to;
    ImageView back_btn,location,select_category,previous_btn,next_btn;
    ArrayList<Properties> propertiesfrom_api;
    Cities citiesfrom_api;
    ViewPager viewPager;
    TrendingPropertiesAdapter  trendingPropertiesAdapter;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    ArrayList<TrendingProperties> trendingPropertiesfrom_api;
    Handler handler;
    Runnable Update;
    View headerView;




    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.property_list);
       headerView = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.trending_properties, null, false);
        listView = (ListView) findViewById(R.id.property_list);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        select_city = (TextView) findViewById(R.id.select_city);
        location = (ImageView) findViewById(R.id.location);
        select_category = (ImageView) findViewById(R.id.select_category);
        viewPager = (ViewPager) headerView.findViewById(R.id.view_pager);
        previous_btn = (ImageView) headerView.findViewById(R.id.previous_btn);
        next_btn = (ImageView) headerView.findViewById(R.id.next_btn);
        propertiesfrom_api = new ArrayList<>();
        trendingPropertiesfrom_api = new ArrayList<>();
        listView.addHeaderView(headerView);


        price_from="";
        price_to="";

//        NUM_PAGES =trendingPropertiesfrom_api.size();

//         handler = new Handler();
//         Update = new Runnable() {
//            public void run() {
//                if (currentPage == NUM_PAGES) {
//                    currentPage = 0;
//                }
//                viewPager.setCurrentItem(currentPage++, true);
//                //viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
//            }
//        };
//        Timer swipeTimer = new Timer();
//        swipeTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(Update);
//            }
//        }, 3000, 3000);



        if (getIntent()!=null && getIntent().hasExtra("city")){
            cities = getIntent().getStringExtra("city");
            city_id = getIntent().getStringExtra("id");
            mobile = getIntent().getStringExtra("phone");

        }

        if (getIntent()!=null && getIntent().hasExtra("area_id")){
            areas_id = getIntent().getStringExtra("area_id");
            Log.e("areaResponse",areas_id);
            cat_id = getIntent().getStringExtra("cat_id");
            Log.e("catResponse",cat_id);
            city_id = getIntent().getStringExtra("id");
            Log.e("cityidresponse",city_id);
            price_from = getIntent().getStringExtra("range_from");
            Log.e("rang",price_from);
            price_to = getIntent().getStringExtra("range_to");
            Log.e("rangeto",price_to);

        }


        trendingPropertiesAdapter = new TrendingPropertiesAdapter(MainActivity.this,trendingPropertiesfrom_api,propertiesfrom_api,this);
        viewPager.setAdapter(trendingPropertiesAdapter);

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
            }
        });

        previous_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0){
                    previous_btn.setVisibility(View.GONE);
                }else {
                    previous_btn.setVisibility(View.VISIBLE);
                }

                if (position == trendingPropertiesAdapter.getCount()-1){
                    next_btn.setVisibility(View.GONE);
                }else {
                    next_btn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        select_city.setText(cities);
        select_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CityPage.class);
                startActivity(intent);
            }
        });

        select_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,FilterPage.class);
                intent.putExtra("id",city_id);
                startActivity(intent);
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CityPage.class);
                startActivity(intent);
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               MainActivity.this.onBackPressed();
            }
        });

        propertyListAdapter = new PropertyListAdapter(MainActivity.this,mobile,propertiesfrom_api);
        listView.setAdapter(propertyListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,PropertyDetailPage.class);
                intent.putExtra("property",propertiesfrom_api.get(i-1));
                intent.putExtra("mobile",mobile);
                startActivity(intent);
            }
        });

        get_properties();
        get_trending_properties();
    }

    public void get_properties(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(this)
                .load(Session.SERVER_URL+"properties.php")
                .setBodyParameter("city",city_id)
                .setBodyParameter("area",areas_id)
                .setBodyParameter("category",cat_id)
                .setBodyParameter("from",price_from)
                .setBodyParameter("to",price_to)
                .asJsonArray()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonArray>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonArray> result) {
                        try {
                            if (progressDialog != null)
                                progressDialog.dismiss();
                            Log.e("response", result.getServedFrom().toString());


                            if (e != null) {
                                e.printStackTrace();
                                Log.e("error", e.getLocalizedMessage());

                            } else
                                try {
                                    for (int i = 0; i < result.getResult().size(); i++) {
                                        Properties properties = new Properties(result.getResult().get(i).getAsJsonObject(), MainActivity.this);
                                        propertiesfrom_api.add(properties);
                                    }
                                    propertyListAdapter.notifyDataSetChanged();

                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }


                    }
                });
    }



    public void get_trending_properties(){
        Ion.with(this)
                .load(Session.SERVER_URL+"trending-properties.php")
                .setBodyParameter("city",city_id)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        try {
                            for (int i = 0; i < result.size(); i++) {
                                TrendingProperties trendingProperties = new TrendingProperties(result.get(i).getAsJsonObject(), MainActivity.this);
                                trendingPropertiesfrom_api.add(trendingProperties);
                            }
                            trendingPropertiesAdapter.notifyDataSetChanged();
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }
                    }
                });
    }


    public void get_single_property(String id){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(this)
                .load(Session.SERVER_URL+"properties.php")
                .setBodyParameter("property_id",id)
                .asJsonArray()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonArray>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonArray> result) {

                        Log.e("response",String.valueOf(result.getResult().size()));
                        try {
                            if (progressDialog != null)
                                progressDialog.dismiss();


                            if (e != null) {
                                e.printStackTrace();
                                Log.e("error", e.getLocalizedMessage());

                            } else
                                try {
                                    for (int i = 0; i < result.getResult().size(); i++) {
                                        Properties properties = new Properties(result.getResult().get(i).getAsJsonObject(), MainActivity.this);
                                        Intent intent = new Intent(MainActivity.this,PropertyDetailPage.class);
                                        intent.putExtra("property",properties);
                                        intent.putExtra("mobile",mobile);
                                        startActivity(intent);
                                    }
                                 //   propertyListAdapter.notifyDataSetChanged();

                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }


                    }
                });
    }



}
