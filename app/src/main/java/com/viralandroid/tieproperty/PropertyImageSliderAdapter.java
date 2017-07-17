package com.viralandroid.tieproperty;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by T on 25-04-2017.
 */

public class PropertyImageSliderAdapter  extends PagerAdapter{
    Context context;
    int images[];
    LayoutInflater layoutInflater;
    Properties properties;

    public PropertyImageSliderAdapter(Context context, int images[],Properties properties){
        this.context = context;
        this.images = images;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.properties = properties;
    }
    @Override
    public int getCount() {
        return properties.images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position){
        View itemView = layoutInflater.inflate(R.layout.property_image_slider, container, false);
        final ImageView imageView = (ImageView) itemView.findViewById(R.id.myImage);
//        imageView.setImageResource(images[position]);
        try {
            Ion.with(context)
                    .load(properties.images.get(position).image)
                    .withBitmap()
                    .intoImageView(imageView);
            container.addView(itemView);
        }catch (Exception e){
            e.printStackTrace();
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,SlidingActivity.class);
                ArrayList<String> img;
                img = new ArrayList<String>();
                for (int i=0;i<properties.images.size();i++){
                    img.add(properties.images.get(i).image);
                }
                intent.putExtra("images",img);
                context.startActivity(intent);
            }
        });
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
