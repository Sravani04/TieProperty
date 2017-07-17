package com.viralandroid.tieproperty;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by yellowsoft on 19/6/17.
 */

public class ServicesPage extends Activity {
    String service_title;
    String service_desc;
    TextView services_title;
    WebView webView;
    ImageView close_btn;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.services_page);

        if (getIntent()!=null && getIntent().hasExtra("title")){
            service_title = getIntent().getStringExtra("title");
            service_desc = getIntent().getStringExtra("data");
        }

        services_title = (TextView) findViewById(R.id.services_title);
        webView = (WebView) findViewById(R.id.webView);
        close_btn = (ImageView) findViewById(R.id.close_btn);

        services_title.setText(service_title);
        webView.loadData(service_desc,"text/html","utf-8");
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
