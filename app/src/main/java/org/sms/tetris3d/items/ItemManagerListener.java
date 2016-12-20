package org.sms.tetris3d.items;

/**
 * 아이템 관리자에게 꼭필요한 메소드들을 정의해둔 헤더
 *
 * @version 1.1
 *
 * @author 황세현
 *
 *
 */
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
