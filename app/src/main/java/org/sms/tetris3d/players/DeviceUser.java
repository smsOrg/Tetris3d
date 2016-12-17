package org.sms.tetris3d.players;


import org.sms.tetris3d.GameStatus;
import com.trippleit.android.tetris3d.render.OpenGlRenderer;
import org.sms.tetris3d.items.*;
import android.content.*;

import java.io.Serializable;

/**
 * Created by hsh on 2016. 11. 16..
 */

public class DeviceUser extends User implements Serializable{
    protected transient Context mContext = null;
    protected  ItemManagerForEachUser item_manager = new ItemManagerForEachUser();
    public ItemManagerForEachUser getItemManager(){
        if(item_manager==null){
            item_manager=new ItemManagerForEachUser();
        }
        return item_manager;
    }

    @Override
    protected DeviceUser myIdentity(){
        return this;
    }
    public DeviceUser(){super();

    }
    public DeviceUser(Context ctx){
        this();
        setContext(ctx);
        item_manager=new ItemManagerForEachUser();
        /*item_manager.add(AvailableItems.getPositionResetItem());
        item_manager.add(AvailableItems.getItemByID(ctx,1,null));
        item_manager.add(AvailableItems.getItemByID(ctx,3,null));*/
    }
    public DeviceUser setContext(Context ctx){
        mContext = ctx;
        return this;
    }
    @Override
    public com.trippleit.android.tetris3d.shapes.IShape chooseObject(int rand){
        return super.chooseObject(rand);
    }
    public void newShape() {
        int objNum = randInt(0, 5);
        setCurrentObject(getNextObject()!=null?getNextObject():chooseObject(objNum));
        objNum = randInt(0, 5);
        setNextObject(chooseObject(objNum));
        setCurrentPosition(GameStatus.getStartX(), GameStatus.getStartY(), GameStatus.getGameHeight());
    }
    public void dropDown(final OpenGlRenderer ogr) {
        if (GameStatus.isEnd()) return;
        if (ogr.getOneSec() != 0) return;
        synchronized (GameStatus.getGameBoolMatrix()) {
            boolean ret = setCurrentObjectPositionDown();
            if (GameStatus.isDropFast())
                while ((ret=setCurrentObjectPositionDown())) ;

            if (!ret) {
                if (!checkOverlayPlayerBlock(this)) {
                    savePositionToBoolMatrix();
                    if (GameStatus.checkEnd() == false) {
                        newShape();
                    }
                } else {
                    setCurrentPosition(GameStatus.getStartX(), GameStatus.getStartY(), GameStatus.getGameHeight());
                }

            }

        }
    }
}
