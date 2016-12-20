package org.sms.tetris3d.items;

/**
 * Created by hsh on 2016. 12. 17..
 */

/**
 * 아이템 객체를 getItemById로 가져올때 부족한 매개변수를 매꿔주는 클래스
 *
 * @version 1.01
 *
 * @author 황세현
 *
 */
public  class ArgumentAdapter implements java.io.Serializable{
    public int removeCnt=0;
    public int offsetHeight=0;
}
