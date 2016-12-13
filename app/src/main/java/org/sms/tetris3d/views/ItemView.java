package org.sms.tetris3d.views;

/**
 * Created by hsh on 2016. 11. 27..
 */
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.util.*;
import android.view.*;

import org.sms.tetris3d.GameStatus;
import org.sms.tetris3d.controls.RotateControls;
import org.sms.tetris3d.drawables.CoolDrawable;
import org.sms.tetris3d.items.*;
public class ItemView extends View implements View.OnClickListener {

    private BaseItem mItem=null;
    private CoolDrawable mCoolDrawable = null;
    private  Drawable mBackgroundDrawable = new ItemBackgroundDrawable();
    protected boolean coolState = false;
    protected long coolStartTime,coolCurrentTime;
    protected Thread mCoolThread=null;
    public ItemView(Context context){
        super(context);
    }
    public ItemView(Context context,BaseItem item){
        super(context);
        applyData(item);
    }
    /*
    public ItemView(Context context, AttributeSet attrs){
        super(context,attrs);
    }
    public ItemView(Context context,AttributeSet attrs,int themResId){
        super(context,attrs,themResId);
    }*/
    public ItemView applyData(BaseItem mItem){
       this. mItem=mItem;
        invalidate();
        return this;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackGround(canvas,false);
        if(mItem!=null&&mItem.getItemIcon()!=null){
            mItem.getItemIcon().draw(canvas);

        }
        if(mItem!=null)
        drawCoolingImage(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    protected void drawBackGround(Canvas c,boolean isEmpty){
        mBackgroundDrawable.draw(c);
    }
    protected void drawCoolingImage(Canvas c){
    if(mCoolDrawable==null){
        mCoolDrawable = new CoolDrawable();
    }
        if(coolState) {
            mCoolDrawable.draw(c);
        }
    }
    @Override
    public void onClick(View v) {
        if(mItem!=null&&mItem.isSupportedCoolTime()){
            if(mItem.getListener()!=null&&!coolState) {
                mItem.getListener().onActiveItem(GameStatus.getPlayers().get(0));
                coolState = true;
                coolStartTime=coolCurrentTime=System.currentTimeMillis();
                if(mCoolThread==null){
                    mCoolThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while((coolCurrentTime=System.currentTimeMillis())-coolStartTime<mItem.getCoolTime()){
                                if(mCoolDrawable!=null) {
                                   mCoolDrawable.setCurrentRadius(
                                           mCoolDrawable.computeCurrentRadius(
                                                   coolCurrentTime-coolStartTime,mItem.getCoolTime()
                                           ));
                                }
                                postInvalidate();
                                try {
                                    Thread.sleep(500);
                                }catch(Exception e){}
                            }
                            coolState=false;
                            mCoolThread=null;
                        }
                    });
                }

            }
        }else{}
    }

    protected class ItemBackgroundDrawable extends Drawable{
        public boolean isEmptyItem = false;
        private Paint p = new Paint();
        public ItemBackgroundDrawable(){

        }
        private void drawRoundedStrokeLine(Canvas c,float dl){
            final float height_ratio = 0.85f;
            float height = dl*height_ratio;
            float gap = (dl*(1-height_ratio))/2;
            float width = dl*0.05f;
            RectF r = new RectF(dl/3-width/2,gap,dl/3+width/2,dl-gap);
            c.drawRect(r,p);
            RectF r2 = new RectF(dl/2-width/2,gap-r.width()/2,dl/2+width/2,gap+r.width()/2);
            c.drawArc(r2,-180,180,true,p);
            r2.set(dl/2-width/2,dl-gap-r.width()/2,dl/2+width/2,dl-gap+r.width()/2);
            c.drawArc(r2,0,180,true,p);
        }
        @Override
        public void draw(Canvas canvas) {
            p.setColor(Color.CYAN);
            final float height = canvas.getHeight();
            final float width = canvas.getWidth();
            final float diagonal_line_size =(float) Math.sqrt(Math.pow(height,2)+Math.pow(width,2));
            android.util.Log.e("parent size log: ",String.format("%f X %f",width,height));
            p.setStyle(Paint.Style.STROKE);
            p.setStrokeWidth(Math.min(height,width)*0.015f);
            RectF r = new RectF(width*0.01f,height*0.01f,width*0.99f,height*0.99f);
            double radius =Math.PI- Math.asin((width/2)/(diagonal_line_size/2));
            double degree = Math.toDegrees(radius);
            canvas.save();
            canvas.save();
            //canvas.rotate((float)degree,width/2,height/2);
            canvas.drawRoundRect(r,width*0.1f,height*0.1f,p);
            canvas.restore();
            p.setStyle(Paint.Style.FILL_AND_STROKE);
            for(int i =1;i<=1;i+=2) {
               /* canvas.save();
                canvas.rotate(i*(float)degree,  height / 2,width/2);
                canvas.save();*/
                //drawRoundedStrokeLine(canvas, diagonal_line_size);
                /*canvas.restore();
                canvas.restore();*/
            }
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

}
