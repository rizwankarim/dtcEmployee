package com.example.dtcemployee.Activities;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dtcemployee.R;

public class ViewerActivity extends AppCompatActivity {

    WebView webview;
    AlertDialog loadingDialog;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer);
        url =  getIntent().getStringExtra("orign");

        webview = findViewById(R.id.webview);
        showLoadingDialog();

        webview.loadUrl(url);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.setInitialScale(1);






        webview.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url){
                // do your stuff here
                hideLoadingDialog();
            }
        });
}

    public void  showLoadingDialog() {
        loadingDialog = new AlertDialog.Builder(this).create();
        View view = LayoutInflater.from(this).inflate(R.layout.loading_dailoug,null,false);
        loadingDialog.setView(view);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadingDialog.show();

    }

    public void hideLoadingDialog() {
        loadingDialog.dismiss();
    }
}