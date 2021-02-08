package com.ammarahmed.rnadmob.nativeads;

import android.content.Context;
import android.widget.LinearLayout;

public class RNAdmobComponentsWrapper extends LinearLayout {
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

    public RNAdmobComponentsWrapper(Context context) {
        super(context);
        mContext = context;
    }


    @Override
    public void requestLayout() {
        super.requestLayout();
        post(measureAndLayout);
    }
}
