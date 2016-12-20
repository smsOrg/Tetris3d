package org.sms.tetris3d.dialogs;

/**
 * Created by hsh on 2016. 12. 21..
 */
/**
 * 세이브 데이터를 저장하기전에 데이터가 어떤이름으로 저장될 것인지를 선택할 수 있게한다
 *
 * @author 황세현
 */

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.SwipeDismissBehavior;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.*;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.view.*;

import org.sms.tetris3d.*;
public class SavePointNameInputDialog extends AlertDialog implements TextWatcher,DialogInterface.OnShowListener{
    TextInputLayout til;
    TextInputEditText tiet ;
   // private DataContainer dc;
    private OnApplyListener oal;
    public SavePointNameInputDialog(@NonNull Context context,OnApplyListener dc) {
        super(context,android.support.design.R.style.Base_Theme_AppCompat_Light_Dialog_Alert);
        oal = dc;

        //setView();
        View tlayout =((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.sp_name_input_layout,null);
        setView(tlayout);
        //setContentView(R.layout.sp_name_input_layout);
        setOnShowListener(this);
        til = (TextInputLayout)tlayout.findViewById(R.id.til_2);
        tiet = (TextInputEditText)tlayout.findViewById(R.id.et_2);
        tiet.addTextChangedListener(this);
        til.setCounterEnabled(true);
        til.setCounterMaxLength(20);

        //setOnDismissListener(this);
        setCancelable(false);
        setButton(BUTTON_NEGATIVE,context.getString(android.R.string.cancel),(OnClickListener)null);
        setButton(BUTTON_POSITIVE, context.getString(R.string.sp_name_save_btn), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(oal!=null){
                    oal.onApply(tiet.getText().toString().trim());
                }
            }
        });

        //getButton(BUTTON_POSITIVE).setEnabled(false);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    /**
     * 이름의 길이를 구해 이름으로 사용가능한 이름인지 확인한다
     * @param s
     */
    @Override
    public void afterTextChanged(Editable s) {
        String str = s.toString();
        str=str.trim();
        if(str.length()<1)
        {
            til.setError(getContext().getString(R.string.sp_name_input_error));
            android.widget.Button btn = getButton(BUTTON_POSITIVE);
            if(btn!=null){
                btn.setEnabled(false);
            }
        }
        else
        {
            til.setError(null);
            android.widget.Button btn = getButton(BUTTON_POSITIVE);
            if(btn!=null){
                btn.setEnabled(true);
            }
        }
    }

    /**
     *  이름이 공백일수는 없으므로 초기화시 적용버튼을 누르지 못하게한다
     * @param dialog
     */
    @Override
    public void onShow(DialogInterface dialog) {
        if(getButton(BUTTON_POSITIVE)!=null)
        getButton(BUTTON_POSITIVE).setEnabled(false);
    }

    public static interface OnApplyListener{
        public void onApply(String name);
    }
}
