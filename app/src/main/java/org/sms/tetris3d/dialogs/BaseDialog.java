package org.sms.tetris3d.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.*;
/**
 * 디버깅용 메세지 박스
 *
 * @author 이민수
 */

/**
 * Created by hsh on 2016. 11. 20..
 */

public class BaseDialog extends AlertDialog {
    public BaseDialog(Context ctx){
        super(ctx);
    }
    public BaseDialog(Context ctx,int themeResId){
        super(ctx,themeResId);
    }
    public void setTopLayout(int resId){
        setContentView(resId);
    }
    public void addListView(int resId){

        ViewGroup layout = (ViewGroup)findViewById(resId);//top layout
        layout.addView(new ListView(getContext()));
    }

}
