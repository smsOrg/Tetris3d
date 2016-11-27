package org.sms.tetris3d.views;

/**
 * Created by hsh on 2016. 11. 27..
 */
import android.content.*;
import android.graphics.drawable.Drawable;
import android.util.*;
import android.view.*;
import org.sms.tetris3d.items.*;
import org.sms.tetris3d.players.User;

public class EmptyItemView extends ItemView {
    public EmptyItemView(Context ctx,boolean autoSet){
        super(ctx);
        if(autoSet) {
            applyData(new EmptyItem(-1,null));
        }
    }
public static class EmptyItem extends BaseItem{
    public EmptyItem(long id, Drawable icon){
        super(id,icon);
    }


    @Override
    public long getItemCount() {
        return 0;
    }

    @Override
    public boolean isSupportedCoolTime() {
        return false;
    }


}
}
