package org.sms.tetris3d.players;


import org.sms.tetris3d.GameStatus;
import com.trippleit.android.tetris3d.render.OpenGlRenderer;
import org.sms.tetris3d.items.*;
/**
 * Created by hsh on 2016. 11. 16..
 */

public class DeviceUser extends User {
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
        item_manager=new ItemManagerForEachUser();
    item_manager.add(AvailableItems.getPositionResetItem());
        item_manager.add(AvailableItems.getRandomLayersRemoverItem());
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
