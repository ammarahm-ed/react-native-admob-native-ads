package com.ammarahmed.rnadmob.nativeads;

import android.content.Context;
import android.view.Choreographer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.ads.formats.MediaView;

public class RNMediaView extends LinearLayout {

    Context mContext;
    MediaView mediaView;
    LinearLayout mediaContainer;

    private final Runnable measureAndLayout = new Runnable() {
        @Override
        public void run() {
            measure(
                    MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
            layout(getLeft(), getTop(), getRight(), getBottom());
        }
    };


    public RNMediaView(Context context) {
        super(context);
        mContext = context;
        createMediaView(context);
    }

    public void createMediaView(Context context) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View viewRoot = layoutInflater.inflate(R.layout.media_view, this, true);
        mediaContainer = (LinearLayout) viewRoot.findViewById(R.id.media_view_container);
        mediaView = (MediaView) mediaContainer.findViewById(R.id.media_view_id);
        setupLayoutHack();

    }

    @Override
    public void requestLayout() {
        super.requestLayout();
        post(measureAndLayout);
    }


    void setupLayoutHack() {

        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long frameTimeNanos) {
                manuallyLayoutChildren();
                getViewTreeObserver().dispatchOnGlobalLayout();
                Choreographer.getInstance().postFrameCallback(this);
            }
        });
    }

    void manuallyLayoutChildren() {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(MeasureSpec.makeMeasureSpec(getMeasuredWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.EXACTLY));
            child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
        }
    }


}
