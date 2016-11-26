package org.sms.tetris3d.items;

/**
 * Created by hsh on 2016. 11. 20..
 */
import org.sms.tetris3d.players.*;


public class ItemManagerForEachUser extends BaseItemManager implements  ItemManagerListener {
    private User innerUser = null;
    public ItemManagerForEachUser(){
        super();
    }
    public ItemManagerForEachUser(User inner){
        super();
        innerUser = inner;
    }
    public User getInnerUser(){
        return innerUser;
    }
    @Override
    public void onRemoveItem(User usr,BaseItem item) {

    }

    @Override
    public void onAddItem(User usr,BaseItem item) {

    }

    @Override
    public void onUseItem(User usr,BaseItem item) {

    }

    @Override
    public void onCoolItem(User usr,BaseItem item) {
        if(item.isSupportedCoolTime()){

        }
    }
}
