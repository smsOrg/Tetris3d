package org.sms.tetris3d.players;

import com.trippleit.android.tetris3d.shapes.IShape;

/**
 * Created by hsh on 2016. 11. 16..
 */

public abstract class User implements  UserDefaultBehavior {


    protected IShape currentObject=null,nextObject=null;

    private  int currentObjectX, currentObjectY, currentObjectZ;

    public  int getCurrentObjectX() {
        return currentObjectX;
    }

    public  void setCurrentObjectX(int currentObjectX) {
        this.currentObjectX = currentObjectX;
    }

    public  int getCurrentObjectY() {
        return currentObjectY;
    }

    public  void setCurrentObjectY(int currentObjectY) {
        this.currentObjectY = currentObjectY;
    }

    public  int getCurrentObjectZ() {
        return currentObjectZ;
    }

    public  void setCurrentObjectZ(int currentObjectZ) {
        this.currentObjectZ = currentObjectZ;
    }


    public  IShape getNextObject(){
        return nextObject;
    }

    public  void setCurrentObject(IShape currentObject){
        this.currentObject = currentObject;
    }

    public  void setNextObject(IShape nextObject){
        this.nextObject = nextObject;
    }

    public IShape getCurrentObject(){
        return currentObject;
    }


    @Override
    public void onSwipeRight() {

    }

    @Override
    public void onSwipeLeft() {

    }

    @Override
    public void onSwipeTop() {

    }

    @Override
    public void onSwipeBottom() {

    }

    @Override
    public void moveX(boolean opposite) {

    }

    @Override
    public void moveY(boolean opposite) {

    }

    @Override
    public boolean swipeBlock(boolean fix) {
        return false;
    }

    @Override
    public void rotate(int axis) {

    }

    @Override
    public boolean setCurrentObjectPositionDown() {
        return false;
    }
    protected User myIdentity(){
        return this;
    }
}
