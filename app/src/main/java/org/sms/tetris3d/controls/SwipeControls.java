package org.sms.tetris3d.controls;

import org.sms.tetris3d.GameStatus;
import org.sms.tetris3d.controls.MovePanelAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnTouchListener;
/**
 * 화면을 드래그같은 모션을 이용해 도형을 이동시키고 카메라 각도를 조절하고 확대해주는 클래스
 *
 * @version 1.1
 *
 * @author 황세현
 *
 */
public class SwipeControls implements OnTouchListener {

	public SwipeControls(Context _c) {
	}

	/**
	 * 화면을 우측으로 이동했을때에 동작하는 함수
	 */
	public void onSwipeRight() {
		//Log.d("Kruno", "" + isMultiTouch);
		GameStatus.getPlayers().get(0).setCurrentXPositionPos();
	}
	/**
	 * 화면을 좌측으로 이동했을때에 동작하는 함수
	 */
	public void onSwipeLeft() {
		//Log.d("Kruno", "" + isMultiTouch);
		GameStatus.getPlayers().get(0).setCurrentXPositionNeg();
	}
	/**
	 * 화면을 위로 이동했을때에 동작하는 함수
	 */
	public void onSwipeTop() {
		//Log.d("Kruno", "" + isMultiTouch);
		GameStatus.getPlayers().get(0).setCurrentYPositionPos();
	}
	/**
	 * 화면을 아래로 이동했을때에 동작하는 함수
	 */
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

	enum ZOOM_STATE{Zoom,None,Freeze_Screen}

	enum SINGLE_FINGER_MODE{UNKNOWN,DRAG,MOVE_BLOCK}

	SINGLE_FINGER_MODE sfm = SINGLE_FINGER_MODE.UNKNOWN;

	ZOOM_STATE zs = ZOOM_STATE.None;

	private  MovePanelAdapter mpa = null;

	MotionEvent tmpevent = null;

	/**
	 * 좌표를 이용해 터치하고 있는 손가락 사이의 길이를 가져오는 함수
	 *
	 * @param event
	 * @return 손가락과 손가락 사이의 길이
     */
	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return (float)Math.sqrt(x * x + y * y);
	}
	float oldDist = 1f;
	float newDist = 1f;

	private boolean isMovingBlock = false;


	/**
	 * 모션유형을 검사해 도형을 이동시키거나 카메라를 회전시켜주게하는 함수
	 *
	 * @param v
	 * @param event
     * @return 활성화 비활성화
     */
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {

			if (vt == null) {
				vt = VelocityTracker.obtain();
			}

			vt.addMovement(event);

		int action = event.getAction() & MotionEvent.ACTION_MASK;

		switch (action) {
			case MotionEvent.ACTION_DOWN: {

				// Log.d("Kruno", "Action Down1");
				isMovingBlock = false;
				sfm = SINGLE_FINGER_MODE.UNKNOWN;
				if(GameStatus.isSupportCameraDrag()) {
					x1 = event.getX(0);
					y1 = event.getY(0);
				}else {
					x1 = event.getX();
					y1 = event.getY();
				}
				fingersCount = event.getPointerCount();
				time = System.currentTimeMillis();
				break;
			}


			case MotionEvent.ACTION_POINTER_DOWN: {
				// Log.d("Kruno", "Pointer Down");
				fingersCount = event.getPointerCount();
				if (fingersCount == 3) {
					/*if(zs!=ZOOM_STATE.Freeze_Screen) {
						zs = ZOOM_STATE.Freeze_Screen;
					}else{
						zs = ZOOM_STATE.Zoom;
					}*/
				}
				else if(fingersCount==2){
				zs = ZOOM_STATE.Zoom;
				newDist = spacing(event);
				oldDist = spacing(event);}
				isMultiTouch = true;

					//GameStatus.getPlayers().get(0).getCurrentObject().rotate('z');
				break;
			}
			case MotionEvent.ACTION_POINTER_UP: {
				// Log.d("Kruno", "Pointer up");

				fingersCount = event.getPointerCount();
			if(zs!=ZOOM_STATE.Freeze_Screen) {
					zs = ZOOM_STATE.None;
				}

				break;
			}
			case MotionEvent.ACTION_UP: {
				isMovingBlock=false;
				sfm=SINGLE_FINGER_MODE.UNKNOWN;
				if(zs!=ZOOM_STATE.Freeze_Screen)
				zs=ZOOM_STATE.None;
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
				if(!GameStatus.isSupportCameraDrag()) {
					x2 = event.getX();
					y2 = event.getY();

					move(x1, y1, x2, y2, fingersCount);
				}

				isMultiTouch = false;
				fingersCount = 0;
				break;
			}
			case MotionEvent.ACTION_MOVE: {
				if(GameStatus.isSupportCameraDrag()) {

					if(zs==ZOOM_STATE.None){
						final int maxVelocity = 3000;
						vt.computeCurrentVelocity(1000, maxVelocity);

						float vx = -1 * vt.getXVelocity();
						float vy = vt.getYVelocity();
						float vv = (float) Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2));

						if(vv>(maxVelocity/4.0f)*3 &&sfm!=SINGLE_FINGER_MODE.DRAG){
							if(mpa==null) {
								mpa = new MovePanelAdapter();
							}
							if(!isMovingBlock) {
								sfm=SINGLE_FINGER_MODE.MOVE_BLOCK;
								if (Math.abs(vy) > Math.abs(vx)) {
									if (vy > 0) {
										mpa.move_bottom(GameStatus.getPlayers().get(0));
									} else {
										mpa.move_top(GameStatus.getPlayers().get(0));
									}

								} else {
									if (vx > 0) {
										mpa.move_left(GameStatus.getPlayers().get(0));
									} else {
										mpa.move_right(GameStatus.getPlayers().get(0));
									}
								}
								isMovingBlock=true;
							}

						}
						else if(sfm!=SINGLE_FINGER_MODE.MOVE_BLOCK){
							sfm=SINGLE_FINGER_MODE.DRAG;
							final float deltaXAngle = vx / (float)(maxVelocity*0.7);//vv / vx;
							final float deltaYAngle = vy / (3*2/maxVelocity+1+maxVelocity/2);//(vv) / (vy * (float) Math.sqrt(vv));
							if (!Float.isNaN(deltaXAngle)) {
								GameStatus.setCameraR((GameStatus.getCameraR() + deltaXAngle) % 360);
							}
							if (!Float.isNaN(deltaYAngle)) {
								//GameStatus.setCameraHR((GameStatus.getCameraHR() + deltaYAngle) % 360);
								GameStatus.setCameraH(GameStatus.getCameraH() + deltaYAngle);
							}
						}
					}
					else if(zs==ZOOM_STATE.Zoom){
						vt.computeCurrentVelocity(1100, 2);
						float yfirst = vt.getYVelocity(0);
						float ySecond = vt.getYVelocity(1);

						if(yfirst<0&&ySecond<0){
							GameStatus.setPivotZ(-Math.min(yfirst,ySecond)/5.2f+GameStatus.getPivotZ());
						}
						else if(yfirst>0&&ySecond>0){
							GameStatus.setPivotZ(-Math.max(yfirst,ySecond)/5.2f+GameStatus.getPivotZ());
						}else {
							newDist = spacing(event);
							if (newDist - oldDist > 20) { // zoom in

							} else { // zoom out

							}

							final float newscale = GameStatus.getCircleSize() - (newDist - oldDist) / (GameStatus.getCircleSize() + 1);//+magnify(x1, y1, x2, y2, Math.max( v.getMeasuredHeight(),v.getMeasuredWidth()));
							GameStatus.setCircleSize(newscale);
							oldDist = newDist;
						}
					}
				}

				// Log.d("Kruno", "Action Move");
				break;
			}
		}
		return true;
	}

	/**
	 * 손가락으로 확대한 정도의 값을 가져오는 함수
	 *
	 * @param xFirst
	 * @param yFirst
	 * @param xSecond
	 * @param ySecond
	 * @param fCount
     * @return 확대한정도
     */
	private float magnify(float xFirst, float yFirst, float xSecond, float ySecond,
						 int fCount){
	float result = 0;
		float diffY = ySecond - yFirst;
		float diffX = xSecond - xFirst;
		result=Math.min(diffX,diffY)/fCount;

		return  result;
	}

	/**
	 * 도형을 움직이는 모션을 취했을떄 도형을 이동하는 메소드
	 *
	 * @param xFirst
	 * @param yFirst
	 * @param xSecond
	 * @param ySecond
     * @param fCount
     */
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

	/**
	 *
	 * 해당하는 사분면을 가져오는 함수
	 *
	 * @param xFirst
	 * @param yFirst
	 * @param xSecond
	 * @param ySecond
     * @return 임시 사분면 데이터
     */
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