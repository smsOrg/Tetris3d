package org.sms.tetris3d.controls;
/**
 * 떨어지고 있는 도형을 x,y축을 기준으로 이동해주는 클래스인데 현재 테트리스 탑을 보여주고 있는 카메라의 뷰각도에 따라 적응해 도형을 이동시킴
 *
 * @version 1.2
 * @author 황세현
 */

import org.sms.tetris3d.GameStatus;
import org.sms.tetris3d.players.*;
/**
 * Created by hsh on 2016. 11. 19..
 */

public class MovePanelAdapter {

    /**
     * 현재 카메라의 각도에 적응해 기준 사분면을 결정하는 메소드
     * @return 사분면을 의미하는 byte형 데이터
     */
    public static byte getQuadrant(){
           float angle = (GameStatus.getCameraR()<0? (720-GameStatus.getCameraR())%360:GameStatus.getCameraR())%360;
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

    /**
     * 중심이 되는 사분면을 이용해 도형을 좌측으로 이동시킵니다
     *
     * @param usr
     */
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
    /**
     * 중심이 되는 사분면을 이용해 도형을 우측으로 이동시킵니다
     *
     * @param usr
     */
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
    /**
     * 중심이 되는 사분면을 이용해 도형을 위로 이동시킵니다
     *
     * @param usr
     */
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
    /**
     * 중심이 되는 사분면을 이용해 도형을 아래로 이동시킵니다
     *
     * @param usr
     */
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
