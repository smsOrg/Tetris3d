package org.sms.tetris3d;

import android.content.Context;

import java.util.ArrayList;
import org.sms.tetris3d.players.*;
/**
 * Created by hsh on 2016. 11. 16..
 */

public class GameStatus extends com.trippleit.android.tetris3d.GameStatus{
    protected static final  ArrayList<User> players = new ArrayList<User>(){
        @Override
        public User remove(int idx){
            if(idx>0){
                return super.remove(idx);
            }
            else{
                return get(0);
            }
        }
        @Override
        public void clear(){
            final User usr = get(0);
            super.clear();
            add(usr);
        }
        @Override
        public boolean remove(Object usr){
            if(usr instanceof  DeviceUser){
                return false;
            }else{
                return super.remove(usr);
            }
        }
        @Override
        public void removeRange(int start,int end){
            super.removeRange(start>0?start:1,end>0?end:1);
        }
    };
    public static ArrayList<User> getPlayers(){
        return players;
    }
    public static void init(Context _c) {
        players.add(new DeviceUser());
        gameHeight = 10;
        gridSize = 5;
        gStatus = GAME_STATUS.START;
        restartGameBoolMatrix();
        setCamera(-65, 10);
        start=true;
        end = false;
        startX = 2;
        startY = 2;
        dropFast = false;
    }
}
