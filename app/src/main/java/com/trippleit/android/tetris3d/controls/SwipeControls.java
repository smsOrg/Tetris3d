package com.trippleit.android.tetris3d.controls;

import org.sms.tetris3d.GameStatus;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnTouchListener;

public class SwipeControls implements OnTouchListener {

	public SwipeControls(Context _c) {
	}

	public void onSwipeRight() {
		//Log.d("Kruno", "" + isMultiTouch);
		GameStatus.getPlayers().get(0).setCurrentXPositionPos();
	}

	public void onSwipeLeft() {
		//Log.d("Kruno", "" + isMultiTouch);
		GameStatus.getPlayers().get(0).setCurrentXPositionNeg();
	}

	public void onSwipeTop() {
		//Log.d("Kruno", "" + isMultiTouch);
		GameStatus.getPlayers().get(0).setCurrentYPositionPos();
	}

	public void onSwipeBottom() {
		//Log.d("Kruno", "" + isMultiTouch);
		GameStatus.getPlayers().get(0).setCurrentYPositionNeg();
	}

	private boolean isMultiTouch = false;
	private Integer fingersCount = 0;
	private float x1 = 0, y1 = 0;
	private float x2 = 0, y2 = 0;
	private long time = 0;
	private  VelocityTracker vt =null;
	MotionEvent tmpevent = null;
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(vt==null){
			vt = VelocityTracker.obtain();
		}

			vt.addMovement(event);

		int action = event.getAction() & MotionEvent.ACTION_MASK;

		switch (action) {
			case MotionEvent.ACTION_DOWN: {

				// Log.d("Kruno", "Action Down1");
				x1 = event.getX();
				y1 = event.getY();
				fingersCount = event.getPointerCount();
				time = System.currentTimeMillis();
				break;
			}
			case MotionEvent.ACTION_MOVE: {
				if(GameStatus.isSupportCameraDrag()) {


					vt.computeCurrentVelocity(100, 3);
					float vx = -1 * vt.getXVelocity();
					float vy = vt.getYVelocity();
					float vv = (float) Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2));
					final float deltaXAngle = vv / vx;
					final float deltaYAngle = (vv) / (vy * (float) Math.sqrt(vv));
					if (!Float.isNaN(deltaXAngle)) {
						GameStatus.setCameraR((GameStatus.getCameraR() + deltaXAngle) % 360);
					}
					android.util.Log.e("tracking", GameStatus.getCameraR() + "");
					if (!Float.isNaN(deltaYAngle)) {
						GameStatus.setCameraH(GameStatus.getCameraH() + deltaYAngle);
					}
				}

				// Log.d("Kruno", "Action Move");
				break;
			}

			case MotionEvent.ACTION_POINTER_DOWN: {
				// Log.d("Kruno", "Pointer Down");
				isMultiTouch = true;
				fingersCount = event.getPointerCount();
				if (fingersCount == 3)
					GameStatus.getPlayers().get(0).getCurrentObject().rotate('z');
				break;
			}
			case MotionEvent.ACTION_POINTER_UP: {
				// Log.d("Kruno", "Pointer up");
				// fingersCount = event.getPointerCount();
				break;
			}
			case MotionEvent.ACTION_UP: {
				//Log.d("RG", "diffX: " + ((System.currentTimeMillis() - time)<90));
				if(vt!=null) {
					vt.recycle();
					vt=null;
					tmpevent=null;
				}
				if((System.currentTimeMillis() - time)<90){
					GameStatus.setDropFast();
					return true;
				}				
				// Log.d("Kruno", "Action up1");
				x2 = event.getX();
				y2 = event.getY();
				move(x1, y1, x2, y2, fingersCount);
				isMultiTouch = false;
				fingersCount = 0;
				break;
			}
		}
		return true;
	}

	private void move(float xFirst, float yFirst, float xSecond, float ySecond,
			int fCount) {
		switch (fCount) {
			case 1:
				switch (detectDirection(xFirst, yFirst, xSecond, ySecond)) {
					case 1:
						onSwipeRight();
						break;
					case 2:
						onSwipeLeft();
						break;
					case 3:
						onSwipeBottom();
						break;
					case 4:
						onSwipeTop();
						break;
				}
				break;
			case 2:
				switch (detectDirection(xFirst, yFirst, xSecond, ySecond)) {
					case 1:
						GameStatus.getPlayers().get(0).getCurrentObject().rotate('x');
						break;
					case 2:
						GameStatus.getPlayers().get(0).getCurrentObject().rotate('x');
						break;
					case 3:
						GameStatus.getPlayers().get(0).getCurrentObject().rotate('y');
						break;
					case 4:
						GameStatus.getPlayers().get(0).getCurrentObject().rotate('y');
						break;
				}
				break;
		}
	}

	public int detectDirection(float xFirst, float yFirst, float xSecond,
			float ySecond) {
		int rez = 0;
		int limit = 50;
		try {

			float diffY = ySecond - yFirst;
			float diffX = xSecond - xFirst;
			if (Math.abs(diffX) > Math.abs(diffY)) {
				if (Math.abs(diffX) < limit)
					return 0;
				if (diffX > 0) {
					return 1;
				} else {
					return 2;
				}
			} else {
				if (Math.abs(diffY) < limit)
					return 0;
				if (diffY > 0) {
					return 3;
				} else {
					return 4;
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return rez;
	}

}