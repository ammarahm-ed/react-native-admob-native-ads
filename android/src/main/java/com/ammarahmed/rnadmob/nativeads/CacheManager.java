package com.ammarahmed.rnadmob.nativeads;

import android.content.Context;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.google.android.gms.ads.AdListener;

import java.util.HashMap;
import java.util.Map;

public class CacheManager {

    public static final String EVENT_AD_PRELOAD_LOADED = "onAdPreloadLoaded";
    public static final String EVENT_AD_PRELOAD_ERROR = "onAdPreloadError";
    public static CacheManager instance = new CacheManager();
    Map<String, RNAdMobUnifiedAdQueueWrapper> repositoriesMap = new HashMap<>();

    public boolean isLoading(String id) {
        if (repositoriesMap.get(id) != null) {
            return repositoriesMap.get(id).isLoading();
        } else {
            return false;
        }
    }

    public int numberOfAds(String id) {
        if (repositoriesMap.containsKey(id)) {
            return repositoriesMap.get(id).nativeAds.size();
        } else {
            return 0;
        }

    }

    public void attachAdListener(String id, AdListener listener) {
        if (repositoriesMap.get(id) != null) {
            repositoriesMap.get(id).attachAdListener(listener);
        }
    }

    public void detachAdListener(String id) {
        if (repositoriesMap.get(id) != null) {
            repositoriesMap.get(id).detachAdListener();
        }
    }

    public void registerRepository(Context context, ReadableMap config, Promise promise) {
        try {
            String name = config.getString("name");
            String adUnitId = config.getString("adUnitId");

            if (adUnitId == null) {
                promise.reject("Error", "the adUnitId has to be set in config");
                return;
            }

            if (name == null) name = adUnitId;

            if (!repositoriesMap.containsKey(name)) {
                repositoriesMap.put(name, new RNAdMobUnifiedAdQueueWrapper(context, config, name));
                CacheManager.instance.requestAds(name);
            }

            promise.resolve(true);

        } catch (Exception e) {
            promise.reject("Error", e.getMessage());
        }
    }

    public void unRegisterRepository(String repo) {
        repositoriesMap.remove(repo);
    }

    public void resetCache() {
        repositoriesMap.clear();
    }

    public void requestAds(String repo) {
        repositoriesMap.get(repo).loadAds();
    }

    public void requestAd(String repo) {
        repositoriesMap.get(repo).loadAd();
    }

    public Boolean isRegistered(String repository) {
        return repositoriesMap.containsKey(repository);
    }

    public RNAdMobUnifiedAdContainer getNativeAd(String repository) {

        if (repositoriesMap.containsKey(repository)) {
            return repositoriesMap.get(repository).getAd();
        } else {
            return null;
        }
    }

    public WritableMap hasAd(String repository) {
        if (repositoriesMap.containsKey(repository)) {
            return repositoriesMap.get(repository).hasAd();
        } else {
            WritableMap args = Arguments.createMap();
            args.putInt(repository, 0);
            return args;
        }
    }

}

