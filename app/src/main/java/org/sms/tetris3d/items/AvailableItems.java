package org.sms.tetris3d.items;

import android.graphics.drawable.Drawable;

import org.sms.tetris3d.GameStatus;
import org.sms.tetris3d.players.*;
/**
 * Created by hsh on 2016. 11. 24..
 */

public class AvailableItems {
    public static BaseItem getPositionResetItem(){
        BaseItem item = new BaseItem(0){
            @Override
            public void onActiveItem(User usr) {
                usr.setCurrentPosition(GameStatus.getStartX(),GameStatus.getStartY(),GameStatus.getGameHeight());
            }
            @Override
            public long getItemCount() {
                return 0;
            }

            @Override
            public boolean isSupportedCoolTime() {
                return true;
            }

            @Override
            public void onCoolItem(boolean isCool) {

            }
            @Override
            public void onRemoveItem(User usr) {

            }

            @Override
            public void onAddItem(User usr) {

            }
            @Override
            public Drawable getItemIcon() {
                return null;
            }
        };


        return item;
    }
}
