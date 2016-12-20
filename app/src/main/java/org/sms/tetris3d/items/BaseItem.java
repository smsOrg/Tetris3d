package org.sms.tetris3d.items;

import android.graphics.drawable.Drawable;

import java.io.Serializable;
/**
 * 아이템의 정보를 담는 객체클래스
 *
 * @version 1.3
 *
 * @author 황세현
 *
 */

/**
 * Created by hsh on 2016. 11. 20..
 */

public abstract class BaseItem implements Serializable{
    public static final long DEFAULT_COOL_REFRESH_TIME_MILLIS =500;
    private long crt = DEFAULT_COOL_REFRESH_TIME_MILLIS;

    /**
     *  쿨타임 이미지의 새로고침 주기를 설정합니다
     * @param val
     * @return 아이템 객체
     */
    public BaseItem setCRT(long val){
        crt=val;
        return this;
    }

    /**
     *  쿨타임의 새로고침 주기를 가져옵니다
     * @return crt
     */
    public long getCRT(){
        return crt;
    }
    protected ItemListener mListener = null;

    /**
     *
     * @param listener
     * @return 아이템 객체
     */
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

    /**
     * 객체를 셍성한뒤 아이디를 설정합니다
     *
     * @param id
     */
    protected BaseItem(long id){
        mId = id;
    }

    /**
     * 객체를 설정한뒤 아이디도 설정하면서 이미지도 설정합니다
     * @param id
     * @param mIcon
     */
    protected BaseItem(long id,Drawable mIcon){
        this(id);
        setItemIcon(mIcon);
    }
    /**
     * 객체를 설정한뒤 아이디도 설정하면서 이미지의 리소스아이디를 설정합니다
     * @param id
     * @param drawable_res_id
     */
    protected BaseItem(long id,int drawable_res_id){
        this(id);
        setIconResourceId(drawable_res_id);
    }

    /**
     * 아이콘 리소스아이디를 가져옵니다
     *
     *
     * @return icon_res_id
     */
    public int getIconResourceId(){
        return icon_res_id;
    }

    /**
     * 아이콘 리소스아이디를 설정합니다
     * @param resId
     * @return 아이템 객체
     */
    public BaseItem setIconResourceId(int resId){
        icon_res_id=resId;
        return this;
    }

    /**
     *  아이템의 갯수를 설정합니다
     *  현재는 지원하지 않는 기능입니다
     *
     * @return 갯수
     */
    public long getItemCount(){
        return itemCount;
    }

    /**
     * 해당 아이템이 쿨타임을 지원하는지 가져옵니다
     *
     * @return 쿨타임지원?
     */
    public boolean isSupportedCoolTime(){
        return false;
    }

    /**
     * 아이템의 고유 아이디를 가져옵나다
     *
     * @return 아이디
     */
    public long getItemID(){
        return mId;
    }

    /**
     * 쿨타임이 지원할때 쿨타임시간을 거져옵니다
     * @return coolTime
     */
    public long getCoolTime(){
        return coolTime;
    }

    /**
     *  아이템의 이미지를 설정합니다
     * @param icon
     * @return 아이템 객체
     */
    protected BaseItem setItemIcon(Drawable icon){
        mIcon = icon;
        return this;
    }

    /**
     * 아이템의 이미지를 가져옵니다
     * @return 이미지 객체
     */
    public Drawable getItemIcon(){
        return mIcon;
    }

    /**
     * 쿨타임 시간을 설정합니다
     * @param coolT
     * @return
     */
    public BaseItem setCoolTime(long coolT){
        coolTime = coolT;
        return this;
    }


}
