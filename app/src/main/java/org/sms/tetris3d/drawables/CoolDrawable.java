package org.sms.tetris3d.drawables;

import android.graphics.drawable.*;
import android.graphics.*;

import org.sms.tetris3d.items.BaseItem;
/**
 * 쿨타임이 적용되었을때 나타나는 일종의 벡터이미지의 소스코드
 *
 * @version 1.2
 *
 * @author 황세현
 *
 */

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

    /**
     * 현재 쿨타임이 백분율로 어느정도 진행되는지 현재 확률을 360도를 기준으로 나타낸값을 리턴
     *
     * @return 각도
     */
    public float getCurrentRadius(){
        return rad;
    }

    /**
     * 현재 쿨타임이 백분율로 어느정도 진행되는지 현재 확률을 360도를 기준으로 나타낸값을 설정
     *
     * @param r
     */
    public void setCurrentRadius(float r){
        rad = r;
    }


    /**
     * 현재 진행된 시간을 쿨타임시간으로 나눈값에 360를 곱해서 다음에 적용될 현재 각도를 계산함
     *
     * @param currentTime
     * @param totalTime
     * @return 계산된 각도
     */
    public float computeCurrentRadius(long currentTime,long totalTime){
        if(totalTime>0) {
            return ((float) currentTime / (0.0f + totalTime)) * 360;
        }
        else{
            return 0;
        }
    }

    /**
     * 아이템객체에서 이미지 새로고침 시간을 설정한다
     *
     * @param val as long
     * @return 코드 이미지 객체
     */
    public CoolDrawable setCoolRefreshTime(long val){
        crtime = val;
        return this;
    }

    /**
     *  이미지 새로고침시간을 가져온다
     *
     * @return 새로고침 시간
     */
    public long getCoolRefreshTime(){
        return crtime;
    }

    /**
     *  쿨타임때 나타나는 이미지를 그린다
     * @param canvas
     */
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
