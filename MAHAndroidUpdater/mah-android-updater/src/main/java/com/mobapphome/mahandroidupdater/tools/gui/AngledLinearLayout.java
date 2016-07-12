package com.mobapphome.mahandroidupdater.tools.gui;

import android.content.Context;  
import android.graphics.Canvas;  
import android.util.AttributeSet;  
import android.util.DisplayMetrics;
import android.widget.LinearLayout;

public class AngledLinearLayout extends LinearLayout  
{  
	DisplayMetrics metrics = getResources().getDisplayMetrics();
    public AngledLinearLayout(Context context, AttributeSet attrs)  
    {  
        super(context, attrs);  
  
    }  
  
    @Override
    public void draw(Canvas canvas) {
      //Save the current matrix  
      canvas.save();  
      //Rotate this View at its center  
//      canvas.rotate(-45, this.getWidth()/2, this.getHeight()/2);       
      int densityDpiX = (int)(metrics.density * 18);
      int densityDpiY = (int)(metrics.density * 17);
      canvas.rotate(-45, densityDpiX, this.getHeight()/2+densityDpiY);  
      //Draw it  
  		super.draw(canvas);
      //Restore to the previous matrix  
      canvas.restore(); 
    }
    
    
//    @Override  
//    protected void onDraw(Canvas canvas)  
//    {  
//        //Save the current matrix  
//        canvas.save();  
//        //Rotate this View at its center  
//        canvas.rotate(-45, this.getWidth()/2, this.getHeight()/2);  
//        //Draw it  
//        super.onDraw(canvas);  
//        //Restore to the previous matrix  
//        canvas.restore();  
//    }  
} 