package com.ammarahmed.rnadmob.nativeads;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.uimanager.util.ReactFindViewUtil;
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
    RNAdComponentsWrapper adComponentsWrapper;
    public String adHeadline = "adHeadlineView";
    public String adTagline = "adTaglineView";
    public String adAdvertiser = "adAdvertiserView";
    public String adStarRating = "adStarRating";
    public String adImageView = "adImageView";
    public String adIconView = "adIconView";
    public String adCallToAction = "adCallToAction";
    public String adStoreView = "adStoreView";
    public int AdChoicesViewId = 733;
    private String admobAdUnitId = "";
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
            args.putString("body", nativeAd.getBody());
            args.putString("advertiser", nativeAd.getAdvertiser());
            args.putString("callToAction", nativeAd.getCallToAction());
            args.putBoolean("video", nativeAd.getMediaContent().hasVideoContent());

            if (nativeAd.getStore() != null) {
                args.putString("store", nativeAd.getStore());
            }

            if (nativeAd.getStarRating() != null) {
                args.putInt("rating", nativeAd.getStarRating().intValue());
            }

            WritableArray images = Arguments.createArray();

            for (int i = 0; i < nativeAd.getImages().size(); i++) {
                WritableMap map = Arguments.createMap();

                map.putString("uri", nativeAd.getImages().get(i).getUri().toString());
                map.putInt("height", nativeAd.getImages().get(i).getHeight());
                map.putInt("width", nativeAd.getImages().get(i).getWidth());
                map.putInt("width", nativeAd.getImages().get(i).getWidth());
                images.pushMap(map);
            }
            args.putArray("images", images);

            WritableMap map = Arguments.createMap();
            map.putString("uri", nativeAd.getIcon().getUri().toString());
            map.putInt("height", nativeAd.getIcon().getHeight());
            map.putInt("width", nativeAd.getIcon().getWidth());
            map.putInt("width", nativeAd.getIcon().getWidth());
            args.putMap("icon", map);


            sendEvent(RNAdMobNativeViewManager.EVENT_UNIFIED_NATIVE_AD_LOADED, args);

            attachViews();

            nativeAdView.requestLayout();


        } catch (Exception e) {

        }
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

                View icon = ReactFindViewUtil.findView(nativeAdView, adIconView);
                if (icon != null)
                    nativeAdView.setIconView(icon);

                View image = ReactFindViewUtil.findView(nativeAdView, adImageView);
                if (image != null)

                    nativeAdView.setImageView(image);



                View callToAction = ReactFindViewUtil.findView(nativeAdView, adCallToAction);
                callToAction.setEnabled(true);
                callToAction.setClickable(true);
                if (callToAction != null)
                    nativeAdView.setCallToActionView(callToAction);

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
                nativeAdView.getId(),
                name,
                event);
    }

    private void loadAd() {

        AdLoader.Builder builder = new AdLoader.Builder(mContext, admobAdUnitId);
        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd nativeAd) {

                if (nativeAd != null) {
                    unifiedNativeAd = nativeAd;
                    nativeAdView.setNativeAd(unifiedNativeAd);
                }
                setNativeAdToJS(nativeAd);
            }
        });


        VideoOptions videoOptions = new VideoOptions.Builder()
                .setStartMuted(true)
                .build();
        NativeAdOptions adOptions = new NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build();
        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader = builder.withAdListener(new AdListener() {
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
        })
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());

    }




    public void addNewView(View child, int index) {
        nativeAdView.addView(child,index);
    }

    public  void setAdUnitId(String id) {
        admobAdUnitId = id;
        loadAd();
    }

    @Override
    public void requestLayout() {
        super.requestLayout();
        post(measureAndLayout);
    }


}
