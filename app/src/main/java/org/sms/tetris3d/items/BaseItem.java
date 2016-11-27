package org.sms.tetris3d.items;

import android.graphics.drawable.Drawable;

/**
 * Created by hsh on 2016. 11. 20..
 */

public abstract class BaseItem{
    protected ItemListener mListener = null;
    public BaseItem setItemListener(ItemListener listener){
        mListener = listener;
        return this;
    }
    public ItemListener getListener(){
        return mListener;
    }
    //item
    protected Drawable mIcon = null;
    private long coolTime=0;
    protected long mId = -1;
    private long itemCount = 0;

    protected BaseItem(long id){
        mId = id;
    }
    protected BaseItem(long id,Drawable mIcon){
        this(id);
        setItemIcon(mIcon);
    }
    public long getItemCount(){
        return itemCount;
    }
    public boolean isSupportedCoolTime(){
        return false;
    }
    public long getItemID(){
        return mId;
    }

    public long getCoolTime(){
        return coolTime;
    }
    protected BaseItem setItemIcon(Drawable icon){
        mIcon = icon;
        return this;
    }
    public Drawable getItemIcon(){
        return mIcon;
    }
    public BaseItem setCoolTime(long coolT){
        coolTime = coolT;
        return this;
    }


}
