package com.ammarahmed.rnadmob.nativeads;

import android.os.Bundle;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.ads.mediation.facebook.FacebookAdapter;
import com.google.ads.mediation.facebook.FacebookExtras;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.nativead.NativeAdOptions;

import java.util.List;

public class Utils {

    public static void setTargetingOptions(ReadableMap options, AdManagerAdRequest.Builder adRequest) {
        if (options == null) return;
        if (options.hasKey("targets")) {
            ReadableArray targets = options.getArray("targets");
            for (int i = 0; i < targets.size(); i++) {
                ReadableMap target = targets.getMap(i);
                String key = target.getString("key");
                if (target.getType("value") == ReadableType.Array) {
                    List list = Arguments.toList(target.getArray("value"));
                    adRequest.addCustomTargeting(key, list);
                } else {
                    adRequest.addCustomTargeting(key, target.getString("value"));
                }
            }
        }
        if (options.hasKey("categoryExclusions")) {
            ReadableArray categoryExclusions = options.getArray("categoryExclusions");
            for (int i = 0; i < categoryExclusions.size(); i++) {
                adRequest.addCategoryExclusion(categoryExclusions.getString(i));
            }
        }
        if (options.hasKey("publisherId")) {
            adRequest.setPublisherProvidedId(options.getString("publisherId"));
        }
        if (options.hasKey("requestAgent")) {
            adRequest.setRequestAgent(options.getString("requestAgent"));
        }
        if (options.hasKey("keywords")) {
            ReadableArray keywords = options.getArray("keywords");
            for (int i = 0; i < keywords.size(); i++) {
                adRequest.addKeyword(keywords.getString(i));
            }
        }
        if (options.hasKey("contentUrl")) {
            adRequest.setContentUrl(options.getString("contentUrl"));
        }
        if (options.hasKey("neighboringContentUrls")) {
            List list = Arguments.toList(options.getArray("neighboringContentUrls"));
            adRequest.setNeighboringContentUrls(list);
        }
    }

    public static void setVideoOptions(ReadableMap options, VideoOptions.Builder videoOptions, NativeAdOptions.Builder adOptions) {
        if (options == null) {
            adOptions.setVideoOptions(videoOptions.build());
            return;
        }
        if (options.hasKey("muted")) {
            videoOptions.setStartMuted(options.getBoolean("muted"));
        }

        if (options.hasKey("clickToExpand")) {
            videoOptions.setClickToExpandRequested(options.getBoolean("clickToExpand"));
        }

        if (options.hasKey("clickToExpand")) {
            videoOptions.setCustomControlsRequested(options.getBoolean("clickToExpand"));
        }

        adOptions.setVideoOptions(videoOptions.build());
    }

    public static void setMediaAspectRatio(int type, NativeAdOptions.Builder adOptions) {
        adOptions.setMediaAspectRatio(type);
    }

    public static void setMediationOptions(ReadableMap options, AdManagerAdRequest.Builder adRequest) {
        if (options == null) return;
        if (options.hasKey("nativeBanner")) {
            Bundle facebookExtras = new FacebookExtras().setNativeBanner(options.getBoolean("nativeBanner")).build();
            adRequest.addNetworkExtrasBundle(FacebookAdapter.class, facebookExtras);
        }
    }

    public static void setRequestNonPersonalizedAdsOnly(boolean npa, AdManagerAdRequest.Builder adRequest) {
        Bundle extras = new Bundle();
        if (npa) {
            extras.putString("npa", "1");
        } else {
            extras.putString("npa", "0");
        }
        adRequest.addNetworkExtrasBundle(AdMobAdapter.class, extras);

    }
}
