package com.example.levi.wanandroidapp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.example.levi.wanandroidapp.R;

import java.lang.ref.WeakReference;

public class LiveLoadingView extends View {
    private int mBackgroundColor;
    private int mStrokeWidth;
    private boolean mIsNeedBackground;
    private int mInnerTriangleColor;
    private int mInnerTriangleRadius;
    private int mOuterCircleColor;
    private int mOuterCircleRadius;

    private Paint mPaint;
    private Paint mTrianglePaint;
    private Paint mBackgroundPaint;
    private Path mPath;
    private PointF mRotateCenter;
    private RectF mRoundRectF;
    private MyHandler mHandler;

    private RectF mRectF;
    private boolean isReverse = false;
    private int mDel = 30;
    private int mProgress = 0;
    private int mStartAngle = -90;
    private int mRotateAngle = 0;

    public LiveLoadingView(Context context) {
        this(context, null);
    }

    public LiveLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LiveLoadingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LiveLoadingView);
        mOuterCircleRadius = typedArray.getDimensionPixelSize(R.styleable.LiveLoadingView_outerCircleRadius, 50);
        mOuterCircleColor = typedArray.getColor(R.styleable.LiveLoadingView_outerCircleColor, 0xFF228B22);
        mInnerTriangleRadius = typedArray.getDimensionPixelSize(R.styleable.LiveLoadingView_innerTriangleRadius, 25);
        mInnerTriangleColor = typedArray.getColor(R.styleable.LiveLoadingView_innerTriangleColor, 0xFF228B22);
        mIsNeedBackground = typedArray.getBoolean(R.styleable.LiveLoadingView_isNeedBackground, true);
        mBackgroundColor = typedArray.getColor(R.styleable.LiveLoadingView_backgroundColor, 0xBB222222);
        mStrokeWidth = typedArray.getDimensionPixelSize(R.styleable.LiveLoadingView_strokeWidth, 5);
        typedArray.recycle();
        init();
    }

    private void init() {
        //进度圈
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mOuterCircleColor);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        //内部三角
        mTrianglePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTrianglePaint.setColor(mInnerTriangleColor);
        //背景
        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setColor(mBackgroundColor);

        mPath = new Path();
        mRotateCenter = new PointF();
        mRoundRectF = new RectF();
        mHandler = new MyHandler(this);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //外圆半径
        mOuterCircleRadius = (int) Math.min(mOuterCircleRadius, (Math.min(w - getPaddingLeft() - getPaddingRight(), h - getPaddingBottom() - getPaddingTop()) - 4 * mPaint.getStrokeWidth()) / 2);
        if (mOuterCircleRadius < 0) {
            mStrokeWidth = Math.min(w - getPaddingRight() - getPaddingLeft(), h - getPaddingTop() - getPaddingBottom()) / 2;
            mOuterCircleRadius = Math.min(w - getPaddingRight() - getPaddingLeft(), h - getPaddingTop() - getPaddingBottom()) / 4;
        }
        float left = (w - 2 * mOuterCircleRadius) / 2;
        float top = (h - 2 * mOuterCircleRadius) / 2;
        float diameter = 2 * mOuterCircleRadius;
        mRectF = new RectF(left, top, left + diameter, top + diameter);

        //内圆半径
        mInnerTriangleRadius = (mInnerTriangleRadius < mOuterCircleRadius) ? mInnerTriangleRadius : 3 * mOuterCircleRadius / 5;
        if (mInnerTriangleRadius < 0) {
            mInnerTriangleRadius = 0;
        }

        //圆心
        float centerX = left + mOuterCircleRadius;
        float centerY = top + mOuterCircleRadius;

        //内圆的内接三角形的三个定点组成的path
        mPath.moveTo(centerX - mInnerTriangleRadius / 2, (float) (centerY - Math.sqrt(3) * mInnerTriangleRadius / 2));
        mPath.lineTo(centerX + mInnerTriangleRadius, centerY);
        mPath.lineTo(centerX - mInnerTriangleRadius / 2, (float) (centerY + Math.sqrt(3) * mInnerTriangleRadius / 2));
        mPath.close();

        mRotateCenter.set(getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        mRoundRectF.left = 0;
        mRoundRectF.top = 0;
        mRoundRectF.right = w;
        mRoundRectF.bottom = h;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureSize(widthMeasureSpec), measureSize(heightMeasureSpec));
    }

    private int measureSize(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int resultSize = 150;
        switch (specMode) {
            case MeasureSpec.EXACTLY: {
                resultSize = specSize;
                break;
            }
            case MeasureSpec.AT_MOST: {

            }
            case MeasureSpec.UNSPECIFIED: {
                resultSize = Math.min(specSize, resultSize);
                break;
            }
        }
        return resultSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mIsNeedBackground) {
            canvas.drawRoundRect(mRoundRectF, 8, 8, mBackgroundPaint);
        }
        if (isReverse) {
            mProgress -= mDel;
            mStartAngle += mDel;
            if (mStartAngle >= 270) {
                mStartAngle = -90;
                isReverse = false;
            }
            mRotateAngle += mDel;
            if (mRotateAngle >= 360) {
                mRotateAngle = 0;
            }
            canvas.save();
            canvas.rotate(mRotateAngle, mRotateCenter.x, mRotateCenter.y);
            canvas.drawPath(mPath, mTrianglePaint);
        } else {
            mProgress += mDel;
            if (mProgress >= 360) {
                isReverse = true;
            }
            //绘制内部三角形
            canvas.drawPath(mPath, mTrianglePaint);
        }
        //绘制外部圆
        canvas.drawArc(mRectF, mStartAngle, mProgress, false, mPaint);
        mHandler.sendEmptyMessageDelayed(MyHandler.REFRESH_VIEW, 100);
    }

    private static class MyHandler extends Handler {
        public static final int REFRESH_VIEW = 0;
        private final WeakReference<LiveLoadingView> mLiveLoadingViewWeakReference;

        public MyHandler(LiveLoadingView liveLoadingView) {
            mLiveLoadingViewWeakReference = new WeakReference<>(liveLoadingView);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mLiveLoadingViewWeakReference.get() != null) {
                switch (msg.what) {
                    case REFRESH_VIEW: {
                        mLiveLoadingViewWeakReference.get().postInvalidate();
                        break;
                    }
                }
            }
        }
    }
}
