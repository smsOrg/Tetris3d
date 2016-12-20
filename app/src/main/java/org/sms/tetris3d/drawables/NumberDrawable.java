package org.sms.tetris3d.drawables;

import android.graphics.*;
import android.graphics.ColorFilter;
import android.graphics.drawable.*;
/**
 *
 * 순위나 랭크를 표현할때 랭크숫자를 그려주는 이미지 객체
 *
 * @version 1.1
 *
 * @author 황세현
 *
 */

/**
 * Created by hsh on 2016. 11. 22..
 */

public class NumberDrawable extends Drawable {
    int num;
    protected  final  Paint numPaint = new Paint();
    protected final Paint circlePaint = new Paint();
    protected final Rect numRect = new Rect(0,0,0,0);
    boolean isInitializedFontSize = false;
    int numX = 0;
    int numY = 0;

    /**
     *  숫자는 정수범위로 입력하시오
     *
     * @param number
     * @param textColor
     * @param bgColor
     */
    public NumberDrawable(int number,int textColor,int bgColor){
        num = number;
        circlePaint.setColor(bgColor);
        circlePaint.setAntiAlias(true);
        numPaint.setColor(textColor);
        numPaint.setAntiAlias(true);
        numPaint.setTypeface(Typeface.DEFAULT);

    }

    /**
     *  원안에 숫자를 그립니다
     *
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        final int height = canvas.getHeight();
        final int width = canvas.getWidth();
        final char[] number = Integer.toString(num).toCharArray();
        final float r = Math.min(width/2,height/2);
        //canvas.drawColor(Color.BLACK);
        if(!isInitializedFontSize){

            int tmpsz = 3;
            final float maxRan = (float)Math.sqrt(2)*r;
            while(numRect.width()<=maxRan&&numRect.height()<=maxRan){
                numPaint.setTextSize(tmpsz);
                numPaint.getTextBounds(number,0,number.length,numRect);
               // android.util.Log.e("size calc","calculating...  "+tmpsz+"  and  "+numRect.width());

                tmpsz+=3;
            }
            while(!(numRect.width()<maxRan&&numRect.height()<maxRan)){
                numPaint.setTextSize(tmpsz);
                numPaint.getTextBounds(number,0,number.length,numRect);
                // android.util.Log.e("size calc","calculating...  "+tmpsz+"  and  "+numRect.width());
                tmpsz-=2;
            }
            tmpsz--;
            numPaint.setTextSize(tmpsz);
            isInitializedFontSize = true;
            numPaint.getTextBounds(number,0,number.length,numRect);
            numX =Math.max(0, width/2-  numRect.width()/2-numRect.left);
            numY =Math.max(0, height/2+  numRect.height()/2);
        }
       canvas.drawCircle(width/2,height/2,r,circlePaint);
        canvas.drawText(number,0,number.length,numX,numY,numPaint);

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
