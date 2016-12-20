package org.sms.tetris3d.players;

import com.trippleit.android.tetris3d.render.AbstractOpenGlRenderer;

import org.sms.tetris3d.GameStatus;

/**
 * Created by hsh on 2016. 11. 16..
 */

/**
 * 게임모드를 추가 할 경우를 대비한 클래스
 * 현 단계에서는 시용하지 않는 클래스
 *
 * @version 1.0
 *
 * @author 황세현
 *
 */
public class Computer extends User {
    public Computer(){
        super();
    }
    @Override
    public Computer myIdentity(){
        return this;
    }
    public void newShape() {
        int objNum = randInt(0, 5);
        setCurrentObject(chooseObject(objNum));
        setCurrentPosition(GameStatus.getStartX(), GameStatus.getStartY(), GameStatus.getGameHeight());
    }
    public void think(){

    }
    public void dropDown(final AbstractOpenGlRenderer ogr) {
        if(GameStatus.isEnd()) return;
        if (ogr.getOneSec() != 0) return;
        boolean ret = setCurrentObjectPositionDown();
        if(GameStatus.isDropFast())
            while(setCurrentObjectPositionDown());
        if (!ret) {
            savePositionToBoolMatrix();
            if (GameStatus.checkEnd() == false) {
                newShape();
            }
        }
    }
}
