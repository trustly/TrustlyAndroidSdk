# Trustly Android SDK

The Trustly Android SDK provides an easy way to implement the Trustly Checkout in your Android app. The SDK handles communication with the Web View and exposes Checkout events that allows you to customize your Checkout flow. 

**`NOTE: The latest version of the SDK does not support the older version of the Trustly Checkout. If you do use the older Checkout, please use version 2.0.0 of the SDK. If you're not sure what version of the Trustly Checkout you are using, please contact our intergration support for help`**
 
## Integration
Integrate the Trustly Android SDK through Gradle.

To authenticate with Github Packages you need to add your GitHub username and a [GitHub Access Token](https://docs.github.com/en/github/authenticating-to-github/keeping-your-account-and-data-secure/creating-a-personal-access-token) to your project level build.gradle file. 

Make sure that you your token have both read and write permissions for packages.

**`NOTE: Do not commit these credentials to your repo.`**
```gradle
repositories {
      maven {
            url = uri("https://maven.pkg.github.com/trustly/TrustlyAndroidSdk")
            credentials {
                username = "<GITHUB-USERNAME"
                password = "<ACCESS-TOKEN>"
            }
    }
}
```

Add the following to your dependencies in the build. gradle file.
```gradle
implementation 'trustly.android.library:trustly-android-library:+'
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

## Handling of TrustlyCheckoutEvents
If you provide you own implementation of the TrustlyEventHandler, when a redirect happens in
```
void onTrustlyCheckoutRedirect(TrustlySDKEventObject eventObject);
```
 you will need to handle opening of third party applications yourself.
In this case **refrain** from querying for particular schemes or packages before starting an activity, due to limited app visibility. This affects the return results of methods that give information about other apps, such as ```queryIntentActivities()```, ```getPackageInfo()```, and ```getInstalledApplications()```.

Prefer using
```
startActivity(intent);
```
but embedded in a try-catch block. The case of no activity installed with the ability to open the redirect should be hanled in the catch block.

Sample code bellow
```java
  @Override
  public void onTrustlyCheckoutRedirect(TrustlySDKEventObject eventObject) {
    Activity activity = eventObject.getActivity();
    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(eventObject.getUrl()));
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    try {
      activity.startActivity(intent);
    } catch (ActivityNotFoundException e) {
      // Define what your app should do if no activity can handle the intent.
    }
  }
```
## Notes about URLScheme

Please note that when rendering the Trustly Checkout from a native app you are required to pass your application’s [URL scheme](https://developer.android.com/training/app-links/deep-linking) as an attribute to the order initiation request. By doing so, Trustly can redirect users back to your app after using external identification apps. 

You can pass your URLScheme by including it in the "URLScheme" attribute when making an API call to Trustly. [You can read more about it here.](https://developers.trustly.com/emea/docs/android#custom-url-scheme)
