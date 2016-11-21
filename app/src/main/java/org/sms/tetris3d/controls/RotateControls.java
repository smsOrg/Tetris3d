package org.sms.tetris3d.controls;

import android.view.View;
import android.widget.Button;

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
            du.getCurrentObject().rotate(((Button)v).getText().charAt(0));
        }
    }
}
