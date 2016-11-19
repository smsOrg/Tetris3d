package org.sms.tetris3d.render;

import android.opengl.GLU;

import com.trippleit.android.tetris3d.render.OpenGlRenderer;
import com.trippleit.android.tetris3d.shapes.Coords;
import com.trippleit.android.tetris3d.shapes.Grid;

import org.sms.tetris3d.GameStatus;
import org.sms.tetris3d.players.Computer;
import org.sms.tetris3d.players.DeviceUser;
import org.sms.tetris3d.players.User;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by hsh on 2016. 11. 19..
 */

public class GameRenderer extends OpenGlRenderer {

    @Override
    public void onDrawFrame(GL10 gl, boolean firstDraw) {
        DeviceUser du = (DeviceUser) GameStatus.getPlayers().get(0);
        gl.glTranslatef((float)GameStatus.getGridSize()/2, (float)GameStatus.getGridSize()/2,0);
        gl.glPushMatrix();
        if (firstDraw||du.getCurrentObject()==null) du.newShape();
        while(du.getCurrentObject().getObjectMatrix()==null||du.getNextObject()==null){
            du.newShape();
        }
        if(GameStatus.isStarting()) {
            //drawStartCount(startcnt);
            //if((--startcnt)<1){
            GameStatus.setStartStatus(false);
            //}
        }
        else if(GameStatus.isPaused()){
            drawPause(gl);
        }
        else {

            //GLU.gluLookAt(gl, GameStatus.getCameraX(), GameStatus.getCameraY(), GameStatus.getCameraZ(), 0, 0, 0, 0, 0, 1);
            GLU.gluLookAt(gl, GameStatus.getCameraX(),GameStatus.getCameraY(), GameStatus.getCameraZ(), (float)GameStatus.getGridSize()/2, (float)GameStatus.getGridSize()/2, (float)GameStatus.getGameHeight()/4, 0, 0, 1);


            new Coords(GameStatus.getGridSize(), GameStatus.getGameHeight()).draw(gl);
            new Grid(GameStatus.getGridSize()).draw(gl);

           // du.dropDown(this);
            for(User usr:GameStatus.getPlayers()) {
                if (User.checkOverlayPlayerBlock(usr)) {
                    usr.setCurrentPosition(GameStatus.getStartX(), GameStatus.getStartY(), GameStatus.getGameHeight());
                }
                if(usr instanceof DeviceUser){
                    ((DeviceUser)usr).dropDown(this);
                }else if(usr instanceof Computer){
                    //((Computer)usr).dropDown(this);
                }
            }
            //dropDown();
            removeFullRows();
            printAllObjects(gl);
            //printCurrentObject(gl);
            //for(User usr:GameStatus.getPlayers()) {
            for(int i = GameStatus.getPlayers().size()-1;i>=0;i--){

                User usr = GameStatus.getPlayers().get(i);
                if (!GameStatus.isEnd()&&usr.getNextObject()!=null) {
                    User.printAbstractObject(gl,usr);
                }
                User.printCurrentObject(gl,usr);
            }

        }
        gl.glPopMatrix();
    }
}
