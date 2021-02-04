package com.ammarahmed.rnadmob.nativeads;

import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.nativead.MediaView;


import javax.annotation.Nullable;

public class RNMediaView extends MediaView {

    ReactContext mContext;
    VideoController vc;

    private final Runnable measureAndLayout = new Runnable() {
        @Override
        public void run() {
            measure(
                    MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
            layout(getLeft(), getTop(), getRight(), getBottom());
        }
    };



    public void setVideoController(VideoController videoController) {
         vc = videoController;
         
    }

    public void getCurrentProgress() {
        if (vc == null) return;
        WritableMap progress = Arguments.createMap();
        progress.putString("currentTime", String.valueOf(vc.getVideoCurrentTime()));
        progress.putString("duration", String.valueOf(vc.getVideoDuration()));
        Log.d("RNGADMediaView","PROGRESS UPDATE");
        sendEvent(RNAdMobMediaViewManager.EVENT_ON_VIDEO_PROGRESS,progress);

    }


    public RNMediaView(ReactContext context) {
        super(context);
        mContext = context;
        requestLayout();


    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        requestLayout();
    }



    public VideoController.VideoLifecycleCallbacks videoLifecycleCallbacks = new VideoController.VideoLifecycleCallbacks() {
        @Override
        public void onVideoStart() {
            super.onVideoStart();
                sendEvent(RNAdMobMediaViewManager.EVENT_ON_VIDEO_START,null);
        }

        @Override
        public void onVideoPlay() {
            super.onVideoPlay();
                sendEvent(RNAdMobMediaViewManager.EVENT_ON_VIDEO_PLAY,null);
        }

        @Override
        public void onVideoPause() {
            super.onVideoPause();
                sendEvent(RNAdMobMediaViewManager.EVENT_ON_VIDEO_PAUSE,null);
        }

        @Override
        public void onVideoEnd() {
            super.onVideoEnd();
            sendEvent(RNAdMobMediaViewManager.EVENT_ON_VIDEO_END,null);
        }

        @Override
        public void onVideoMute(boolean b) {
            super.onVideoMute(b);

            WritableMap event = Arguments.createMap();
            event.putBoolean("muted",b);
            sendEvent(RNAdMobMediaViewManager.EVENT_ON_VIDEO_MUTE, event);

        }
    };




    @Override
    public void requestLayout() {
        super.requestLayout();
        post(measureAndLayout);
    }


     public void sendEvent(String name, @Nullable WritableMap event) {

            ReactContext reactContext = (ReactContext) mContext;
            reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                    getId(),
                    name,
                    event);
}

}