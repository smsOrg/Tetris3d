package org.sms.tetris3d;

import android.content.Context;

import org.json.JSONObject;
import org.sms.tetris3d.players.*;

import java.util.ArrayList;
import org.sms.tetris3d.players.*;
import org.sms.tetris3d.players.DeviceUser;
import org.sms.tetris3d.players.User;
import org.sms.tetris3d.savepoint.SavePoint;

/**
 * Created by hsh on 2016. 11. 16..
 */

public class GameStatus extends com.trippleit.android.tetris3d.GameStatus{
    public static final int DB_FILE_VERSION = 3;
    protected static final  RegisteredPlayers players = new RegisteredPlayers(){
    };

    protected static long play_time=0;

    public static void setPlayTime(long val){play_time = val;}

    public static long getPlayTime(){return play_time;}

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
    public static DeviceUser getDeviceUser(){
        return (DeviceUser) players.get(0);
    }

    public static int getAvailableZPos(final User who){
        int result = 0;
        synchronized (GameStatus.getGameBoolMatrix()) {

            for (int i = 0; i <= who.getCurrentObjectZ(); i++) {
                boolean isExist = false;
                for (int j = 0; !isExist && j < who.getCurrentObject().getObjectMatrix().length; j++) {
                    for (int k = 0; !isExist && k < who.getCurrentObject().getObjectMatrix()[j].length; k++) {
                        for (int l = 0; !isExist && l < who.getCurrentObject().getObjectMatrix()[j][k].length; l++) {
                            final boolean isValid = (j + who.getCurrentObjectX()) < GameStatus.getGameBoolMatrix().length
                                    && (k + who.getCurrentObjectY()) < GameStatus.getGameBoolMatrix()[0].length &&
                                    (l + i) < GameStatus.getGameBoolMatrix()[0][0].length;
                            if (who.getCurrentObject().getObjectMatrix()[j][k][l] &&
                                    isValid &&
                                    GameStatus.getGameBoolMatrix()[j + who.getCurrentObjectX()][k + who.getCurrentObjectY()][i + l]) {
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

    public static  boolean isExistSomeBlockInLayer(int layer){
        if(0<=layer&&layer<=GameStatus.getGameHeight()){
            for(int i=0;i<GameStatus.getGameBoolMatrix().length;i++){
                for(int j=0;j<GameStatus.getGameBoolMatrix()[i].length;j++){
                    if(GameStatus.getGameBoolMatrix()[i][j][layer]){
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    public static boolean forceRemoveRows(final int offsetHeight,final int count){

        ArrayList<Integer> rows = new ArrayList<Integer>();
        synchronized (gameBoolMatrix) {
            for (int i = offsetHeight + count - 1; i >= offsetHeight; i--) {
                if (0 <= i && i <= GameStatus.getGameHeight()&&isExistSomeBlockInLayer(i)) {
                    rows.add(i);
                }
            }
            if (!rows.isEmpty()) {
                GameStatus.remove_line_count += rows.size();
                removeRows(rows);
                if (orol != null) {
                    orol.onRemove(GameStatus.remove_line_count);
                }
                return true;
            }
        }
        return false;
    }
    public static void init(Context _c) {
        config_data=new JSONObject();

        players.forceClear();
        remove_line_count=0;
        players.add(new DeviceUser(_c));
        gameHeight = 12;
        setPivotZ((float)gameHeight/4);
        gridSize = 6;
        gStatus = GAME_STATUS.START;
        restartGameBoolMatrix();
        setCamera(initialCameraAngle, gameHeight);
        start=true;
        end = false;
        startX = 2;
        play_time=0;
        startY = 2;
        dropFast = false;
        try {
            config_data.put("game_board_xy_size", gridSize);
            config_data.put("game_board_height",gameHeight);
            config_data.put("participated_player_count",players.size());

        }catch (Exception e){

        }
    }

    public static void initFromSavePoint(Context c,SavePoint sp) throws Exception{
        if(sp.getGameMatrix()!=null&&sp.getUserData()!=null){
            config_data=new JSONObject();
            players.forceClear();
            play_time=sp.getPlayTime();
            gameHeight=sp.getGameHeight();
            gridSize=sp.getGridSize();
            startX=sp.getStartX();
            startY=sp.getStartY();
            players.add(sp.getUserData().setContext(c));

            remove_line_count=0;
            players.add(sp.getUserData());
            setPivotZ((float)gameHeight/4);
            gStatus = GAME_STATUS.START;
            //restartGameBoolMatrix();
            gameBoolMatrix=sp.getGameMatrix();
            gameColorMatrix=sp.getGameMatColor();
            setCamera(initialCameraAngle, gameHeight);
            setCameraH(sp.getCameraH());
            setCameraR(sp.getCameraR());
            start=true;
            end = false;
            dropFast = false;

            try {
                config_data.put("game_board_xy_size", gridSize);
                config_data.put("game_board_height",gameHeight);
                config_data.put("participated_player_count",players.size());

            }catch (Exception e){

            }
        }
        else{
            throw new NullPointerException("game matrix or user data is null.");
        }
    }
}
