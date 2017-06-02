package com.viralandroid.tieproperty;

import android.content.Context;
import android.os.Parcelable;
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
     ArrayList<TrendingProperties> trendingProperties;
     LayoutInflater inflater;
     Context context;
    MainActivity mainActivity;
    ArrayList<Properties> properties;

    public TrendingPropertiesAdapter(Context context,ArrayList<TrendingProperties> trendingProperties,ArrayList<Properties> properties,MainActivity mainActivity) {
        this.context = context;
        this.trendingProperties=trendingProperties;
        inflater = LayoutInflater.from(context);
        this.properties = properties;
        this.mainActivity = mainActivity;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
         return trendingProperties.size();
    }


    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View imageLayout = inflater.inflate(R.layout.trending_property_image, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.myImage);
        final TextView property_name = (TextView) imageLayout.findViewById(R.id.property_name);
        LinearLayout trending_slide = (LinearLayout) imageLayout.findViewById(R.id.trending_slide);



        Ion.with(context).load(trendingProperties.get(position).image).withBitmap().placeholder(R.drawable.placeholder500x250).intoImageView(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context,PropertyDetailPage.class);
//                intent.putExtra("property_id",trendingProperties.get(position).id);
//                intent.putExtra("trending_prop",trendingProperties.get(position));
//                context.startActivity(intent);

                mainActivity.get_single_property(trendingProperties.get(position).property_id);
                Log.e("id",trendingProperties.get(position).property_id);


            }
        });
        property_name.setText(trendingProperties.get(position).property_name);

        view.addView(imageLayout, 0);
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
