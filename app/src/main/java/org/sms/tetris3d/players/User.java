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

import java.io.Serializable;
import java.util.*;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by hsh on 2016. 11. 16..
 */

public abstract class User implements  UserDefaultBehavior,Serializable {

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
        return currentObject!=null? currentObject:(currentObject=chooseObject(randInt(0,5)));
    }

    public void allClearData(){
        currentObject=nextObject=null;

    }

    /**
     *  현재 블럭 모양의 위치를 강제로 설정합니다
     * @param x
     * @param y
     * @param z
     */
    public  void setCurrentPosition(int x, int y, int z) {
        currentObjectX = x;
        currentObjectY = y;
        currentObjectZ = z;
    }

    /**
     *  범위내의 임의 정수를 뽑아 리턴합니다
     * @param min
     * @param max
     * @return 정수
     */
    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    /**
     * 현재  블럭의 위치를 x축으로 +1칸 옮겨갑니다
     * @return 성공여부
     */
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
    /**
     * 현재  블럭의 위치를 x축으로 -1칸 옮겨갑니다
     * @return 성공여부
     */
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
    /**
     * 현재  블럭의 위치를 y축으로 +1칸 옮겨갑니다
     * @return 성공여부
     */
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

    /**
     * diectection 방향으로 이동이 가능한지 알려주는 함수입니다
     * @param direction
     * @return 이동가능여부
     */
    public  boolean isAvailable(int direction) {
        synchronized (GameStatus.getGameBoolMatrix()) {
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
        }
        return true;
    }
    /**
     * 현재  블럭의 위치를 y축으로 -1칸 옮겨갑니다
     * @return 성공여부
     */
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

    /**
     * x축으로 한칸이동합니다
     * @param oppo
     */
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

    /**
     * 모양을 가져옵니다
     * @param shapeId
     * @return 모양데이터
     */
    public IShape chooseObject(int shapeId) {
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
    /**
     * y축으로 한칸이동합니다
     * @param oppo
     */
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

    /**
     * 바꿀 블럭데이터가 현재 보드(게임판)과 의 상호작용에서 겹칩문제가 발생하는지 체크하는 함수
     * @return 가능여부
     */
    public  boolean isAvailableSwipeBlock(){
        synchronized (GameStatus.getGameBoolMatrix()) {
            if (getNextObject() != null) {
                for (int i = 0; i < getNextObject().getObjectMatrix().length; i++) {
                    for (int j = 0; j < getNextObject().getObjectMatrix()[i].length; j++) {
                        for (int k = 0; k < getNextObject().getObjectMatrix()[i][j].length; k++) {
                            final int bX = i + getCurrentObjectX();
                            final int bY = j + getCurrentObjectY();
                            final int bZ = k + getCurrentObjectZ();
                            final boolean isValid = bX < GameStatus.getGameBoolMatrix().length && bY < GameStatus.getGameBoolMatrix()[0].length && bZ < GameStatus.getGameBoolMatrix()[0][0].length;

                            if (isValid && getNextObject().getObjectMatrix()[i][j][k] && GameStatus.getGameBoolMatrix()[bX][bY][bZ]) {
                                return false;
                            } else if (!isValid && getNextObject().getObjectMatrix()[i][j][k]) {
                                return false;
                            }
                        }
                    }
                }
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 현재 모양과 다음 모양을 바꿉니다
     * @param fixCurrentObjectPos
     * @return
     */
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

    /**
     * 현재 모양을 회전합니다
     * @param axis
     */
    @Override
    public void rotate(int axis) {
        if(getCurrentObject()!=null){
            getCurrentObject().rotate(axis);
        }
    }

    /**
     * 현재 모양을 그래픽뷰에 렌더링합니다
     * @param gl
     * @param who
     */
    public static void printCurrentObject(GL10 gl, User who){
        gl.glPushMatrix();
        gl.glTranslatef(who.getCurrentObjectX(),who. getCurrentObjectY(), who.getCurrentObjectZ());
        who.getCurrentObject().draw(gl);
        gl.glPopMatrix();
    }

    /**
     * 현재 모양이 그대로 떨어진다면 어디에 떨어질지 예측하는 도형을 그래픽뷰에 렌더링합니다
     * @param gl
     * @param who
     */
    public static void printAbstractObject(GL10 gl, User who){
        gl.glPushMatrix();
        gl.glTranslatef(who.getCurrentObjectX(), who.getCurrentObjectY(), GameStatus.getAvailableZPos(who));
        who.getCurrentObject().drawLineBone(gl);
        gl.glPopMatrix();
    }

    /**
     * 협력모드를 구현할때 사용할 사용자간의 블럭겹칩유무의 판단하는 함수
     * @param pivot
     * @param usr
     * @return 겹칩유무
     */
    public static boolean checkOverlayPlayerBlock2(User pivot,User usr){

            if(!usr.equals(pivot)){
                boolean[][][] pivotMatrix = pivot.getCurrentObject().getObjectMatrix();
                boolean[][][] mat = usr.getCurrentObject().getObjectMatrix();
                for(int i =0;i<pivotMatrix.length;i++){
                    for(int j =0;j<pivotMatrix[i].length;j++){
                        for(int k=0;k<pivotMatrix[i][j].length;k++){
                            final boolean inValid = (i+pivot.getCurrentObjectX())<pivotMatrix.length &&
                                    (j+pivot.getCurrentObjectY())<pivotMatrix[0].length &&
                                    (k+pivot.getCurrentObjectZ())<pivotMatrix[0][0].length &&(i+usr.getCurrentObjectX())<pivotMatrix.length &&
                                    (j+usr.getCurrentObjectY())<pivotMatrix[0].length &&
                                    (k+usr.getCurrentObjectZ())<pivotMatrix[0][0].length;
                            if(inValid&&
                                    pivotMatrix[i+pivot.getCurrentObjectX()][j+pivot.getCurrentObjectY()][k+pivot.getCurrentObjectZ()]==pivotMatrix[i+usr.getCurrentObjectX()][j+usr.getCurrentObjectY()][k+usr.getCurrentObjectZ()]){
                                return true;
                            }

                        }
                    }
                }

        }
        return false;
    }
    /**
     * 협력모드를 구현할때 사용할 사용자간의 블럭겹칩유무의 판단하는 함수
     * @param pivot
     * @return 겹칩유무
     */
    public static boolean checkOverlayPlayerBlock(User pivot){
        ArrayList<User> players = GameStatus.getPlayers();
         for(User usr:players){
            if(!usr.equals(pivot)){
                boolean[][][] pivotMatrix = pivot.getCurrentObject().getObjectMatrix();
                boolean[][][] mat = usr.getCurrentObject().getObjectMatrix();
                for(int i =0;i<pivotMatrix.length;i++){
                    for(int j =0;j<pivotMatrix[i].length;j++){
                        for(int k=0;k<pivotMatrix[i][j].length;k++){
                            final boolean inValid = (i+pivot.getCurrentObjectX())<pivotMatrix.length &&
                                    (j+pivot.getCurrentObjectY())<pivotMatrix[0].length &&
                                    (k+pivot.getCurrentObjectZ())<pivotMatrix[0][0].length &&(i+usr.getCurrentObjectX())<pivotMatrix.length &&
                                    (j+usr.getCurrentObjectY())<pivotMatrix[0].length &&
                                    (k+usr.getCurrentObjectZ())<pivotMatrix[0][0].length;
                            if(inValid&&
              pivotMatrix[i+pivot.getCurrentObjectX()][j+pivot.getCurrentObjectY()][k+pivot.getCurrentObjectZ()]==pivotMatrix[i+usr.getCurrentObjectX()][j+usr.getCurrentObjectY()][k+usr.getCurrentObjectZ()]){
                                return true;
                            }

                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 내려오던 블럭이 게임 매트릭스에 적용되어지는 함수
     */
    public  void savePositionToBoolMatrix() {
synchronized (GameStatus.getGameBoolMatrix()) {
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
    }

    /**
     *  현재 모양을 1칸씩 떨어뜨립니다
     * @return 적용성공유부
     */
    @Override
    public boolean setCurrentObjectPositionDown() {
        if (currentObjectZ <= 0  || currentObject==null) {
            return false;
        }
    synchronized (GameStatus.getGameBoolMatrix()) {
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
    }
        return true;
    }
    protected User myIdentity(){
        return this;
    }
}
