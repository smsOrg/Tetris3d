package org.sms.tetris3d;

import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.*;
import com.dexafree.materialList.view.*;
import com.dexafree.materialList.card.*;
import com.nhaarman.supertooltips.ToolTip;
import com.dexafree.materialList.listeners.RecyclerItemClickListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.DialogPlusBuilder;
import android.widget.*;
import android.view.*;

import org.sms.tetris3d.drawables.NumberDrawable;
import org.sms.tetris3d.savepoint.SavePoint;
import org.sms.tetris3d.savepoint.SavePointManager;

import java.text.SimpleDateFormat;
import java.util.*;

import mehdi.sakout.fancybuttons.FancyButton;

public class ScrollingActivity extends AppCompatActivity {
public static class LCardProvider extends com.dexafree.materialList.card.CardProvider{

}
    public class SimpleAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;
        ArrayList<Object[]> mList;
        public SimpleAdapter(Context context,ArrayList<Object[]> list) {
            layoutInflater = LayoutInflater.from(context);
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
            ViewHolder viewHolder;
            FancyButton view = (FancyButton)convertView;

            if (view == null) {

                    view = (FancyButton)layoutInflater.inflate(R.layout.simple_list_item, parent, false);

                //viewHolder = new ViewHolder();

                //viewHolder.img_button = (FancyButton) view.findViewById(R.id.text_btn);
                //view.setTag(viewHolder);
            } else {
                //viewHolder = (ViewHolder) view.getTag();
            }

            Context context = parent.getContext();
            Object[] obj =(Object[]) getItem(position);
            view.setText((String)obj[0]);
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
    interface OnClickListener{
        boolean onClick(View v,DialogPlus dp,Object arg);
    }
    private void startGameActivity(){
        Intent it = new Intent();
        it.setClass(getApplicationContext(),MainGameActivity.class);
        it.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT|Intent.FLAG_ACTIVITY_NEW_TASK);
        it.putExtra("save_point_mode",false);
        it.putExtra("check",(long)(('s'+'m'+'s')<<10)^'s');
        getApplicationContext().startActivity(it);
    }
private SavePointManager spm=null;
    public ArrayList<Object[]> getSavePointsFromDB(){
        ArrayList<Object[]> rst = new ArrayList<Object[]>();
        if(spm==null){
            spm = new SavePointManager(this);
        }
        Cursor query = spm.getSavePointData(spm.getDataBase(false));
        int cnt = query.getCount();
        if(cnt>0){
            boolean first = true;
            SavePoint sp=null;
            while(query.moveToNext()){
                if(first){
                    query.moveToFirst();
                    first = false;
                }

                String title = query.getString(1);
                byte[] raw_data = query.getBlob(4);
                String configStr = query.getString(2);
                long time_stamp = query.getLong(3);

                if(raw_data!=null) {
                    sp = SavePoint.createSavePointFromByteArray(raw_data);

                    if(sp!=null){
                       rst.add(new Object[]{title+String.format(" (%s)",new SimpleDateFormat("yy.M.d").format(new Date(time_stamp))),
                    new OnClickListener() {
                           @Override
                           public boolean onClick(View v,DialogPlus dp,Object arg) {
                                if(arg!=null && arg instanceof SavePoint){

                                    SavePoint mSavePoint = (SavePoint)arg;
                                    Intent it = new Intent();
                                    it.putExtra("mSavePoint",mSavePoint);
                                    it.setClass(getApplicationContext(),MainGameActivity.class);
                                    it.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT|Intent.FLAG_ACTIVITY_NEW_TASK);
                                    it.putExtra("check",(long)(('s'+'m'+'s')<<10)^'s');
                                    it.putExtra("save_point_mode",true);
                                    getApplicationContext().startActivity(it);
                                }
                               return true;
                           }
                       },sp});
                    }

                }

            }

        }
        return rst;
    }

    DialogPlus dp_dialog;
    private void selectSavePoint(){
        if(dp_dialog!=null) {
            dp_dialog.dismiss();
        }
        new Thread(new Runnable(){
            @Override
            public void run(){
                final ArrayList<Object[]> lst =  getSavePointsFromDB(); //new ArrayList<Object[]>();

                try{
                while(dp_dialog!=null&&dp_dialog.isShowing()){

                }}catch(Exception e){}
                runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        if(lst.size()==0){
                            lst.add(new Object[]{"There is no SavePoint",new OnClickListener() {
                                @Override
                                public boolean onClick(View v,DialogPlus dp,Object arg) {

                                    return false;
                                }
                                }
                            });
                        }
                        //lst.add(new Object[]{"Start New Game reset"});
                        lst.add(new Object[]{"Close"});
                        DialogPlus dp_dialog = DialogPlus.newDialog(ScrollingActivity .this).
                                setAdapter(new SimpleAdapter(ScrollingActivity.this, lst)) //(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new String[]{"asdfa"})))
                                .setOnItemClickListener(new com.orhanobut.dialogplus.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                                        Object[] objs = (Object[]) item;
                                        boolean isDismissable = true;
                                        if(objs.length>2 && objs[1]!=null && objs[2]!=null && objs[1] instanceof OnClickListener){
                                            isDismissable = ((OnClickListener)objs[1]).onClick(null,dialog,objs[2]);
                                        }
                                        else if(objs.length>1 && objs[1]!=null && objs[1] instanceof OnClickListener){
                                            isDismissable = ((OnClickListener)objs[1]).onClick(null,dialog,null);
                                        }
                                        if(isDismissable)
                                        dialog.dismiss();
                                    }
                                })
                                .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
                                .setContentHolder(new com.orhanobut.dialogplus.ListHolder())
                                .create();
                        dp_dialog.show();


                    }
                });
            }
        }).start();


    }
    private void startGame(){

        ArrayList<Object[]> lst = new ArrayList<Object[]>();
        lst.add(new Object[]{"Start New Game",new OnClickListener() {
            @Override
            public boolean onClick(View v,DialogPlus dp,Object arg) {
                startGameActivity();
                return true;
            }
        }});
        lst.add(new Object[]{"Start Game from Save Point", new OnClickListener() {
            @Override
            public boolean onClick(View v,DialogPlus dp,Object arg) {

                /*f(dp!=null&&dp.isShowing()){
                    dp.dismiss();
                }*/
                selectSavePoint();
                return false;
            }
        }});
        lst.add(new Object[]{"Back", new OnClickListener() {
            @Override
            public boolean onClick(View v,DialogPlus dp,Object arg) {
                return true;
            }
        }});

        dp_dialog = DialogPlus.newDialog(this)
                .setAdapter(new SimpleAdapter(this,lst)) //(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new String[]{"asdfa"})))
                .setOnItemClickListener(new com.orhanobut.dialogplus.OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        Object[] objs = (Object[]) item;
                        boolean isDismissable = true;
                        if(objs.length>1&&objs[1]!=null && objs[1] instanceof OnClickListener){
                            isDismissable=((OnClickListener)objs[1]).onClick(null,dialog,null);
                        }
                        if(isDismissable)
                        dialog.dismiss();
                    }
                })
                .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                .setContentHolder(new com.orhanobut.dialogplus.ListHolder())
                .create();

        dp_dialog.show();

    }
    private void viewLog(){
        Intent it = new Intent();
        it.setClass(getApplicationContext(),GameLogViewActivity.class);
        it.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT|Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(it);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       MaterialListView mlv =(MaterialListView)findViewById(R.id.menu_listview);
        Card cardwel =new Card.Builder(this)
                .setTag("welcome")
                .setDismissible()
                .withProvider(new CardProvider())
                .setLayout(R.layout.material_welcome_card_layout)
                .setTitleColor(android.graphics.Color.WHITE)
                .setTitle("안녕하세요!")
                .setDescription("게임시간 버튼을 누른뒤 최대한 오랫동한 살아남으세요.")
                .setDescriptionColor(android.graphics.Color.WHITE)
                .setSubtitle("게임 목표")
                .setSubtitleColor(android.graphics.Color.WHITE)
                .setBackgroundColor(android.graphics.Color.BLUE)
                .setDrawable(R.drawable.sample_android)
                .addAction(R.id.ok_button, new com.dexafree.materialList.card.action. WelcomeButtonAction(this)
                        .setText("Okay!")
                        .setTextColor(android.graphics.Color.WHITE)
                        .setListener(new OnActionClickListener() {
                            @Override
                            public void onActionClicked(View view, Card card) {
                                Snackbar.make(view,"WelCome!",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                                //Toast.makeText(mContext, "Welcome!", Toast.LENGTH_SHORT).show();
                            }
                        }))
                .endConfig()
                .build();
                Card card =new Card.Builder(this)
                .setTag(MainGameActivity.class.getName())
                .withProvider(new CardProvider())
                .setLayout(R.layout.material_basic_image_buttons_card_layout)
                .setTitle(R.string.start_game)
                .setDescription(R.string.start_game_desc)
                .setDrawable(R.drawable.ic_launcher2)
                .endConfig()
                .build();
        Card logcard =new Card.Builder(this)
                .setTag(GameLogViewActivity.class.getName())
                .withProvider(new CardProvider())
                .setLayout(R.layout.material_basic_image_buttons_card_layout)
                .setTitle(R.string.startLog_title)
                .setDescription(R.string.startLog_desc)
                .setDrawable(R.drawable.rank)
                .endConfig()
                .build();
        mlv.getAdapter().add(cardwel);
        mlv.getAdapter().add(card);
        mlv.getAdapter().add(logcard);
        mlv .addOnItemTouchListener(new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(Card card, int position) {
               if(card.getTag().equals(MainGameActivity.class.getName())){
                   startGame();
               }
                else if(card.getTag().equals(GameLogViewActivity.class.getName())){
                   viewLog();
                }
            }

            @Override
            public void onItemLongClick(Card card, int position) {
               // Log.d("LONG_CLICK", card.getTag().toString());
            }
        });
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
