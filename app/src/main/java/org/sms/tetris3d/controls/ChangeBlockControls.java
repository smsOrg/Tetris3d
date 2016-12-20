package org.sms.tetris3d.controls;
/**
 * 현재블럭과 다음 모양의 순서를 바꾸어주는 클래스
 *
 * @author 황세현
 *
 * @version 1.0
 *
 */


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
