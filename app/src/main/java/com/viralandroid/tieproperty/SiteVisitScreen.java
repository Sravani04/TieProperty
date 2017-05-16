package com.viralandroid.tieproperty;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by T on 16-05-2017.
 */

public class SiteVisitScreen extends Activity{
    ImageView back_btn,add_visit;
    ListView listView;
    TextView time;
    int starthMonth = 5;
    int startDay = 1;
    @Override
    public void onCreate(Bundle savedInstanceState){
      super.onCreate(savedInstanceState);
        setContentView(R.layout.site_visit_list);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        add_visit = (ImageView) findViewById(R.id.add_visit);
        add_visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(getApplicationContext());
                View form = li.inflate(R.layout.add_site_visit_items, null);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SiteVisitScreen.this);
                alertDialogBuilder.setView(form);
                final EditText name = (EditText) form.findViewById(R.id.name);
                final EditText phone = (EditText) form.findViewById(R.id.phone);
                final EditText address = (EditText) form.findViewById(R.id.address);
                final TextView date = (TextView) form.findViewById(R.id.date);
                time = (TextView) form.findViewById(R.id.time);
                time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dialog dialog = onCreateDialogSingleChoice();
                        dialog.show();

                    }
                });
                date.setOnClickListener(new View.OnClickListener() {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View v) {
                        final Calendar mcurrentDate=Calendar.getInstance();
                        final int mYear = mcurrentDate.get(Calendar.YEAR);
                        final int mMonth = mcurrentDate.get(Calendar.MONTH);
                        final int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog mDatePicker=new DatePickerDialog(SiteVisitScreen.this, new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                                date.setText(selectedday +"-"+selectedmonth +"-"+selectedyear);
                            }
                        },mYear, mMonth, mDay);
                        mDatePicker.setTitle("Select date");
                        mDatePicker.show();  }
                });
                final EditText property = (EditText) form.findViewById(R.id.property);
                TextView submit_btn = (TextView) form.findViewById(R.id.submit_btn);
                ImageView close_btn = (ImageView) form.findViewById(R.id.close_btn);
                submit_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name_string = name.getText().toString();
                        String phone_string = phone.getText().toString();
                        String address_string = address.getText().toString();
                        String date_string = date.getText().toString();
                        String time_string = time.getText().toString();
                        String property_string = property.getText().toString();
                        if (name_string.equals("")){
                            Toast.makeText(getApplicationContext(),"Please Enter Name",Toast.LENGTH_SHORT).show();
                        }else if (phone_string.equals("")){
                            Toast.makeText(getApplicationContext(),"Please Enter Phone",Toast.LENGTH_SHORT).show();
                        }else if (address_string.equals("")){
                            Toast.makeText(getApplicationContext(),"Please Enter Address",Toast.LENGTH_SHORT).show();
                        }else if (date_string.equals("")){
                            Toast.makeText(getApplicationContext(),"Please Enter Date",Toast.LENGTH_SHORT).show();
                        }else if (time_string.equals("")){
                            Toast.makeText(getApplicationContext(),"Please Enter Time",Toast.LENGTH_SHORT).show();
                        }else if (property_string.equals("")){
                            Toast.makeText(getApplicationContext(),"Please Enter Property",Toast.LENGTH_SHORT).show();
                        }else {
                            Ion.with(getApplicationContext())
                                    .load(Session.SERVER_URL+"site-visit.php")
                                    .setBodyParameter("agent_id",Session.GetUserId(getApplicationContext()))
                                    .setBodyParameter("name",name_string)
                                    .setBodyParameter("phone",phone_string)
                                    .setBodyParameter("address",address_string)
                                    .setBodyParameter("date",date_string)
                                    .setBodyParameter("time",time_string)
                                    .setBodyParameter("prop_id",property_string)
                                    .asJsonObject()
                                    .setCallback(new FutureCallback<JsonObject>() {
                                        @Override
                                        public void onCompleted(Exception e, JsonObject result) {
                                            if (result.get("status").getAsString().equals("Success")){
                                                Toast.makeText(getApplicationContext(),result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                            }else {
                                                Toast.makeText(getApplicationContext(),result.get("message").getAsString(),Toast.LENGTH_SHORT).show();

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

    }

    public Dialog onCreateDialogSingleChoice() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final CharSequence[] array = {"06:00 AM", "06:30 AM", "07:00 AM","07:30 AM","08:00 AM","08:30 AM",
                "09:00 AM","09:30 AM","10:00 AM","10:30 AM","11:00 AM","11:30 AM","12:00 PM","12:30 PM",
                "01:00 PM","01:30 PM","02:00 PM","02:30 PM","03:00 PM","03:30 PM","04:00 PM","04:30 PM","05:00 PM",
                "05:30 PM","06:00 PM","06:30 PM","07:00 PM","07:30 PM","08:00 PM","08:30 PM","09:00 PM"};


        builder.setTitle("Select Time")
                .setSingleChoiceItems(array, 0, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       String selectedItem = array[which].toString();
                        Log.e("select",selectedItem);
                        time.setText(selectedItem);

                    }
                })

                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }


}
