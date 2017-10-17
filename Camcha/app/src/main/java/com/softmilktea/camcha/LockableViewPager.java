package com.softmilktea.camcha;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by SEJIN on 2017-10-06.
 */

public class LockableViewPager extends ViewPager {
    private boolean mSwipeable;

    public LockableViewPager(Context context) {
        super(context);
    }

    /**
     * Constructor. Make this viewPager swipeable.
     * @param context
     * @param attrs
     */
    public LockableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mSwipeable = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.mSwipeable) {
            return super.onTouchEvent(event);
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.mSwipeable) {
            return super.onInterceptTouchEvent(event);
        }
        return false;
    }

    public void setmSwipeable(boolean mSwipeable) {
        this.mSwipeable = mSwipeable;
    }
}
