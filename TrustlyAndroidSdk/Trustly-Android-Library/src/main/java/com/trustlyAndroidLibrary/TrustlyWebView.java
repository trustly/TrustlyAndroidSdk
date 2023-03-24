package com.trustlyAndroidLibrary;

import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

/**
 * A wrapper around WebView allowing communication with the Trustly checkout.
 *
 * The checkout passes events (success, error and abort)  to the web view.
 * Through `successHanlder`, `abortHanlder` and `errorHandler` variables you you can provide your own
 * custom logic for those events
 * @implNote Note if no custom event handling is provided, the web view will load the `SuccessURL`
 * for success eventsor `FailURL` for error and abort events.
 * The value of these parameters is passed by your backend API call to the Trustly backend.
 * @see https://eu.developers.trustly.com/doc/docs/order-initiation
 */
public class TrustlyWebView extends WebView {
  /**
   * Custom lambda that will be invoked when the Trustly checkout has successfully completed an order.
   */
  public TrustlyCheckoutSuccessHandler successHandler = null;
  /**
   * Custom lambda that will be invoked when the Trustly checkout has encountered an error.
   */
  public TrustlyCheckoutErrorHandler errorHandler = null;
  /**
   * Custom lambda that will be invoked when the Trustly checkout has been aborted by the end user.
   */
  public TrustlyCheckoutAbortHandler abortHandler = null;

  public TrustlyWebView(AppCompatActivity activity, String url) {
    super(activity);
    tryOpeningUrlInWebView(activity, url);
  }

  private void tryOpeningUrlInWebView(AppCompatActivity activity, String url) {
    try {
      // Enable javascript and DOM Storage
      configWebSettings();

      this.setWebViewClient(new WebViewClient());
      this.setWebChromeClient(new TrustlyWebChromeClient());
      this.addJavascriptInterface(new TrustlyJavascriptInterface(activity, this), TrustlyJavascriptInterface.NAME);

      this.setLayoutParams(
          new LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)));

      this.loadUrl(url);
    } catch (WebSettingsException e) {
      Log.d("WebView", "configWebView: Could not config WebSettings");
    } catch (Exception e) {
      Log.d("WebView", "configWebView: Unknown Problem happened");
    }
  }

  private void configWebSettings() throws WebSettingsException {
    try {
      WebSettings webSettings = getSettings();
      webSettings.setJavaScriptEnabled(true);
      webSettings.setDomStorageEnabled(true);
      webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
      webSettings.setSupportMultipleWindows(true);
    } catch (Exception e) {
      throw new WebSettingsException(e.getMessage());
    }
  }
}
