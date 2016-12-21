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

/**
 * 아이템 데이터 객체로부터 데이터들을 가져와 ui에 보여주도록하는 클래스
 */
public class ItemView extends ImageView implements View.OnClickListener,View.OnTouchListener {
    /**
     * 아이템 데이터 객체
     */
    private BaseItem mItem=null;
    /**
     * 쿨타임이 작동될때 로딩이미지 객체
     */
    private CoolDrawable mCoolDrawable = null;
    /**
     * 기본적인 아이템의 프레임
     */
    private final  ItemBackgroundDrawable mBackgroundDrawable = new ItemBackgroundDrawable();
    /**
     * 지금 쿨타임이 작동중인지 체크하는 변수
     */
    protected boolean coolState = false;
    /**
     * 쿨타임이 작동한후의 시간을 계산할때 사용되는 변수
     */
    protected long coolStartTime,coolCurrentTime;
    /**
     * 쿨타임 애니메이션을 동작시킬 자식 프로세스 객체
     */
    protected Thread mCoolThread=null;

    /**
     * 동적뷰로서 소스코드 안에서만 객체가 생성될 수 있음
     * @param context
     */
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

    /**
     *  동적뷰로서 소스코드 안에서만 객체가 생성될 수 있고 생성하면서 데이터를 설정
     * @param context
     * @param item
     */
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

    /**
     * 아이템 데이터 저장 객체로부터 데이터를 가져와 설정
     * @param mItem
     * @return 현재 객체
     */
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

    /**
     * 뷰의 UI를 알맞게 렌터링함
     * @param canvas
     */
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

    /**
     * 뷰의 배경을 그림
     * @param c
     * @param isEmpty
     */
    protected void drawBackGround(Canvas c,boolean isEmpty){
        mBackgroundDrawable.draw(c);
    }

    /**
     * 쿨타임이 작동될떄 쿨타임애니매이션을 렌더링
     * @param c
     */
    protected void drawCoolingImage(Canvas c){
        if(mCoolDrawable==null){
            mCoolDrawable = new CoolDrawable();
            mCoolDrawable.setCoolRefreshTime(mItem==null?mCoolDrawable.getCoolRefreshTime():mItem.getCRT());
        }
        if (coolState) {
            mCoolDrawable.draw(c);
        }

    }

    /**
     * 뷰가 클릭되었을때 작동되는 함수
     * @param v
     */
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

    /**
     * 터치된 영역이 유효한지 체크할때 사용되는 변수
     */
    private final  Rect tmp_area = new Rect();
    /**
     * 최초로 뷰가 클릭됐을떄 터치좌표를 저장하는 변수
     */
    float touch_x,touch_y;

    /**
     * 뷰가 터치가 될때 동작을 제어하는 함수
     * @param v
     * @param event
     * @return
     */
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

    /**
     * 배경이미지를 그리는 클래스
     *
     * @version 1.0
     *
     * @author  황세현
     */
    protected class ItemBackgroundDrawable extends Drawable{
        /**
         * 사용되지 않는 변수
         */
        public boolean isEmptyItem = false;
        /**
         * 배경의 색을 제어하는 변수
         */
        private final Paint p = new Paint();
        /**
         * 뷰가 클릭됬는지 안됬는지 체크하는 변수
         */
        private boolean mClickState = false;

        public ItemBackgroundDrawable(){

        }

        /**
         * 외부에서 뷰클릭상태를 지정하는 함수
         * @param state
         */

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

        /**
         * 배경을 그리는 함수
         * @param canvas
         */
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
