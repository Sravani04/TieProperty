package com.viralandroid.tieproperty;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by T on 12-05-2017.
 */

public class PrivacyPolicyPage extends Activity {
    ImageView close_btn;
    TextView policy;
    String privacy_policy;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.policy_page);
        close_btn = (ImageView) findViewById(R.id.close_btn);
        policy = (TextView) findViewById(R.id.policy);

        if (getIntent()!=null && getIntent().hasExtra("privacy")){
            privacy_policy = getIntent().getStringExtra("privacy");
        }

        policy.setText(Html.fromHtml(privacy_policy));

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
