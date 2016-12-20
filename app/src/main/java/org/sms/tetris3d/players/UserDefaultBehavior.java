package org.sms.tetris3d.players;

/**
 * Created by hsh on 2016. 11. 16..
 */

/**
 * 사용자 객체가 기본적으로 갖추어야 할 기본적인 메소드들을 정의해논 헤더
 *
 */
 interface UserDefaultBehavior {
 /**
  * 오른쪽으로 드래그헀을때
  */
 public void onSwipeRight();
 /**
  * 왼쪽으로 드래그헀을때
  */
 public void onSwipeLeft() ;

 /**
  * 위쪽으로 드래그헀을때
  */
 public void onSwipeTop() ;
 /**
  * 아래쪽으로 드래그헀을때
  */
 public void onSwipeBottom() ;

 /**
  * 기준점을 기준으로 x축으로 도형이동
  * @param opposite
     */
 public void moveX(boolean opposite);

 /**
  * 기준점을 기준으로 y축으로 도형이동
  * @param opposite
  */
 public void moveY(boolean opposite);

 /**
  * 도형을 드래그해서 이동
  * @param fix
     * @return 성공유무
     */
 public boolean swipeBlock(boolean fix);

 /**
  * 기준점을 기준으로 도형화전
  * @param axis
     */
 public void rotate(int axis);

 /**
  * 현재블럭이 떨어지도록하는 메소드
  * @return
     */
 public  boolean setCurrentObjectPositionDown();
}
