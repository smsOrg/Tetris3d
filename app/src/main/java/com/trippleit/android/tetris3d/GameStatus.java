package com.trippleit.android.tetris3d;

import java.util.ArrayList;
import org.sms.tetris3d.interfaces.OnRemoveLayer;

import android.content.Context;
import android.util.Log;

public class GameStatus {
    public final static float initialCameraAngle = 360-65;
    protected static float cameraR, cameraH;
    protected static float cameraX, cameraY, cameraZ;
    protected static long remove_line_count=0l;
    protected static float circleSize = 0;
    protected static int gameHeight;
    protected static int gridSize;
    protected static int startX, startY;
    protected static OnRemoveLayer orol = null;
    //protected static IShape currentObject,nextObject;
//	protected static int currentObjectX, currentObjectY, currentObjectZ;

    protected static boolean gameBoolMatrix[][][];
    protected static String gameColorMatrix[][][];

    protected static boolean dropFast;
    protected static boolean end;
    protected static boolean start=true;
    public enum GAME_STATUS {END,START,PAUSE,ONGOING};
    protected static GAME_STATUS gStatus  = GAME_STATUS.START;
    public enum PLAYER_STATUS{
        DISCONNECT,CONNECT,MOVE_BLOCK,ROTATE_BLOCK,PUT_BLOCK,SWIPE_BLOCK,NEW_SHAPE
    }
    public static long getRemoveLineCount(){
        return GameStatus.remove_line_count;
    }
    public static void init(Context _c) {
        gameHeight = 10;
        gridSize = 5;
        gStatus = GAME_STATUS.START;
        restartGameBoolMatrix();
        setCamera(initialCameraAngle, gameHeight);
        start=true;
        end = false;
        startX = 2;
        startY = 2;
        dropFast = false;
    }

    public static boolean isPaused() {
        return gStatus==GAME_STATUS.PAUSE;
    }

    public static void setGameStatus(GAME_STATUS gs) {
        gStatus = gs;
    }
    public static GAME_STATUS getGameStatus() {
        return gStatus;
    }
    public static void setStartStatus(final boolean state){
        start = state;
        if(state){
            gStatus = GAME_STATUS.START;
        }
        else if(gStatus!=GAME_STATUS.PAUSE&&gStatus!=GAME_STATUS.END) {
            gStatus = GAME_STATUS.ONGOING;
        }

    }
    public static boolean isStarting(){
        return GAME_STATUS.START==gStatus;
    }
    public static void setOnRemoveOneLayer(OnRemoveLayer orol){
        GameStatus.orol = orol;
    }
    /**
     * Postavi kameru
     *
     * @param r
     *            Radijus kružnice na kojoj je kamera
     * @param h
     *            Visina kammere
     */
    public static void setCamera(float r, float h) {
        setCameraR(r);
        setCameraH(h);

        //calculateCamera();
    }
    public static float getDefaultCircleSize(){
        return (gridSize*2);
    }
    public static float getMaxCircleSize(){
        return (gridSize*4);
    }
    public static float getCircleSize(){
        return circleSize;
    }
    public static void setCircleSize(float size){
        if(size>=0&&size<getMaxCircleSize()){
            circleSize = size;
        }
    }

    protected static void calculateCamera() {
        GameStatus.cameraX = ((getDefaultCircleSize()+getCircleSize()) * (float) Math.cos(Math
                .toRadians(GameStatus.cameraR)))+gridSize/2;
        GameStatus.cameraY = ((getDefaultCircleSize()+getCircleSize()) * (float) Math.sin(Math
                .toRadians(GameStatus.cameraR)))+gridSize/2;
        if(Math.abs(GameStatus.cameraH)<getGameHeight()*2.3) {
            GameStatus.cameraZ = GameStatus.cameraH+getGameHeight()/2;
        }
    }

    public static float getCameraX() {
        calculateCamera();
        return cameraX;
    }

    public static float getCameraY() {
        calculateCamera();
        return cameraY;
    }

    public static float getCameraZ() {
        calculateCamera();
        return cameraZ;
    }

    public static float getCameraR() {
        return cameraR;
    }

    public static void setCameraR(float cameraR) {
        if(cameraR<0){
            cameraR=360-cameraR;
        }
        GameStatus.cameraR = cameraR;
        calculateCamera();
    }

    public static float getCameraH() {
        return cameraH;
    }

    public static void setCameraH(float cameraH) {
        GameStatus.cameraH =  cameraH;
        calculateCamera();
    }


    public static boolean isSupportCameraDrag(){
        return true;
    }
    /**
     * Postavljanje matrice zauzeća polja, ukoliko nije inicijalizirana
     * inicijalizira se sa veličinom mreže i visinom igre
     *
     * @param x
     * @param y
     * @param z
     */
    public static void setGameBoolMatrix(int x, int y, int z) {
        if (gameBoolMatrix == null) {
            restartGameBoolMatrix();
        }
        gameBoolMatrix[x][y][z] = true;
    }

    public static void restartGameBoolMatrix() {
        remove_line_count=0;
        int objectBuffer = 5;
        gameBoolMatrix = new boolean[gridSize][gridSize][gameHeight
                + objectBuffer];
        gameColorMatrix = new String[gridSize][gridSize][gameHeight
                + objectBuffer];
        for (int i = 0; i < gridSize; i++)
            for (int j = 0; j < gridSize; j++)
                for (int k = 0; k < gameHeight + objectBuffer; k++) {
                    gameBoolMatrix[i][j][k] = false;
                    gameColorMatrix[i][j][k] = "#000000";
                }
    }

    public static boolean[][][] getGameBoolMatrix() {
        return gameBoolMatrix;
    }

    public static String[][][] getGameColorMatrix() {
        return gameColorMatrix;
    }

    public static void setGameColorMatrix(String[][][] gameColorMatrix) {
        GameStatus.gameColorMatrix = gameColorMatrix;
    }

    public static int getGridSize() {
        return gridSize;
    }

    public static void setGridSize(int gridSize) {
        GameStatus.gridSize = gridSize;
    }

	/*public static int getAvailableZPos(final User who){
		int result=0;
		for (int i = 0; i <= who.getCurrentObjectZ(); i++) {
			boolean isExist = false;
			for(int j =0;!isExist&&j<who.getCurrentObject().getObjectMatrix().length;j++){
				for(int k =0;!isExist&&k<who.getCurrentObject().getObjectMatrix()[j].length;k++) {
					for (int l = 0;!isExist&&l<who.getCurrentObject().getObjectMatrix()[j][k].length;l++) {
						final boolean isValid = (j + who.getCurrentObjectX()) < GameStatus.getGameBoolMatrix().length && (k + who.getCurrentObjectY()) < GameStatus.getGameBoolMatrix()[0].length&&(l + i) < GameStatus.getGameBoolMatrix()[0][0].length;
						if (who.getCurrentObject().getObjectMatrix()[j][k][l] && isValid && GameStatus.getGameBoolMatrix()[j + who.getCurrentObjectX()][k + who.getCurrentObjectY()][i+l]) {
							isExist = true;
						}
					}
				}
			}
			if(isExist){
				result = i+1;
			}
		}
		return result;
	}*/

    public static int getGameHeight() {
        return gameHeight;
    }

    public static void setGameHeight(int gameHeight) {
        GameStatus.gameHeight = gameHeight;
    }

    public static boolean isEnd() {
        return gStatus==GAME_STATUS.END;
    }

    public static boolean checkEnd() {
        boolean end = gameBoolMatrix[startX][startY][gameHeight - 1];
        if (end){
            gStatus = GAME_STATUS.END;
            Log.d("Kruno", "---END---");
        }
        return end;
    }

    public static int getStartX() {
        return startX;
    }

    public static void setStartX(int startX) {
        GameStatus.startX = startX;
    }

    public static int getStartY() {
        return startY;
    }

    public static void setStartY(int startY) {
        GameStatus.startY = startY;
    }

    public static boolean isDropFast() {
        boolean temp = dropFast;
        dropFast = false;
        return temp;
    }

    public static void setDropFast() {
        GameStatus.dropFast = true;
    }

    public static boolean removeFullRows() {
        synchronized (gameBoolMatrix) {
            ArrayList<Integer> rowsToRemove = new ArrayList<Integer>();
            for (int k = gameHeight; k >= 0; k--) {
                boolean remove = true;
                for (int i = 0; i < gridSize; i++)
                    for (int j = 0; j < gridSize; j++)
                        if (gameBoolMatrix[i][j][k] == false)
                            remove = false;
                if (remove)
                    rowsToRemove.add(k);
            }
            if (!rowsToRemove.isEmpty()) {
                GameStatus.remove_line_count += rowsToRemove.size();
                removeRows(rowsToRemove);
                if (GameStatus.orol != null) {
                    orol.onRemove(GameStatus.remove_line_count);
                }
                return true;
            }
        }
        return false;
    }



    protected static void removeRows(ArrayList<Integer> rowsToRemove) {
        for (Integer x : rowsToRemove) {
            for (int k = x; k < gameHeight; k++)
                for (int i = 0; i < gridSize; i++)
                    for (int j = 0; j < gridSize; j++)
                        if (x == gameHeight - 1){
                            gameBoolMatrix[i][j][k] = false;
                            gameColorMatrix[i][j][k] = "";
                        }else{
                            gameBoolMatrix[i][j][k] = gameBoolMatrix[i][j][k + 1];
                            gameColorMatrix[i][j][k] = gameColorMatrix[i][j][k + 1];
                        }
        }
    }
}