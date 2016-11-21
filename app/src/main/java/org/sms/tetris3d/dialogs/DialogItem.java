package org.sms.tetris3d.dialogs;

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
