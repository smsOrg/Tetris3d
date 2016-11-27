package org.sms.tetris3d.items;

import android.graphics.drawable.Drawable;
import org.sms.tetris3d.players.*;
/**
 * Created by hsh on 2016. 11. 20..
 */

 public interface ItemListener {
    public void onActiveItem(User usr);

    public void onCoolItem(boolean isCool);
   public void onRemoveItem(User usr);
   public void onAddItem(User usr);

}
