package com.trustlyAndroidLibrary;

public interface TrustlyEventHandler {

  void onTrustlyCheckoutSuccess(TrustlySDKEventObject eventObject);

  void onTrustlyCheckoutRedirect(TrustlySDKEventObject eventObject);

  void onTrustlyCheckoutAbort(TrustlySDKEventObject eventObject);

  void onTrustlyCheckoutError(TrustlySDKEventObject eventObject);

}
