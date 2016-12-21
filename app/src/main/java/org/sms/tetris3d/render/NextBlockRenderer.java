package org.sms.tetris3d.render;


import com.trippleit.android.tetris3d.shapes.Coords;
import javax.microedition.khronos.opengles.GL10;
import org.sms.tetris3d.players.*;
import org.sms.tetris3d.GameStatus;
import android.opengl.GLU;
/**
 * Created by hsh on 2016. 11. 17..
 */

public class NextBlockRenderer extends com.trippleit.android.tetris3d.render.AbstractOpenGlRenderer {
    protected float cx,cy,cz,cr=-0.05f,rad=0;

    /**
     * 다음 모양을 가져와 렌더링을 합니다
     * @param gl
     * @param firstDraw
     */
    @Override
    public void onDrawFrame(GL10 gl, boolean firstDraw) {
        if(!GameStatus.isEnd()&&!GameStatus.isStarting()) {
            final DeviceUser du = (DeviceUser)GameStatus.getPlayers().get(0);

            GLU.gluLookAt(gl, 0, -15, 3, 0, 0, 1, 0, 0, 1);
            //new Coords(GameStatus.getGridSize(), GameStatus.getGameHeight()).draw(gl);
            if (GameStatus.getPlayers().get(0).getNextObject() == null) {
                final int objNum = du.randInt(0, 5);
                du.setNextObject(du.chooseObject(objNum));
            }
final int zSz = du.getNextObject().getZsize();
            gl.glTranslatef(2.5f, -7, 5-zSz/2);
            gl.glPushMatrix();
            gl.glRotatef(rad, 0.0f, 0.0f, 1);
            rad = (rad + 2) % 360;
            printCurrentObject(gl,du);
            gl.glPopMatrix();
        }
    }

    /**
     * 다음 블럭을 모양을 렌더링합니다
     * @param gl
     * @param who
     */
    private void printCurrentObject(GL10 gl,User who){
        gl.glPushMatrix();


        gl.glTranslatef(-(float) GameStatus.getPlayers().get(0).getNextObject().getXsize()/2,
                -(float)GameStatus.getPlayers().get(0).getNextObject().getYsize()/2,0);//GameStatus.getCurrentObjectX(), GameStatus.getCurrentObjectY(), GameStatus.getCurrentObjectZ());

        GameStatus.getPlayers().get(0).getNextObject().draw(gl);

        gl.glPopMatrix();
    }
}
