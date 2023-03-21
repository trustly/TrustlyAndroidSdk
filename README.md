# Trustly Android SDK

The Trustly Android SDK provides an easy way to implement the Trustly Checkout in your Android app. The SDK handles communication with the Web View and exposes Checkout events that allows you to customize your Checkout flow. 

 
## Integration
Integrate the Trustly Android SDK through Maven Central.
Add the following to your Project level `build.gradle` file
```gradle
repositories {
  ...
  mavenCentral()
  ...
}
```

Add Trustly SDK as dependency in your Module level the `build.gradle` file like so
```gradle
dependencies {
  ...
  //replace x.x.x with the current version
  implementation 'com.trustly:trustly-android-library:x.x.x'
  ...
}
```

You also need to add the `INTERNET` permission to your `AndroidManifest.xml`
```xml
<uses-permission android:name="android.permission.INTERNET"/>
```

## Usage
Add a WebView to the activity you wish to launch the Trustly Checkout from.

Example usage:
```xml
<WebView
   android:id="@+id/webview"
   android:layout_width="match_parent"
   android:layout_height="match_parent"/>
```

Instantiate a TrustlyWebView by passing the CheckouURL as a parameter from the activity. Assign the TrustlyWebView to the WebView.
```java
WebView main = findViewById(R.id.webview);
TrustlyWebView trustlyView = new TrustlyWebView(this, trustlyCheckoutUrl);
main.addView(trustlyView);
```
### Receiving Checkout Events
If you want more control of your Checkout flow you can choose to provide custom handlers.

Provide `successHanlder`, `abortHanlder` and `errorHandler` lambdas.
In case no custom functionality is provided, the webview will load the `SuccessURL`, in case of a success event, or the `FailURL` in case of a error or an abort event.
Read more https://eu.developers.trustly.com/doc/docs/order-initiation


```java
 trustlyView = new TrustlyWebView(this, trustlyCheckoutUrl);
    trustlyView.successHandler = () -> {
      // your custom implementation here.
    };
    trustlyView.errorHandler = () -> {
      // your custom implementation here.
    };
    trustlyView.abortHandler = () -> {
      // your custom implementation here.
    };
```


## Automatic re-directs back to your application
It can happen that during the order flow, the user needs to be redirected outside of your application, to a third party application or website (in external stand alone browser). This could be part of the authentication and authorisation process.
To enable automatic re-directs back to your native application, you can pass a [link](https://developer.android.com/training/app-links) as an attribute to the order initiation request. You can pass your link (web, app link or deep link) value by including it in the "URLScheme" attribute when making an API call to Trustly. [You can read more about it here.](https://developers.trustly.com/emea/docs/ios)

:warning: It's important that the Activity hosting the checkout doesn't reload when the intent is received. Otherwise the Trustly checkout session will be lost. 
When defining you intent filters make sure to set `android:launchMode="singleTop"` parameter in your manifest file.
Below is an example intent filter setup in the manifest xml.
```
<activity
            android:name=".YOUR_TRUSTLY_WEB_VIEW_ACTIVITY"
            android:exported="true"
            android:launchMode="singleTop">
 <intent-filter>
                <action android:name="android.intent.action.VIEW" />
                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />

                <data
                    android:host="@string/scheme_host_sdk"
                    android:scheme="@string/scheme" />
</intent-filter>
```

