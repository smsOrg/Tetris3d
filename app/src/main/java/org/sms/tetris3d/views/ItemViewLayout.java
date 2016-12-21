package org.sms.tetris3d.views;

/**
 * Created by hsh on 2016. 11. 27..
 */
import android.widget.*;
import android.content.*;
import android.util.*;
import android.view.*;
import org.sms.tetris3d.items.*;

/**
 * 메인 게임 창에서 장착된 아이템을 렌더링하는 뷰
 */

public class ItemViewLayout extends LinearLayout {
    /**
     * 레이아웃에 보여질 수 있는 아이템의 최대 갯수
     */
    public final static int MAX_ITEM_COUNT = 3;

    /**
     * 레이아웃의 자식이 추가되는 방향을 설정
     */
    private void defaultOrientation(){
        setOrientation(LinearLayout.HORIZONTAL);
    }

    /**
     * 소스코드안에서 생성할수 있는 생성자
     * @param ctx
     */
    public ItemViewLayout(Context ctx){
        super(ctx);
        defaultOrientation();
    }

    /**
     * xml에서 객체를 파싱할 수 있는 생성자
     * @param ctx
     * @param attrs
     */
    public ItemViewLayout(Context ctx,AttributeSet attrs){
        super(ctx,attrs);
        defaultOrientation();

    }

    /**
     * xml에서 객체를 파싱할 수 있는 생성자인데 android:style 속성을 사용할 경우 사용되는 생성자
     * @param ctx
     * @param attrs
     * @param themResId
     */
    public ItemViewLayout(Context ctx,AttributeSet attrs,int themResId){
        super(ctx,attrs,themResId);
        defaultOrientation();
    }

    /**
     * 아이템 뷰가 하나가 붙여질때마다 붙여지는 방식을 설정해 아이템뷰를 추가
     * @param child
     */
    public void addItemView(ItemView child){
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,1);
        addView(child,lp);
    }

    /**
     * 아이템리스트로부터 데이터를 가져와 추가하는 메소드
     * @param list
     * @return
     */
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
