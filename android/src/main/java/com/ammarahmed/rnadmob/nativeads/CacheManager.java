package com.ammarahmed.rnadmob.nativeads;

import android.content.Context;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.google.android.gms.ads.AdListener;

import java.util.HashMap;
import java.util.Map;

public class CacheManager {

    Map<String, RNAdMobUnifiedAdQueueWrapper> repositoriesMap = new HashMap<>();

    public static CacheManager instance = new CacheManager();
    public static final String EVENT_AD_PRELOAD_LOADED = "onAdPreloadLoaded";
    public static final String EVENT_AD_PRELOAD_ERROR = "onAdPreloadError";

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
        if (repositoriesMap.get(id) != null){
            repositoriesMap.get(id).attachAdListener(listener);
        }
    }

    public void detachAdListener(String id) {
        if (repositoriesMap.get(id) != null){
            repositoriesMap.get(id).detachAdListener();
        }
    }

    public WritableMap registerRepo(Context context, ReadableMap config) {
        try {
            String repo = null;
            WritableMap args = Arguments.createMap();
            if (!config.hasKey("adUnitId") || config.getString("adUnitId") == null){
                args.putBoolean("success", false);
                args.putString("error", "the adUnitId has to be set in config");
            }
            if (config.hasKey("name") && config.getString("name") != null) {
                repo = config.getString("name");
            } else {
                if (config.hasKey("adUnitId") && config.getString("adUnitId") != null) {
                    repo = config.getString("adUnitId");
                }
            }
            if (repo != null){
                if (!repositoriesMap.containsKey(repo)){
                    repositoriesMap.put(repo, new RNAdMobUnifiedAdQueueWrapper(context, config, repo));
                    args.putBoolean("success", true);
                    args.putString("repo", repo);
                } else {
                    args.putBoolean("success", false);
                    args.putString("error", "the given repo has been registered before");
                }
            } else {
                args.putBoolean("success", false);
                args.putString("error", "the adUnitId or name has to be set in config");
            }
            return args;
        } catch (Exception e) {
            WritableMap args = Arguments.createMap();
            args.putBoolean("success", false);
            args.putString("error", e.getCause() != null ? e.getCause().toString() : "");
            return args;
        }
    }

    public void unRegisterRepo(String repo) {
        repositoriesMap.remove(repo);
    }

    public void resetCache(){
        repositoriesMap.clear();
    }

    public void requestAds(String repo){
        repositoriesMap.get(repo).loadAds();
    }

    public void requestAd(String repo){
        repositoriesMap.get(repo).loadAd();
    }

    public Boolean isRegistered(String repo){
        return repositoriesMap.containsKey(repo);
    }

    public RNAdMobUnifiedAdContainer getNativeAd(String repo) {

        if (repositoriesMap.containsKey(repo)) {
            return repositoriesMap.get(repo).getAd();
        } else {
            return null;
        }
    }

    public WritableMap hasAd(String repo) {
        if (repositoriesMap.containsKey(repo)) {
            return repositoriesMap.get(repo).hasAd();
        } else {
            WritableMap args = Arguments.createMap();
            args.putInt(repo, 0);
            return args;
        }
    }

}

