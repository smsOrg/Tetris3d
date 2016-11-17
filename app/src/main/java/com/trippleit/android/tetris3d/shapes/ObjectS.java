package com.trippleit.android.tetris3d.shapes;

import org.sms.tetris3d.players.User;

import javax.microedition.khronos.opengles.GL10;

public class ObjectS extends AbstractDraw implements IShape {

	String color = "#00DEDE";
	
	boolean objectMatrix[][][];

	public ObjectS(User usr) {
		objectMatrix = createFalsMatrix(3);
		objectMatrix[0][0][0] = true;
		objectMatrix[1][0][0] = true;
		objectMatrix[1][0][1] = true;
		objectMatrix[2][0][1] = true;
		setUser(usr);
	}

	@Override
	public void draw(GL10 gl) {
		drawObject(gl, objectMatrix, color);
		/*gl.glPushMatrix();		
		Cube c = new Cube(color);		
		c.draw(gl);
		gl.glTranslatef(1, 0, 0);
		c.draw(gl);
		gl.glTranslatef(0, 0, 1);
		c.draw(gl);
		gl.glTranslatef(1, 0, 0);
		c.draw(gl);		
		gl.glPopMatrix();*/
	}
	@Override
	public User getUser() {
		return usr;
	}

	@Override
	public void setUser(User usr) {
		this.usr = usr;
	}
	@Override
	public void drawLineBone(GL10 gl){
		drawObjectBone(gl,objectMatrix, color);
	}
	@Override
	public boolean[][][] getObjectMatrix() { 
		return objectMatrix;
	}

	@Override
	public String getColor() {
		return color;
	}

	@Override
	public int getXsize() {
		return getXsize(objectMatrix);
	}

	@Override
	public int getYsize() {
		return getYsize(objectMatrix);
	}
	
	@Override
	public int getZsize() {
		return getZsize(objectMatrix);
	}
	
	@Override
	public void rotate(int axis) {
		switch(axis){
			case 1:
			case 'x':
				objectMatrix = rotateX(objectMatrix,getUser()); break;
			case 2:
			case 'y':
				objectMatrix = rotateY(objectMatrix,getUser()); break;
			case 3:
			case 'z':
				objectMatrix = rotateZ(objectMatrix,getUser()); break;
		}
	}

}
