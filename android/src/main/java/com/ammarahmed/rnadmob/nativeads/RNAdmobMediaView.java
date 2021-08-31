package com.ammarahmed.rnadmob.nativeads;

import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.google.android.gms.ads.MediaContent;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.nativead.MediaView;

 
import javax.annotation.Nullable;

public class RNAdmobMediaView extends MediaView {

    ReactContext mContext;
    MediaContent mediaContent;
    private final Runnable measureAndLayout = new Runnable() {
        @Override
        public void run() {
            measure(
                    MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
            layout(getLeft(), getTop(), getRight(), getBottom());
        }
    };


    public void setVideoController(MediaContent mediaContent) {
        this.mediaContent = mediaContent;

    }

    public void getCurrentProgress() {
        if (mediaContent == null) return;
        WritableMap progress = Arguments.createMap();
        progress.putString("currentTime", String.valueOf(mediaContent.getCurrentTime()));
        progress.putString("duration", String.valueOf(mediaContent.getDuration()));
        Log.d("RNGADMediaView", "PROGRESS UPDATE");
        sendEvent(RNAdmobMediaViewManager.EVENT_ON_VIDEO_PROGRESS, progress);

    }


    public RNAdmobMediaView(ReactContext context) {
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
            sendEvent(RNAdmobMediaViewManager.EVENT_ON_VIDEO_START, null);
        }

        @Override
        public void onVideoPlay() {
            super.onVideoPlay();
            sendEvent(RNAdmobMediaViewManager.EVENT_ON_VIDEO_PLAY, null);
        }

        @Override
        public void onVideoPause() {
            super.onVideoPause();
            sendEvent(RNAdmobMediaViewManager.EVENT_ON_VIDEO_PAUSE, null);
        }

        @Override
        public void onVideoEnd() {
            super.onVideoEnd();
            sendEvent(RNAdmobMediaViewManager.EVENT_ON_VIDEO_END, null);
        }

        @Override
        public void onVideoMute(boolean b) {
            super.onVideoMute(b);

            WritableMap event = Arguments.createMap();
            event.putBoolean("muted", b);
            sendEvent(RNAdmobMediaViewManager.EVENT_ON_VIDEO_MUTE, event);

        }
    };

    public void setPause(boolean pause) {
        if (mediaContent == null) return;
        if (pause) {
            mediaContent.getVideoController().pause();
        } else {
            mediaContent.getVideoController().play();
        }
    }

    public void setMuted(boolean muted) {
        if (mediaContent == null) return;
        mediaContent.getVideoController().mute(muted);

    }


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