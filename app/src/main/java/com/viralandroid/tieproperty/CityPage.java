package com.viralandroid.tieproperty;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SlidingPaneLayout;
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
    ImageView call_btn,email,menu_btn,callbtn,email_btn;
    public TextView txtPhn,txtEmail,select_city,properties,resale_properties,home_loans,emi_calculator,about_text,policy,terms_text;
    ArrayList<Cities> citiesfrom_api;
    ArrayList<Settings> settingsfrom_api;
    String logo,title,emails,phone,itunes_link,playstore_link,about,privacy,contact,terms;
    String city_id,cityId,agent_id,agent;
    SlidingPaneLayout slidingPaneLayout;
    int MY_PERMISSIONS_REQUEST_CALL_PHONE;
    ImageView facebook_btn,twitter_btn,linkedin_btn,instagram_btn,facebook_btn1,twitter_btn1,linkedin_btn1,instagram_btn1;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedinstanceState) {
        super.onCreate(savedinstanceState);
        setContentView(R.layout.city_page);
        gridView = (GridView) findViewById(R.id.city_list);
        call_btn = (ImageView) findViewById(R.id.call_btn);
        txtPhn = (TextView) findViewById(R.id.txtPhn);
        email = (ImageView) findViewById(R.id.email);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        menu_btn = (ImageView) findViewById(R.id.menu_btn);
        slidingPaneLayout = (SlidingPaneLayout) findViewById(R.id.sliding_pane_layout);
        select_city = (TextView) findViewById(R.id.select_city);
        properties = (TextView) findViewById(R.id.properties);
        resale_properties = (TextView) findViewById(R.id.resale_properties);
        home_loans = (TextView) findViewById(R.id.home_loans);
        facebook_btn = (ImageView) findViewById(R.id.facebook_btn);
        twitter_btn = (ImageView) findViewById(R.id.twitter_btn);
        instagram_btn = (ImageView) findViewById(R.id.instagram_btn);
        linkedin_btn = (ImageView) findViewById(R.id.linkedin_btn);
        emi_calculator = (TextView) findViewById(R.id.emi_calculator);
        about_text = (TextView) findViewById(R.id.about_text);
        policy = (TextView) findViewById(R.id.policy);
        terms_text = (TextView) findViewById(R.id.terms_text);
        facebook_btn1 = (ImageView) findViewById(R.id.facebook_btn_1);
        twitter_btn1 = (ImageView) findViewById(R.id.twitter_btn_1);
        instagram_btn1 = (ImageView) findViewById(R.id.instagram_btn_1);
        linkedin_btn1 = (ImageView) findViewById(R.id.linkedin_btn_1);
        callbtn = (ImageView) findViewById(R.id.callbtn);
        email_btn = (ImageView) findViewById(R.id.email_btn);






        call_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (ActivityCompat.checkSelfPermission(CityPage.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE);
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


        facebook_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.facebook.com/www.tieproperty.in/"));
                startActivity(intent);
            }
        });

        twitter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://twitter.com/tie_property"));
                startActivity(intent);
            }
        });

        instagram_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.instagram.com/tieproperty/"));
                startActivity(intent);
            }
        });

        linkedin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.linkedin.com/in/tie-property-508b81143/"));
                startActivity(intent);
            }
        });


        callbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (ActivityCompat.checkSelfPermission(CityPage.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE);
                    return;
                }

                startActivity(callIntent);
            }
        });

        email_btn.setOnClickListener(new View.OnClickListener() {
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


        facebook_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.facebook.com/www.tieproperty.in/"));
                startActivity(intent);
            }
        });

        twitter_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://twitter.com/tie_property"));
                startActivity(intent);
            }
        });

        instagram_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.instagram.com/tieproperty/"));
                startActivity(intent);
            }
        });

        linkedin_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.linkedin.com/in/tie-property-508b81143/"));
                startActivity(intent);
            }
        });



        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               slidingPaneLayout.openPane();
            }
        });
        cities = new ArrayList<>();
        citiesfrom_api = new ArrayList<>();
        settingsfrom_api = new ArrayList<>();

        select_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_city.setBackgroundColor(Color.parseColor("#181818"));
                select_city.setTextColor(Color.parseColor("#303C21"));
                properties.setBackgroundColor(Color.parseColor("#202020"));
                properties.setTextColor(Color.parseColor("#ffffff"));
                resale_properties.setBackgroundColor(Color.parseColor("#202020"));
                resale_properties.setTextColor(Color.parseColor("#ffffff"));
                home_loans.setBackgroundColor(Color.parseColor("#202020"));
                home_loans.setTextColor(Color.parseColor("#ffffff"));
                policy.setBackgroundColor(Color.parseColor("#202020"));
                policy.setTextColor(Color.parseColor("#ffffff"));
                about_text.setBackgroundColor(Color.parseColor("#202020"));
                about_text.setTextColor(Color.parseColor("#ffffff"));
                terms_text.setBackgroundColor(Color.parseColor("#202020"));
                terms_text.setTextColor(Color.parseColor("#ffffff"));
                emi_calculator.setBackgroundColor(Color.parseColor("#202020"));
                emi_calculator.setTextColor(Color.parseColor("#ffffff"));
                Intent intent = new Intent(CityPage.this, CityPage.class);
                startActivity(intent);
                finish();
                slidingPaneLayout.closePane();
            }
        });

        properties.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               properties.setBackgroundColor(Color.parseColor("#181818"));
               properties.setTextColor(Color.parseColor("#303C21"));
               select_city.setBackgroundColor(Color.parseColor("#202020"));
               select_city.setTextColor(Color.parseColor("#ffffff"));
               resale_properties.setBackgroundColor(Color.parseColor("#202020"));
               resale_properties.setTextColor(Color.parseColor("#ffffff"));
               home_loans.setBackgroundColor(Color.parseColor("#202020"));
               home_loans.setTextColor(Color.parseColor("#ffffff"));
               policy.setBackgroundColor(Color.parseColor("#202020"));
               policy.setTextColor(Color.parseColor("#ffffff"));
               about_text.setBackgroundColor(Color.parseColor("#202020"));
               about_text.setTextColor(Color.parseColor("#ffffff"));
               terms_text.setBackgroundColor(Color.parseColor("#202020"));
               terms_text.setTextColor(Color.parseColor("#ffffff"));
               emi_calculator.setBackgroundColor(Color.parseColor("#202020"));
               emi_calculator.setTextColor(Color.parseColor("#ffffff"));
               Intent intent = new Intent(CityPage.this,CityPage.class);
               startActivity(intent);
               finish();
               slidingPaneLayout.closePane();
           }
       });

        resale_properties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resale_properties.setBackgroundColor(Color.parseColor("#181818"));
                resale_properties.setTextColor(Color.parseColor("#303C21"));
                select_city.setBackgroundColor(Color.parseColor("#202020"));
                select_city.setTextColor(Color.parseColor("#ffffff"));
                properties.setBackgroundColor(Color.parseColor("#202020"));
                properties.setTextColor(Color.parseColor("#ffffff"));
                home_loans.setBackgroundColor(Color.parseColor("#202020"));
                home_loans.setTextColor(Color.parseColor("#ffffff"));
                policy.setBackgroundColor(Color.parseColor("#202020"));
                policy.setTextColor(Color.parseColor("#ffffff"));
                about_text.setBackgroundColor(Color.parseColor("#202020"));
                about_text.setTextColor(Color.parseColor("#ffffff"));
                terms_text.setBackgroundColor(Color.parseColor("#202020"));
                terms_text.setTextColor(Color.parseColor("#ffffff"));
                emi_calculator.setBackgroundColor(Color.parseColor("#202020"));
                emi_calculator.setTextColor(Color.parseColor("#ffffff"));
                Intent intent = new Intent(CityPage.this,ResaleProperties.class);
                startActivity(intent);
                slidingPaneLayout.closePane();
            }
        });



        home_loans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home_loans.setBackgroundColor(Color.parseColor("#181818"));
                home_loans.setTextColor(Color.parseColor("#303C21"));
                select_city.setBackgroundColor(Color.parseColor("#202020"));
                select_city.setTextColor(Color.parseColor("#ffffff"));
                properties.setBackgroundColor(Color.parseColor("#202020"));
                properties.setTextColor(Color.parseColor("#ffffff"));
                resale_properties.setBackgroundColor(Color.parseColor("#202020"));
                resale_properties.setTextColor(Color.parseColor("#ffffff"));
                policy.setBackgroundColor(Color.parseColor("#202020"));
                policy.setTextColor(Color.parseColor("#ffffff"));
                about_text.setBackgroundColor(Color.parseColor("#202020"));
                about_text.setTextColor(Color.parseColor("#ffffff"));
                terms_text.setBackgroundColor(Color.parseColor("#202020"));
                terms_text.setTextColor(Color.parseColor("#ffffff"));
                emi_calculator.setBackgroundColor(Color.parseColor("#202020"));
                emi_calculator.setTextColor(Color.parseColor("#ffffff"));
                Intent intent = new Intent(CityPage.this,HomeLoansPage.class);
                startActivity(intent);
                slidingPaneLayout.closePane();
            }
        });



        emi_calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emi_calculator.setBackgroundColor(Color.parseColor("#181818"));
                emi_calculator.setTextColor(Color.parseColor("#303C21"));
                select_city.setBackgroundColor(Color.parseColor("#202020"));
                select_city.setTextColor(Color.parseColor("#ffffff"));
                properties.setBackgroundColor(Color.parseColor("#202020"));
                properties.setTextColor(Color.parseColor("#ffffff"));
                resale_properties.setBackgroundColor(Color.parseColor("#202020"));
                resale_properties.setTextColor(Color.parseColor("#ffffff"));
                home_loans.setBackgroundColor(Color.parseColor("#202020"));
                home_loans.setTextColor(Color.parseColor("#ffffff"));
                policy.setBackgroundColor(Color.parseColor("#202020"));
                policy.setTextColor(Color.parseColor("#ffffff"));
                about_text.setBackgroundColor(Color.parseColor("#202020"));
                about_text.setTextColor(Color.parseColor("#ffffff"));
                terms_text.setBackgroundColor(Color.parseColor("#202020"));
                terms_text.setTextColor(Color.parseColor("#ffffff"));
                Intent intent = new Intent(CityPage.this,EmiCalculatorPage.class);
                startActivity(intent);
                slidingPaneLayout.closePane();
            }
        });

        about_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                about_text.setBackgroundColor(Color.parseColor("#181818"));
                about_text.setTextColor(Color.parseColor("#303C21"));
                emi_calculator.setBackgroundColor(Color.parseColor("#202020"));
                emi_calculator.setTextColor(Color.parseColor("#ffffff"));
                select_city.setBackgroundColor(Color.parseColor("#202020"));
                select_city.setTextColor(Color.parseColor("#ffffff"));
                properties.setBackgroundColor(Color.parseColor("#202020"));
                properties.setTextColor(Color.parseColor("#ffffff"));
                resale_properties.setBackgroundColor(Color.parseColor("#202020"));
                resale_properties.setTextColor(Color.parseColor("#ffffff"));
                home_loans.setBackgroundColor(Color.parseColor("#202020"));
                home_loans.setTextColor(Color.parseColor("#ffffff"));
                policy.setBackgroundColor(Color.parseColor("#202020"));
                policy.setTextColor(Color.parseColor("#ffffff"));
                terms_text.setBackgroundColor(Color.parseColor("#202020"));
                terms_text.setTextColor(Color.parseColor("#ffffff"));
                Intent intent = new Intent(CityPage.this,AboutUsPage.class);
                intent.putExtra("aboutus",about);
                intent.putExtra("tielogo",logo);
                startActivity(intent);
                slidingPaneLayout.closePane();
            }
        });

        policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                policy.setBackgroundColor(Color.parseColor("#181818"));
                policy.setTextColor(Color.parseColor("#303C21"));
                about_text.setBackgroundColor(Color.parseColor("#202020"));
                about_text.setTextColor(Color.parseColor("#ffffff"));
                emi_calculator.setBackgroundColor(Color.parseColor("#202020"));
                emi_calculator.setTextColor(Color.parseColor("#ffffff"));
                select_city.setBackgroundColor(Color.parseColor("#202020"));
                select_city.setTextColor(Color.parseColor("#ffffff"));
                properties.setBackgroundColor(Color.parseColor("#202020"));
                properties.setTextColor(Color.parseColor("#ffffff"));
                resale_properties.setBackgroundColor(Color.parseColor("#202020"));
                resale_properties.setTextColor(Color.parseColor("#ffffff"));
                home_loans.setBackgroundColor(Color.parseColor("#202020"));
                home_loans.setTextColor(Color.parseColor("#ffffff"));
                terms_text.setBackgroundColor(Color.parseColor("#202020"));
                terms_text.setTextColor(Color.parseColor("#ffffff"));
                Intent intent = new Intent(CityPage.this,PrivacyPolicyPage.class);
                intent.putExtra("privacy",privacy);
                startActivity(intent);
                slidingPaneLayout.closePane();
            }
        });

        terms_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                terms_text.setBackgroundColor(Color.parseColor("#181818"));
                terms_text.setTextColor(Color.parseColor("#303C21"));
                policy.setBackgroundColor(Color.parseColor("#202020"));
                policy.setTextColor(Color.parseColor("#ffffff"));
                about_text.setBackgroundColor(Color.parseColor("#202020"));
                about_text.setTextColor(Color.parseColor("#ffffff"));
                emi_calculator.setBackgroundColor(Color.parseColor("#202020"));
                emi_calculator.setTextColor(Color.parseColor("#ffffff"));
                select_city.setBackgroundColor(Color.parseColor("#202020"));
                select_city.setTextColor(Color.parseColor("#ffffff"));
                properties.setBackgroundColor(Color.parseColor("#202020"));
                properties.setTextColor(Color.parseColor("#ffffff"));
                resale_properties.setBackgroundColor(Color.parseColor("#202020"));
                resale_properties.setTextColor(Color.parseColor("#ffffff"));
                home_loans.setBackgroundColor(Color.parseColor("#202020"));
                home_loans.setTextColor(Color.parseColor("#ffffff"));
                Intent intent = new Intent(CityPage.this,TermsPage.class);
                intent.putExtra("terms",terms);
                startActivity(intent);
                slidingPaneLayout.closePane();
            }
        });




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
                            privacy = result.get("privacy_policy").getAsString();
                            contact = result.get("contact").getAsString();
                            terms = result.get("terms").getAsString();

                        }catch (Exception e1){
                            e1.printStackTrace();
                        }

                    }
                });
    }










}
