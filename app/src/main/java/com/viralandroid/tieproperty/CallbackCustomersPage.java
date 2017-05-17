package com.viralandroid.tieproperty;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;


/**
 * Created by T on 15-05-2017.
 */

public class CallbackCustomersPage extends Activity {
    ImageView add_call,back_btn;
    ListView listView;
    String agent_id;
    TextView date;
    ArrayList<CallbackCustomers> callbackCustomersfrom_api;
    CallbackCustomersAdapter adapter;
    public void onCreate(Bundle savedinstanceState) {
        super.onCreate(savedinstanceState);
        setContentView(R.layout.add_callback_customer_list);
        add_call = (ImageView) findViewById(R.id.add_call);
        listView = (ListView) findViewById(R.id.callback_customers_list);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallbackCustomersPage.this.onBackPressed();
            }
        });
        callbackCustomersfrom_api = new ArrayList<>();

        if (getIntent()!=null && getIntent().hasExtra("agentId")){
            agent_id = getIntent().getStringExtra("agentId");
        }

        adapter = new CallbackCustomersAdapter(this,callbackCustomersfrom_api);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });


        add_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(getApplicationContext());
                final View form = li.inflate(R.layout.add_callback_items, null);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CallbackCustomersPage.this);
                alertDialogBuilder.setView(form);
                final EditText name = (EditText) form.findViewById(R.id.name);
                final EditText email = (EditText) form.findViewById(R.id.email);
                final EditText phone = (EditText) form.findViewById(R.id.phone);
                final EditText property = (EditText) form.findViewById(R.id.property);
                 date = (TextView) form.findViewById(R.id.date);
                date.setOnClickListener(new View.OnClickListener() {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View v) {
                        final Calendar mcurrentDate=Calendar.getInstance();
                        final int mYear = mcurrentDate.get(Calendar.YEAR);
                        final int mMonth = mcurrentDate.get(Calendar.MONTH);
                        final int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog mDatePicker=new DatePickerDialog(CallbackCustomersPage.this, new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                                date.setText(selectedday +"-"+selectedmonth +"-"+selectedyear);
                            }
                        },mYear, mMonth, mDay);
                        mDatePicker.setTitle("Select date");
                        mDatePicker.show();  }
                });
                final EditText message = (EditText) form.findViewById(R.id.message);
                final ImageView close_btn = (ImageView) form.findViewById(R.id.close_btn);
                TextView submit_btn = (TextView) form.findViewById(R.id.submit_btn);
                submit_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name_string = name.getText().toString();
                        String email_string = email.getText().toString();
                        String phone_string = phone.getText().toString();
                        String property_string = property.getText().toString();
                        String date_string =date.getText().toString();
                        String message_string = message.getText().toString();
                        if (name_string.equals("")){
                            Toast.makeText(CallbackCustomersPage.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                            name.requestFocus();
                        }else if (email_string.equals("")){
                            Toast.makeText(CallbackCustomersPage.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                            email.requestFocus();
                        }else if (phone_string.equals("")){
                            Toast.makeText(CallbackCustomersPage.this, "Please Enter Phone", Toast.LENGTH_SHORT).show();
                            phone.requestFocus();
                        }else if (property_string.equals("")){
                            Toast.makeText(CallbackCustomersPage.this, "Please Enter Property", Toast.LENGTH_SHORT).show();
                            property.requestFocus();
                        }else if (date_string.equals("")){
                            Toast.makeText(CallbackCustomersPage.this, "Please Enter Date", Toast.LENGTH_SHORT).show();
                            date.requestFocus();
                        }else if (message_string.equals("")){
                            Toast.makeText(CallbackCustomersPage.this, "Please Enter Message", Toast.LENGTH_SHORT).show();
                            message.requestFocus();
                        }else {
                            Ion.with(CallbackCustomersPage.this)
                                    .load(Session.SERVER_URL+"callback_customer.php")
                                    .setBodyParameter("name",name_string)
                                    .setBodyParameter("email",email_string)
                                    .setBodyParameter("phone",phone_string)
                                    .setBodyParameter("date",date_string)
                                    .setBodyParameter("message",message_string)
                                    .setBodyParameter("property_id","3")
                                    .setBodyParameter("agent_id",Session.GetUserId(CallbackCustomersPage.this))
                                    .asJsonObject()
                                    .setCallback(new FutureCallback<JsonObject>() {
                                        @Override
                                        public void onCompleted(Exception e, JsonObject result) {
                                            if (result.get("status").getAsString().equals("Success")){
                                                Toast.makeText(CallbackCustomersPage.this,result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                                finish();
                                            }else {
                                                Toast.makeText(CallbackCustomersPage.this,result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                });

                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                close_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
            }
        });
        get_callback_customers();
    }

    public void get_callback_customers(){
        Ion.with(this)
                .load(Session.SERVER_URL+"callback_customers.php")
                .setBodyParameter("agent_id",Session.GetUserId(CallbackCustomersPage.this))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        for (int i=0;i<result.size();i++) {
                                CallbackCustomers callback = new CallbackCustomers(result.get(i).getAsJsonObject(), getApplicationContext());
                                callbackCustomersfrom_api.add(callback);
                            }
                            adapter.notifyDataSetChanged();



                    }
                });
    }





}
