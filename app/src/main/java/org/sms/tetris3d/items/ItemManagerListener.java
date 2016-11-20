package org.sms.tetris3d.items;

/**
 * Created by hsh on 2016. 11. 20..
 */

public interface ItemManagerListener {
    public void onRemoveItem(BaseItem item);
    public void onAddItem(BaseItem item);
    public void onUseItem(BaseItem item);
    public void onCoolItem(BaseItem item);
}
