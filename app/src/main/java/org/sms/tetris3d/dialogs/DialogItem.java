package org.sms.tetris3d.dialogs;

/**
 * 디버그용 클래스
 *
 * @version 0.1
 *
 * @author 이민수
 */

/**
 * Created by hsh on 2016. 11. 21..
 */

public class DialogItem {
    private String title="";
    public DialogItem setTitle(String t){
         title=t;
        return this;
    }
    public void onClickItem(){

    }
    @Override
    public String toString(){
     return title;
    }

}
