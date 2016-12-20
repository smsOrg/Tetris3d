package org.sms.tetris3d.players;

/**
 * Created by hsh on 2016. 11. 16..
 */

/**
 * 멀티플레이를 할 경우를 대비한 클래스
 * 현 단계에서는 시용하지 않는 클래스
 *
 * @version 1.0
 *
 * @author 황세현
 *
 */
public class AnotherUser extends User {
    @Override
    public AnotherUser myIdentity(){
        return this;
    }
    public AnotherUser(){super();}
}
