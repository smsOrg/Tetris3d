package com.trippleit.android.tetris3d.shapes;

import javax.microedition.khronos.opengles.GL10;
import org.sms.tetris3d.players.User;
public interface IShape {
	public User getUser();
	public void setUser(User usr);
	public void draw(GL10 gl);
	public void drawLineBone(GL10 gl);
	public boolean[][][] getObjectMatrix();
	public String getColor();
	public int getXsize();
	public int getYsize();
	public int getZsize();
	public void rotate(int axis);
}
