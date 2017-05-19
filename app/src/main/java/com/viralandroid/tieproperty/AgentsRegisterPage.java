package com.viralandroid.tieproperty;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by T on 18-05-2017.
 */

public class AgentsRegisterPage extends Activity {
    EditText name,email,phone,city_name,about;
    TextView register_btn;
    ImageView close_btn;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agents_register_page);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        city_name = (EditText) findViewById(R.id.city_name);
        register_btn = (TextView) findViewById(R.id.register_btn);
        close_btn = (ImageView) findViewById(R.id.close_btn);
        about = (EditText) findViewById(R.id.about);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name_string = name.getText().toString();
                String email_string = email.getText().toString();
                String phone_string = phone.getText().toString();
                String city_string = city_name.getText().toString();
                String about_string = about.getText().toString();
                if (name_string.equals("")){
                    Toast.makeText(AgentsRegisterPage.this,"Please Enter Name",Toast.LENGTH_SHORT).show();
                    name.requestFocus();
                }else if (email_string.equals("")){
                    Toast.makeText(AgentsRegisterPage.this,"Please Enter Email",Toast.LENGTH_SHORT).show();
                    email.requestFocus();
                }else if (phone_string.equals("")){
                    Toast.makeText(AgentsRegisterPage.this,"Please Enter Phone",Toast.LENGTH_SHORT).show();
                    phone.requestFocus();
                }else if(city_string.equals("")) {
                    Toast.makeText(AgentsRegisterPage.this,"Please Enter City",Toast.LENGTH_SHORT).show();
                    city_name.requestFocus();
                }else if(about_string.equals("")) {
                    Toast.makeText(AgentsRegisterPage.this,"Please Enter About",Toast.LENGTH_SHORT).show();
                    about.requestFocus();
                }else {
                    final ProgressDialog progressDialog = new ProgressDialog(AgentsRegisterPage.this);
                    progressDialog.setMessage("please wait..");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    Ion.with(AgentsRegisterPage.this)
                            .load(Session.SERVER_URL+"signup.php")
                            .setBodyParameter("name",name_string)
                            .setBodyParameter("email",email_string)
                            .setBodyParameter("phone",phone_string)
                            .setBodyParameter("city",city_string)
                            .setBodyParameter("about",about_string)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    if (progressDialog!=null)
                                        progressDialog.dismiss();
                                    if (result.get("status").getAsString().equals("Success")){
                                        Toast.makeText(AgentsRegisterPage.this,result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(AgentsRegisterPage.this,LoginPage.class);
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(AgentsRegisterPage.this,result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }
}
