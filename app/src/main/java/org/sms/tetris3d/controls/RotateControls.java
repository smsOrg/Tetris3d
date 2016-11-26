package org.sms.tetris3d.controls;

import android.view.View;
import android.widget.Button;
import org.sms.tetris3d.players.*;
import org.sms.tetris3d.GameStatus;
import org.sms.tetris3d.players.DeviceUser;

/**
 * Created by hsh on 2016. 11. 21..
 */

public class RotateControls implements View.OnClickListener {
    @Override
    public void onClick(View v){
        if(v instanceof Button){
          final DeviceUser du = (DeviceUser) GameStatus.getPlayers().get(0);
           while( du.getCurrentObject()==null){
               du.newShape();
           }
            rotate((Button)v,du);
            //du.getCurrentObject().rotate(((Button)v).getText().charAt(0));
        }
    }
    protected void rotate(final Button btn,final User usr){
        switch (btn.getText().charAt(0)){
            case 'X':
            case 'x':{
                rotateX(usr);
                break;
            }
            case 'Y':
            case 'y':{
                rotateY(usr);
                break;
            }
            case 'Z':
            case 'z':{
                rotateZ(usr);
                break;
                }
            default:break;
        }
    }
    public void rotateX(final User usr){
        switch(MovePanelAdapter.getQuadrant()){
            case 1:{
                usr.getCurrentObject().rotate('x');
                break;
            }
            case 2:{
                usr.getCurrentObject().rotate('y');
                break;
            }
            case 3:{
                usr.getCurrentObject().rotate('x');
                break;
            }
            case 4:{
                usr.getCurrentObject().rotate('y');
                break;
            }
            default:break;
        }
    }
    public void rotateY(final User usr){
        switch(MovePanelAdapter.getQuadrant()){
            case 1:{
                usr.getCurrentObject().rotate('y');
                break;
            }
            case 2:{
                usr.getCurrentObject().rotate('x');
                break;
            }
            case 3:{
                usr.getCurrentObject().rotate('y');
                break;
            }
            case 4:{
                usr.getCurrentObject().rotate('x');
                break;
            }
            default:break;
        }
    }
    public void rotateZ(final User usr){
       usr.getCurrentObject().rotate('z');
    }
}
