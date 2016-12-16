package org.sms.tetris3d.items;

import android.graphics.drawable.Drawable;

/**
 * Created by hsh on 2016. 11. 20..
 */

public abstract class BaseItem{
    public static final long DEFAULT_COOL_REFRESH_TIME_MILLIS =500;
    private long crt = DEFAULT_COOL_REFRESH_TIME_MILLIS;
    public BaseItem setCRT(long val){
        crt=val;
        return this;
    }
    public long getCRT(){
        return crt;
    }
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
    private int icon_res_id=0;

    protected BaseItem(long id){
        mId = id;
    }
    protected BaseItem(long id,Drawable mIcon){
        this(id);
        setItemIcon(mIcon);
    }
    protected BaseItem(long id,int drawable_res_id){
        this(id);
        setIconResourceId(drawable_res_id);
    }
    public int getIconResourceId(){
        return icon_res_id;
    }
    public BaseItem setIconResourceId(int resId){
        icon_res_id=resId;
        return this;
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
