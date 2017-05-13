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

public class TermsPage extends Activity {
    TextView terms;
    ImageView close_btn;
    String term;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms_page);
        terms = (TextView) findViewById(R.id.terms);
        close_btn = (ImageView) findViewById(R.id.close_btn);

        if (getIntent()!=null && getIntent().hasExtra("terms")){
            term = getIntent().getStringExtra("terms");
        }

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        terms.setText(Html.fromHtml(term));



    }
}
