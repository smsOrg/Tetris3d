package org.sms.tetris3d.views;

/**
 * Created by hsh on 2016. 11. 29..
 */
import android.content.*;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.widget.*;
import android.view.*;
import android.util.*;
import android.graphics.*;
import android.graphics.drawable.*;

import com.dexafree.materialList.view.MaterialListView;

import org.sms.tetris3d.GameStatus;
import org.sms.tetris3d.controls.RotateControls;
import org.sms.tetris3d.players.DeviceUser;

public class RotateButtonView extends View implements View.OnTouchListener,Runnable{
    protected final static int X_AXIS_BUTTON_COLOR = Color.argb(0xff,0x90,0,0);

    protected final static int Y_AXIS_BUTTON_COLOR = Color.argb(0xff,0,0x80,0);

    protected final static int Z_AXIS_BUTTON_COLOR = Color.argb(0xff,0x30,0x30,0xa1);

    protected final static float AREA_DEGREE =360.0f/3;

    protected final static float PREFIX_DEGREE = AREA_DEGREE/2;

    protected final static byte UNKNOWN_AXIS_CLICK_INDEX = -1;

    protected final static byte X_AXIS_CLICK_INDEX = 2;

    protected final static byte Y_AXIS_CLICK_INDEX = 1;

    protected final static byte Z_AXIS_CLICK_INDEX = 0;


    protected Paint x_pnt=null,y_pnt=null,z_pnt=null,txt_pnt,btnclk_pnt,rm_pnt;

    protected RotateControls rc = null;

    protected byte mClickState =UNKNOWN_AXIS_CLICK_INDEX;

    protected final Handler mHandler = new Handler();

    private final Object mSync = new Object();

   // protected final Thread mMultiProcessThread = new Thread(new Runnable(){@Override public void run(){mHandler.post(RotateButtonView.this);}});
    public RotateButtonView(Context ctx) {
        super(ctx);
        setOnTouchListener(this);
        initPaint();

    }

    public RotateButtonView(Context ctx, AttributeSet attrs) {
         super(ctx,attrs);
        setOnTouchListener(this);
        initPaint();
    }

    public RotateButtonView(Context ctx, AttributeSet attrs,int themResId){
        super(ctx,attrs,themResId);
        setOnTouchListener(this);
        initPaint();
    }

    @Override
    public void run(){
        synchronized (mSync){
            invalidate();
        }
    }

    private void initPaint(){
        x_pnt = new Paint();
        y_pnt = new Paint();
        z_pnt = new Paint();
        txt_pnt = new Paint();
        btnclk_pnt=new Paint();
        rm_pnt=new Paint();
        x_pnt.setAntiAlias(true);
        y_pnt.setAntiAlias(true);
        z_pnt.setAntiAlias(true);
        txt_pnt.setAntiAlias(true);
        btnclk_pnt.setAntiAlias(true);
        rm_pnt.setAntiAlias(true);
        x_pnt.setColor(X_AXIS_BUTTON_COLOR);
        y_pnt.setColor(Y_AXIS_BUTTON_COLOR);
        z_pnt.setColor(Z_AXIS_BUTTON_COLOR);
        rm_pnt.setAlpha(0x0);
        rm_pnt.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        rm_pnt.setColor(Color.WHITE);
        btnclk_pnt.setColor(Color.argb(0x90,0x0,0x0,0x0));

        txt_pnt.setColor(Color.WHITE);
        txt_pnt.setTextSize(40);
    }
    Paint[] getPackedPaints(){
        final Paint[] rst = {z_pnt,y_pnt,x_pnt};
        return rst;
    }
    String[] getAxisString(){

        return new String[]{"Z","y","X"};
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        final float height =Math.max(getMeasuredHeight(), canvas.getHeight());
        final float width = Math.max(getMeasuredWidth(), canvas.getWidth());
        Paint[] pnt_easy_ary = getPackedPaints(); //{x_pnt,y_pnt,z_pnt};
        canvas.save();
        canvas.rotate(-(90+PREFIX_DEGREE),width/2,height/2);

        RectF area = new RectF();
        if(height>width){
            area.set(0,height/2-width/2,width,height/2+width+2);
        }
        else if(height<width){
            area.set(width/2-height/2,0,width/2+height/2,height);
        }
        else{
            area.set(0,0,width,height);
        }
        for(int i=0;i<pnt_easy_ary.length;i++){
            canvas.drawArc(area,AREA_DEGREE*i,AREA_DEGREE,true ,pnt_easy_ary[i]);
            if(mClickState==i){
                synchronized (mSync) {
                    canvas.drawArc(area, AREA_DEGREE * i, AREA_DEGREE, true, btnclk_pnt);
                }
            }
            //android.util.Log.e("rbv color:",i+"th = "+AREA_DEGREE*(i+1));
        }
        canvas.restore();
        String[] strs = getAxisString();

        for(int i =0;i<strs.length;i++){
            canvas.save();
            canvas.rotate(AREA_DEGREE*i,width/2,height/2);
            String str = strs[i];
            Rect rct = new Rect();
            txt_pnt.getTextBounds(str.toCharArray(),0,str.toCharArray().length,rct);
            canvas.drawText(strs[i],width/2-rct.width()/2,area.height()/5+rct.height()/2,txt_pnt);
            canvas.restore();
        }
        RectF rct = new RectF();
        rct.set(area.width()/2-area.width()*0.05f,area.height()/2-area.height()*0.05f,area.width()/2+area.width()*0.05f,area.height()/2+area.height()*0.05f);
        canvas.drawArc(rct,0,360,true,rm_pnt);
    }
    protected float getClickedDegree(float r,float relative_xPos,float relative_yPos){
        final float relative_r = (float)Math.sqrt(Math.pow(relative_xPos,2)+ Math.pow(relative_yPos,2));
        float currentDegree =Float.POSITIVE_INFINITY;
        if(r>10){

            if(relative_xPos!=0){
                currentDegree= (float)
                        Math.atan(relative_yPos/relative_xPos);

                if(relative_xPos>0&&relative_yPos>=0){//dai 1
                    currentDegree=(float)Math.PI/2-currentDegree;

                    // android.util.Log.e("pos log: ","dai 1 area");

                }
                else if(relative_xPos>0&&relative_yPos<0){//dai 4
                    currentDegree=(float)Math.PI/2+Math.abs(currentDegree);
                    //android.util.Log.e("pos log: ","dai 4 area");
                }
                else if(relative_xPos<0&&relative_yPos<0){//dai 3
                    currentDegree=(float)Math.PI+((float)Math.PI/2-currentDegree);
                    //android.util.Log.e("pos log: ","dai 3 area");
                }
                else{ //dai 2
                    currentDegree=(float)Math.PI*3/2+Math.abs(currentDegree);
                    //android.util.Log.e("pos log: ","dai 2 area");
                }

                currentDegree=(float)Math.toDegrees(currentDegree);
                //android.util.Log.e("rbv original degree: ",currentDegree+" and pos: "+String.format("%f , %f",relative_xPos,relative_yPos));

            }
            else{
                currentDegree = (relative_yPos==0)? 0:(relative_xPos>0)? 0:180;
            }
            if(currentDegree<0){
                currentDegree = (360*3-currentDegree)%360;
            }

        }
            return currentDegree;
    }
    private float touch_x=0,touch_y = 0,currentDegree=0;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                touch_x = event.getX();
                touch_y = event.getY();
                final float height = Math.max(v.getMeasuredHeight(), v.getHeight());
                final float width = Math.max(v.getMeasuredWidth(), v.getWidth());
                final float r = Math.min(height,width);
                //android.util.Log.e("touch x and y: ",String.format("x= %f y= %f",touch_x,touch_y));
                float relative_xPos = width/2- touch_x;
                float relative_yPos = height/2-touch_y;
                relative_xPos *=-1;
                 currentDegree = getClickedDegree(r,relative_xPos,relative_yPos);
                //android.util.Log.e("rbv degree: ",currentDegree+"");

                if(PREFIX_DEGREE<=currentDegree&&currentDegree<PREFIX_DEGREE+AREA_DEGREE){

                    mClickState=Y_AXIS_CLICK_INDEX;
                    //rc.rotateY(du);
                }
                else if(PREFIX_DEGREE+AREA_DEGREE<=currentDegree&&currentDegree<PREFIX_DEGREE+AREA_DEGREE*2){
                    mClickState=X_AXIS_CLICK_INDEX;
                    //rc.rotateX(du);
                }
                else if(!Float.isInfinite(currentDegree)){
                    mClickState=Z_AXIS_CLICK_INDEX;
                    //rc.rotateZ(du);
                }
                else{
                    mClickState=UNKNOWN_AXIS_CLICK_INDEX;
                }
                synchronized (mSync) {
                    invalidate();
                }
                break;
            }
            case MotionEvent.ACTION_UP:{
                /*touch_x = event.getX();
                touch_y = event.getY();
                final float height = Math.max(v.getMeasuredHeight(), v.getHeight());
                final float width = Math.max(v.getMeasuredWidth(), v.getWidth());
                final float r = Math.min(height,width);
                //android.util.Log.e("touch x and y: ",String.format("x= %f y= %f",touch_x,touch_y));
                float relative_xPos = width/2- touch_x;
                float relative_yPos = height/2-touch_y;
                relative_xPos *=-1;
                float currentDegree = getClickedDegree(r,relative_xPos,relative_yPos);
                    //android.util.Log.e("rbv degree: ",currentDegree+"");
                    */

                    if(rc==null){
                        rc = new RotateControls();
                    }
                    final DeviceUser du = GameStatus.getDeviceUser();
                    mClickState=UNKNOWN_AXIS_CLICK_INDEX;
                    if(PREFIX_DEGREE<=currentDegree&&currentDegree<PREFIX_DEGREE+AREA_DEGREE){
                        //mMultiProcessThread.start();
                        mHandler.postDelayed(this,4);
                        rc.rotateY(du);
                    }
                    else if(PREFIX_DEGREE+AREA_DEGREE<=currentDegree&&currentDegree<PREFIX_DEGREE+AREA_DEGREE*2){
                       // mMultiProcessThread.start();
                        mHandler.postDelayed(this,4);
                        rc.rotateX(du);
                    }
                    else if(!Float.isInfinite(currentDegree)){
                        //mMultiProcessThread.start();
                        mHandler.postDelayed(this,4);
                        rc.rotateZ(du);
                    }

                break;
            }
            default:break;
        }
        return true;
    }

}
