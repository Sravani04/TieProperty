package com.viralandroid.tieproperty;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

/**
 * Created by T on 11-05-2017.
 */

public class AboutUsPage extends Activity {
    ImageView close_btn,logo_image;
    TextView about;
    String abouttext,logo,panel;
    SlidingPaneLayout slidingPaneLayout;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us_page);
        close_btn = (ImageView) findViewById(R.id.close_btn);
        logo_image = (ImageView) findViewById(R.id.logo_image);
        about = (TextView) findViewById(R.id.about);

        if (getIntent()!=null && getIntent().hasExtra("aboutus")){
            abouttext =  getIntent().getStringExtra("aboutus");
            logo = getIntent().getStringExtra("tielogo");
        }

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        about.setText(Html.fromHtml(abouttext));
        Ion.with(getApplicationContext()).load(logo).intoImageView(logo_image);

    }
}
