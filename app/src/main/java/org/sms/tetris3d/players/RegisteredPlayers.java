package org.sms.tetris3d.players;

import java.util.ArrayList;

/**
 * Created by hsh on 2016. 11. 19..
 */

public class RegisteredPlayers extends ArrayList<User>{
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
    public void forceClear(){
        super.clear();
    }
    public void forceRemove(int index){
        super.remove(index);
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

}
