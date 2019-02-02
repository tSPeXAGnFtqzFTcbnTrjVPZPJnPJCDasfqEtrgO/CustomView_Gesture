package com.example.customview_gesture.view;

public interface ICustomView {
    void onDragLeft(float st,float end,float dis);
    void onDragRight(float st,float end,float dis);
    void setRadiusSelect(int radiusSelect);
    void setRadiusUnselect(int radiusUnselect);
    void setDistance(int distance);
    void setColorSelect(int colorSelect);
    void setColorUnselect(int colorUnselect);
    void addDot();
    void removeDot();
    void addDotAndChange();

}

