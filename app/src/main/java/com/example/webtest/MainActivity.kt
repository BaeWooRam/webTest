package com.example.webtest

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val webViewSetting =wv.settings
        webViewSetting.javaScriptEnabled = true
        webViewSetting.domStorageEnabled = true

        wv.webChromeClient = WebChromeClient()
        wv.webViewClient = WebViewClientClass()
        wv.addJavascriptInterface(AndroidBridge(), "app")
        wv.setNetworkAvailable(true)
        wv.loadUrl("https://jerryjerryjerry.tistory.com")
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wv.canGoBack()) {//웹뷰에서 뒤로가기 버튼을 누르면 뒤로가짐
            wv.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event)
    }

    private class WebViewClientClass : WebViewClient() {
        //페이지 이동
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            Log.i(javaClass.simpleName, "UrlLoading : $url")
            view.loadUrl(url)
            return true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            Log.i(javaClass.simpleName, "onPageFinished")
            super.onPageFinished(view, url)
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            Log.i(javaClass.simpleName, "onPageStarted")
            super.onPageStarted(view, url, favicon)
        }
    }

    private class AndroidBridge {
        val handler = Handler()

        @JavascriptInterface
        fun setMessage(arg:String) {
            handler.post(object :Runnable{
                override fun run() {
                    Log.i(javaClass.simpleName, "WebView Message : $arg")
                }
            })
    }
    }


}
