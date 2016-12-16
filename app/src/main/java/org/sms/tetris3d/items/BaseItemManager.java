package org.sms.tetris3d.items;

/**
 * Created by hsh on 2016. 11. 20..
 */
import java.util.*;
public abstract class BaseItemManager extends ArrayList<BaseItem>{
    @Override
    public boolean add(BaseItem bi){
        if(bi!=null){
            return super.add(bi);
        }
        return false;
    }
    @Override
    public void add(int idx,BaseItem bi){
        if(bi!=null){
            super.add(idx,bi);
        }
    }
}
