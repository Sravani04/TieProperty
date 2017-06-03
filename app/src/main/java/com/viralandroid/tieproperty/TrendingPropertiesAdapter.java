package com.viralandroid.tieproperty;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by T on 30-05-2017.
 */

public class TrendingPropertiesAdapter  extends PagerAdapter{
    Context context;
    int images[];
    LayoutInflater inflater;
    ArrayList<Properties> properties;
    MainActivity mainActivity;
    ArrayList<TrendingProperties> trendingProperties;

    public TrendingPropertiesAdapter(Context context,ArrayList<TrendingProperties> trendingProperties,ArrayList<Properties> properties,MainActivity mainActivity) {
        this.context = context;
        this.trendingProperties=trendingProperties;
        inflater = LayoutInflater.from(context);
        this.properties = properties;
        this.mainActivity = mainActivity;
    }

    @Override
    public int getCount() {
        return trendingProperties.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container,final int position){
        View itemView = inflater.inflate(R.layout.trending_property_image, container, false);
        final ImageView imageView = (ImageView) itemView.findViewById(R.id.myImage);
        LinearLayout trending_slide = (LinearLayout) itemView.findViewById(R.id.trending_slide);
        final TextView property_name = (TextView) itemView.findViewById(R.id.property_name);
//        imageView.setImageResource(images[position]);
        try {
            Ion.with(context)
                    .load(trendingProperties.get(position).image)
                    .withBitmap()
                    .placeholder(R.drawable.placeholder500x250)
                    .intoImageView(imageView);
            container.addView(itemView);
        }catch (Exception e){
            e.printStackTrace();
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.get_single_property(trendingProperties.get(position).property_id);
                Log.e("id",trendingProperties.get(position).property_id);


            }
        });
        property_name.setText(trendingProperties.get(position).property_name);
        return itemView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
