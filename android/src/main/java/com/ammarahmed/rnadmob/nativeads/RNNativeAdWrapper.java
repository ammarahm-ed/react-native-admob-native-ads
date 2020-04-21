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
import com.facebook.react.uimanager.util.ReactFindViewUtil;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;


import java.util.Random;

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

        Constants.cacheManager.attachAdListener(adListener);
    }

    public void createView(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View viewRoot = layoutInflater.inflate(R.layout.medium_template, this, true);
        nativeAdView = (UnifiedNativeAdView) viewRoot.findViewById(R.id.native_ad_view);

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
            attachViews();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    attachViews();
                }
            },500);


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


    public  void removeHandler() {
        if (handler != null) {
            handler.removeCallbacks(null);
            handler = null;
        }

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeHandler();
    }


    public void attachViews() {

        try {

            if (nativeAdView != null) {

                View advertiser = ReactFindViewUtil.findView(nativeAdView, adAdvertiser);
                if (advertiser != null)
                    nativeAdView.setAdvertiserView(advertiser);

                View headline = ReactFindViewUtil.findView(nativeAdView, adHeadline);
                if (headline != null)
                    nativeAdView.setHeadlineView(headline);

                View tagline = ReactFindViewUtil.findView(nativeAdView, adTagline);
                if (tagline != null)
                    nativeAdView.setBodyView(tagline);

                View store = ReactFindViewUtil.findView(nativeAdView, adStoreView);
                if (store != null)
                    nativeAdView.setStoreView(store);

                View iconv = ReactFindViewUtil.findView(nativeAdView, adIconView);
                if (iconv != null)
                    nativeAdView.setIconView(iconv);

                View image = ReactFindViewUtil.findView(nativeAdView, adImageView);
                if (image != null)

                    nativeAdView.setImageView(image);

                View callToAction = ReactFindViewUtil.findView(nativeAdView, adCallToAction);
                if (callToAction != null)
                    nativeAdView.setCallToActionView(callToAction);

                View adPriceView = ReactFindViewUtil.findView(nativeAdView, adPriceViews);
                if (adPriceView != null)
                    nativeAdView.setCallToActionView(adPriceView);

                View starRating = ReactFindViewUtil.findView(nativeAdView, adStarRating);
                if (starRating != null)
                    nativeAdView.setStarRatingView(starRating);
            }

        } catch (Exception e) {

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
            loadAd();
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

                if (Constants.cacheManager.numberOfAds() != 0) {

                    int numOfAds = Constants.cacheManager.numberOfAds();

                    Random random = new Random();
                    int randomNumber = random.nextInt(numOfAds - 0) + 0;

                    UnifiedNativeAd nativeAd = Constants.cacheManager.getNativeAd(randomNumber);

                    if (nativeAd != null) {

                        if (nativeAd != null) {
                            unifiedNativeAd = nativeAd;
                            nativeAdView.setNativeAd(unifiedNativeAd);
                        }
                        setNativeAdToJS(nativeAd);

                    } else {
                        if (adListener != null)
                            adListener.onAdFailedToLoad(3);
                    }

                } else {
                    Constants.cacheManager.loadNativeAds(mContext,admobAdUnitId,5,600000);
                    if (adListener != null)
                        adListener.onAdFailedToLoad(3);
                }

            }
        },loadWithDelay);
    }

    public  void setLoadWithDelay(int delay) {
        loadWithDelay = delay;
    }

    public void addNewView(View child, int index) {
        try {
            nativeAdView.addView(child, index);

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


}
