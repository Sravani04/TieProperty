package com.viralandroid.tieproperty;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by T on 24-04-2017.
 */

public class PropertyListAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    String mobile,lat,lng,loc;
    ArrayList<Properties> properties;


    public PropertyListAdapter(Context context, String mobile, ArrayList<Properties> properties){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.mobile = mobile;
        this.properties = properties;
    }

    @Override
    public int getCount() {
        return properties.size() ;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       final View item_view =  inflater.inflate(R.layout.activity_main,null);

       ImageView call_btn = (ImageView) item_view.findViewById(R.id.call_btn);
       final TextView address = (TextView) item_view.findViewById(R.id.address);
       TextView property_title = (TextView) item_view.findViewById(R.id.property_title);
       ImageView map = (ImageView) item_view.findViewById(R.id.map);
        TextView stats = (TextView) item_view.findViewById(R.id.stats);
        ImageView item_image = (ImageView) item_view.findViewById(R.id.item_image);
        lat = properties.get(i).latitude;
        lng = properties.get(i).longitude;
        loc = properties.get(i).location;

        address.setText(properties.get(i).address);
        property_title.setText(properties.get(i).title);
        Ion.with(context)
                .load(properties.get(i).images.get(0).image)
                .withBitmap()
                .intoImageView(item_image);
        stats.setText(properties.get(i).stats);


        call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:1800212147"));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                context.startActivity(callIntent);
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Uri uri = Uri.parse("geo:13.070984,80.253639?q=13.070984,80.253639(location)");
//                Intent in = new Intent(Intent.ACTION_VIEW, uri);
//                context.startActivity(in);
                Uri mapUri = Uri.parse("geo:0,0?q=" +  lat + "," + lng +"(" + loc + ")");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);
            }
        });



        return item_view;
    }



}
