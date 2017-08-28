package com.viralandroid.tieproperty;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static android.R.id.message;

public class MainActivity extends Activity implements AbsListView.OnScrollListener{
    ListView listView;
    PropertyListAdapter propertyListAdapter;
    CityAdapter cityAdapter;
    SlidingPaneLayout slidingPaneLayout;
    TrendingPropertiesAdapter  trendingPropertiesAdapter;
    ArrayList<TrendingProperties> trendingPropertiesfrom_api;
    ArrayList<Properties> propertiesfrom_api;
    ArrayList<Cities> categories;
    Cities citiesfrom_api;
    ViewPager viewPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    Handler handler;
    Runnable Update;
    View headerView;
    String cities,mobile,city_id,price_from,price_to;
    public TextView select_city,properties,resale_properties,home_loans,emi_calculator,
            about_text,policy,terms_text,property_name,city,trending_properties;
    ImageView facebook_btn,twitter_btn,linkedin_btn,instagram_btn,
            callbtn,email_btn,menu_btn,back_btn,location,select_category,previous_btn,next_btn,open_menu,drop_btn,open_dropdown;
    int MY_PERMISSIONS_REQUEST_CALL_PHONE;
    LinearLayout services_sub_menu,services_dropdown,progress_holder,trending_slide,cities_dropdown,cities_sub_menu;
    String citiesId = "";
    String areas_id ="";
    String cat_id ="";
    TextView footer_text;
    private  int previouslast;




    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.property_list);
        propertiesfrom_api = new ArrayList<>();
        trendingPropertiesfrom_api = new ArrayList<>();
        categories = new ArrayList<>();
        price_from="";
        price_to="";
        listView = (ListView) findViewById(R.id.property_list);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        select_city = (TextView) findViewById(R.id.select_city);
        location = (ImageView) findViewById(R.id.location);
        select_category = (ImageView) findViewById(R.id.select_category);
        facebook_btn = (ImageView) findViewById(R.id.facebook_btn);
        twitter_btn = (ImageView) findViewById(R.id.twitter_btn);
        instagram_btn = (ImageView) findViewById(R.id.instagram_btn);
        linkedin_btn = (ImageView) findViewById(R.id.linkedin_btn);
        callbtn = (ImageView) findViewById(R.id.callbtn);
        email_btn = (ImageView) findViewById(R.id.email_btn);
        menu_btn = (ImageView) findViewById(R.id.menu_btn);
        slidingPaneLayout = (SlidingPaneLayout) findViewById(R.id.sliding_pane_layout);
        services_dropdown = (LinearLayout) findViewById(R.id.services_dropdown);
        services_sub_menu = (LinearLayout) findViewById(R.id.services_sub_menu);
        open_menu = (ImageView) findViewById(R.id.open_menu);
        drop_btn = (ImageView) findViewById(R.id.drop_btn);
        city = (TextView) findViewById(R.id.city);
        properties = (TextView) findViewById(R.id.properties);
        resale_properties = (TextView) findViewById(R.id.resale_properties);
        home_loans = (TextView) findViewById(R.id.home_loans);
        emi_calculator = (TextView) findViewById(R.id.emi_calculator);
        about_text = (TextView) findViewById(R.id.about_text);
        policy = (TextView) findViewById(R.id.policy);
        terms_text = (TextView) findViewById(R.id.terms_text);
        progress_holder = (LinearLayout) findViewById(R.id.progress_holder);
        progress_holder.setVisibility(View.GONE);
        cities_dropdown = (LinearLayout) findViewById(R.id.cities_dropdown);
        cities_sub_menu = (LinearLayout) findViewById(R.id.cities_sub_menu);
        open_dropdown = (ImageView) findViewById(R.id.open_dropdown);
        trending_properties = (TextView) findViewById(R.id.trending_properties);


//        headerView = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.trending_properties, null, false);
//        viewPager = (ViewPager) headerView.findViewById(R.id.view_pager);
//        previous_btn = (ImageView) headerView.findViewById(R.id.previous_btn);
//        next_btn = (ImageView) headerView.findViewById(R.id.next_btn);
//        trending_slide = (LinearLayout) headerView.findViewById(R.id.trending_slide);
//        property_name = (TextView) headerView.findViewById(R.id.property_name);
//        listView.addHeaderView(headerView);
//        NUM_PAGES =trendingPropertiesfrom_api.size();
//        handler = new Handler();
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


        callbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                JsonParser jsonParser = new JsonParser();
                if(!Session.GetSettings(MainActivity.this).equals("-1")) {
                    JsonObject parse = (JsonObject) jsonParser.parse(Session.GetSettings(MainActivity.this));
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + parse.get("phone").getAsString()));
                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                                MY_PERMISSIONS_REQUEST_CALL_PHONE);
                        return;
                    }

                    startActivity(callIntent);
                }
            }
        });

        email_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonParser jsonParser = new JsonParser();
                if(!Session.GetSettings(MainActivity.this).equals("-1")) {
                    JsonObject parse = (JsonObject) jsonParser.parse(Session.GetSettings(MainActivity.this));
                    Intent Email = new Intent(Intent.ACTION_SEND);
                    Email.setType("text/email");
                    Email.putExtra(Intent.EXTRA_EMAIL, new String[]{parse.get("email").getAsString()});
                    Email.putExtra(Intent.EXTRA_SUBJECT, "Add your Subject");
                    Email.putExtra(Intent.EXTRA_TEXT, message);
                    startActivity(Intent.createChooser(Email, "Send Feedback:"));
                }
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

        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingPaneLayout.openPane();
            }
        });


        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               show_alert_edit();
            }
        });

        drop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_alert_edit();
            }
        });



        select_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelected(0);
                show_alert_edit();
//                Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                startActivity(intent);
                slidingPaneLayout.closePane();
            }
        });

        select_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,FilterPage.class);
                intent.putExtra("id",citiesId);
                intent.putExtra("title",city.getText().toString());
                startActivityForResult(intent,1);
            }
        });

//        properties.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                setSelected(1);
//                Intent intent = new Intent(MainActivity.this,MainActivity.class);
//                startActivity(intent);
//                finish();
//                slidingPaneLayout.closePane();
//            }
//        });

        resale_properties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelected(2);
                Intent intent = new Intent(MainActivity.this,ResaleProperties.class);
                startActivity(intent);
                slidingPaneLayout.closePane();
            }
        });

        trending_properties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelected(3);
                show_trending_properties();
                slidingPaneLayout.closePane();
            }
        });

        home_loans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelected(4);
                Intent intent = new Intent(MainActivity.this,HomeLoansPage.class);
                startActivity(intent);
                slidingPaneLayout.closePane();
            }
        });

        emi_calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelected(5);
                Intent intent = new Intent(MainActivity.this,EmiCalculatorPage.class);
                startActivity(intent);
                slidingPaneLayout.closePane();
            }
        });

        about_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonParser jsonParser = new JsonParser();
                if(!Session.GetSettings(MainActivity.this).equals("-1")) {
                    JsonObject parse= (JsonObject) jsonParser.parse(Session.GetSettings(MainActivity.this));
                    setSelected(6);
                    Intent intent = new Intent(MainActivity.this, AboutUsPage.class);
                    intent.putExtra("aboutus", parse.get("about").getAsString());
                    intent.putExtra("tielogo", parse.get("logo").getAsString());
                    startActivity(intent);
                    slidingPaneLayout.closePane();
                }
            }
        });

        policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonParser jsonParser = new JsonParser();
                if(!Session.GetSettings(MainActivity.this).equals("-1")) {
                    JsonObject parse = (JsonObject) jsonParser.parse(Session.GetSettings(MainActivity.this));
                    setSelected(7);
                    Intent intent = new Intent(MainActivity.this, PrivacyPolicyPage.class);
                    intent.putExtra("privacy", parse.get("privacy_policy").getAsString());
                    startActivity(intent);
                    slidingPaneLayout.closePane();
                }

            }
        });

        terms_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonParser jsonParser = new JsonParser();
                if(!Session.GetSettings(MainActivity.this).equals("-1")) {
                    JsonObject parse = (JsonObject) jsonParser.parse(Session.GetSettings(MainActivity.this));
                    setSelected(8);
                    Intent intent = new Intent(MainActivity.this, TermsPage.class);
                    intent.putExtra("terms", parse.get("terms").getAsString());
                    startActivity(intent);
                    slidingPaneLayout.closePane();
                }
            }
        });

        services_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (services_sub_menu.getVisibility() == View.GONE) {
                    services_sub_menu.setVisibility(View.VISIBLE);
                    setSelected(9);
                    JsonParser jsonParser = new JsonParser();
                    if (!Session.GetServices(MainActivity.this).equals("-1")) {
                        JsonArray parse = (JsonArray) jsonParser.parse(Session.GetServices(MainActivity.this));
                        display_custom(parse);
                        open_menu.setImageResource(R.drawable.down);
                    }
                }else {
                    services_sub_menu.setVisibility(View.GONE);
                    open_menu.setImageResource(R.drawable.uparrow);
                }

            }
        });


        cities_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cities_sub_menu.getVisibility() == View.GONE) {
                    cities_sub_menu.setVisibility(View.VISIBLE);
                    setSelected(1);
                    JsonParser jsonParser = new JsonParser();
                    if (!Session.GetCities(MainActivity.this).equals("-1")) {
                        JsonArray parse = (JsonArray) jsonParser.parse(Session.GetCities(MainActivity.this));
                        display_cities(parse);
                        open_dropdown.setImageResource(R.drawable.down);
                    }
                }else {
                    cities_sub_menu.setVisibility(View.GONE);
                    open_dropdown.setImageResource(R.drawable.uparrow);
                }

            }
        });



//        location.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this,CityPage.class);
//                startActivity(intent);
//            }
//        });
//
//        back_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               MainActivity.this.onBackPressed();
//            }
//        });

        JsonParser jsonParser = new JsonParser();
        if (!Session.GetCities(MainActivity.this).equals("-1")) {
            JsonArray parse = (JsonArray) jsonParser.parse(Session.GetCities(MainActivity.this));
            Log.e("cities",parse.toString());
            for (int i = 0; i < parse.size(); i++) {
                Cities temp = new Cities(parse.get(i).getAsJsonObject(), MainActivity.this);
                categories.add(temp);
            }
        }

        propertyListAdapter = new PropertyListAdapter(MainActivity.this,mobile,propertiesfrom_api);
       // trendingPropertiesAdapter = new TrendingPropertiesAdapter(MainActivity.this,trendingPropertiesfrom_api);
        listView.setAdapter(propertyListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,PropertyDetailPage.class);
                intent.putExtra("property",propertiesfrom_api.get(i));
//                intent.putExtra("property",propertiesfrom_api.get(i-1));
//                intent.putExtra("mobile",mobile);
                startActivity(intent);
            }
        });

        listView.setOnScrollListener(this);

        View footerView =  ((LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_layout, null, false);
        listView.addFooterView(footerView);
        footer_text = (TextView) footerView.findViewById(R.id.footer_text);


//        trendingPropertiesAdapter = new TrendingPropertiesAdapter(MainActivity.this,trendingPropertiesfrom_api,propertiesfrom_api,this);
//        viewPager.setAdapter(trendingPropertiesAdapter);
//
//        if (city_id.equals("10")){
//            trending_slide.setVisibility(View.GONE);
//        }else {
//            trending_slide.setVisibility(View.VISIBLE);
//        }
//
//        if(trendingPropertiesfrom_api.size()<=1){
//            previous_btn.setVisibility(View.GONE);
//            next_btn.setVisibility(View.VISIBLE);
//        }else {
//            previous_btn.setVisibility(View.GONE);
//            next_btn.setVisibility(View.VISIBLE);
//        }
//
//        next_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
//            }
//        });
//
//        previous_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
//            }
//        });
//
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                if (position == 0){
//                    previous_btn.setVisibility(View.GONE);
//                }else {
//                    previous_btn.setVisibility(View.VISIBLE);
//                }
//
//                if (position == trendingPropertiesAdapter.getCount()-1){
//                    next_btn.setVisibility(View.GONE);
//                }else {
//                    next_btn.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
        city.setText("All Cities");
        get_properties();
        //get_cities();
        //get_trending_properties();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if (resultCode == Activity.RESULT_OK) {

                // do something with the result

                if (data!=null && data.hasExtra("area_id")){
                    areas_id = data.getExtras().getString("area_id");
                    Log.e("areaResponse",areas_id);
                    cat_id = data.getExtras().getString("cat_id");
                    Log.e("catResponse",cat_id);
                    city_id = data.getExtras().getString("id");
                    Log.e("cityidresponse",city_id);
                    price_from = data.getExtras().getString("range_from");
                    Log.e("rang",price_from);
                    price_to = data.getExtras().getString("range_to");
                    Log.e("rangeto",price_to);
                    propertiesfrom_api.clear();
                    get_properties();

                }



            } else if (resultCode == Activity.RESULT_CANCELED) {
                // some stuff that will happen if there's no result
            }
        }
    }

    public void show_progress(){
        progress_holder.setVisibility(View.VISIBLE);
    }

    public void hide_progress(){
        progress_holder.setVisibility(View.GONE);
    }



    public void get_cities(){
        show_progress();
        Ion.with(this)
                .load(Session.SERVER_URL+"cities.php")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        hide_progress();
                        try {
                            for (int i=0;i<result.size();i++){
                                Log.e("response",result.get(i).toString());
                                Cities cities = new Cities(result.get(i).getAsJsonObject(),MainActivity.this);
                                categories.add(cities);
                            }

                        }catch (Exception e1){
                            e1.printStackTrace();
                        }

                    }
                });
    }


    public void show_alert_edit(){

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);


// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater=null;
        inflater = (LayoutInflater) MainActivity.this.
                getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.select_city_popup, null);

        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        TextView pop_title = (TextView) dialogView.findViewById(R.id.pop_up_title);
        final ListView listView = (ListView) dialogView.findViewById(R.id.city_list);
        cityAdapter = new CityAdapter(MainActivity.this,categories);

        listView.setAdapter(cityAdapter);
        pop_title.setText("Select City");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(cityAdapter!=null) {
                    city.setText(categories.get(i).title);
                    citiesId = categories.get(i).id;
                    propertiesfrom_api.clear();
                    propertyListAdapter.notifyDataSetChanged();
                    areas_id ="";
                    cat_id = "";
                    price_from = "";
                    price_to = "";
                    get_properties();
                    alertDialog.dismiss();
                }
            }
        });

        final EditText search_city = (EditText) dialogView.findViewById(R.id.search_city);
        TextView save = (TextView) dialogView.findViewById(R.id.pop_save_btn);
        TextView cancel = (TextView) dialogView.findViewById(R.id.pop_cancel_btn);
        LinearLayout main_cat_select = (LinearLayout) dialogView.findViewById(R.id.select_product_category);
        main_cat_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




            }
        });

        search_city.setText("");

        search_city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(cityAdapter!=null)
                    cityAdapter.getFilter().filter(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });



        cityAdapter.notifyDataSetChanged();


        alertDialog.show();


    }


    private void show_trending_properties(){

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);


// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater=null;
        inflater = (LayoutInflater) MainActivity.this.
                getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.select_city_popup, null);

        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        TextView pop_title = (TextView) dialogView.findViewById(R.id.pop_up_title);
        ListView listView = (ListView) dialogView.findViewById(R.id.city_list);
        cityAdapter = new CityAdapter(MainActivity.this,categories);

        listView.setAdapter(cityAdapter);
        pop_title.setText("Select City");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                   Log.e("came","came");
                    Intent intent = new Intent(MainActivity.this,TrendingPropertyActivity.class);
                    intent.putExtra("id",categories.get(i).id);
                    intent.putExtra("title",categories.get(i).title);
                intent.putExtra("areas_id",areas_id);
                intent.putExtra("cat_id",cat_id);
                intent.putExtra("price_from",price_from);
                intent.putExtra("price_to",price_to);
                intent.putExtra("phone",mobile);
                    startActivity(intent);
                    alertDialog.dismiss();
            }
        });

        final EditText search_city = (EditText) dialogView.findViewById(R.id.search_city);
        TextView save = (TextView) dialogView.findViewById(R.id.pop_save_btn);
        TextView cancel = (TextView) dialogView.findViewById(R.id.pop_cancel_btn);
        LinearLayout main_cat_select = (LinearLayout) dialogView.findViewById(R.id.select_product_category);
        main_cat_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




            }
        });

        search_city.setText("");

        search_city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(cityAdapter!=null)
                    cityAdapter.getFilter().filter(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });



        cityAdapter.notifyDataSetChanged();


        alertDialog.show();


    }

    public void get_properties(){
        show_progress();
        Ion.with(this)
                .load(Session.SERVER_URL+"properties.php")
                .setBodyParameter("city",citiesId)
                .setBodyParameter("area",areas_id)
                .setBodyParameter("category",cat_id)
                .setBodyParameter("from",price_from)
                .setBodyParameter("to",price_to)
                .setBodyParameter("start",String.valueOf(propertiesfrom_api.size()))
                .setBodyParameter("end","10")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        hide_progress();
                        try {
                            Log.e("prop_resp",result.toString());
                            if (result.size()<10)
                                footer_text.setText("End of Properties List");
                            else
                                footer_text.setText("Loading Properties List");
                            for (int i = 0; i < result.size(); i++) {
                                Properties properties = new Properties(result.get(i).getAsJsonObject(), MainActivity.this);
                                propertiesfrom_api.add(properties);
                            }
                            propertyListAdapter.notifyDataSetChanged();

                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                    }
                });
    }


    ArrayList<TextView> custom_names;


    public void display_custom(final JsonArray jsonArray) {

        custom_names = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            final TextView temp = new TextView(MainActivity.this);
            try {
                temp.setText(jsonArray.get(i).getAsJsonObject().get("title").getAsString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            TableRow.LayoutParams params = new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
            temp.setLayoutParams(params);
            temp.setPadding(15,15,15,15);
            temp.setSingleLine(true);
            temp.setGravity(Gravity.CENTER_VERTICAL);
            temp.setTextSize(15);
            temp.setTextColor(Color.parseColor("#ffffff"));
            final int finalI = i;
            temp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this,ServicesPage.class);
                        intent.putExtra("title",jsonArray.get(finalI).getAsJsonObject().get("title").getAsString());
                        intent.putExtra("data",jsonArray.get(finalI).getAsJsonObject().get("description").getAsString());
                        startActivity(intent);
                }
            });

            custom_names.add(temp);
            services_sub_menu.addView(temp);
        }

    }

    ArrayList<TextView> allcities;


    public void display_cities(final JsonArray jsonArray) {

        allcities = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            final TextView temp = new TextView(MainActivity.this);
            try {
                temp.setText(jsonArray.get(i).getAsJsonObject().get("title").getAsString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            TableRow.LayoutParams params = new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
            temp.setLayoutParams(params);
            temp.setPadding(15,15,15,15);
            temp.setSingleLine(true);
            temp.setGravity(Gravity.CENTER_VERTICAL);
            temp.setTextSize(15);
            temp.setTextColor(Color.parseColor("#ffffff"));
            final int finalI = i;
            temp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent intent = new Intent(MainActivity.this,MainActivity.class);
                        Log.e("dfdf",jsonArray.get(finalI).getAsJsonObject().get("title").getAsString());
                        city.setText(jsonArray.get(finalI).getAsJsonObject().get("title").getAsString());
                        citiesId = jsonArray.get(finalI).getAsJsonObject().get("id").getAsString();
                        slidingPaneLayout.closePane();
                        propertiesfrom_api.clear();
                    propertyListAdapter.notifyDataSetChanged();
                        areas_id = "";
                        cat_id = "";
                        price_from = "";
                        price_to = "";
                        get_properties();
//                    intent.putExtra("city",jsonArray.get(finalI).getAsJsonObject().get("title").getAsString());
//                    intent.putExtra("id",jsonArray.get(finalI).getAsJsonObject().get("id").getAsString());
//                    startActivity(intent);
                }
            });

            allcities.add(temp);
            cities_sub_menu.addView(temp);
        }

    }



//    public void get_trending_properties(){
//        Ion.with(this)
//                .load(Session.SERVER_URL+"trending-properties.php")
//                .setBodyParameter("city",citiesId)
//                .asJsonArray()
//                .setCallback(new FutureCallback<JsonArray>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonArray result) {
//                        try {
//                            Log.e("trend_prop",result.toString());
//                            for (int i = 0; i < result.size(); i++) {
//                                TrendingProperties trendingProperties = new TrendingProperties(result.get(i).getAsJsonObject(), MainActivity.this);
//                                trendingPropertiesfrom_api.add(trendingProperties);
//                            }
//                            trendingPropertiesAdapter.notifyDataSetChanged();
//                        }catch (Exception e1){
//                            e1.printStackTrace();
//                        }
//                    }
//                });
//    }


//    public void get_single_property(String id){
//        show_progress();
//        Ion.with(this)
//                .load(Session.SERVER_URL+"properties.php")
//                .setBodyParameter("property_id",id)
//                .setBodyParameter("city",citiesId)
//                .setBodyParameter("area",areas_id)
//                .setBodyParameter("category",cat_id)
//                .setBodyParameter("from",price_from)
//                .setBodyParameter("to",price_to)
//                .asJsonArray()
//                .withResponse()
//                .setCallback(new FutureCallback<Response<JsonArray>>() {
//                    @Override
//                    public void onCompleted(Exception e, Response<JsonArray> result) {
//
//                        Log.e("response",String.valueOf(result.getResult().size()));
//                        try {
//                            hide_progress();
//
//
//                            if (e != null) {
//                                e.printStackTrace();
//                                Log.e("error", e.getLocalizedMessage());
//
//                            } else
//                                try {
//                                    for (int i = 0; i < result.getResult().size(); i++) {
//                                        Log.e("trend_prop_resp",result.getRequest().toString());
//                                        Properties properties = new Properties(result.getResult().get(i).getAsJsonObject(), MainActivity.this);
//                                        Intent intent = new Intent(MainActivity.this,PropertyDetailPage.class);
//                                        intent.putExtra("property",properties);
//                                        intent.putExtra("mobile",mobile);
//                                        startActivity(intent);
//                                    }
//                                    propertyListAdapter.notifyDataSetChanged();
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


    public void setSelected(int position){
        switch (position){
            case 0:
            {
                reset_selected();
                select_city.setBackgroundColor(Color.parseColor("#181818"));
                select_city.setTextColor(Color.parseColor("#303C21"));
            }
            break;
            case  1:
            {
                reset_selected();
                properties.setBackgroundColor(Color.parseColor("#181818"));
                properties.setTextColor(Color.parseColor("#303C21"));
            }
            break;


            case  2:
            {
                reset_selected();
                resale_properties.setBackgroundColor(Color.parseColor("#181818"));
                resale_properties.setTextColor(Color.parseColor("#303C21"));
            }
            break;

            case  3:
            {
                reset_selected();
                trending_properties.setBackgroundColor(Color.parseColor("#181818"));
                trending_properties.setTextColor(Color.parseColor("#303C21"));
            }
            break;

            case 4:
            {
                reset_selected();
                home_loans.setBackgroundColor(Color.parseColor("#181818"));
                home_loans.setTextColor(Color.parseColor("#303C21"));
            }
            break;
            case 5:
            {
                reset_selected();
                emi_calculator.setBackgroundColor(Color.parseColor("#181818"));
                emi_calculator.setTextColor(Color.parseColor("#303C21"));
            }
            break;
            case 6:
            {
                reset_selected();
                about_text.setBackgroundColor(Color.parseColor("#181818"));
                about_text.setTextColor(Color.parseColor("#303C21"));
            }
            break;
            case 7:
            {
                reset_selected();
                policy.setBackgroundColor(Color.parseColor("#181818"));
                policy.setTextColor(Color.parseColor("#303C21"));
            }
            break;
            case 8:
            {
                reset_selected();
                terms_text.setBackgroundColor(Color.parseColor("#181818"));
                terms_text.setTextColor(Color.parseColor("#303C21"));
            }
            break;
            case 9:
            {
                reset_selected();
            }

        }
    }

    private void reset_selected(){
        select_city.setBackgroundColor(Color.parseColor("#202020"));

        select_city.setTextColor(Color.parseColor("#ffffff"));

        properties.setBackgroundColor(Color.parseColor("#202020"));

        properties.setTextColor(Color.parseColor("#ffffff"));

        trending_properties.setBackgroundColor(Color.parseColor("#202020"));

        trending_properties.setTextColor(Color.parseColor("#ffffff"));

        resale_properties.setBackgroundColor(Color.parseColor("#202020"));

        resale_properties.setTextColor(Color.parseColor("#ffffff"));

        home_loans.setBackgroundColor(Color.parseColor("#202020"));

        home_loans.setTextColor(Color.parseColor("#ffffff"));

        policy.setBackgroundColor(Color.parseColor("#202020"));

        policy.setTextColor(Color.parseColor("#ffffff"));

        about_text.setBackgroundColor(Color.parseColor("#202020"));

        about_text.setTextColor(Color.parseColor("#ffffff"));

        emi_calculator.setBackgroundColor(Color.parseColor("#202020"));

        emi_calculator.setTextColor(Color.parseColor("#ffffff"));

        terms_text.setBackgroundColor(Color.parseColor("#202020"));

        terms_text.setTextColor(Color.parseColor("#ffffff"));

    }


    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        int lastitem = i + i1;
        if (lastitem == i2-3){
            if (previouslast!=lastitem){
                Log.e("result","last");
                get_properties();
                previouslast = lastitem;
            }
        }
    }
}
