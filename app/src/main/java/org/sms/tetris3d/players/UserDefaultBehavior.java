package org.sms.tetris3d.players;

/**
 * Created by hsh on 2016. 11. 16..
 */

 interface UserDefaultBehavior {
 public void onSwipeRight();
 public void onSwipeLeft() ;

 public void onSwipeTop() ;

 public void onSwipeBottom() ;
 public void moveX(boolean opposite);

 public void moveY(boolean opposite);

 public boolean swipeBlock(boolean fix);

 public void rotate(int axis);
 public  boolean setCurrentObjectPositionDown();
}
