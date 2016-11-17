package com.trippleit.android.tetris3d.render;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import org.sms.tetris3d.GameStatus;
import com.trippleit.android.tetris3d.shapes.Coords;
import com.trippleit.android.tetris3d.shapes.Cube;
import com.trippleit.android.tetris3d.shapes.Grid;
import org.sms.tetris3d.players.*;
import android.opengl.GLU;

public class OpenGlRenderer extends AbstractOpenGlRenderer {

	@Override
	public void onDrawFrame(GL10 gl, boolean firstDraw) {
		DeviceUser du = (DeviceUser) GameStatus.getPlayers().get(0);
		if (firstDraw) du.newShape();
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
			GLU.gluLookAt(gl, GameStatus.getCameraX(), GameStatus.getCameraY(), GameStatus.getCameraZ(), 0, 0, 0, 0, 0, 1);

			new Coords(GameStatus.getGridSize(), GameStatus.getGameHeight()).draw(gl);
			new Grid(GameStatus.getGridSize()).draw(gl);

			du.dropDown(this);
			//dropDown();
			removeFullRows();
			printAllObjects(gl);
			//printCurrentObject(gl);
			for(User usr:GameStatus.getPlayers()) {
				if (!GameStatus.isEnd()&&usr.getNextObject()!=null) {
					//printAbstractObject(gl);
					User.printAbstractObject(gl,usr);
				}
				User.printCurrentObject(gl,usr);
				//printCurrentObject(gl);
			}
		}
	}
	protected void drawStartCount(final int cnt){

	}
	protected void drawPause(GL10 gl){
		GLU.gluLookAt(gl, GameStatus.getCameraX(), GameStatus.getCameraY(), GameStatus.getCameraZ(), 0, 0, 0, 0, 0, 1);

		gl.glPushMatrix();
		Cube a = new Cube("#001000",GameStatus.getPlayers().get(0));
		a.draw(gl);
		gl.glPopMatrix();
	}

	
	private void removeFullRows() {
		GameStatus.removeFullRows();
	}
	
	private void printAllObjects(GL10 gl) {
		for (int i = 0; i < GameStatus.getGridSize(); i++)
			for (int j = 0; j < GameStatus.getGridSize(); j++)
				for (int k = 0; k < GameStatus.getGameHeight(); k++)
					if (GameStatus.getGameBoolMatrix()[i][j][k]) {
						gl.glPushMatrix();
						Cube ccc = new Cube(
								GameStatus.getGameColorMatrix()[i][j][k],GameStatus.getPlayers().get(0));
						gl.glTranslatef(i, j, k);
						ccc.draw(gl);
						gl.glPopMatrix();
					}
	}


}
