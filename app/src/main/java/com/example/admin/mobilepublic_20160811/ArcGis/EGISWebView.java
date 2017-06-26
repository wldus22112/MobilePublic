package com.example.admin.mobilepublic_20160811.ArcGis;

import android.app.Activity;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.admin.mobilepublic_20160811.R;

/**
 * Created by admin on 2016-08-22.
 */
public class EGISWebView extends Activity {

    private int num;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);


        Intent intent = getIntent();
        num = intent.getIntExtra("EGIS",0);

        Log.v("num : ",Integer.toString(num));

        String url = null;

        if(num==1){
            url = "https://egis.me.go.kr/ba/epeIntroPage.do?mode=1";

        }else if(num==2){
            url = "https://egis.me.go.kr/ba/ezmIntroPage.do?mode=1";

        }else if(num==3){
            url = "https://egis.me.go.kr/ba/epeIntroPage.do?mode=1";

        }else{
            url = "https://egis.me.go.kr/main.do";
        }



        WebView webView = (WebView)findViewById(R.id.webview);



        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
                handler.proceed();
            }
        });

        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webView.loadUrl(url);


    }

    }
