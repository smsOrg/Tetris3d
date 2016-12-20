package org.sms.tetris3d.players;

import java.util.ArrayList;

/**
 * Created by hsh on 2016. 11. 19..
 */

/**
 * 메인 게임 관리객체에서 사용자들을 관리할떄 사용하는 클래스
 *
 */
public class RegisteredPlayers extends ArrayList<User>{
    /**
     *  사용자 제거
     * @param idx
     * @return 제거될 사용자
     */
    @Override
    public User remove(int idx){
        if(idx>0){
            return super.remove(idx);
        }
        else{
            return get(0);
        }
    }

    /**
     * 현재 디바이스의 사용자를 제외한 모든 사용자를 제거
     *
     */
    @Override
    public void clear(){
        final User usr = get(0);
        super.clear();
        add(usr);
    }

    /**
     * 강제적으로 모든 사용자를 제거
     *
     */
    public void forceClear(){
        super.clear();
    }
    /**
     * 강제적으로 모든 사용자를 제거
     *
     */
    public void forceRemove(int index){
        super.remove(index);
    }
    /**
     * 디바이스 사용자가 아니면 제거
     *
     */
    @Override
    public boolean remove(Object usr){
        if(usr instanceof  DeviceUser){
            return false;
        }else{
            return super.remove(usr);
        }
    }

    /**
     *
     * 범위로 제거
     * @param start
     * @param end
     */
    @Override
    public void removeRange(int start,int end){
        super.removeRange(start>0?start:1,end>0?end:1);
    }

}
