package org.sms.tetris3d;

import android.app.ListActivity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.content.*;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dexafree.materialList.listeners.RecyclerItemClickListener;
import com.dexafree.materialList.view.*;
import com.dexafree.materialList.card.*;

import org.json.JSONObject;
import org.sms.tetris3d.drawables.NumberDrawable;
import org.sms.tetris3d.logs.GameLogManager;
import org.sms.tetris3d.savepoint.SavePoint;
import org.sms.tetris3d.savepoint.SavePointManager;

import java.util.*;
/**
 * Created by hsh on 2016. 11. 22..
 */

public class GameLogViewActivity extends AppCompatActivity {
    GameLogManager glm;
    SavePointManager spm;
    MaterialListView mlv;
    final int[] rankColors = {Color.BLUE,0xff800080,0xffff8c00,Color.GRAY};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         mlv =(MaterialListView)findViewById(R.id.menu_listview);
         glm = new GameLogManager(this);
        spm = new SavePointManager(this);

        addList();
    }
    public void clearAllLog(){
        glm.clearAllLog(glm.getDataBase(true));
        mlv.getAdapter().clearAll();
        addList();
    }
    public void addList(){
int idx = 1;
        Cursor query = glm.getLog(glm.getDataBase(false));
        int cnt = query.getCount();
        if(cnt==0){
            Card card = new Card.Builder(this)
                    .setTag(MainGameActivity.class.getName())
                    .withProvider(new CardProvider())
                    .setLayout(R.layout.material_basic_buttons_card)
                    .setTitle(R.string.gamelogactivity_nolog_title)
                    .endConfig()
                    .build();
            mlv.getAdapter().add(card);
        }else {
            boolean first = true;

            while(query.moveToNext()){
                if(first){
                    query.moveToFirst();
                    first = false;
                }
                StringBuffer sb = new StringBuffer();
                StringBuffer titleSb = new StringBuffer();
                long timestamp =query.getLong(4);
                int playtime = query.getInt(3);
                        int linecount = query.getInt(2);
                Date d = new Date(timestamp);
                titleSb.append(d.toString()+"  :: "+linecount);
                String configStr = query.getString(1);
                JSONObject config=null;
                sb.append(getString(R.string.gamelogactivity_item_rmlayer)+linecount+"\n");
                sb.append(getString(R.string.gamelogactivity_item_playtime)+playtime+"\n");
                sb.append(getString(R.string.gamelogactivity_item_time)+d.toString()+"\n");

                try {
                    config = new JSONObject(configStr);
                    sb.append(getString(R.string.gamelogactivity_item_player_cnt)+config.get("participated_player_count")+"\n");
                    sb.append(String.format("보드 사이즈: %s (x axis) X %s (y axis) X %s (z axis)\n",config.get("game_board_xy_size"),config.get("game_board_xy_size"),config.get("game_board_height")));
                }catch(Exception e){

                }

                Card card = new Card.Builder(this)
                        .setTag("tag")
                        .withProvider(new CardProvider())
                        .setLayout(R.layout.material_basic_image_buttons_card_layout)
                        .setTitle(titleSb.toString())
                        .setDescription(sb.toString())
                        .setDrawable(new NumberDrawable(idx++, Color.WHITE,idx-2<rankColors.length?rankColors[idx-2]:rankColors[rankColors.length-1])) //R.drawable.sample_android)
                        .endConfig()
                        .build();
                mlv.getAdapter().add(card);

            }
        }
        mlv .addOnItemTouchListener(new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(Card card, int position) {

            }

            @Override
            public void onItemLongClick(Card card, int position) {
                // Log.d("LONG_CLICK", card.getTag().toString());
            }
        });
        addListWithSavePoint();
    }
    public String getFormattedString(String key,String fntTyp,Object... ob){
        return String.format(key+":  "+fntTyp+"\n",ob);
    }
    public String getFormattedMatrix(boolean[][][] objs){
        StringBuffer sb = new StringBuffer();
        sb.append("{");
         for(int i=0;i<objs.length;i++){
             for(int j=0;j<objs[i].length;j++){
                 for(int k=0;k<objs[i][j].length;k++){
                     if(k==objs[i][j].length-1){
                         sb.append(" "+(objs[i][j][k]?"1":"0")+" ");
                     }
                     else{
                         sb.append(" "+(objs[i][j][k]?"1":"0")+", ");
                     }
                 }
                 sb.append("},\n");
             }
             sb.append("},\n");
         }
        sb.append("},\n");
        final String rst = sb.toString();
        sb=null;
        return rst;
    }
    public void addListWithSavePoint(){
        int idx = 1;
        Cursor query = spm.getSavePointData(spm.getDataBase(false));
        int cnt = query.getCount();
        if(cnt==0){
            Card card = new Card.Builder(this)
                    .setTag(MainGameActivity.class.getName())
                    .withProvider(new CardProvider())
                    .setLayout(R.layout.material_basic_buttons_card)
                    .setTitle(R.string.gamelogactivity_nolog_title)
                    .endConfig()
                    .build();
            mlv.getAdapter().add(card);
        }else {
            boolean first = true;

            while(query.moveToNext()){
                if(first){
                    query.moveToFirst();
                    first = false;
                }
                StringBuffer sb = new StringBuffer();
                StringBuffer titleSb = new StringBuffer();
                String title = query.getString(1);
                byte[] raw_data = query.getBlob(4);
               String configStr = query.getString(2);
                long time_stamp = query.getLong(3);
                titleSb.append(title);
                sb.append(String.format("time stamp: %d\n",time_stamp));
                sb.append(configStr+"\n");
                sb.append("\n===========\n\n");
                if(raw_data!=null) {
                    SavePoint sp = SavePoint.createSavePointFromByteArray(raw_data);
                    if(sp!=null){
                        try {
                            GameStatus.initFromSavePoint(this, sp);
                        }catch(Exception e){

                        }
                    }
                    sb.append(getFormattedString("camera h","%f",sp.getCameraH()));
                    sb.append(getFormattedString("camera r","%f",sp.getCameraR()));
                    sb.append(getFormattedString("game height","%d",sp.getGameHeight()));
                    sb.append(getFormattedString("grid size","%d",sp.getGridSize()));
                    sb.append(getFormattedString("play time","%d",sp.getPlayTime()));
                    sb.append(getFormattedString("remove line count","%d",sp.getRemoveLineCount()));
                    if(sp.getUserData()!=null){
                        sb.append(getFormattedString("usrdt::cur objZ","%d",sp.getUserData().getCurrentObjectZ()));
                        if(sp.getUserData().getCurrentObject().getObjectMatrix()!=null){
                            sb.append(getFormattedString("usrdt::cur obj leng","%d",sp.getUserData().getCurrentObject().getObjectMatrix().length));

                        }
                    }else{
                        sb.append(getFormattedString("user data","%s","is null"));
                    }

                    if(sp.getGameMatrix()!=null){
                       sb.append("bool mat: "+"\n"+getFormattedMatrix(sp.getGameMatrix())+"\n");
                    }else{
                        sb.append(getFormattedString("game bool matrix","%s","is null"));
                    }

                }else{
                    sb.append("raw data is null");
                }
                Card card = new Card.Builder(this)
                        .setTag("tag")

                        .withProvider(new CardProvider())
                        .setLayout(R.layout.material_basic_image_buttons_card_layout)
                        .setTitle(titleSb.toString())
                        .setDescription(sb.toString())
                        .setDrawable(new NumberDrawable(idx++, Color.WHITE,idx-2<rankColors.length?rankColors[idx-2]:rankColors[rankColors.length-1])) //R.drawable.sample_android)
                        .endConfig()
                        .build();
                mlv.getAdapter().add(card);

            }
        }
        mlv .addOnItemTouchListener(new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(Card card, int position) {

            }

            @Override
            public void onItemLongClick(Card card, int position) {
                // Log.d("LONG_CLICK", card.getTag().toString());
            }
        });
    }
    protected  void sortLog(){

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        menu.add(0,10,0,getString(R.string.gamelogactivity_clearlog_title)).setTitle(getString(R.string.gamelogactivity_clearlog_title)).setShowAsAction(MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
        menu.add(0,11,0,getString(R.string.gamelogactivity_sort_title)).setTitle(getString(R.string.gamelogactivity_sort_title)).setShowAsAction(MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
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
        else if(id==10){
            clearAllLog();
        }
        else if(id==11){
            sortLog();
        }
        return super.onOptionsItemSelected(item);
    }
}
