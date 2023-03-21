package com.trustlyAndroidLibrary;

@FunctionalInterface
/**
 * Invoked when the TrustlyWebView has successfully completed an order.
 *
 * @implNote The webview will not autoclose and you can use this lambda to dismiss the web view yourself.
 */
public interface TrustlyCheckoutSuccessHandler {
    void onTrustlyCheckoutSuccess();
}