package org.sms.tetris3d.views;

/**
 * Created by hsh on 2016. 11. 27..
 */
import android.content.*;
import android.graphics.drawable.Drawable;
import android.util.*;
import android.view.*;

import org.sms.tetris3d.drawables.EmptyItemDrawable;
import org.sms.tetris3d.items.*;
import org.sms.tetris3d.players.User;

/**
 *
 * 아이템이 아무것도 설정되지 않은 아이템 뷰
 *
 * @version 1.1
 *
 * @author 황세현
 *
 */
public class EmptyItemView extends ItemView {
    /**
     * 객체생성과 동시에 데이터를 설정합니다
     * @param ctx
     * @param autoSet
     */
    public EmptyItemView(Context ctx,boolean autoSet){
        super(ctx);
        if(autoSet) {
            applyData(new EmptyItem(-1,new EmptyItemDrawable()));
        }
    }

    /**
     * 아이템 객체의 생성자의 접근 수준을 재설정합니다
     */

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
