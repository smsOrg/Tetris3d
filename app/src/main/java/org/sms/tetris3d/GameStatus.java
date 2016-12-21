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

/**
 * 메인 게임 데이터 관리객체
 */
public class GameStatus extends com.trippleit.android.tetris3d.GameStatus{
    /**
     * db파일 버전
     */
    public static final int DB_FILE_VERSION = 3;
    /**
     * 게임에 필요한 사용자를 관리하는 객체
     */
    protected static final  RegisteredPlayers players = new RegisteredPlayers(){
    };
    /**
     * 플레이된 시간을 관리하는 객체
     */
    protected static long play_time=0;

    /**
     *
     * @param val
     */
    public static void setPlayTime(long val){play_time = val;}

    /**
     *
     * @return
     */
    public static long getPlayTime(){return play_time;}

    /**
     * 게임로그에 포함될 최소한의 게임 정보를 관리하는 객체
     */
    protected static  JSONObject config_data = new JSONObject();

    /**
     * 게임 정보객체를 불러옵니다
     * @return
     */
    public static JSONObject getConfigData(){
        return config_data;
    }

    /**
     * 사용자 관리객체를 불러옵니다
     * @return
     */
    public static ArrayList<User> getPlayers(){
        return players;
    }

    /**
     * 카메라가 보고 있는 게임 보드의 높이를 담고있는 변수
     */
    protected static float pivotZ ;

    /**
     * 카메라가 응시하고 있는 높이를 가져옵니다
     * @return
     */
    public static float getPivotZ(){
        return pivotZ;
    }

    /**
     * 카메라가 응시하고 있을 높이를 설정합니다
     * @param pz
     */
    public static void setPivotZ(float pz){
        if((float)getGameHeight()/4<=pz&&pz<=getGameHeight()+2) {
            pivotZ = pz;
        }
    }

    /**
     * 코드간략화를 위한 함수::디바이스 사용자 객체를 가져옵니다
     * @return
     */
    public static DeviceUser getDeviceUser(){
        return (DeviceUser) players.get(0);
    }

    /**
     * 매개변수로 들어온 사용자의 현재 블럭모양이 계속떨어질떄 상대적인 지면이될 높이를 가져옵니다
     * @param who
     * @return 놓아질 수 있는 높이
     */
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

    /**
     * 게임을 리셋합니다
     */
    public static void restartGame(){
        init(null);
    }

    /**
     * 어떤 1x1x1 블럭이 어떤 층에 하나라도 존재하는 지 확인합니다
     * @param layer
     * @see org.sms.tetris3d.items.AvailableItems
     * @return 확인 여부
     */
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

    /**
     * 층을 강제로 삭제합니다
     * @param offsetHeight
     * @param count
     * @return
     */
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

    /**
     * 저장관리객체의 내부데이터들을 초기화합니다
     * @param _c
     */
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

    /**
     * 저장관리객체의 내부데이터들을 매개변수로 들어온 세이브포인트 객체의 데이터를 기준으로 초기화합니다
     * @param c
     * @param sp
     * @throws Exception
     */
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
