package com.viralandroid.tieproperty;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.eftimoff.viewpagertransformers.CubeOutTransformer;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import static java.lang.Double.parseDouble;
//import com.google.android.gms.maps.OnMapReadyCallback;

/**
 * Created by T on 24-04-2017.
 */

public class PropertyDetailPage extends FragmentActivity implements OnMapReadyCallback{
    ViewPager viewPager;
    int images[] = {R.drawable.building, R.drawable.scetch_image, R.drawable.select_city};
    PropertyImageSliderAdapter propertyImageSliderAdapter;
    private GoogleMap map;
    ImageView back_btn, previous_btn, next_btn, call_btn,line1,line2;
    public int a;
    Properties properties;

    TextView property_price,property_area,property_address,property_title,banks,textView27,textView88,
            flat_description,amenities,flat_title,flat_title2,flat_title3,form_btn;
    String phone,id;
    int position;
    LinearLayout ame_box,description,bank_box,specification_box;
    ViewFlipper flipper;
    ListView listView;
    SpecificationsAdapter specificationsAdapter;
    int MY_PERMISSIONS_REQUEST_CALL_PHONE;
    int ASK_MULTIPLE_PERMISSION_REQUEST_CODE;
    TrendingProperties trendingProperties;



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.property_detail_page);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setPageTransformer(true, new CubeOutTransformer());
        back_btn = (ImageView) findViewById(R.id.back_btn);
        previous_btn = (ImageView) findViewById(R.id.previous_btn);
        next_btn = (ImageView) findViewById(R.id.next_btn);
        call_btn = (ImageView) findViewById(R.id.call_btn);
        property_price = (TextView) findViewById(R.id.property_price);
        property_area = (TextView) findViewById(R.id.property_area);
        property_address = (TextView) findViewById(R.id.property_address);
        property_title = (TextView) findViewById(R.id.property_title);
        banks = (TextView) findViewById(R.id.banks);
        textView27 = (TextView) findViewById(R.id.textView27);
        flat_description = (TextView) findViewById(R.id.flat_description);
        amenities = (TextView) findViewById(R.id.amenities);
        flat_title = (TextView) findViewById(R.id.flat_title);
        flat_title2 = (TextView) findViewById(R.id.flat_title2);
        flat_title3 = (TextView) findViewById(R.id.flat_title3);
        line1 = (ImageView) findViewById(R.id.line1);
        line2 = (ImageView) findViewById(R.id.line2);
        description = (LinearLayout) findViewById(R.id.description);
        ame_box = (LinearLayout) findViewById(R.id.ame_box);
        flipper = (ViewFlipper) findViewById(R.id.flipper);
        listView = (ListView) findViewById(R.id.specifications_list);
        form_btn = (TextView) findViewById(R.id.form_btn);
        bank_box = (LinearLayout) findViewById(R.id.bank_box);
        specification_box = (LinearLayout) findViewById(R.id.specifications_box);





        if (getIntent()!=null && getIntent().hasExtra("property")){
            properties = (Properties) getIntent().getSerializableExtra("property");
            phone = getIntent().getStringExtra("mobile");

        }else if(getIntent()!=null && getIntent().hasExtra("propertystr")){
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(getIntent().getStringExtra("propertystr")).getAsJsonObject();
            properties = new Properties(jsonObject,this);

        }

        if (getIntent()!=null && getIntent().hasExtra("property_id")){
            id = getIntent().getStringExtra("property_id");
            trendingProperties = (TrendingProperties) getIntent().getSerializableExtra("trending_prop");
        }

        specificationsAdapter = new SpecificationsAdapter(this,properties);
        listView.setAdapter(specificationsAdapter);
        setListViewHeightBasedOnItems(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        flipper.setDisplayedChild(0);

        textView27.setText(R.string.Location);

        try {
            flat_title.setText(properties.flats.get(0).title);

            if (properties.flats.get(1)!=null) {
                if (properties.flats.get(1).title.equals("")){
                    flat_title2.setVisibility(View.GONE);
                    line1.setVisibility(View.GONE);
                }else {
                    flat_title2.setText(properties.flats.get(1).title);
                    line1.setVisibility(View.VISIBLE);
                    flat_title2.setVisibility(View.VISIBLE);
                }
            }

            if (properties.flats.get(2)!=null) {
                if (properties.flats.get(2).title.equals("")) {
                    flat_title3.setVisibility(View.GONE);
                    line2.setVisibility(View.GONE);
                } else {
                    flat_title3.setText(properties.flats.get(2).title);
                    line2.setVisibility(View.VISIBLE);
                    flat_title3.setVisibility(View.VISIBLE);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }



        try {
            property_price.setText(properties.flats.get(0).price);
            property_area.setText(properties.flats.get(0).area + properties.flats.get(0).type);
            property_address.setText(properties.address);
            property_title.setText(properties.title);


            try {
                if (properties.banks!=null ) {
                    if (properties.banks.isEmpty()){
                        bank_box.setVisibility(View.GONE);
                    }else {
                        banks.setText(properties.banks.get(0).title + "," + properties.banks.get(1).title);
                        bank_box.setVisibility(View.VISIBLE);
                    }
                }

                if (properties.banks.get(0)!=null || properties.banks.get(1)!=null || properties.banks.get(2)!=null){
                    banks.setText(properties.banks.get(0).title + "," + properties.banks.get(1).title +","+properties.banks.get(2).title);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            if (properties.flats.get(0).description!=null){
                if (properties.flats.get(0).description.equals("")){
                    description.setVisibility(View.GONE);
                }else {
                    flat_description.setText(Html.fromHtml(properties.flats.get(0).description));
                    description.setVisibility(View.VISIBLE);
                }
            }


            if (properties.specifications.isEmpty()){
                specification_box.setVisibility(View.GONE);
            }else {
                specification_box.setVisibility(View.VISIBLE);
            }



            if (properties.amenities!=null) {
                if (properties.amenities.isEmpty()){
                    ame_box.setVisibility(View.GONE);
                }else {
                    amenities.setText(properties.amenities.get(0).title + "," + properties.amenities.get(1).title);
                    ame_box.setVisibility(View.VISIBLE);
                }
            }



        }catch (Exception e){
            e.printStackTrace();
        }




        flat_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flat_title.setTextColor(Color.parseColor("#0D5097"));
                flat_title2.setTextColor(Color.parseColor("#000000"));
                flat_title3.setTextColor(Color.parseColor("#000000"));
                try {
                    property_price.setText(properties.flats.get(0).price);
                    property_area.setText(properties.flats.get(0).area + properties.flats.get(0).type);
                    property_address.setText(properties.address);
                    property_title.setText(properties.title);


                    textView27.setText(R.string.Location);

                    if (properties.flats.get(0).description!=null){
                        if (properties.flats.get(0).description.equals("")){
                            description.setVisibility(View.GONE);
                        }else {
                            flat_description.setText(Html.fromHtml(properties.flats.get(0).description));
                            description.setVisibility(View.VISIBLE);
                        }
                    }



                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        flat_title2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flat_title2.setTextColor(Color.parseColor("#0D5097"));
                flat_title.setTextColor(Color.parseColor("#000000"));
                flat_title3.setTextColor(Color.parseColor("#000000"));
                line1.setVisibility(View.VISIBLE);
                try {

                    property_price.setText(properties.flats.get(1).price);
                    property_area.setText(properties.flats.get(1).area + properties.flats.get(1).type);
                    property_address.setText(properties.address);
                    property_title.setText(properties.title);


                    textView27.setText(R.string.Location);



                    if (properties.flats.get(1).description!=null){
                        if (properties.flats.get(1).description.equals("")){
                            description.setVisibility(View.GONE);
                        }else {
                            flat_description.setText(Html.fromHtml(properties.flats.get(1).description));
                            description.setVisibility(View.VISIBLE);
                        }
                    }


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        flat_title3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flat_title3.setTextColor(Color.parseColor("#0D5097"));
                flat_title2.setTextColor(Color.parseColor("#000000"));
                flat_title.setTextColor(Color.parseColor("#000000"));
                try {
                    property_price.setText(properties.flats.get(2).price);
                    property_area.setText(properties.flats.get(2).area + properties.flats.get(2).type);
                    property_address.setText(properties.address);
                    property_title.setText(properties.title);

                    textView27.setText(R.string.Location);
                    if (properties.flats.get(2).description!=null){
                        if (properties.flats.get(2).description.equals("")){
                            description.setVisibility(View.GONE);
                        }else {
                            flat_description.setText(Html.fromHtml(properties.flats.get(2).description));
                            description.setVisibility(View.VISIBLE);
                        }
                    }


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });




        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PropertyDetailPage.this.onBackPressed();
            }
        });

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //viewPager.setCurrentItem(getItem(+1),true);

                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
            }
        });

        previous_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //viewPager.setCurrentItem(getItem(-1),true);
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
            }
        });

        call_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+phone));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (ActivityCompat.checkSelfPermission(PropertyDetailPage.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE);
                    return;
                }
                startActivity(callIntent);
            }
        });




        SpannableString str = new SpannableString("I'm Interested");
        str.setSpan(new UnderlineSpan(), 0, str.length(), Spanned.SPAN_PARAGRAPH);
        form_btn.setText(str);



        form_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(getApplicationContext());
                View form = li.inflate(R.layout.interested_popup, null);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PropertyDetailPage.this);
                alertDialogBuilder.setView(form);
                final EditText name = (EditText) form.findViewById(R.id.name);
                final EditText email = (EditText) form.findViewById(R.id.email);
                final EditText phone = (EditText) form.findViewById(R.id.phone);
                final EditText message = (EditText) form.findViewById(R.id.message);
                final ImageView close_btn = (ImageView) form.findViewById(R.id.close_btn);
                TextView submit_btn = (TextView) form.findViewById(R.id.submit_btn);
                submit_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String form_name = name.getText().toString();
                        String form_email = email.getText().toString();
                        String form_phone = phone.getText().toString();
                        String form_message = message.getText().toString();
                          if (form_name.equals("")){
                              Toast.makeText(PropertyDetailPage.this,"please enter name",Toast.LENGTH_SHORT).show();
                              name.requestFocus();
                          }else if (form_email.equals("")){
                              Toast.makeText(PropertyDetailPage.this,"please enter email",Toast.LENGTH_SHORT).show();
                              email.requestFocus();
                          }else if (form_phone.equals("")){
                              Toast.makeText(PropertyDetailPage.this,"please enter phone",Toast.LENGTH_SHORT).show();
                              phone.requestFocus();
                          }else if (form_message.equals("")){
                              Toast.makeText(PropertyDetailPage.this,"please enter message",Toast.LENGTH_SHORT).show();
                              message.requestFocus();
                          } else {
                              Ion.with(PropertyDetailPage.this)
                                      .load(Session.SERVER_URL+"intrested.php")
                                      .setBodyParameter("property",properties.id)
                                      .setBodyParameter("name",form_name)
                                      .setBodyParameter("email",form_email)
                                      .setBodyParameter("phone",form_phone)
                                      .setBodyParameter("message",form_message)
                                      .asJsonObject()
                                      .setCallback(new FutureCallback<JsonObject>() {
                                          @Override
                                          public void onCompleted(Exception e, JsonObject result) {
                                             if (result.get("status").getAsString().equals("Success")){
                                                 Toast.makeText(PropertyDetailPage.this,result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                                 PropertyDetailPage.this.onBackPressed();
                                             }else {
                                                 Toast.makeText(PropertyDetailPage.this,result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                             }
                                          }
                                      });
                          }
                    }
                });



                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                alertDialog.show();

                close_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });


            }
        });

        try {
//            MapFragment mapFragment = (MapFragment) getFragmentManager()
//                    .findFragmentById(R.id.fragment);
//            mapFragment.getMapAsync(this);
            SupportMapFragment mapFragment =
                    (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
            mapFragment.getMapAsync(this);
        } catch (Exception e) {
            e.printStackTrace();
        }



        propertyImageSliderAdapter = new PropertyImageSliderAdapter(PropertyDetailPage.this, images,properties);
        viewPager.setAdapter(propertyImageSliderAdapter);




    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap map) {

        if (properties.location!=null) {
            map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CAMERA},
                        ASK_MULTIPLE_PERMISSION_REQUEST_CODE);
                return;
            }
//        map.setMyLocationEnabled(false);
//        map.setTrafficEnabled(false);
//        map.setIndoorEnabled(false);
//        map.setBuildingsEnabled(true);

            map.getUiSettings().setZoomControlsEnabled(true);
            LatLng point = new LatLng(parseDouble(properties.latitude), parseDouble(properties.longitude));
            Marker marker = map.addMarker(new MarkerOptions().position(point).title(properties.location).visible(true).icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
            CameraUpdate location = CameraUpdateFactory.newLatLngZoom(point, 15);
            map.animateCamera(location);
            map.moveCamera(location);

        }

    }





    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        SpecificationsAdapter specificationsAdapter = (SpecificationsAdapter) listView.getAdapter();
        if (specificationsAdapter != null) {

            int numberOfItems = specificationsAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = specificationsAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }
    }

}

