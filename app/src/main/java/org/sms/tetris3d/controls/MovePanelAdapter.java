package org.sms.tetris3d.controls;

import org.sms.tetris3d.GameStatus;
import org.sms.tetris3d.players.*;
/**
 * Created by hsh on 2016. 11. 19..
 */

public class MovePanelAdapter {
    protected byte initQuadrant = -1;
    public static byte getQuadrant(){
        //final float initAngle = (GameStatus.initialCameraAngle<0? 360.0f-GameStatus.initialCameraAngle:GameStatus.initialCameraAngle)%360;
            float angle = (GameStatus.getCameraR()<0? (720-GameStatus.getCameraR())%360:GameStatus.getCameraR())%360;
         //   angle=(angle-initAngle+360)%360;
           // android.util.Log.e("ANGLE: ",angle+"");
            final float prefixAngle =Math.abs( 45);
            final float initialAngleCondtion = 90;
            if(prefixAngle<=angle&&angle<initialAngleCondtion+prefixAngle){
                return 2;
            }else if(initialAngleCondtion+prefixAngle<=angle&&angle<2*initialAngleCondtion+prefixAngle){
                return 3;
            }
            else if(2*initialAngleCondtion+prefixAngle<=angle&&angle<3*initialAngleCondtion+prefixAngle){
                return 4;
            }else{
                return 1;
            }

    }
    public MovePanelAdapter(){
    }
    public void move_left(User usr){

        switch (getQuadrant()){
            case 1:{
                usr.moveY(true);
                break;
            }
            case 2:{
                usr.moveX(false);
                break;
            }
            case 3:{
                usr.moveY(false);
                break;
            }
            case 4:{
                usr.moveX(true);
                break;
            }default:break;
        }

    }
    public void move_right(User usr){
        switch (getQuadrant()){
            case 1:{
                usr.moveY(false);
                break;
            }
            case 2:{
                usr.moveX(true);
                break;
            }
            case 3:{
                usr.moveY(true);
                break;
            }
            case 4:{
                usr.moveX(false);
                break;
            }default:break;
        }

    }
    public void move_top(User usr){
       switch (getQuadrant()){
            case 1:{
                usr.moveX(true);
                break;
            }
            case 2:{
                usr.moveY(true);
                break;
            }
            case 3:{
                usr.moveX(false);
                break;
            }
            case 4:{
                usr.moveY(false);
                break;
            }default:break;
        }
    }
    public void move_bottom(User usr){
        switch (getQuadrant()){
            case 1:{
                usr.moveX(false);
                break;
            }
            case 2:{
                usr.moveY(false);
                break;
            }
            case 3:{
                usr.moveX(true);
                break;
            }
            case 4:{
                usr.moveY(true);
                break;
            }default:break;
        }
    }
}
