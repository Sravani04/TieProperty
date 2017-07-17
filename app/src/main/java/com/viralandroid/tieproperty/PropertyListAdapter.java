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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by T on 24-04-2017.
 */

public class PropertyListAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    String mobile, lat, lng, loc;
    ArrayList<Properties> properties;
    String[] items ;
    String title;






    public PropertyListAdapter(Context context, String mobile, ArrayList<Properties> properties) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.mobile = mobile;
        this.properties = properties;
    }

    @Override
    public int getCount() {
        return properties.size();
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
        final View item_view = inflater.inflate(R.layout.activity_main, null);


        ImageView call_btn = (ImageView) item_view.findViewById(R.id.call_btn);
        final TextView address = (TextView) item_view.findViewById(R.id.address);
        TextView property_title = (TextView) item_view.findViewById(R.id.property_title);
        ImageView map = (ImageView) item_view.findViewById(R.id.map);
        TextView stats = (TextView) item_view.findViewById(R.id.stats);
        ImageView item_image = (ImageView) item_view.findViewById(R.id.item_image);
        TextView flat_price = (TextView) item_view.findViewById(R.id.flat_price);
        TextView flat_area = (TextView) item_view.findViewById(R.id.flat_area);
//        TextView flat_type = (TextView) item_view.findViewById(R.id.flat_type);
        TextView flat_title = (TextView) item_view.findViewById(R.id.flat_title);
//        TextView flat_price2 = (TextView) item_view.findViewById(R.id.flat_price2);
//        TextView flat_area1 = (TextView) item_view.findViewById(R.id.flat_area1);
        ImageView offer = (ImageView) item_view.findViewById(R.id.offer);
//        TextView hiphen  = (TextView) item_view.findViewById(R.id.hiphen);
//        TextView hiphen2 = (TextView) item_view.findViewById(R.id.hiphen2);
        LinearLayout stats_box = (LinearLayout) item_view.findViewById(R.id.stats_box);



        lat = properties.get(i).latitude;
        lng = properties.get(i).longitude;
        loc = properties.get(i).location;

        address.setText(properties.get(i).address);
        property_title.setText(properties.get(i).title);
        Ion.with(context)
                .load(properties.get(i).images.get(0).image)
                .withBitmap()
                .intoImageView(item_image);



        if (properties.get(i).stats.isEmpty()){
            stats_box.setVisibility(View.GONE);
        }else {
            String data = properties.get(i).stats;
            String[] items = data.split("\n");
            for (String item : items)
            {
                System.out.println("item = " + item);
                stats.setText(item.replace("_", "\n"));
            }
        }



        try {
            if (properties.get(i).price_disp != null) {
                if (properties.get(i).price_disp.equals("") || properties.get(i).area_disp.equals("")) {
                    flat_price.setVisibility(View.GONE);
                    flat_area.setVisibility(View.GONE);
                } else {
                    flat_price.setText(String.valueOf(properties.get(i).price_disp));
                    flat_area.setText(properties.get(i).area_disp);
                    flat_price.setVisibility(View.VISIBLE);
                    flat_area.setVisibility(View.VISIBLE);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

         try{
             flat_title.setText(properties.get(i).flats.get(0).title);
         }catch (Exception e){
             e.printStackTrace();
         }


        call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonParser jsonParser = new JsonParser();
                if (!Session.GetServices(context).equals("-1")) {
                    JsonObject parse = (JsonObject) jsonParser.parse(Session.GetSettings(context));
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + parse.get("phone").getAsString()));
                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    context.startActivity(callIntent);
                }
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Uri uri = Uri.parse("geo:13.070984,80.253639?q=13.070984,80.253639(location)");
//                Intent in = new Intent(Intent.ACTION_VIEW, uri);
//                context.startActivity(in);
                Uri mapUri = Uri.parse("geo:0,0?q=" + lat + "," + lng + "(" + loc + ")");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);
            }
        });


        return item_view;
    }





}
