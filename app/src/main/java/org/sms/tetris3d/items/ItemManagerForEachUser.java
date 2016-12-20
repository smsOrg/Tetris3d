package org.sms.tetris3d.items;

/**
 * Created by hsh on 2016. 11. 20..
 */
import org.sms.tetris3d.players.*;

/**
 * 유저클래스가 아이템을 지원할경우 꼭 가져야할 아이템 관리 클래스
 *
 * @version 1.3
 *
 * @author 황세현
 *
 */
public class ItemManagerForEachUser extends BaseItemManager implements  ItemManagerListener {
    private User innerUser = null;

    /**
     * 일반적인 생성자
     *
     */
    public ItemManagerForEachUser(){
        super();
    }

    /**
     * 내부에 유저객체를 설정해 내부에서 무언가 조작할때 사용합니다
     * @param inner
     */
    public ItemManagerForEachUser(User inner){
        super();
        innerUser = inner;
    }
    public User getInnerUser(){
        return innerUser;
    }
    @Override
    public void onRemoveItem(User usr,BaseItem item) {

    }

    @Override
    public void onAddItem(User usr,BaseItem item) {

    }

    @Override
    public void onUseItem(User usr,BaseItem item) {

    }

    @Override
    public void onCoolItem(User usr,BaseItem item) {
        if(item.isSupportedCoolTime()){

        }
    }
}
