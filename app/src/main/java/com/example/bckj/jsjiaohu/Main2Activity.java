package com.example.bckj.jsjiaohu;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    private WebView myWebView;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        iv = (ImageView) findViewById(R.id.im);
        myWebView = (WebView) findViewById(R.id.myWebView);
        //启用数据库
        myWebView.getSettings().setDatabaseEnabled(true);
        myWebView.getSettings().setDomStorageEnabled(true);
        myWebView.getSettings().setGeolocationEnabled(true);
        //设置定位的数据库路径
        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        myWebView.getSettings().setGeolocationDatabasePath(dir);
        //启用地理定位
        myWebView.getSettings().setGeolocationEnabled(true);
        //配置权限
        myWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
            }
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin,GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
        });

        myWebView.requestFocus();
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 与js交互，JavaScriptinterface是个接口，与js交互时用到的，这个接口实现了从网页跳到app中的activity的方法，特别重要
        myWebView.addJavascriptInterface(new JavaScriptinterface(this), "android");
        // 将html里的内容加载到webview中
        myWebView.loadUrl("http://118.190.91.24:8080/freewifi/index.html");
        myWebView.setWebViewClient(new myWebViewClient());
    }

    class myWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }
    }

    // 此按键监听的是返回键，能够返回到上一个网页（通过网页的hostlistery）
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public class JavaScriptinterface {
        private Context mContext;
        //这个一定要定义，要不在showToast()方法里没办法启动intent
        Activity activity;

        /** Instantiate the interface and set the context */
        public JavaScriptinterface(Context c) {
            mContext = c;
            activity = (Activity) c;
        }

        /** 与js交互时用到的方法，在js里直接调用的 */
       /* @JavascriptInterface
        public void forTaxi() {
            //Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(mContext, Main3Activity.class);
            activity.startActivity(intent);
        }*/
        @JavascriptInterface
        public void forMore(String flag) {
            Toast.makeText(mContext, flag, Toast.LENGTH_SHORT).show();
            if(flag!=null){
                iv.setVisibility(View.GONE);
                //flag=null;
            }else {
                //iv.setVisibility(View.VISIBLE);
            }
        }
    }

}
