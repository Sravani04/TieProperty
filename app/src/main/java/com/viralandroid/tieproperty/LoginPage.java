package com.viralandroid.tieproperty;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by T on 12-05-2017.
 */

public class LoginPage extends Activity {
    EditText email,password;
    TextView submit_btn;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        submit_btn = (TextView) findViewById(R.id.submit_btn);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_string = email.getText().toString();
                String password_string = password.getText().toString();
                if (email_string.equals("")){
                    Toast.makeText(LoginPage.this,"Please Enter Your Email",Toast.LENGTH_SHORT).show();
                }else if (password_string.equals("")){
                    Toast.makeText(LoginPage.this,"Please Enter Your Password",Toast.LENGTH_SHORT).show();
                }else {
                    Ion.with(LoginPage.this)
                            .load(Session.SERVER_URL + "login.php")
                            .setBodyParameter("email",email_string)
                            .setBodyParameter("password",password_string)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    if (result.get("status").getAsString().equals("Success")){
                                        Session.SetUserId(LoginPage.this,result.get("member_id").getAsString());
                                        //Toast.makeText(LoginPage.this,result.get("member_id").getAsString(),Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginPage.this,AgentsAccountPage.class);
                                        intent.putExtra("agentId",result.get("member_id").getAsString());
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(LoginPage.this,result.get("message").getAsString(), Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
            }
        });

    }
}