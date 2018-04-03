package com.hafsa.hafsastore.customview;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by User on 3/4/2018.
 */

public class MyDragShadowBuilder extends View.DragShadowBuilder {

    private static Drawable shadow;

    public MyDragShadowBuilder(View view, int imageResource) {
        super(view);
        shadow = getView().getContext().getResources().getDrawable(imageResource);
    }

    @Override
    public void onProvideShadowMetrics(Point outShadowSize, Point outShadowTouchPoint) {
        int height;
        int width;
        int imageRatio;

        imageRatio = shadow.getIntrinsicWidth() / shadow.getIntrinsicHeight();

        //height = getView().getHeight() / 2;
        width = getView().getWidth() / 2;
        height = width * imageRatio;

        shadow.setBounds(0, 0, width, height);

        outShadowSize.set(width, height);
        outShadowTouchPoint.set(width / 2, height / 2);
    }

    @Override
    public void onDrawShadow(Canvas canvas) {
        shadow.draw(canvas);
    }
}


















