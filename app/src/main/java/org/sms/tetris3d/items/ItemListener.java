package org.sms.tetris3d.items;

import android.graphics.drawable.Drawable;
import org.sms.tetris3d.players.*;

import java.io.Serializable;

/**
 * Created by hsh on 2016. 11. 20..
 */

/**
 * 아이템이 가져야할 메소드들을 정의해둔 헤더
 * 코드간소화를 위해 직렬화 헤더를 상속
 *
 * @version 1.01
 *
 * @author 황세현
 *
 */
 public interface ItemListener extends Serializable{
    public void onActiveItem(User usr);

    public void onCoolItem(boolean isCool);
   public void onRemoveItem(User usr);
   public void onAddItem(User usr);

}
