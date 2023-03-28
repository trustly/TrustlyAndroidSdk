package com.trustlyAndroidLibrary;

/**
 * Invoked when the TrustlyWebView has encountered an error
 *
 * @implNote The webview will not autoclose and you can use this lambda to dismiss the web view yourself.
 */
@FunctionalInterface
public interface TrustlyCheckoutErrorHandler {

    void onTrustlyCheckoutError();

}
