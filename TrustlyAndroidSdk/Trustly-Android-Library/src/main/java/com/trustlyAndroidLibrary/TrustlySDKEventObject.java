package com.trustlyAndroidLibrary;

import android.app.Activity;

public class TrustlySDKEventObject {

  private TrustlyEventType type;
  private String url;
  private String packageName;
  private Activity activity;

  public TrustlySDKEventObject(String type, String url, String packageName, Activity activity) {
    this.type = TrustlyEventType.valueOfEventName(type);
    this.url = url;
    this.packageName = packageName;
    this.activity = activity;
  }

  public TrustlyEventType getType() {
    return type;
  }

  public String getUrl() {
    return url;
  }

  public String getPackageName() {
    return packageName;
  }

  public Activity getActivity() {
    return activity;
  }

  public enum TrustlyEventType {
    SUCCESS("onTrustlyCheckoutSuccess"),
    REDIRECT("onTrustlyCheckoutRedirect"),
    ABORT("onTrustlyCheckoutAbort"),
    ERROR("onTrustlyCheckoutError");

    private String eventName;

    TrustlyEventType(String eventName) {
      this.eventName = eventName;
    }

    public String getEventName() {
      return eventName;
    }

    public static TrustlyEventType valueOfEventName(String eventName) {
      for (TrustlyEventType e : values()) {
        if (e.eventName.equals(eventName)) {
          return e;
        }
      }
      return null;
    }
  }
}
