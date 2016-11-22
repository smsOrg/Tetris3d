package org.sms.tetris3d;

import android.content.Context;

import org.json.JSONObject;
import org.sms.tetris3d.players.*;

import java.util.ArrayList;
import org.sms.tetris3d.players.*;
import org.sms.tetris3d.players.DeviceUser;
import org.sms.tetris3d.players.User;

/**
 * Created by hsh on 2016. 11. 16..
 */

public class GameStatus extends com.trippleit.android.tetris3d.GameStatus{
    public static final int DB_FILE_VERSION = 1;
    protected static final  RegisteredPlayers players = new RegisteredPlayers(){

    };
    protected static  JSONObject config_data = new JSONObject();
    public static JSONObject getConfigData(){
        return config_data;
    }
    public static ArrayList<User> getPlayers(){
        return players;
    }
    protected static float pivotZ ;
    public static float getPivotZ(){
        return pivotZ;
    }
    public static void setPivotZ(float pz){
        if((float)getGameHeight()/4<=pz&&pz<=getGameHeight()+2) {
            pivotZ = pz;
        }
    }
    public static int getAvailableZPos(final User who){
        int result = 0;
        synchronized (GameStatus.getGameBoolMatrix()) {

            for (int i = 0; i <= who.getCurrentObjectZ(); i++) {
                boolean isExist = false;
                for (int j = 0; !isExist && j < who.getCurrentObject().getObjectMatrix().length; j++) {
                    for (int k = 0; !isExist && k < who.getCurrentObject().getObjectMatrix()[j].length; k++) {
                        for (int l = 0; !isExist && l < who.getCurrentObject().getObjectMatrix()[j][k].length; l++) {
                            final boolean isValid = (j + who.getCurrentObjectX()) < GameStatus.getGameBoolMatrix().length && (k + who.getCurrentObjectY()) < GameStatus.getGameBoolMatrix()[0].length && (l + i) < GameStatus.getGameBoolMatrix()[0][0].length;
                            if (who.getCurrentObject().getObjectMatrix()[j][k][l] && isValid && GameStatus.getGameBoolMatrix()[j + who.getCurrentObjectX()][k + who.getCurrentObjectY()][i + l]) {
                                isExist = true;
                            }
                        }
                    }
                }
                if (isExist) {
                    result = i + 1;
                }
            }
        }
        return result;
    }
    public static void restartGame(){
        init(null);
    }

    public static void init(Context _c) {
        config_data=new JSONObject();

        players.forceClear();
        players.add(new DeviceUser());
        gameHeight = 12;
        setPivotZ((float)gameHeight/4);
        gridSize = 6;
        gStatus = GAME_STATUS.START;
        restartGameBoolMatrix();
        setCamera(initialCameraAngle, gameHeight);
        start=true;
        end = false;
        startX = 2;
        startY = 2;
        dropFast = false;
        try {
            config_data.put("game_board_xy_size", gridSize);
            config_data.put("game_board_height",gameHeight);
            config_data.put("participated_player_count",players.size());

        }catch (Exception e){

        }
    }
}
