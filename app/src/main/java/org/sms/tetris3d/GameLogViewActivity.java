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

/**
 * Created by hsh on 2016. 11. 22..
 */

public class GameLogViewActivity extends AppCompatActivity {
    GameLogManager glm;
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
                    .setTitle("There are no game logs")
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
                for (String cn : query.getColumnNames()) {
                    if(!cn.equals("id")) {
                        sb.append(query.getString(query.getColumnIndexOrThrow(cn)) + "\n");
                    }
                }
                Card card = new Card.Builder(this)
                        .setTag("tag")
                        .withProvider(new CardProvider())
                        .setLayout(R.layout.material_basic_image_buttons_card_layout)
                        .setTitle("log")
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        menu.add(0,10,0,"Clear Log").setTitle("Clear Log").setShowAsAction(MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
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
        return super.onOptionsItemSelected(item);
    }
}
