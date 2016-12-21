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

/**
 * 게임 기록을 보기위한 창(윈도우) 클래스
 *
 * @version 1.7
 *
 * @author  황세현
 */
public class GameLogViewActivity extends AppCompatActivity {
    /**
     * 게임 기록이 저장되어있는 데이터베이스 관리객체
     */
    GameLogManager glm;

    /**
     * 목록 리스트뷰
     */
    MaterialListView mlv;
    /**
     * 랭크의 색상이 지정되어있는 배열
     */
    final int[] rankColors = {Color.BLUE,0xff800080,0xffff8c00,Color.GRAY};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         mlv =(MaterialListView)findViewById(R.id.menu_listview);
         glm = new GameLogManager(this);

        addList();
    }

    /**
     * 로그모두삭제하는 함수
     */
    public void clearAllLog(){
        glm.clearAllLog(glm.getDataBase(true));
        mlv.getAdapter().clearAll();
        addList();
    }

    /**
     * 모든 로그 데이터를 불러와 리스트뷰에 추가하는 함수
     */
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

    }

    /**
     * 사용하지 않는 함수
     */
    protected  void sortLog(){

    }

    /**
     * 메뉴지정함수
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        menu.add(0,10,0,getString(R.string.gamelogactivity_clearlog_title)).setTitle(getString(R.string.gamelogactivity_clearlog_title)).setShowAsAction(MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
        menu.add(0,11,0,getString(R.string.gamelogactivity_sort_title)).setTitle(getString(R.string.gamelogactivity_sort_title)).setShowAsAction(MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    /**
     * 메뉴 클릭시 동작함수
     * @param item
     * @return
     */
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
