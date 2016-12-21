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

/**
 * 게임시작 선택메뉴의 자식 뷰들을 배열로 가지고있는 클래스
 *
 * @version 1.0
 *
 * @author  황세현
 */
public class SimpleAdapter extends BaseAdapter {
    /**
     * 내부 자식 뷰가 클릭됬을때 발생하는 이벤트
     * @version 1.2
     *
     * @author  황세현
     */
    public static interface OnClickListener{
        boolean onClick(View v,DialogPlus dp,Object arg);
    }

    /**
     * xml에서 레이아웃 파싱을 도와주는 객체
     */
    private LayoutInflater layoutInflater;
    /**
     * 자식 뷰에 설정될 데이터들이 저장되어있는 가변 배열 변수
     */
    private ArrayList<Object[]> mList;
    /**
     * app과 상호작용을 위한 변수
     */
    private Context mContext;
    public Context getContext(){
       return  mContext;
    }

    /**
     * 기본적인 생성자
     * @param context
     * @param list
     */
    public SimpleAdapter(Context context, ArrayList<Object[]> list) {
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
        mList = list;
    }

    /**
     * 내부 자식의 자식리스트뷰를 구현하기위한 변수지만 지금은 쓰이지 않음
     *
     */
    public DialogPlus dp;

    /**
     * 지금은 쓰이지 않는 함수
     * @return dp
     */
    public DialogPlus getDialogPlus(){
        return dp;
    }

    /**
     * 지금은 쓰이지 않는 함수
     * @param mDP
     * @return 현재객체
     */
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

    /**
     * 자식 뷰를 설정하고 생성하는 함수
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
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

    /**
     * 뷰의 자식데이터를 임시로 가져오기위한 클래스
     */
    class ViewHolder {
        TextView textView;
        ImageView imageView;
        FancyButton img_button;
    }
}