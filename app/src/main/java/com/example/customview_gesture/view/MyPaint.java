package com.example.customview_gesture.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

public class MyPaint {
    private Paint paint = null;
    private int radius;
    private PointF center;
    private RectF rectF;
    public MyPaint(){
        paint = new Paint();
        center = new PointF();
        rectF = new RectF(0,0,0,0);
        paint.setAntiAlias(true);

        //paint.setFilterBitmap(true);
    }

    public void setColor(int color) {
        paint.setColor(color);
    }
    public int getColor(){
        return this.paint.getColor();
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
    public void setRectF(float left,float top,float width,float height){
        rectF.left = left;
        rectF.top = top;
        rectF.right = left+width;
        rectF.bottom = top+height;
    }
    public void setWidth(float width){
        rectF.right = rectF.left+width;
    }

    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    public void setCenter(float x, float y) {
        this.center.x = x;
        this.center.y = y;
    }
    public PointF getCenter(){
        return this.center;
    }
    public void startDraw(Canvas canvas){
        canvas.drawRect(rectF,paint);

//        canvas.drawCircle(this.center.x,this.center.y,this.radius,paint);
  //      Log.d("AAA","drawing: " + this.center.x+"_"+this.center.y+"  " +this.radius );
    }
}
