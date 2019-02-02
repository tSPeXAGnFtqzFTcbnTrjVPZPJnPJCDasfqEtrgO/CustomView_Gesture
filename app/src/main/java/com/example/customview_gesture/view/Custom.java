package com.example.customview_gesture.view;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.customview_gesture.R;

import java.util.ArrayList;

public class Custom extends View implements ICustomView {

    private final int DEFAULT_RADIUS_SELECT = 2;
    private final int DEFAULT_RADIUS_UNSELECT = 1;

    private final float DEFAULT_WIDTH = 5;
    private final int DEFAULT_HEIGHT = 1;

    private final int DEFAULT_COLOR_UNSELECT = Color.WHITE;
    private final int DEFAULT_COLOR_SELECT = Color.BLUE;
    private final float DEFAULT_DISTANCE = 6;
    private final int DEFAULT_PADDING = 0;
    private final int DEFAULT_DISTANCE_TO_CHANGE = 100;
    private final long DEFAULT_DUARATION_ANIMATION = 1000;
    private final float DEFAULT_WIDTH_BEFORE = getWidth()/2F;

    private ArrayList<MyPaint> dot = new ArrayList<>();

    private int colorSelect = DEFAULT_COLOR_SELECT;
    private int colorUnselect = DEFAULT_COLOR_UNSELECT;
    private float width = DEFAULT_WIDTH;
    private int height = DEFAULT_HEIGHT;

    private int radiusSelect = DEFAULT_RADIUS_SELECT;
    private int radiusUnselect = DEFAULT_RADIUS_UNSELECT;
    private float distance = DEFAULT_DISTANCE;
    private int currentSelect = 0;

    ValueAnimator animatorIn;


    GestureDetector gestureDetector = new GestureDetector(getContext(), new CustomGesture(this));


    public Custom(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Custom);
        this.height = typedArray
                .getDimensionPixelSize(R.styleable.Custom_custom_height, DEFAULT_HEIGHT);

        this.colorSelect = typedArray
                .getColor(R.styleable.Custom_color_select, DEFAULT_COLOR_SELECT);
        this.colorUnselect = typedArray
                .getColor(R.styleable.Custom_color_unselect, DEFAULT_COLOR_UNSELECT);

        typedArray.recycle();

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        Log.d("AAA","onlayout");

        int yCenter = getHeight() / 2;

        width = getWidth() / (dot.size());
        distance = width * 0.2F;
        distance = Math.min(distance, DEFAULT_DISTANCE);

        width -= distance;

        for (int i = 0; i < dot.size(); i++) {
            if (i == 0) {
                dot.get(i).setRectF(0, 5, width, height);
                //dot.get(i).setCenter(DEFAULT_PADDING+radiusUnselect,yCenter);
            } else {
                dot.get(i).setRectF((width + distance) * i, 5, width, height);

                //dot.get(i).setCenter(dot.get(i-1).getCenter().x+distance+2*radiusUnselect,yCenter);
            }

            dot.get(i).setColor(i==currentSelect?colorSelect:colorUnselect);

            //dot.get(i).setAlpha(255*radiusUnselect/radiusSelect);

            //dot.get(i).setRadius(radiusUnselect);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int desiredHeight = 2 * radiusUnselect;
        int width, height;

        int widthMode, heightMode, widthSize, heightSize;
        widthMode = MeasureSpec.getMode(widthMeasureSpec);
        heightMode = MeasureSpec.getMode(heightMeasureSpec);
        widthSize = MeasureSpec.getSize(widthMeasureSpec);
        heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.AT_MOST) {
            width = widthSize;
        } else {
            width = 0;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(heightSize, desiredHeight);
        } else {
            height = desiredHeight;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    public void addDotAndChange() {
        dot.add(new MyPaint());
        requestLayout();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < dot.size(); i++) {
            dot.get(i).startDraw(canvas);
        }
    }

    @Override
    public void onDragLeft(float st, float end, float dis) {

        int cur = currentSelect;

        dot.get(currentSelect).setColor(colorUnselect);
        currentSelect -= 1;
        if (currentSelect < 0) currentSelect += dot.size();
        dot.get(currentSelect).setColor(colorSelect);

        Log.d("AAA", "drag left: " + dis);


        AnimatorSet animatorSet = new AnimatorSet();

        animatorIn = ValueAnimator.ofFloat(DEFAULT_WIDTH_BEFORE,width);
        animatorIn.addUpdateListener(animation -> {
            animateUpdate(currentSelect, (Float) animation.getAnimatedValue());
        });

        animatorSet.setDuration(DEFAULT_DUARATION_ANIMATION);
        animatorSet.play(animatorIn);
        animatorSet.start();

    }

    @Override
    public void onDragRight(float st, float end, float dis) {
        dot.get(currentSelect).setColor(colorUnselect);
        currentSelect += 1;
        currentSelect %= dot.size();

        dot.get(currentSelect).setColor(colorSelect);

        Log.d("AAA", "drag right: " + dis);

        AnimatorSet animatorSet = new AnimatorSet();

        animatorIn = ValueAnimator.ofFloat(DEFAULT_WIDTH_BEFORE,width);
        animatorIn.addUpdateListener(animation -> {
            animateUpdate(currentSelect, (Float) animation.getAnimatedValue());
        });

        animatorSet.setDuration(DEFAULT_DUARATION_ANIMATION);
        animatorSet.play(animatorIn);
        animatorSet.start();

    }


    @Override
    public void setRadiusSelect(int radiusSelect) {
        this.radiusSelect = radiusSelect;
    }

    @Override
    public void setRadiusUnselect(int radiusUnselect) {
        this.radiusUnselect = radiusUnselect;
    }

    @Override
    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public void setColorSelect(int colorSelect) {
        this.colorSelect = colorSelect;
    }

    @Override
    public void setColorUnselect(int colorUnselect) {
        this.colorUnselect = colorUnselect;
    }

    @Override
    public void addDot() {
        dot.add(new MyPaint());
    }

    @Override
    public void removeDot() {
        if (dot.size() > 0) dot.remove(0);
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        gestureDetector.onTouchEvent(event);
        return true;
    }

    private void animateUpdate(int position,float curVal){
        dot.get(position).setWidth(curVal);
        invalidate();
    }
}
