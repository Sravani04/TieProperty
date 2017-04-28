package com.viralandroid.tieproperty;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eftimoff.viewpagertransformers.CubeOutTransformer;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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
    ImageView back_btn, previous_btn, next_btn, call_btn;
    public int a;
    Properties properties;
    TextView property_price,property_area,property_address,property_title;


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


        if (getIntent()!=null && getIntent().hasExtra("property")){
            properties = (Properties) getIntent().getSerializableExtra("property");
        }


        property_price.setText(properties.flats.get(0).price);
        property_area.setText(properties.flats.get(0).area+properties.flats.get(0).type);
        property_address.setText(properties.address);
        property_title.setText(properties.title);



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
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:1800212147"));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (ActivityCompat.checkSelfPermission(PropertyDetailPage.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
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

    @Override
    public void onMapReady(GoogleMap map) {
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
//        map.setMyLocationEnabled(false);
//        map.setTrafficEnabled(false);
//        map.setIndoorEnabled(false);
//        map.setBuildingsEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        LatLng point = new LatLng(parseDouble(properties.latitude), parseDouble(properties.longitude));
        Marker marker = map.addMarker(new MarkerOptions().position(point).title(properties.location).visible(true));

    }




}

