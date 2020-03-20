package com.ammarahmed.rnadmob.nativeads;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

import java.util.Arrays;
import java.util.List;

class ReactNativeView extends LinearLayout {

    private static final String TAG = "RNNativeAdView";
    private final Runnable measureAndLayout = new Runnable() {
        @Override
        public void run() {
            measure(
                    MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
            layout(getLeft(), getTop(), getRight(), getBottom());
        }
    };

    protected UnifiedNativeAd unifiedNativeAd;
    protected UnifiedNativeAdView unifiedNativeAdView;
    protected LinearLayout mediaContainer;
    protected ImageView imageView;
    private String adSize = "small";
    private String admobAdUnitId;
    private LinearLayout headingWrapper;
    private TextView headingText;
    private LinearLayout tagLineWrapper;
    private TextView tagLineText;
    private LinearLayout advertiserWrapper;
    private RatingBar ratingBar;
    private TextView advertiserText;
    private ImageView iconView;
    private MediaView mediaView;
    private LinearLayout callToActionParentView;
    private Button callToActionView;
    private LinearLayout background;

    GradientDrawable gradientDrawableA;
    GradientDrawable gradientDrawableB;
    GradientDrawable backroundDrawable;

    private String mTextColor;
    private String mBackgroundColor;
    private String mBorderColor;
    private int mBorderWidth;
    private int mBorderRadius;

    private String mBgBackground;
    private String mBgBorderColor;
    private int mBgBorderRadius;
    private int mBgBorderWidth;


    public ReactNativeView(Context context) {
        super(context);
        createNativeAd(context);
    }

    private void createNativeAd(Context context) {
        if (unifiedNativeAdView != null) {
            unifiedNativeAdView.destroy();
            removeAllViews();
        }

        int template;

        if (adSize.equals("medium")) {
            template = R.layout.ad_custom_layout;
        } else if (adSize.equals("large")) {
            template = R.layout.medium_template;
        } else {
            template = R.layout.small_template;
        }

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View viewRoot = layoutInflater.inflate(template, this, true);
        unifiedNativeAdView = (UnifiedNativeAdView) viewRoot.findViewById(R.id.native_ad_view);

    }

    private void initViews() {
        if (unifiedNativeAdView == null || unifiedNativeAd == null) return;

        mediaContainer = (LinearLayout) unifiedNativeAdView.findViewById(R.id.ad_media_container);
        advertiserText = (TextView) findViewById(R.id.tertiary);
        advertiserWrapper = (LinearLayout) findViewById(R.id.third_line);
        iconView = (ImageView) findViewById(R.id.icon);
        headingWrapper = (LinearLayout) findViewById(R.id.headline);
        background = (LinearLayout) findViewById(R.id.background);
        headingText = (TextView) findViewById(R.id.primary);
        tagLineText = (TextView) findViewById(R.id.secondary);


        if (mediaContainer != null) {

            mediaView = (MediaView) unifiedNativeAdView.findViewById(R.id.media_view);
            imageView = (ImageView) unifiedNativeAdView.findViewById(R.id.image_view);
            unifiedNativeAdView.setMediaView(mediaView);
        }


        callToActionParentView = (LinearLayout) findViewById(R.id.cta_parent);
        tagLineWrapper = (LinearLayout) findViewById(R.id.body);
        callToActionView = (Button) findViewById(R.id.cta);


        ratingBar = (RatingBar) findViewById(R.id.rating_bar);

        unifiedNativeAdView.setCallToActionView(callToActionView);


        if (headingWrapper != null) {
            unifiedNativeAdView.setHeadlineView(headingWrapper);
        }

        if (advertiserWrapper != null) {
            unifiedNativeAdView.setStoreView(advertiserWrapper);
            unifiedNativeAdView.setAdvertiserView(advertiserWrapper);
        }

        if (ratingBar != null) {
            ratingBar.setEnabled(true);
            unifiedNativeAdView.setStarRatingView(ratingBar);
        }

        if (iconView != null) {
            unifiedNativeAdView.setIconView(iconView);
        }

        if (tagLineWrapper != null) {
            unifiedNativeAdView.setBodyView(tagLineWrapper);
        }


    }

    @Override
    public void requestLayout() {
        super.requestLayout();

        post(measureAndLayout);
    }

    private void refreshAd() {

        AdLoader.Builder builder = new AdLoader.Builder(getContext(), admobAdUnitId);
        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd nativeAd) {
                if (nativeAd != null) {
                    unifiedNativeAd = nativeAd;
                }

                if (unifiedNativeAdView != null) {
                    populateUnifiedNativeAdView();
                }
                sendEvent(RNAdMobNativeViewManager.EVENT_UNIFIED_NATIVE_AD_LOADED, null);
            }

        });


        VideoOptions videoOptions = new VideoOptions.Builder()
                .setStartMuted(true)
                .build();
        NativeAdOptions adOptions = new NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .setAdChoicesPlacement(NativeAdOptions.ADCHOICES_TOP_RIGHT)
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


    public void setTestDevices(String[] testDevices) {
        List<String> testDeviceIds = Arrays.asList(testDevices);
        RequestConfiguration configuration =
                new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.setRequestConfiguration(configuration);
    }


    private boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    private boolean adHasOnlyStore(UnifiedNativeAd nativeAd) {
        String store = nativeAd.getStore();
        String advertiser = nativeAd.getAdvertiser();
        return !isNullOrEmpty(store) && isNullOrEmpty(advertiser);
    }

    private boolean adHasOnlyAdvertiser(UnifiedNativeAd nativeAd) {
        String store = nativeAd.getStore();
        String advertiser = nativeAd.getAdvertiser();
        return !isNullOrEmpty(advertiser) && isNullOrEmpty(store);
    }

    private boolean adHasBothStoreAndAdvertiser(UnifiedNativeAd nativeAd) {
        String store = nativeAd.getStore();
        String advertiser = nativeAd.getAdvertiser();
        return (!isNullOrEmpty(advertiser)) && (!isNullOrEmpty(store));
    }


    private void populateUnifiedNativeAdView() {

        if (unifiedNativeAdView == null || unifiedNativeAd == null) return;

        try {
            initViews();

            setButtonStyle(mTextColor, mBackgroundColor, mBorderColor, mBorderWidth, mBorderRadius);
            setBackgroundStyle(mBgBackground, mBgBorderColor, mBgBorderWidth, mBgBorderRadius);

            if (mediaContainer != null) {

                VideoController vc = unifiedNativeAd.getMediaContent().getVideoController();

                vc.play();

                if (vc != null) {
                    vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                        public void onVideoEnd() {
                            super.onVideoEnd();


                        }
                    });

                }
                if (vc != null && vc.hasVideoContent()) {
                    unifiedNativeAdView.setMediaView(mediaView);
                    mediaView.setVisibility(VISIBLE);
                    imageView.setVisibility(GONE);
                } else {
                    unifiedNativeAdView.setImageView(imageView);
                    mediaView.setVisibility(GONE);
                    imageView.setVisibility(VISIBLE);

                    if (!unifiedNativeAd.getImages().isEmpty()) {
                        imageView.setImageDrawable(unifiedNativeAd.getImages().get(0).getDrawable());
                    }
                }
            }


            String store = unifiedNativeAd.getStore();
            String advertiser = unifiedNativeAd.getAdvertiser();
            String headline = unifiedNativeAd.getHeadline();
            String body = unifiedNativeAd.getBody();
            String cta = unifiedNativeAd.getCallToAction();
            Double starRating = unifiedNativeAd.getStarRating();
            NativeAd.Image icon = unifiedNativeAd.getIcon();

            String tertiaryText;

            if (adHasOnlyStore(unifiedNativeAd)) {

                if (advertiserWrapper != null) {
                    advertiserWrapper.setVisibility(VISIBLE);
                }

                tertiaryText = store;
            } else if (adHasOnlyAdvertiser(unifiedNativeAd)) {


                if (advertiserWrapper != null) {
                    advertiserWrapper.setVisibility(VISIBLE);
                }

                if (tagLineText != null) {
                    tagLineText.setLines(2);

                }
                tertiaryText = advertiser;

            } else if (adHasBothStoreAndAdvertiser(unifiedNativeAd)) {
                if (advertiserWrapper != null) {
                    advertiserWrapper.setVisibility(VISIBLE);
                }
                if (tagLineText != null) {
                    tagLineText.setLines(2);
                }

                tertiaryText = advertiser;
            } else {
                tertiaryText = "";
                if (advertiserWrapper != null) {
                    advertiserWrapper.setVisibility(GONE);
                }
                if (tagLineText != null) {
                    tagLineText.setLines(3);
                }

            }


            headingText.setText(headline);
            advertiserText.setText(tertiaryText);
            callToActionView.setText(cta);

            if (starRating != null && starRating > 0) {
                ratingBar.setVisibility(VISIBLE);
                ratingBar.setRating(starRating.floatValue());
                ratingBar.setEnabled(true);
                advertiserText.setTextSize(10.0f);
                advertiserText.setLines(1);
            } else {
                ratingBar.setVisibility(GONE);
                ratingBar.setEnabled(false);
                advertiserText.setTextSize(11.5f);
                advertiserText.setLines(1);
            }
            tagLineText.setText(body);
            if (tagLineWrapper != null) {
                tagLineWrapper.setVisibility(VISIBLE);
            }
            if (icon != null) {
                iconView.setVisibility(VISIBLE);
                iconView.setImageDrawable(icon.getDrawable());
            } else {
                iconView.setVisibility(GONE);
            }


            unifiedNativeAdView.setNativeAd(unifiedNativeAd);

            postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadNativeAd(admobAdUnitId);

                }
            }, 1200000);

        } catch (Exception e) {

            Log.d(TAG, e.getMessage());

        }
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }


    public void setBackgroundStyle(@Nullable String backgroundColor, @Nullable String borderColor, @Nullable int borderWidth, @Nullable int borderRadius) {
        if (unifiedNativeAdView == null) return;

        backroundDrawable = new GradientDrawable();

        if (backgroundColor != null) {
            mBgBackground = backgroundColor;
            int bc = Color.parseColor(backgroundColor);
            backroundDrawable.setColor(bc);

        }
        if (borderRadius > 0) {
            mBgBorderRadius = borderRadius;
            backroundDrawable.setCornerRadius(borderRadius);
        }
        if (borderWidth > 0) {
            mBgBorderWidth = borderWidth;
            int brc = Color.parseColor("#000000");
            if (borderColor != null) {
                mBgBorderColor = borderColor;
                brc = Color.parseColor(borderColor);
            }

            backroundDrawable.setStroke(borderWidth, brc);
        }

        findViewById(R.id.native_ad_view).setBackground(backroundDrawable);
    }


    public void setButtonStyle(@Nullable String textColor, @Nullable String backgroundColor, @Nullable String borderColor, @Nullable int borderWidth, @Nullable int borderRadius) {
        if (unifiedNativeAdView == null) return;

        gradientDrawableA = new GradientDrawable();
        gradientDrawableB = new GradientDrawable();

        Button button;

        TextView text;
        int id_ = R.id.ad_notification_view;
        int id = R.id.cta;
        if (id > -1) {
            button = findViewById(R.id.cta);
        } else {
            return;
        }

        if (id_ > -1) {
            text = findViewById(R.id.ad_notification_view);
        } else {
            return;
        }

        if (button == null) return;

        if (textColor != null) {
            mTextColor = textColor;
            int tc = Color.parseColor(textColor);
            button.setTextColor(tc);
            text.setTextColor(tc);
        }

        if (backgroundColor != null) {
            mBackgroundColor = backgroundColor;

            int bc = Color.parseColor(backgroundColor);
            gradientDrawableA.setColor(bc);
            gradientDrawableB.setColor(bc);
        }
        if (borderRadius > 0) {
            mBorderRadius = borderRadius;
            gradientDrawableA.setCornerRadius(borderRadius* 3);
            gradientDrawableB.setCornerRadius(borderRadius * 3);
        }
        if (borderWidth > 0) {
            mBorderWidth = borderWidth;
            int brc = Color.parseColor("#000000");
            if (borderColor != null) {
                mBorderColor = borderColor;
                brc = Color.parseColor(borderColor);
            }

            gradientDrawableA.setStroke(borderWidth, brc);
            gradientDrawableB.setStroke(borderWidth, brc);

        }
        button.setBackground(gradientDrawableA);
        text.setBackground(gradientDrawableB);
    }

    private void sendEvent(String name, @Nullable WritableMap event) {

        ReactContext reactContext = (ReactContext) getContext();
        reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                getId(),
                name,
                event);
    }

    public void setAdmobAdUnitId(@NonNull String admobAdUnitId) {
        this.admobAdUnitId = admobAdUnitId;
    }

    public void setHeadlineColor(@NonNull int color) {
        if (unifiedNativeAdView != null) {
            TextView headline = (TextView) unifiedNativeAdView.findViewById(R.id.primary);
            if (headline == null) return;

            headline.setTextColor(color);
        }

    }

    public void setBodyTextColor(@NonNull int color) {
        if (unifiedNativeAdView != null) {
            TextView tagline = (TextView) unifiedNativeAdView.findViewById(R.id.secondary);
            if (tagline == null) return;
            tagline.setTextColor(color);
        }
    }


    public void setAdvertiserTextColor(@NonNull int color) {
        if (unifiedNativeAdView != null) {
            TextView advertiser = (TextView) unifiedNativeAdView.findViewById(R.id.tertiary);
            if (advertiser == null) return;
            advertiser.setTextColor(color);
        }

    }


    public void setRatingColor(int color) {
        if (unifiedNativeAdView != null) {
            if (ratingBar != null) {

                RatingBar starRating = (RatingBar) findViewById(R.id.rating_bar);
                if (starRating == null) return;
                LayerDrawable stars = (LayerDrawable) starRating.getProgressDrawable();
                stars.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);

            }
        }
    }

    public void setBgColor(int color) {
        if (unifiedNativeAdView != null) {
            findViewById(R.id.background).setBackgroundColor(color);
        }
    }


    public void setAdSize(String size) {
        adSize = size;
        createNativeAd(getContext());
        loadNativeAd(admobAdUnitId);
    }


    public void loadNativeAd(final String adUnitID) {
        admobAdUnitId = adUnitID;
        refreshAd();
    }
}
