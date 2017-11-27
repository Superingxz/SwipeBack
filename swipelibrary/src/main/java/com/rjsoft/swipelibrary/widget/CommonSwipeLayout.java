package com.rjsoft.swipelibrary.widget;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by moligy on 2017/11/21.
 */

public class CommonSwipeLayout extends FrameLayout{
    private View mDragView;

    private ViewDragHelper mDragHelper;
    private int mDragOriLeft = 0;
    private int mDragOriTop = 0;

    private Point mAutoBackOrignalPoint = new Point();

    private Point mCurArrivePoint = new Point();

    private OnFinishScroll mFinishScroll;

    private float finishPercent = 0.5f; //消失临界比例值

    public void setOnFinishScroll(OnFinishScroll FinishScroll) {
        this.mFinishScroll = FinishScroll;
    }

    public void setFinishPercent(float finishPercent) {
        this.finishPercent = finishPercent;
    }

    public CommonSwipeLayout(Context context) {
        this(context, null);
    }

    public CommonSwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonSwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return true;
            }

            @Override
            public void onViewCaptured(View capturedChild, int activePointerId) {
                super.onViewCaptured(capturedChild, activePointerId);
                mDragOriLeft = capturedChild.getLeft();
                mDragOriTop = capturedChild.getTop();
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                mCurArrivePoint.x = left;
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                mCurArrivePoint.y = top;
                return top;
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                mDragHelper.settleCapturedViewAt(mAutoBackOrignalPoint.x, mAutoBackOrignalPoint.y);
                if (mCurArrivePoint.x > getWidth() * finishPercent
                        || mCurArrivePoint.x < -getWidth() * finishPercent
                        || mCurArrivePoint.y < -getHeight() * finishPercent
                        || mCurArrivePoint.y > getHeight() * finishPercent) {
                    if (mFinishScroll != null) {
                        mFinishScroll.complete();
                    }
                }
                mCurArrivePoint.x =0;
                mCurArrivePoint.y =0;
                invalidate();
            }

            @Override
            public void onViewDragStateChanged(int state) {
                super.onViewDragStateChanged(state);
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
            }
        });
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mDragHelper != null && mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }


    public interface OnFinishScroll {
        void complete();
    }
}
