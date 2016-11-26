package org.sms.tetris3d.items;

import android.graphics.drawable.Drawable;
import org.sms.tetris3d.players.*;
/**
 * Created by hsh on 2016. 11. 20..
 */

 interface ItemListener {
    public void onActiveItem(User usr);
    public long getItemCount();
    public long getItemID();
    public boolean isSupportedCoolTime();
    public void onCoolItem(boolean isCool);
    public long getCoolTime();
   public void onRemoveItem(User usr);
   public void onAddItem(User usr);
   public Drawable getItemIcon();

}
