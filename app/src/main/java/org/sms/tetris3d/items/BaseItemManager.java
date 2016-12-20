package org.sms.tetris3d.items;


/**
 * 아이템 객체를 관리하는 클래스
 *
 * @version 1.0
 *
 * @author 황세현
 *
 */
/**
 * Created by hsh on 2016. 11. 20..
 */
import java.util.*;
public abstract class BaseItemManager extends ArrayList<BaseItem>{
    /**
     *  저장하려는 객체가 null이 아닐떄만 저장합니다
     * @param bi
     * @return 성공함의 유무
     */
    @Override
    public boolean add(BaseItem bi){
        if(bi!=null){
            return super.add(bi);
        }
        return false;
    }

    /**
     * 저장하려는 객체가 null이 아닐때만 저장합니다
     *
     * @param idx
     * @param bi
     */
    @Override
    public void add(int idx,BaseItem bi){
        if(bi!=null){
            super.add(idx,bi);
        }
    }
}
