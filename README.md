# Trustly Android SDK

The Trustly Android SDK provides an easy way to implement the Trustly Checkout in your Android app. The SDK handles communication with the Web View and exposes Checkout events that allows you to customize your Checkout flow. 

## Integration
Integrate the Trustly Android SDK through Gradle by adding the following to your dependencies in the build.gradle file.
```gradle
implementation 'trustly.android.library:trustly-android-library:+'
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
### Checkout Events
If you want more control of your Checkout flow you can choose to opt-in to receiving and handling Checkout events. 

You opt-in by creating an implementation of the TrustlyEventHandler interface.
```java
public interface TrustlyEventHandler {

  void onTrustlyCheckoutSuccess(TrustlySDKEventObject eventObject);

  void onTrustlyCheckoutRedirect(TrustlySDKEventObject eventObject);

  void onTrustlyCheckoutAbort(TrustlySDKEventObject eventObject);

  void onTrustlyCheckoutError(TrustlySDKEventObject eventObject);

}
```
and passing your implementation to the TrustlyWebView.

```java
TrustlyEventHandler eventHandler = new TrustlyEventHandlerImplementation();
TrustlyWebView trustlyView = new TrustlyWebView(this, trustlyCheckoutUrl, eventHandler);
```