package com.viralandroid.tieproperty;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by T on 12-05-2017.
 */

public class ContactUsPage extends FragmentActivity implements OnMapReadyCallback {
    ImageView call_btn,email_btn,close_btn;
    TextView phone_no,email_id,submit_btn,address,contact_us,contact_form;
    EditText name,email,phone,subject,message;
    String contact_address,contact_no,contact_email;
    int ASK_MULTIPLE_PERMISSION_REQUEST_CODE;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactus_page);
        call_btn = (ImageView) findViewById(R.id.call_btn);
        email_btn = (ImageView) findViewById(R.id.email_btn);
        phone_no = (TextView) findViewById(R.id.phone_no);
        email_id = (TextView) findViewById(R.id.email_id);
        submit_btn =(TextView) findViewById(R.id.submit_btn);
        address =(TextView) findViewById(R.id.address);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        subject = (EditText) findViewById(R.id.subject);
        message = (EditText) findViewById(R.id.message);
        contact_us = (TextView) findViewById(R.id.contact_us);
        contact_form = (TextView) findViewById(R.id.contact_form);
        close_btn = (ImageView) findViewById(R.id.close_btn);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if (getIntent()!=null && getIntent().hasExtra("contact")){
            contact_address = getIntent().getStringExtra("contact");
            contact_no = getIntent().getStringExtra("phone");
            contact_email = getIntent().getStringExtra("email");
        }

        try {

            SupportMapFragment mapFragment =
                    (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
            mapFragment.getMapAsync(this);
        } catch (Exception e) {
            e.printStackTrace();
        }



        address.setText(Html.fromHtml(contact_address));
        phone_no.setText(contact_no);
        email_id.setText(contact_email);
        contact_us.setText(R.string.Contactus);
        contact_form.setText(R.string.ContactForm);

        call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + contact_no));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (ActivityCompat.checkSelfPermission(ContactUsPage.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
                Email.putExtra(Intent.EXTRA_EMAIL,new String[]{contact_email});
                Email.putExtra(Intent.EXTRA_SUBJECT,"Add your Subject");
                startActivity(Intent.createChooser(Email, "Send Feedback:"));
            }
        });

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name_string = name.getText().toString();
                String email_string = email.getText().toString();
                String phone_string = phone.getText().toString();
                String message_string = message.getText().toString();

                if (name_string.equals("")){
                    Toast.makeText(ContactUsPage.this,"Please Enter Name",Toast.LENGTH_SHORT).show();
                    name.requestFocus();
                }else if (email_string.equals("")){
                    Toast.makeText(ContactUsPage.this,"Please Enter Email",Toast.LENGTH_SHORT).show();
                    email.requestFocus();
                }else if (phone_string.equals("")){
                    Toast.makeText(ContactUsPage.this,"Please Enter Phone",Toast.LENGTH_SHORT).show();
                    phone.requestFocus();
                }else if (message_string.equals("")){
                    Toast.makeText(ContactUsPage.this,"Please Enter Message",Toast.LENGTH_SHORT).show();
                    message.requestFocus();
                }else {
                    Ion.with(ContactUsPage.this)
                            .load(Session.SERVER_URL+"contact-us.php")
                            .setBodyParameter("name",name_string)
                            .setBodyParameter("email",email_string)
                            .setBodyParameter("phone",phone_string)
                            .setBodyParameter("message",message_string)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    if (result.get("status").getAsString().equals("Success")){
                                        Toast.makeText(ContactUsPage.this,result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(ContactUsPage.this,result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap map) {
        if (contact_address!=null) {
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
            LatLng point = new LatLng(16.5134,80.6653);
            Marker marker = map.addMarker(new MarkerOptions().position(point).title(String.valueOf(Html.fromHtml(contact_address))).visible(true).icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
            CameraUpdate location = CameraUpdateFactory.newLatLngZoom(point, 15);
            map.animateCamera(location);
            map.moveCamera(location);

        }
    }

}
