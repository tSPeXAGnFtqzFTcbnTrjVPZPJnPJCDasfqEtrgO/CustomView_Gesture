package com.example.customview_gesture.view;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class CustomGesture extends GestureDetector.SimpleOnGestureListener {
    ICustomView iCustomView;

    public CustomGesture(ICustomView iCustomView) {
        this.iCustomView = iCustomView;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        iCustomView.onDragRight(0,0,0);
        return true;
    }


    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
       iCustomView.addDotAndChange();

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(e1.getRawX()==e2.getRawX()) return false;
        if(e1.getRawX()>e2.getRawX()){
            iCustomView.onDragRight(e1.getRawX(),e2.getRawX(),Math.abs(e1.getRawX()-e2.getRawX()));
        }else{
            iCustomView.onDragLeft(e1.getRawX(),e2.getRawX(),Math.abs(e1.getRawX()-e2.getRawX()));
        }
        return true;
    }

}
