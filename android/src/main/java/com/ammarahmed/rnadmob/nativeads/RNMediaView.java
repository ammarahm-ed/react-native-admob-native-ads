package com.ammarahmed.rnadmob.nativeads;

import android.content.Context;
import com.google.android.gms.ads.formats.MediaView;

public class RNMediaView extends MediaView {

    Context mContext;

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

    @Override
    public void requestLayout() {
        super.requestLayout();
        post(measureAndLayout);
    }
}
