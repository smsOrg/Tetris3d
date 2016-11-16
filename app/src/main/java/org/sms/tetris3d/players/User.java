package org.sms.tetris3d.players;

import org.sms.tetris3d.GameStatus;
import com.trippleit.android.tetris3d.shapes.IShape;
import java.util.*;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by hsh on 2016. 11. 16..
 */

public abstract class User implements  UserDefaultBehavior {

    protected User(){
        super();
    }

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


    public  void setCurrentPosition(int x, int y, int z) {
        currentObjectX = x;
        currentObjectY = y;
        currentObjectZ = z;
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
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
        if(getCurrentObject()!=null){
            getCurrentObject().rotate(axis);
        }
    }

    public static void printCurrentObject(GL10 gl, User who){
        gl.glPushMatrix();
        gl.glTranslatef(who.getCurrentObjectX(),who. getCurrentObjectY(), who.getCurrentObjectZ());
        who.getCurrentObject().draw(gl);
        gl.glPopMatrix();
    }

    public static void printAbstractObject(GL10 gl, User who){
        gl.glPushMatrix();
        gl.glTranslatef(who.getCurrentObjectX(), who.getCurrentObjectY(), GameStatus.getAvailableZPos(who));
        who.getCurrentObject().drawLineBone(gl);
        gl.glPopMatrix();
    }
    @Override
    public boolean setCurrentObjectPositionDown() {
        return false;
    }
    protected User myIdentity(){
        return this;
    }
}
