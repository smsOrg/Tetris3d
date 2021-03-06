package com.trippleit.android.tetris3d.controls;

import org.sms.tetris3d.*;
import org.sms.tetris3d.controls.MovePanelAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class ButtonControls extends Activity implements OnTouchListener {

	private Handler mHandler_up, mHandler_down, mHandler_right, mHandler_left;
private final MovePanelAdapter mpa = new MovePanelAdapter();
	public ButtonControls(Context c) {

	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {

		switch (v.getId()) {

		case R.id.btnUp:
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (mHandler_up != null)
					return true;
				mHandler_up = new Handler();
				mHandler_up.postDelayed(mAction_up, 50);
				break;
			case MotionEvent.ACTION_UP:
				if (mHandler_up == null)
					return true;
				mHandler_up.removeCallbacks(mAction_up);
				mHandler_up = null;
				break;
			}
			break;
		case R.id.btnDown:
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (mHandler_down != null)
					return true;
				mHandler_down = new Handler();
				mHandler_down.postDelayed(mAction_down, 50);
				break;
			case MotionEvent.ACTION_UP:
				if (mHandler_down == null)
					return true;
				mHandler_down.removeCallbacks(mAction_down);
				mHandler_down = null;
				break;
			}
			break;
		case R.id.btnLeft:
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (mHandler_left != null)
					return true;
				mHandler_left = new Handler();
				mHandler_left.postDelayed(mAction_left, 50);
				break;
			case MotionEvent.ACTION_UP:
				if (mHandler_left == null)
					return true;
				mHandler_left.removeCallbacks(mAction_left);
				mHandler_left = null;
				break;
			}
			break;
		case R.id.btnRight:
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (mHandler_right != null)
					return true;
				mHandler_right = new Handler();
				mHandler_right.postDelayed(mAction_right, 50);
				break;
			case MotionEvent.ACTION_UP:
				if (mHandler_right == null)
					return true;
				mHandler_right.removeCallbacks(mAction_right);
				mHandler_right = null;
				break;
			}
			break;
		}

		return false;
	}

	Runnable mAction_up = new Runnable() {
		@Override
		public void run() {
			if(GameStatus.isSupportCameraDrag()){
				mpa.move_top(GameStatus.getPlayers().get(0));
				//GameStatus.getPlayers().get(0).moveY(false);
			}else {
				GameStatus.setCameraH(GameStatus.getCameraH() + 1);mHandler_up.postDelayed(this, 50);
			}

		}
	};

	Runnable mAction_down = new Runnable() {
		@Override
		public void run() {
			if(GameStatus.isSupportCameraDrag()){
				mpa.move_bottom(GameStatus.getPlayers().get(0));
				//GameStatus.getPlayers().get(0).moveY(true);
			}else {
				GameStatus.setCameraH(GameStatus.getCameraH() - 1);mHandler_down.postDelayed(this, 50);
			}

		}
	};

	Runnable mAction_left = new Runnable() {
		@Override
		public void run() {
			if(GameStatus.isSupportCameraDrag()){
				mpa.move_left(GameStatus.getPlayers().get(0));
				//GameStatus.getPlayers().get(0).moveX(true);
			}else {
				GameStatus.setCameraR(GameStatus.getCameraR() - 1);mHandler_left.postDelayed(this, 50);
			}

		}
	};

	Runnable mAction_right = new Runnable() {
		@Override
		public void run() {
			if(GameStatus.isSupportCameraDrag()){
				//GameStatus.getPlayers().get(0).moveX(false);
				mpa.move_right(GameStatus.getPlayers().get(0));
			}else {

				GameStatus.setCameraR(GameStatus.getCameraR() + 1);mHandler_right.postDelayed(this, 50);
			}

		}
	};

}