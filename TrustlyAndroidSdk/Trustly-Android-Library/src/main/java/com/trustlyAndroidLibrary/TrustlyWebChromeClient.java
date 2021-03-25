package com.trustlyAndroidLibrary;

import android.os.Message;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.browser.customtabs.CustomTabsIntent;

import java.lang.ref.WeakReference;

class TrustlyWebChromeClient extends WebChromeClient {

  @Override
  public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
    WeakReference<WebView> tabView = new WeakReference<>(new WebView(view.getContext()));
    WebView webview = tabView.get();
    if (webview != null) {
      webview.setWebViewClient(new Client());
      WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
      transport.setWebView(webview);
      resultMsg.sendToTarget();

      return true;
    }
    return false;
  }

  private static class Client extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
      CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
      CustomTabsIntent customTab = builder.build();
      customTab.launchUrl(view.getContext(), request.getUrl());
      return true;
    }
  }
}
