package org.sms.tetris3d.drawables;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.*;
import android.graphics.*;

/**
 * Created by hsh on 2016. 12. 11..
 */

public class EmptyItemDrawable extends Drawable {
    private final Paint p = new Paint();
   /* private void drawRoundedStrokeLine(Canvas c,float dl){
        final float height_ratio = 0.75f;
        float height = dl*height_ratio;
        float gap = (dl*(1-height_ratio))/2;
        float width = dl*0.05f;
        RectF r = new RectF(dl/2-width/2,gap,dl/2+width/2,dl-gap);
        c.drawRect(r,p);
        RectF r2 = new RectF(dl/2-width/2,gap-r.width()/2,dl/2+width/2,gap+r.width()/2);
        c.drawArc(r2,-180,180,true,p);
        r2.set(dl/2-width/2,dl-gap-r.width()/2,dl/2+width/2,dl-gap+r.width()/2);
        c.drawArc(r2,0,180,true,p);
    }
    */
    @Override
    public void draw(Canvas canvas) {
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(7);
        p.setAntiAlias(true);
        final int height = canvas.getHeight();
        final int width = canvas.getWidth();
        final float R = Math.min(width,height)*0.97f;
        float r = R/2;
        final float widthgap = r*0.07f;
        final float heightgap = r*0.07f;
        r*=(1-0.07);
        android.util.Log.e("size log: ",String.format("%d X %d",width,height));
        canvas.drawCircle(width/2,height/2,r,p);
        r= (float )((R/2)*(1-0.1));
        p.setStrokeWidth(10);
        final float newoffsetgap =(float) ((r*2)/Math.sqrt(2))/2;
        canvas.drawLine(width/2-newoffsetgap,height/2-newoffsetgap,width/2+newoffsetgap,height/2+newoffsetgap,p);
        canvas.drawLine(width/2+newoffsetgap,height/2-newoffsetgap,width/2-newoffsetgap,height/2+newoffsetgap,p);

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
