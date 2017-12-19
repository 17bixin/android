package com.gestures.heart.base.utils;

public class ColorUtils {

    public static int getRandomColor(int index){
        String[] colorArr = new String[]{"#3598da","#F0455C","#FFB6C1" ,"#8EE5EE","#F4A460","#FFF68F","#FF8247","#A2CD5A"};
        String colorStr = colorArr[0];
        L.d("---------   " + colorArr.length  +"   "  + index);
        if(colorArr.length > index )
            colorStr = colorArr[index];
        return android.graphics.Color.parseColor(colorStr);
    }

}
