package org.sms.tetris3d.views;

/**
 * Created by hsh on 2016. 11. 27..
 */
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.util.*;
import android.widget.*;
import android.view.*;

import org.sms.tetris3d.GameStatus;
import org.sms.tetris3d.R;
import org.sms.tetris3d.controls.RotateControls;
import org.sms.tetris3d.drawables.CoolDrawable;
import org.sms.tetris3d.items.*;
public class ItemView extends ImageView implements View.OnClickListener,View.OnTouchListener {

    private BaseItem mItem=null;
    private CoolDrawable mCoolDrawable = null;
    private final  ItemBackgroundDrawable mBackgroundDrawable = new ItemBackgroundDrawable();
    protected boolean coolState = false;
    protected long coolStartTime,coolCurrentTime;
    protected Thread mCoolThread=null;
    public ItemView(Context context){
        super(context);
        setScaleType(ScaleType.FIT_XY);

        setOnTouchListener(this);
        //setOnClickListener(this);
    }

    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:{
                onTouch(this,event);
                break;
            }

        }
        return super.onTouchEvent(event);
    }*/

    public ItemView(Context context, BaseItem item){
        this(context);
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

        if(mItem!=null){
            if(mItem.getIconResourceId()!=0)setImageResource(  mItem.getIconResourceId());
            else setImageDrawable( mItem.getItemIcon());
        }
        //setImageResource(R.drawable.switchh);
        invalidate();
        return this;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        drawBackGround(canvas,false);
        /*if(mItem!=null&&mItem.getItemIcon()!=null){
            mItem.getItemIcon().draw(canvas);
        }else if(mItem!=null&&mItem.getIconResourceId()!=0){
           setImageResource(  mItem.getIconResourceId());
        }*/
        super.onDraw(canvas);
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
            mCoolDrawable.setCoolRefreshTime(mItem==null?mCoolDrawable.getCoolRefreshTime():mItem.getCRT());
        }
        if (coolState) {
            mCoolDrawable.draw(c);
        }

    }
    @Override
    public void onClick(View v) {
        if(mItem!=null&&!coolState && mItem.getListener()!=null){
                mItem.getListener().onActiveItem(GameStatus.getDeviceUser());
        }
        if(mItem!=null&&mItem.isSupportedCoolTime()){
            if(mItem.getListener()!=null&&!coolState) {
                coolState = true;
                coolStartTime=coolCurrentTime=System.currentTimeMillis();
                if(mCoolThread==null){
                    mCoolThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while(!GameStatus.isEnd()&&mCoolDrawable.getCurrentRadius()<=360){ //(coolCurrentTime=System.currentTimeMillis())-coolStartTime<=mItem.getCoolTime()){
                                coolCurrentTime=System.currentTimeMillis();
                                //(coolCurrentTime=System.currentTimeMillis())-coolStartTime<=mItem.getCoolTime();
                                final float a=mCoolDrawable.computeCurrentRadius(
                                        coolCurrentTime-coolStartTime,mItem.getCoolTime()
                                );
                                if(a>=360){
                                    break;
                                }
                                if(mCoolDrawable!=null) {
                                   mCoolDrawable.setCurrentRadius(a);
                                }
                                postInvalidate();
                                try {
                                    Thread.sleep(mCoolDrawable.getCoolRefreshTime());
                                }catch(Exception e){}
                                final long gap = coolCurrentTime-coolStartTime;
                                while(GameStatus.isPaused()){
                                    coolStartTime=System.currentTimeMillis()-(gap);
                                    try {
                                        Thread.sleep(100);
                                    }catch(Exception e){}
                                }
                            }
                            /*if(!GameStatus.isEnd()&&mCoolDrawable!=null&&mCoolDrawable.getCurrentRadius()<360) {
                                    mCoolDrawable.setCurrentRadius(360);
                                postInvalidate();
                                try {
                                    Thread.sleep(100);
                                } catch (Exception e) {
                                }

                            }*/
                            coolState=false;
                            mCoolThread=null;
                            postInvalidate();
                        }
                    });
                    mCoolThread.start();
                }


            }
        }else{}
    }

    private final  Rect tmp_area = new Rect();
    float touch_x,touch_y;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(!GameStatus.isEnd()&&!GameStatus.isPaused()&&!GameStatus.isStarting()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    touch_x=event.getX();
                    touch_y = event.getY();
                    tmp_area.set(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                    if (mBackgroundDrawable != null) {
                        mBackgroundDrawable.setClickState(true);
                        invalidate();
                    }
                    break;
                }
                case MotionEvent.ACTION_MOVE:{
                    touch_x=event.getX();
                    touch_y = event.getY();
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    if (mBackgroundDrawable != null) {
                        mBackgroundDrawable.setClickState(false);
                        invalidate();
                        if(tmp_area.contains((int)(v.getLeft() + touch_x), (int)(v.getTop() + touch_y))) {
                            onClick(this);
                        }

                    }
                    break;
                }
                default:
                    break;
            }
        }
        return true;
    }

    protected class ItemBackgroundDrawable extends Drawable{
        public boolean isEmptyItem = false;

        private final Paint p = new Paint();

        private boolean mClickState = false;

        public ItemBackgroundDrawable(){

        }

        public void setClickState(boolean state){
            mClickState = state;
        }

        /*private void drawRoundedStrokeLine(Canvas c,float dl){
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
        }*/
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
            //canvas.rotate((float)degree,width/2,height/2);
            canvas.drawRoundRect(r,width*0.1f,height*0.1f,p);
            if(mClickState){
                p.setColor(Color.argb(0x80,0x0,0x0,0x0));
                p.setStyle(Paint.Style.FILL_AND_STROKE);
                final float ratio = 0.02f;
                RectF r2 = new RectF(width*ratio,height*ratio,width*(1-ratio),height*(1-ratio));
                canvas.drawRoundRect(r2,width*0.15f,height*0.15f,p);
            }
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
