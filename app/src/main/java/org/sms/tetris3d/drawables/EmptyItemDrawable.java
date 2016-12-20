package org.sms.tetris3d.drawables;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.*;
import android.graphics.*;

/**
 *  아이템이아무것도 설정되지 않았을떄 그려지는 이미지 객체
 *
 *  @version 1.0
 *
 *  @author 황세현
 *
 */


/**
 * Created by hsh on 2016. 12. 11..
 */

public class EmptyItemDrawable extends Drawable {
    private final Paint p = new Paint();

    /**
     * 아이템이 아닌 이미지를 그립니다
     * 현재이미지는 원안에 X가 표시되어있는 이미지입니다
     *
     * @param canvas
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
