package com.example.bckj.jsjiaohu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/8/11.
 */

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
    @JavascriptInterface
    public void forTaxi() {
        //Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.setClass(mContext, Main3Activity.class);
        activity.startActivity(intent);
    }
    @JavascriptInterface
    public void forMore(String flag) {
        Toast.makeText(mContext, flag, Toast.LENGTH_SHORT).show();
        if(flag!=null){

        }

    }
}
