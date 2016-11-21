package org.sms.tetris3d.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.*;
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
