package com.trippleit.android.tetris3d.shapes;

import com.trippleit.android.tetris3d.users.User;

import javax.microedition.khronos.opengles.GL10;

public class ObjectL extends AbstractDraw implements IShape {

	String color = "#1D1DE7";

	boolean objectMatrix[][][];

	public ObjectL(User usr) {
		objectMatrix = createFalsMatrix(3);
		objectMatrix[0][0][0] = true;
		objectMatrix[0][0][1] = true;
		objectMatrix[0][0][2] = true;
		objectMatrix[1][0][0] = true;
		setUser(usr);
	}

	@Override
	public void draw(GL10 gl) {
		drawObject(gl, objectMatrix, color);
		/*gl.glPushMatrix();
		Cube c = new Cube(color);
		gl.glTranslatef(1, 0, 0);
		c.draw(gl);
		gl.glTranslatef(-1, 0, 0);
		c.draw(gl);
		gl.glTranslatef(0, 0, 1);
		c.draw(gl);
		gl.glTranslatef(0, 0, 1);
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

