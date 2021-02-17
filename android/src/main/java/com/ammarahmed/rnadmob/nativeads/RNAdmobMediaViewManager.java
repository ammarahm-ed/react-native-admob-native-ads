package com.ammarahmed.rnadmob.nativeads;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.Map;

import javax.annotation.Nullable;
 
public class RNAdmobMediaViewManager extends ViewGroupManager<RNAdmobMediaView> {
    private static final String REACT_CLASS = "RNGADMediaView";


    public static final String EVENT_ON_VIDEO_START = "onVideoStart";
    public static final String EVENT_ON_VIDEO_END = "onVideoEnd";
    public static final String EVENT_ON_VIDEO_PAUSE = "onVideoPause";
    public static final String EVENT_ON_VIDEO_PLAY = "onVideoPlay";
    public static final String EVENT_ON_VIDEO_MUTE = "onVideoMute";
    public static final String EVENT_ON_VIDEO_PROGRESS = "onVideoProgress";

    public static final String PROP_PAUSE = "pause";
    public static final String PROP_MUTE = "muted" ;

    public static final int COMMAND_GET_PROGRESS = 1;


    @javax.annotation.Nullable
    @Override
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        MapBuilder.Builder<String, Object> builder = MapBuilder.builder();
        String[] events = new String[]{
                EVENT_ON_VIDEO_START,
                EVENT_ON_VIDEO_END,
                EVENT_ON_VIDEO_PAUSE,
                EVENT_ON_VIDEO_PLAY,
                EVENT_ON_VIDEO_MUTE,
                EVENT_ON_VIDEO_PROGRESS,
        };
        for (String event : events) {
            builder.put(event, MapBuilder.of("registrationName", event));
        }
        return builder.build();
    }


    @Override
    public @Nullable
    Map<String, Integer> getCommandsMap() {
        return MapBuilder.<String, Integer>builder()
                .put("getProgress", COMMAND_GET_PROGRESS)
                .build();
    }

    @ReactProp(name = PROP_PAUSE)
    public void setPropPause(final RNAdmobMediaView mediaView, boolean pause) {
        mediaView.setPause(pause);
    }

    @ReactProp(name = PROP_MUTE)
    public void setPropMute(final RNAdmobMediaView mediaView, boolean mute) {
        mediaView.setMuted(mute);
    }


    @Override
    public void receiveCommand(RNAdmobMediaView root, int commandId, @Nullable ReadableArray args) {
        switch (commandId) {
            case COMMAND_GET_PROGRESS:
                root.getCurrentProgress();
                break;
        }
    }


    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected RNAdmobMediaView createViewInstance(ThemedReactContext reactContext) {
        return new RNAdmobMediaView(reactContext);
    }

}

