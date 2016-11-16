package org.sms.tetris3d.players;

/**
 * Created by hsh on 2016. 11. 16..
 */

public class User implements  UserDefaultBehavior {
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
