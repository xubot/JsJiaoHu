package com.example.bckj.jsjiaohu;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init(){
        String url="http://api.map.baidu.com/place/detail?uid=f446091859ff806bb11e363b&output=html&source=placeapi_v2";
        mWebView =(WebView) findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.requestFocus();
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url == null) return false;
                try {
                    if (!url.startsWith("http") && !url.startsWith("https")){
                        return false;
                    }
                } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return false;
                }
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String insertJavaScript = "javascript:setTimeout(function(){{var s=document.createElement('script');s.type='text/javascript';s.charset='UTF-8';s.src=((location && location.href && location.href.indexOf('https') == 0)?'https://ssl.microsofttranslator.com':'http://www.microsofttranslator.com')+'/ajax/v3/WidgetV3.ashx?siteData=ueOIGRSKkd965FeEGM5JtQ**&ctf=False&ui=true&settings=undefined&from=zh-CHS';var p=document.getElementsByTagName('head')[0]||document.documentElement;p.insertBefore(s,p.firstChild); }},0)";
                //添加一个按钮
                //view.loadUrl("javascript:document.body.innerHTML += '<button id=MicrosoftTranslatorWidget></button>'");
               //添加翻译的按钮
                view.loadUrl("javascript:$('.scope-address').attr('id','MicrosoftTranslatorWidget')");
                //删除某些元素
                view.loadUrl("javascript:$('.captain-bar').css('display','none')");
                view.loadUrl("javascript:$('#nav_maplink').css('display','none!important')");
                view.loadUrl("javascript:$('.place-widget-banknearby').css('display','none')");
                view.loadUrl("javascript:$('.scope-widget-book').css('display','none')");
                view.loadUrl("javascript:$('.bottom-banner-float').css('display','none')");
                view.loadUrl("javascript:$('.common-footer-widget').css('display','none !important')");
                view.loadUrl("javascript:$('.common-widget-footer').css('display','none')");
                view.loadUrl("javascript:$('#scope_streetview').css('display','none')");
                view.loadUrl("javascript:$('.captain-img').attr('data-href','#')");
                //添加翻译的脚本
                view.loadUrl(insertJavaScript);
                Log.d("zzzz", 12344566 + "");
                //添加背景颜色
                //view.loadUrl("javascript:document.body.style.backgroundColor='#FF0000'");
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
    }
    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/
    final class InJavaScriptLocalObj {
        @JavascriptInterface
        public void showSource(String html) {
            Log.d("HTML", html);
        }
    }
}
