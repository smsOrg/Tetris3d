package org.sms.tetris3d.views;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.dialogplus.DialogPlus;

import org.sms.tetris3d.R;
import org.sms.tetris3d.ScrollingActivity;

import java.util.ArrayList;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by hsh on 2016. 12. 20..
 */

public class SimpleAdapter extends BaseAdapter {
    public static interface OnClickListener{
        boolean onClick(View v,DialogPlus dp,Object arg);
    }
    private LayoutInflater layoutInflater;
    private ArrayList<Object[]> mList;
    private Context mContext;
    public Context getContext(){
       return  mContext;
    }
    public SimpleAdapter(Context context, ArrayList<Object[]> list) {
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
        mList = list;
    }
    public DialogPlus dp;
    public DialogPlus getDialogPlus(){
        return dp;
    }
    public SimpleAdapter setDialogPlus(DialogPlus mDP){
        dp=mDP;
        return this;
    }

    @Override
    public int getCount() {
        return mList!=null?mList.size():0;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FancyButton view = (FancyButton)convertView;

        if (view == null) {
            view = (FancyButton) layoutInflater.inflate(R.layout.simple_list_item, parent, false);
        }

        Context context = parent.getContext();
        Object[] obj =(Object[]) getItem(position);
        final String title =  (String)obj[0];
        view.setText(title);

        if(title.equals("Close")||title.equals("close")||title.equals("Back")||title.equals("back")){
            android.util.Log.e("str: ",String.format("%s pos: %d",title,position));
            view.setBackgroundColor(Color.argb(0xff,0xff,0x5f,0x69));
        }
        view.setClickable(false);
        view.setFocusable(false);
        view.setFocusableInTouchMode(false);
            /*if(obj.length>1&&obj[1]!=null && obj[1] instanceof View.OnClickListener ){
                view.setOnClickListener((View.OnClickListener) obj[1]);
            }*/
        //viewHolder.img_button.setText((String)obj[0]);
        // viewHolder.img_button.setClickable(false);
           /* if(obj.length>1&&obj[1]!=null){
                viewHolder.img_button.setOnClickListener((View.OnClickListener) obj[1]);
            }*/

        return view;
    }

    class ViewHolder {
        TextView textView;
        ImageView imageView;
        FancyButton img_button;
    }
}