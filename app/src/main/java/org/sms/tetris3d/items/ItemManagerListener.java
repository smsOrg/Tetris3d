package org.sms.tetris3d.items;

/**
 * Created by hsh on 2016. 11. 20..
 */
import org.sms.tetris3d.players.User;
public interface ItemManagerListener {
    public void onRemoveItem(User usr ,BaseItem item);
    public void onAddItem(User usr,BaseItem item);
    public void onUseItem(User usr ,BaseItem item);
    public void onCoolItem(User usr, BaseItem item);
}
