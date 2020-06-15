package com.ammarahmed.rnadmob.nativeads;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

public class RNNativeAdWrapper extends LinearLayout {

    private final Runnable measureAndLayout = new Runnable() {
        @Override
        public void run() {
            measure(
                    MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
            layout(getLeft(), getTop(), getRight(), getBottom());
        }
    };
    public int adRefreshInterval = 60000;
    Context mContext;
    UnifiedNativeAdView nativeAdView;
    UnifiedNativeAd unifiedNativeAd;
    AdListener adListener = new AdListener() {
        @Override
        public void onAdFailedToLoad(int i) {
            super.onAdFailedToLoad(i);
            String errorMessage = "Unknown error";
            switch (i) {
                case AdRequest.ERROR_CODE_INTERNAL_ERROR:
                    errorMessage = "Internal error, an invalid response was received from the ad server.";
                    break;
                case AdRequest.ERROR_CODE_INVALID_REQUEST:
                    errorMessage = "Invalid ad request, possibly an incorrect ad unit ID was given.";
                    break;
                case AdRequest.ERROR_CODE_NETWORK_ERROR:
                    errorMessage = "The ad request was unsuccessful due to network connectivity.";
                    break;
                case AdRequest.ERROR_CODE_NO_FILL:
                    errorMessage = "The ad request was successful, but no ad was returned due to lack of ad inventory.";
                    break;
            }
            WritableMap event = Arguments.createMap();
            WritableMap error = Arguments.createMap();
            error.putString("message", errorMessage);
            event.putMap("error", error);
            sendEvent(RNAdMobNativeViewManager.EVENT_AD_FAILED_TO_LOAD, event);
        }

        @Override
        public void onAdClosed() {
            super.onAdClosed();
            sendEvent(RNAdMobNativeViewManager.EVENT_AD_CLOSED, null);
        }

        @Override
        public void onAdOpened() {
            super.onAdOpened();
            sendEvent(RNAdMobNativeViewManager.EVENT_AD_OPENED, null);
        }

        @Override
        public void onAdClicked() {
            super.onAdClicked();
            sendEvent(RNAdMobNativeViewManager.EVENT_AD_CLICKED, null);

        }

        @Override
        public void onAdLoaded() {
            super.onAdLoaded();
            sendEvent(RNAdMobNativeViewManager.EVENT_AD_LOADED, null);
        }

        @Override
        public void onAdImpression() {
            super.onAdImpression();
            sendEvent(RNAdMobNativeViewManager.EVENT_AD_IMPRESSION, null);
        }

        @Override
        public void onAdLeftApplication() {
            super.onAdLeftApplication();
            sendEvent(RNAdMobNativeViewManager.EVENT_AD_LEFT_APPLICATION, null);
        }
    };
    private int loadWithDelay = 1000;
    private String admobAdUnitId = "";
    private Handler handler;
    UnifiedNativeAd.OnUnifiedNativeAdLoadedListener onUnifiedNativeAdLoadedListener = new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
        @Override
        public void onUnifiedNativeAdLoaded(UnifiedNativeAd nativeAd) {
            if (nativeAd != null) {
                unifiedNativeAd = nativeAd;
                nativeAdView.setNativeAd(unifiedNativeAd);
            }

            setNativeAdToJS(nativeAd);
        }
    };


    public RNNativeAdWrapper(Context context) {
        super(context);
        mContext = context;
        createView(context);
        handler = new Handler();
    }

    public void createView(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View viewRoot = layoutInflater.inflate(R.layout.rn_ad_unified_native_ad, this, true);
        nativeAdView = (UnifiedNativeAdView) viewRoot.findViewById(R.id.native_ad_view);

    }

    public void addMediaView(int id) {

        try {
            RNMediaView adMediaView = (RNMediaView) nativeAdView.findViewById(id);
            if (adMediaView != null) {
                nativeAdView.setMediaView(adMediaView);
                adMediaView.requestLayout();
                if (unifiedNativeAd != null && unifiedNativeAd.getMediaContent().hasVideoContent()) {
                    //unifiedNativeAd.getMediaContent().getVideoController().play();
                }
            }
        } catch (Exception e) {

        }
    }

    private void setNativeAdToJS(UnifiedNativeAd nativeAd) {
        try {
            WritableMap args = Arguments.createMap();
            args.putString("headline", nativeAd.getHeadline());
            args.putString("tagline", nativeAd.getBody());
            args.putString("advertiser", nativeAd.getAdvertiser());
            args.putString("callToAction", nativeAd.getCallToAction());
            args.putBoolean("video", nativeAd.getMediaContent().hasVideoContent());
            args.putString("price", nativeAd.getPrice());
            if (nativeAd.getStore() != null) {
                args.putString("store", nativeAd.getStore());
            }
            if (nativeAd.getStarRating() != null) {
                args.putInt("rating", nativeAd.getStarRating().intValue());
            }

            args.putString("aspectRatio", String.valueOf(1));

            WritableArray images = Arguments.createArray();
                
            if (nativeAd.getImages() != null &&  nativeAd.getImages().size() > 0 ) {
                for (int i = 0; i < nativeAd.getImages().size(); i++) {
                    WritableMap map = Arguments.createMap();
                    map.putString("url", nativeAd.getImages().get(i).getUri().toString());
                    map.putInt("width", nativeAd.getImages().get(i).getWidth());
                    map.putInt("height", nativeAd.getImages().get(i).getHeight());
                    images.pushMap(map);
                }
            }

            args.putArray("images", images);
            args.putString("icon", nativeAd.getIcon().getUri().toString());
            sendEvent(RNAdMobNativeViewManager.EVENT_UNIFIED_NATIVE_AD_LOADED, args);

        } catch (Exception e) {

        }
        if (handler != null) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadAd();
                }
            }, adRefreshInterval);
        }
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    private void sendEvent(String name, @Nullable WritableMap event) {

        ReactContext reactContext = (ReactContext) mContext;
        reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                getId(),
                name,
                event);
    }

    private void loadAd() {

                try {
                    AdLoader.Builder builder = new AdLoader.Builder(mContext, admobAdUnitId);
                    builder.forUnifiedNativeAd(onUnifiedNativeAdLoadedListener);

                    VideoOptions videoOptions = new VideoOptions.Builder()
                            .setStartMuted(true)
                            .build();

                    NativeAdOptions adOptions = new NativeAdOptions.Builder()
                            .setVideoOptions(videoOptions)
                            .setAdChoicesPlacement(NativeAdOptions.ADCHOICES_TOP_RIGHT)
                            .build();
                    builder.withNativeAdOptions(adOptions);

                    AdLoader adLoader = builder.withAdListener(adListener)
                            .build();

                    adLoader.loadAd(new AdRequest.Builder().build());

                } catch (Exception e) {
                }


    }

    public void setLoadWithDelay(int delay) {
        loadWithDelay = delay;
    }


    public void addNewView(View child, int index) {
        try {
            nativeAdView.addView(child, index);
            requestLayout();
            nativeAdView.requestLayout();
        } catch (Exception e) {

        }

    }

    @Override
    public void addView(View child) {
        super.addView(child);
        requestLayout();
    }

    public void setAdRefreshInterval(int interval) {
        adRefreshInterval = interval;
    }

    public void setAdUnitId(String id) {
        admobAdUnitId = id;
        loadAd();
    }

    @Override
    public void requestLayout() {
        super.requestLayout();
        post(measureAndLayout);
    }


    public void removeHandler() {
        if (handler != null) {
            handler.removeCallbacks(null);
            handler = null;
        }

    }


}
