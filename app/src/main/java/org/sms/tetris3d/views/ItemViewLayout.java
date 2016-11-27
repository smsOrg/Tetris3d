package org.sms.tetris3d.views;

/**
 * Created by hsh on 2016. 11. 27..
 */
import android.widget.*;
import android.content.*;
import android.util.*;
import android.view.*;
import org.sms.tetris3d.items.*;
public class ItemViewLayout extends LinearLayout {
    public final static int MAX_ITEM_COUNT = 3;
    private void defaultOrientation(){
        setOrientation(LinearLayout.HORIZONTAL);
    }
    public ItemViewLayout(Context ctx){
        super(ctx);
        defaultOrientation();
    }
    public ItemViewLayout(Context ctx,AttributeSet attrs){
        super(ctx,attrs);
        defaultOrientation();
    }
    public ItemViewLayout(Context ctx,AttributeSet attrs,int themResId){
        super(ctx,attrs,themResId);
        defaultOrientation();
    }
    public void addItemView(ItemView child){
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,1);
        addView(child,lp);
    }
    public ItemViewLayout addViewFromList(BaseItemManager list){
        int itemCount = Math.min(list.size(),MAX_ITEM_COUNT);
        for(int i =0;i<itemCount;i++){
            addItemView(new ItemView(getContext()).applyData(list.get(i)));
        }

        itemCount=MAX_ITEM_COUNT-itemCount;
        for(int i =0;i<itemCount;i++){
            addItemView(new EmptyItemView(getContext(),true));
        }
        return this;
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }
}
