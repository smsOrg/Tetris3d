package org.sms.tetris3d.controls;

/**
 * Created by hsh on 2016. 11. 16..
 */

import android.view.*;
/**
 * Created by hsh on 2016. 11. 10..
 */
import android.view.View.*;

import org.sms.tetris3d.GameStatus;
import org.sms.tetris3d.players.DeviceUser;

public class ChangeBlockControls implements OnClickListener {
    @Override
    public void onClick(View v){
        DeviceUser du = (DeviceUser)GameStatus.getPlayers().get(0);
        du.swipeBlock(false);
    }
}
