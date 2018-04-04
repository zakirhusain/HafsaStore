package com.hafsa.hafsastore.customview;

import android.content.Context;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

/**
 * Created by User on 3/4/2018.
 */

public class ScalingImageView extends AppCompatImageView implements
        View.OnTouchListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {
    private static final String TAG = "ScalingImageView";

    private Context mContext;
    private ScaleGestureDetector mScaleDetector;
    private GestureDetector mGestureDetector;
    private Matrix mMatrix;
    private float[] mMatrixValues;

    public ScalingImageView(Context context) {
        super(context);
        sharedContructing(context);
    }

    public ScalingImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        sharedContructing(context);
    }

    private void sharedContructing(Context context)
    {
        super.setClickable(true);
        mScaleDetector = new ScaleGestureDetector(mContext, new ScaleListener());
        mMatrix = new Matrix();
        mMatrixValues = new float[9];
        setImageMatrix(mMatrix);
        setScaleType(ScaleType.MATRIX);

        mGestureDetector = new GestureDetector(mContext, this);
        setOnTouchListener(this);

    }

    /* OnTouchLister */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }



    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    /* OnDoubleTapListener */
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }


}


















