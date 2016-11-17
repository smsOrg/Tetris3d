package org.sms.tetris3d.players;

/**
 * Created by hsh on 2016. 11. 16..
 */

public class DeviceUser extends User {
    @Override
    protected DeviceUser myIdentity(){
        return this;
    }
    public DeviceUser(){super();}
}
