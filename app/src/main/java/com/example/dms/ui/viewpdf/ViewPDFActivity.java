package com.example.dms.ui.viewpdf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.dms.R;

public class ViewPDFActivity extends AppCompatActivity {
    WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);

        final Intent intent = getIntent();
        webview = findViewById(R.id.webview_pdf);
        webview.requestFocus();
        webview.getSettings().setJavaScriptEnabled(true);
//        String myPdfUrl = "gymnasium-wandlitz.de/vplan/vplan.pdf";
//        String url = "https://docs.google.com/viewer?embedded = true&url = "+myPdfUrl;
//        webview.loadUrl(intent.getStringExtra());
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
//        webview.setWebChromeClient(new WebChromeClient() {
//            public void onProgressChanged(WebView view, int progress) {
//                if (progress < 100) {
//                    progressDialog.show();
//                }
//                if (progress = = 100) {
//                    progressDialog.dismiss();
//                }
//            }
//        });
    }
}
