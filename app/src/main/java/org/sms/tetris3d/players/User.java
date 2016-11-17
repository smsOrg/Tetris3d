package org.sms.tetris3d.players;

import org.sms.tetris3d.GameStatus;

import com.trippleit.android.tetris3d.shapes.Cube;
import com.trippleit.android.tetris3d.shapes.IShape;
import com.trippleit.android.tetris3d.shapes.ObjectC;
import com.trippleit.android.tetris3d.shapes.ObjectI;
import com.trippleit.android.tetris3d.shapes.ObjectL;
import com.trippleit.android.tetris3d.shapes.ObjectS;
import com.trippleit.android.tetris3d.shapes.ObjectT;
import com.trippleit.android.tetris3d.shapes.ObjectZ;

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

    public boolean setCurrentXPositionPos() {
        if (GameStatus.isEnd())
            return false;
        // +1 zato što se želi pomaknuti objekt;
        // -1 zato što je nulto indeksiranje pa objekt treba smanjizi za 1
        if (currentObjectX + currentObject.getXsize() - 1 + 1 < GameStatus.getGridSize()) {
            if (isAvailable(1))
                currentObjectX++;
            return true;
        }
        return false;
    }

    public  boolean setCurrentXPositionNeg() {
        if (GameStatus.isEnd())
            return false;
        if (currentObjectX - 1 >= 0) {
            if (isAvailable(2))
                currentObjectX--;
            return true;
        }
        return false;
    }

    public boolean setCurrentYPositionPos() {
        if (GameStatus.isEnd())
            return false;
        // +1 zato što se želi pomaknuti objekt;
        // -1 zato što je nulto indeksiranje pa objekt treba smanjizi za 1
        if (currentObjectY + currentObject.getYsize() - 1 + 1 < GameStatus.getGridSize()) {
            if (isAvailable(3))
                currentObjectY++;
            return true;
        }
        return false;
    }
    public  boolean isAvailable(int direction) {

        for (int i = 0; i < currentObject.getObjectMatrix().length; i++)
            for (int j = 0; j < currentObject.getObjectMatrix().length; j++)
                for (int k = 0; k < currentObject.getObjectMatrix().length; k++)
                    if (currentObject.getObjectMatrix()[i][j][k] == true)
                        switch (direction) {
                            case 1:
                                if (GameStatus.getGameBoolMatrix()[i + currentObjectX + 1][j
                                        + currentObjectY][k + currentObjectZ] == true)
                                    return false;
                                break;
                            case 2:
                                if (GameStatus.getGameBoolMatrix()[i + currentObjectX - 1][j
                                        + currentObjectY][k + currentObjectZ] == true)
                                    return false;
                                break;
                            case 3:
                                if (GameStatus.getGameBoolMatrix()[i + currentObjectX][j
                                        + currentObjectY + 1][k + currentObjectZ] == true)
                                    return false;
                                break;
                            case 4:
                                if (GameStatus.getGameBoolMatrix()[i + currentObjectX][j
                                        + currentObjectY - 1][k + currentObjectZ] == true)
                                    return false;
                                break;
                        }
        return true;
    }

    public boolean setCurrentYPositionNeg() {
        if (GameStatus.isEnd())
            return false;
        if (currentObjectY - 1 >= 0) {
            if (isAvailable(4))
                currentObjectY--;
            return true;
        }
        return false;
    }
    @Override
    public void onSwipeRight() {
        moveX(false);
    }

    @Override
    public void onSwipeLeft() {
        moveX(true);
    }

    @Override
    public void onSwipeTop() {
        moveY(false);
    }

    @Override
    public void onSwipeBottom() {
        moveY(true);
    }

    @Override
    public void moveX(boolean oppo) {
        if(currentObject!=null){
            if(oppo){
                this.setCurrentXPositionNeg();
            }
            else{
                this.setCurrentXPositionPos();
            }
        }
    }
    protected IShape chooseObject(int shapeId) {
        switch (shapeId) {
            case 0:
            case 'C':
            case 'c':
                return new ObjectC(myIdentity());
            case 1:
            case 'I':
            case 'i':
                return new ObjectI(myIdentity());
            case 2:
            case 'L':
            case 'l':
                return new ObjectL(myIdentity());
            case 3:
            case 'S':
            case 's':
                return new ObjectS(myIdentity());
            case 4:
            case 'T':
            case 't':
                return new ObjectT(myIdentity());
            case 5:
            case 'Z':
            case 'z':
                return new ObjectZ(myIdentity());
        }

        return new Cube(myIdentity());
    }
    @Override
    public void moveY(boolean oppo) {
        if(currentObject!=null){
            if(oppo){
                this.setCurrentYPositionNeg();
            }
            else{
                this.setCurrentYPositionPos();
            }
        }
    }

    public  boolean isAvailableSwipeBlock(){
        if(getNextObject()!=null){
            for(int i =0;i<getNextObject().getObjectMatrix().length;i++){
                for(int j=0;j<getNextObject().getObjectMatrix()[i].length;j++){
                    for(int k=0;k<getNextObject().getObjectMatrix()[i][j].length;k++){
                        final int bX = i+getCurrentObjectX();
                        final int bY=j+getCurrentObjectY();
                        final int bZ=k+getCurrentObjectZ();
                        final boolean isValid= bX<GameStatus.getGameBoolMatrix().length && bY<GameStatus.getGameBoolMatrix()[0].length&&bZ<GameStatus.getGameBoolMatrix()[0][0].length;

                        if(isValid&&getNextObject().getObjectMatrix()[i][j][k]&&GameStatus.getGameBoolMatrix()[bX][bY][bZ]){
                            return false;
                        }
                        else if(!isValid&&getNextObject().getObjectMatrix()[i][j][k]){
                            return false;
                        }
                    }
                }
            }
        }
        else{
            return false;
        }

        return true;
    }

    @Override
    public  boolean swipeBlock(final boolean fixCurrentObjectPos){
        if(isAvailableSwipeBlock()) {
            IShape tmp = getNextObject();
            setNextObject(getCurrentObject());
            setCurrentObject(tmp);
            if (getCurrentObject() == tmp) {
                if (fixCurrentObjectPos) {
                    return true;
                }
            }
        }
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

    public  void savePositionToBoolMatrix() {
        for (int i = 0; i < currentObject.getObjectMatrix().length; i++)
            for (int j = 0; j < currentObject.getObjectMatrix().length; j++)
                for (int k = 0; k < currentObject.getObjectMatrix().length
                        && k < GameStatus.getGameHeight(); k++)
                    if ((k + currentObjectZ) < GameStatus.getGameHeight()
                            && currentObject.getObjectMatrix()[i][j][k] == true) {
                        GameStatus.getGameBoolMatrix()[i + currentObjectX][j + currentObjectY][k
                                + currentObjectZ] = true;
                        GameStatus.getGameColorMatrix()[i + currentObjectX][j + currentObjectY][k
                                + currentObjectZ] = currentObject.getColor();
                    }
    }

    @Override
    public boolean setCurrentObjectPositionDown() {
        if (currentObjectZ <= 0  || currentObject==null) {
            return false;
        }

        for (int i = 0; i < currentObject.getObjectMatrix().length; i++)
            for (int j = 0; j < currentObject.getObjectMatrix().length; j++)
                for (int k = 0; k < currentObject.getObjectMatrix().length; k++)
                    if (currentObject.getObjectMatrix()[i][j][k] == true)
                        if (GameStatus.getGameBoolMatrix()[i + currentObjectX][j
                                + currentObjectY][currentObjectZ - 1] == true) {
                            if (k != 0)
                                currentObjectZ--; // ukoliko kolizija nije na
                            // prvoj razini treba
                            // dopustiti još jedan drop
                            return false;
                        }

        currentObjectZ--;
        return true;
    }
    protected User myIdentity(){
        return this;
    }
}
