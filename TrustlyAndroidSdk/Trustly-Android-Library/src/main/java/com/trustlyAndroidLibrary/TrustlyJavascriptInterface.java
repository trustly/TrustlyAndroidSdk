/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Trustly Group AB
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.trustlyAndroidLibrary;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.JavascriptInterface;

enum TrustlyEventType {
    SUCCESS("onTrustlyCheckoutSuccess"),
    REDIRECT("onTrustlyCheckoutRedirect"),
    ABORT("onTrustlyCheckoutAbort"),
    ERROR("onTrustlyCheckoutError");

    final String eventTypeLabel;

    TrustlyEventType(String eventTypeLabel) {
        this.eventTypeLabel = eventTypeLabel;
    }

    static TrustlyEventType valueForEventTypeLabel(String eventName) {
        for (TrustlyEventType e : values()) {
            if (e.eventTypeLabel.equals(eventName)) {
                return e;
            }
        }
        return null;
    }
}


class TrustlyJavascriptInterface {

    public static final String NAME = "TrustlyAndroid";

    private final Activity activity;
    private final TrustlyWebView webViewHandler;

    public TrustlyJavascriptInterface(Activity activity, TrustlyWebView webViewHandler) {
        this.activity = activity;
        this.webViewHandler = webViewHandler;
    }


    private void handleRedirect(String URLString) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(URLString));
            activity.startActivityForResult(intent, 0);
        } catch (Error e) {
            Log.d("TrustlyAndroidSDK", "handleRedirect: Could not redirect to URL " + URLString);
        }

    }

    /**
     * Creates an event object from the parameters and passes it to the correct event handler method
     */
    @JavascriptInterface
    public void handleTrustlyEvent(String typeLabel, String url, String packageName) {

        TrustlyEventType eventType = TrustlyEventType.valueForEventTypeLabel(typeLabel);
        switch (eventType) {
            case SUCCESS:
                if (webViewHandler.successHandler != null) {
                    webViewHandler.successHandler.onTrustlyCheckoutSuccess();
                }
                break;
            case REDIRECT:
                handleRedirect(url);
                break;
            case ABORT:
                if (webViewHandler.abortHandler != null) {
                    webViewHandler.abortHandler.onTrustlyCheckoutAbort();
                }
                break;
            case ERROR:
                if (webViewHandler.errorHandler != null) {
                    webViewHandler.errorHandler.onTrustlyCheckoutError();
                }
                break;
            default:
                throw new UnsupportedOperationException(String.format("Unsupported event type: %s", typeLabel));
        }
    }
}