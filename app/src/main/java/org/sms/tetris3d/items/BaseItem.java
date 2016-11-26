package org.sms.tetris3d.items;

import android.graphics.drawable.Drawable;

/**
 * Created by hsh on 2016. 11. 20..
 */

public abstract class BaseItem implements ItemListener {
    //item
    protected Drawable mIcon = null;
    private long coolTime=0;
    protected long mId = -1;
    private long itemCount = 0;

    protected BaseItem(long id){
        mId = id;
    }
    @Override
    public long getItemID(){
        return mId;
    }
    @Override
    public long getCoolTime(){
        return coolTime;
    }
    protected BaseItem setItemIcon(Drawable icon){
        mIcon = icon;
        return this;
    }
    @Override
    public Drawable getItemIcon(){
        return mIcon;
    }
    public void setCoolTime(long coolT){
        coolTime = coolT;
    }
}
