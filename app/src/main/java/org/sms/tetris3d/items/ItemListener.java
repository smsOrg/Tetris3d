package org.sms.tetris3d.items;

/**
 * Created by hsh on 2016. 11. 20..
 */

 interface ItemListener {
    public void onActiveItem();
    public long getItemCount();
    public boolean isSupportCoolTime();
    public void onCoolItem();
    public long getCoolTime();
   public void onRemoveOneItem();
   public void onRemoveItems(int count);
   public void onAddItems(int count);
   public void onAddItem();

}
