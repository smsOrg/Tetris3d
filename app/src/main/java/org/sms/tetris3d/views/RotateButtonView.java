package org.sms.tetris3d.views;

/**
 * Created by hsh on 2016. 11. 29..
 */
import android.content.*;
import android.graphics.Canvas;
import android.graphics.Color;
import android.widget.*;
import android.view.*;
import android.util.*;
import android.graphics.*;
import android.graphics.drawable.*;

import org.sms.tetris3d.GameStatus;
import org.sms.tetris3d.controls.RotateControls;
import org.sms.tetris3d.players.DeviceUser;

public class RotateButtonView extends View implements View.OnTouchListener{
    protected final static int X_AXIS_BUTTON_COLOR = Color.argb(0xff,0x90,0,0);

    protected final static int Y_AXIS_BUTTON_COLOR = Color.argb(0xff,0,0x80,0);

    protected final static int Z_AXIS_BUTTON_COLOR = Color.argb(0xff,0x30,0x30,0xa1);

    protected final static float AREA_DEGREE =360.0f/3;

    protected final static float PREFIX_DEGREE = 45;


    protected Paint x_pnt=null,y_pnt=null,z_pnt=null,txt_pnt;

    protected RotateControls rc = null;

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
    private void initPaint(){
        x_pnt = new Paint();
        y_pnt = new Paint();
        z_pnt = new Paint();
        txt_pnt = new Paint();
        x_pnt.setAntiAlias(true);
        y_pnt.setAntiAlias(true);
        z_pnt.setAntiAlias(true);
        txt_pnt.setAntiAlias(true);
        x_pnt.setColor(X_AXIS_BUTTON_COLOR);
        y_pnt.setColor(Y_AXIS_BUTTON_COLOR);
        z_pnt.setColor(Z_AXIS_BUTTON_COLOR);
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
    protected int getQuadrant(float x,float y){
        if(x>=0&&y>=0){
            return 1;
        }
        else if(x<0&&y>=0){
            return 2;
        }
        else if(x<0&&y<0){
            return 3;
        }
        else {
            return 4;
        }
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
            android.util.Log.e("rbv color:",i+"th = "+AREA_DEGREE*(i+1));
        }
        canvas.restore();
        String[] strs = getAxisString();
        for(int i =0;i<strs.length;i++){
            canvas.save();
            canvas.rotate(AREA_DEGREE*i,width/2,height/2);
            canvas.drawText(strs[i],width/2,area.height()/4,txt_pnt);
            canvas.restore();
        }
    }
float touch_x=0,touch_y = 0;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                touch_x = event.getX();
                touch_y = event.getY();
                break;
            }
            case MotionEvent.ACTION_UP:{
                touch_x = event.getX();
                touch_y = event.getY();
                final float height = Math.max(v.getMeasuredHeight(), v.getHeight());
                final float width = Math.max(v.getMeasuredWidth(), v.getWidth());
                final float r = Math.min(height,width);
                float relative_xPos = width- touch_x;
                float relative_yPos = height-touch_y;
                if(r>10){
                    float currentDegree =Float.POSITIVE_INFINITY;
                    if(relative_xPos!=0){
                        currentDegree= (float) Math.toDegrees(
                                Math.atan(relative_yPos/relative_xPos)
                        );
                        switch(getQuadrant(relative_xPos,relative_yPos)){
                            case 1:{
                                currentDegree= 90-currentDegree;
                                break;
                            }
                            case 2:{
                                currentDegree=(720-currentDegree)%360;
                                break;
                            }
                            case 3:{
                                currentDegree=(270-currentDegree)%360;
                                break;
                            }
                            case 4:{
                                currentDegree=(currentDegree+90)%360;
                                break;
                            }
                            default:break;
                        }
                        android.util.Log.e("rbv original degree: ",currentDegree+"");
                    }
                    else{
                        currentDegree = (relative_yPos==0)? 0:(relative_xPos>0)? 0:180;
                    }
                    if(currentDegree<0){
                        currentDegree = (360*3-currentDegree)%360;
                    }
                    android.util.Log.e("rbv degree: ",currentDegree+"");
                    if(rc==null){
                        rc = new RotateControls();
                    }
                    final DeviceUser du = GameStatus.getDeviceUser();
                    if(PREFIX_DEGREE<=currentDegree&&currentDegree<PREFIX_DEGREE+AREA_DEGREE){
                        rc.rotateY(du);
                    }
                    else if(PREFIX_DEGREE+AREA_DEGREE<=currentDegree&&currentDegree<PREFIX_DEGREE+AREA_DEGREE*2){
                        rc.rotateX(du);
                    }
                    else{
                        rc.rotateZ(du);
                    }
                }
                break;
            }
            default:break;
        }
        return true;
    }
}
