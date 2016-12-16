package org.sms.tetris3d.drawables;

import android.graphics.drawable.*;
import android.graphics.*;

import org.sms.tetris3d.items.BaseItem;

/**
 * Created by hsh on 2016. 11. 27..
 */

public class CoolDrawable extends Drawable{
    float rad = 360;
    final Paint coolPaint = new Paint();
    long crtime= BaseItem. DEFAULT_COOL_REFRESH_TIME_MILLIS;
    public CoolDrawable(){
        crtime= BaseItem. DEFAULT_COOL_REFRESH_TIME_MILLIS;
        coolPaint.setAntiAlias(true);
        coolPaint.setColor((Color.GRAY));//&0x00ffffff)|0x30000000);
    }

    public float getCurrentRadius(){
        return rad;
    }
    public void setCurrentRadius(float r){
        rad = r;
    }
    public float computeCurrentRadius(long currentTime,long totalTime){
        if(totalTime>0) {
            return ((float) currentTime / (0.0f + totalTime)) * 360;
        }
        else{
            return 0;
        }
    }

    public CoolDrawable setCoolRefreshTime(long val){
        crtime = val;
        return this;
    }

    public long getCoolRefreshTime(){
        return crtime;
    }

    @Override
    public void draw(Canvas canvas) {
        //canvas.drawColor(Color.argb(0x80,0,0,0));

        canvas.save();
        final float height = canvas.getHeight();
        final float width = canvas.getWidth();
        canvas.rotate(-90,width/2,height/2);
        RectF r = new RectF();
        if(height<width){
            r.set(width/2-height/2,0,width/2+height/2,height);
        }else if(height>width){
            r.set(0,height/2-width/2,width,height/2+width/2);
        }else {
            r.set(0,0,width,height);
        }
        canvas.drawArc(r,0,rad%=360,true,coolPaint);
        canvas.restore();
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
