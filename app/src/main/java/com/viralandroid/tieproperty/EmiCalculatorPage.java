package com.viralandroid.tieproperty;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * Created by T on 18-05-2017.
 */

public class EmiCalculatorPage extends Activity {
    private WebView webView;
    ProgressBar progress;
    ImageView close_btn;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emi_calculator_page);
        close_btn = (ImageView) findViewById(R.id.close_btn);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        webView=(WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new MyWebViewClient());
        webView.loadUrl(Session.SERVER_URL + "calculator.php");
        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setMax(100);
        progress.setProgress(0);

    }

    private class MyWebViewClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            EmiCalculatorPage.this.setValue(newProgress);
            super.onProgressChanged(view, newProgress);

        }
    }

    public void setValue(int progress) {
        this.progress.setProgress(progress);
    }
}
