package com.ammarahmed.rnadmob.nativeads;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.CatalystInstance;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

import java.util.UUID;

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
    public Boolean requestMute = true;
    ReactContext mContext;
    UnifiedNativeAdView nativeAdView;
    UnifiedNativeAd unifiedNativeAd;
    RNAdMobUnifiedAdContainer unifiedNativeAdContainer;
    MediaView mediaView;

    protected @Nullable
    String messagingModuleName;

    private int adChoicesPlacement = 1;
    private boolean requestNonPersonalizedAdsOnly = false;


    AdListener adListener = new AdListener() {
        @Override
        public void onAdFailedToLoad(LoadAdError adError) {
            super.onAdFailedToLoad(adError);
            String errorMessage = "Unknown error";
            switch (adError.getCode()) {
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
            error.putString("errorMessage", adError.getMessage());
            error.putString("message", errorMessage);
            error.putInt("code", adError.getCode());
//            error.putString("responseInfo", adError.getResponseInfo() != null ? adError.getResponseInfo().toString() : "");
            event.putMap("error", error);
            sendEvent(RNAdmobNativeViewManager.EVENT_AD_FAILED_TO_LOAD, event);

            if (handler != null) {
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        loadAd();
                    }
                };
                handler.postDelayed(runnable, adRefreshInterval);
            }
        }

        @Override
        public void onAdClosed() {
            super.onAdClosed();
            sendEvent(RNAdmobNativeViewManager.EVENT_AD_CLOSED, null);
        }

        @Override
        public void onAdOpened() {
            super.onAdOpened();
            sendEvent(RNAdmobNativeViewManager.EVENT_AD_OPENED, null);
        }

        @Override
        public void onAdClicked() {
            super.onAdClicked();
            sendEvent(RNAdmobNativeViewManager.EVENT_AD_CLICKED, null);

        }

        @Override
        public void onAdLoaded() {
            super.onAdLoaded();
            if (adRepo != null){
                CacheManager.instance.detachAdListener(adRepo);
                loadAd();
            }
            sendEvent(RNAdmobNativeViewManager.EVENT_AD_LOADED, null);
        }

        @Override
        public void onAdImpression() {
            super.onAdImpression();
            sendEvent(RNAdmobNativeViewManager.EVENT_AD_IMPRESSION, null);
        }

//        @Override
//        public void onAdLeftApplication() {
//            super.onAdLeftApplication();
//            sendEvent(RNAdMobNativeViewManager.EVENT_AD_LEFT_APPLICATION, null);
//        }
    };
    private int loadWithDelay = 1000;
    private String admobAdUnitId = "";
    private String adRepo = null;
    private Handler handler;


    public RNNativeAdWrapper(ReactContext context) {
        super(context);
        mContext = context;
        createView(context);
        handler = new Handler();
        mCatalystInstance = mContext.getCatalystInstance();
        setId(UUID.randomUUID().hashCode() + this.getId());
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
            }
        } catch (Exception e) {
            // ignore
        }
    }

    private Runnable runnable;

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


            if (nativeAd.getResponseInfo() != null && nativeAd.getResponseInfo().getMediationAdapterClassName() != null) {
                if (nativeAd.getResponseInfo().getMediationAdapterClassName().equals("com.google.ads.mediation.admob.AdMobAdapter")) {
                    if (nativeAd.getMediaContent() != null) {
                        float aspectRatio = nativeAd.getMediaContent().getAspectRatio();

                        if (aspectRatio > 0) {
                            args.putString("aspectRatio", String.valueOf(aspectRatio));
                        } else {
                            args.putString("aspectRatio", String.valueOf(1.0f));
                        }

                    }
                } else {
                    args.putString("aspectRatio", String.valueOf(1.0f));
                }
            } else {
                args.putString("aspectRatio", String.valueOf(1.0f));
            }


            WritableArray images = new WritableNativeArray();

            if (nativeAd.getImages() != null && nativeAd.getImages().size() > 0) {

                for (int i = 0; i < nativeAd.getImages().size(); i++) {
                    WritableMap map = Arguments.createMap();
                    if (nativeAd.getImages().get(i) != null) {
                        map.putString("url", nativeAd.getImages().get(i).getUri().toString());
                        map.putInt("width", nativeAd.getImages().get(i).getWidth());
                        map.putInt("height", nativeAd.getImages().get(i).getHeight());
                        images.pushMap(map);
                    }
                }
            }

            args.putArray("images", images);

            if (nativeAd.getIcon() != null) {
                args.putString("icon", nativeAd.getIcon().getUri().toString());

            } else {
                if (nativeAd.getResponseInfo()!=null && nativeAd.getResponseInfo().getMediationAdapterClassName() != null) {
                    if (nativeAd.getResponseInfo().getMediationAdapterClassName().equals("com.google.ads.mediation.admob.AdMobAdapter")) {
                        args.putString("icon", "noicon");
                    } else {
                        args.putString("icon", "empty");
                    }
                } else {
                    args.putString("icon", "empty");
                }
            }

            if (messagingModuleName == null){
                sendEvent(RNAdmobNativeViewManager.EVENT_UNIFIED_NATIVE_AD_LOADED, args);
            } else {
                sendDirectMessage(args);
            }

        } catch (Exception e) {
            // Log.d("HELLO", e.getMessage());

        }
        if (handler != null) {
            runnable = new Runnable() {
               @Override
               public void run() {
                   loadAd();
               }
            };
            handler.postDelayed(runnable, adRefreshInterval);
        }
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }


    public void setMessagingModuleName(String moduleName) {
        messagingModuleName = moduleName;
    }

    CatalystInstance mCatalystInstance;
    protected void sendDirectMessage(WritableMap data) {

        WritableNativeMap event = new WritableNativeMap();
        event.putMap("nativeEvent", data);
        WritableNativeArray params = new WritableNativeArray();
        params.pushMap(event);

        if (mCatalystInstance != null){
            try {
                mCatalystInstance.callFunction(messagingModuleName, "onUnifiedNativeAdLoaded", params);
            }catch (Exception e){
                // ignore
            }
        }

    }



    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void sendEvent(String name, @Nullable WritableMap event) {

        ReactContext reactContext = (ReactContext) mContext;
        reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                getId(),
                name,
                event);
    }

    private void loadAd(){
        if (adRepo != null){
            getAdFromRepository();
        } else {
            requestAd();
        }
    }

    private void getAdFromRepository() {
        try {
            if (!CacheManager.instance.isRegistered(adRepo)) {
                if (adListener != null)
                    adListener.onAdFailedToLoad(new LoadAdError(3, "The requested repo is not registered", "", null, null));
            } else {
                if (CacheManager.instance.numberOfAds(adRepo) != 0) {
                    unifiedNativeAdContainer = CacheManager.instance.getNativeAd(adRepo);

                    // todo :: check if this is required
//                if (unifiedNativeAd != null) {
//                    unifiedNativeAd.destroy();
//                }
                    if (unifiedNativeAdContainer != null) {
                        unifiedNativeAd = unifiedNativeAdContainer.unifiedNativeAd;
                        nativeAdView.setNativeAd(unifiedNativeAd);
                        if (mediaView != null) {
                            nativeAdView.setMediaView(mediaView);
                            mediaView.requestLayout();
                        }
                        setNativeAdToJS(unifiedNativeAd);
                    }
                } else {
                    if (!CacheManager.instance.isLoading(adRepo)){
                        CacheManager.instance.attachAdListener(adRepo, adListener);
                        CacheManager.instance.requestAd(adRepo);
                    }else{
                        CacheManager.instance.attachAdListener(adRepo, adListener);
                    }
                }
            }

        } catch (Exception e) {
            adListener.onAdFailedToLoad(new LoadAdError(3, e.toString(), "", null, null));
        }
    }

    private void requestAd(){
        try {
            UnifiedNativeAd.OnUnifiedNativeAdLoadedListener onUnifiedNativeAdLoadedListener = new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                @Override
                public void onUnifiedNativeAdLoaded(UnifiedNativeAd nativeAd) {

                    if (unifiedNativeAd != null) {
                        unifiedNativeAd.destroy();
                    }
                    if (nativeAd != null) {

                        unifiedNativeAd = nativeAd;
                        nativeAdView.setNativeAd(unifiedNativeAd);
                        if (mediaView != null) {
                            nativeAdView.setMediaView(mediaView);
                            mediaView.requestLayout();
                        }

                    }

                    setNativeAdToJS(nativeAd);
                }
            };

            AdLoader.Builder builder = new AdLoader.Builder(mContext, admobAdUnitId);
            builder.forUnifiedNativeAd(onUnifiedNativeAdLoadedListener);

            VideoOptions videoOptions = new VideoOptions.Builder()
                    .setStartMuted(true)
                    .build();

            NativeAdOptions adOptions = new NativeAdOptions.Builder()
                    .setVideoOptions(videoOptions)
                    .setAdChoicesPlacement(adChoicesPlacement)
                    .build();
            builder.withNativeAdOptions(adOptions);


            AdLoader adLoader = builder.withAdListener(adListener)
                    .build();

            AdRequest adRequest;

            if (requestNonPersonalizedAdsOnly) {
                Bundle extras = new Bundle();
                extras.putString("npa", "1");
                adRequest = new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, extras).build();
            } else {
                adRequest = new AdRequest.Builder().build();
            }

            adLoader.loadAd(adRequest);

        } catch (Exception e) {
            adListener.onAdFailedToLoad(new LoadAdError(0, e.toString(), "", null, null));
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
            // ignore
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

    public void setMute(Boolean m) {
        requestMute = m;
    }

    public void setAdRepository(String repo) {
        adRepo = repo;
        if (adRepo == null) return;
        getAdFromRepository();
    }

    public void setAdUnitId(String id) {
        admobAdUnitId = id;
        if (id == null || adRepo != null) return;
        loadAd();
    }

    public void setAdChoicesPlacement(int location) {
        adChoicesPlacement = location;
    }

    public void setRequestNonPersonalizedAdsOnly(boolean npa) {
        requestNonPersonalizedAdsOnly = npa;
    }

    @Override
    public void requestLayout() {
        super.requestLayout();
        post(measureAndLayout);
    }

    public void removeHandler() {
        if (handler != null) {
            handler.removeCallbacks(runnable);
            handler = null;
        }
    }
}
