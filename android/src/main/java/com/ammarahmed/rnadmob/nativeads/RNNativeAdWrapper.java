package com.ammarahmed.rnadmob.nativeads;

import android.content.Context;
import android.os.Handler;
import android.view.Choreographer;
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

    Context mContext;
    UnifiedNativeAdView nativeAdView;
    UnifiedNativeAd unifiedNativeAd;
    public int adRefreshInterval = 60000;
    public static final String adPriceViews = "adPriceView";
    public static final String adHeadline = "adHeadlineView";
    public static final String adTagline = "adTaglineView";
    public static final String adAdvertiser = "adAdvertiserView";
    public static final String adStarRating = "adStarRating";
    public static final String adImageView = "adImageView";
    public static final String adIconView = "adIconView";
    public static final String adCallToAction = "adCallToAction";
    public static final String adStoreView = "adStoreView";

    private int loadWithDelay = 1000;

    public int AdChoicesViewId = 733;
    private String admobAdUnitId = "";

    private Handler handler;

    private final Runnable measureAndLayout = new Runnable() {
        @Override
        public void run() {
            measure(
                    MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
            layout(getLeft(), getTop(), getRight(), getBottom());
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
        setupLayoutHack();
    }


    public void addMediaView(int id) {

        try {
            RNMediaView adMediaView = (RNMediaView) nativeAdView.findViewById(id);

            if (adMediaView != null) {
                nativeAdView.setMediaView(adMediaView.mediaView);
                if (unifiedNativeAd != null && unifiedNativeAd.getMediaContent().hasVideoContent()) {
                    adMediaView.mediaView.setMediaContent(unifiedNativeAd.getMediaContent());
                    unifiedNativeAd.getMediaContent().getVideoController().play();
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

            WritableArray images = Arguments.createArray();

            images.pushString(nativeAd.getImages().get(0).getUri().toString());
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
    

    private void sendEvent(String name, @Nullable WritableMap event) {

        ReactContext reactContext = (ReactContext) mContext;
        reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                getId(),
                name,
                event);
    }

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


    private void loadAd() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


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
        }, loadWithDelay);


    }

    public void setLoadWithDelay(int delay) {
        loadWithDelay = delay;
    }


    public void addNewView(View child, int index) {
        try {
            nativeAdView.addView(child, index);
            setupLayoutHack();
        } catch (Exception e) {

        }

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

    void setupLayoutHack() {

        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long frameTimeNanos) {
                manuallyLayoutChildren();
                getViewTreeObserver().dispatchOnGlobalLayout();
                Choreographer.getInstance().postFrameCallback(this);
            }
        });
    }

    void manuallyLayoutChildren() {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(MeasureSpec.makeMeasureSpec(getMeasuredWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.EXACTLY));
            child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
        }
    }


}
