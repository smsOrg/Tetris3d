package org.sms.tetris3d;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.content.*;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dexafree.materialList.listeners.RecyclerItemClickListener;
import com.dexafree.materialList.view.*;
import com.dexafree.materialList.card.*;

import org.json.JSONObject;
import org.sms.tetris3d.logs.GameLogManager;

/**
 * Created by hsh on 2016. 11. 22..
 */

public class GameLogViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MaterialListView mlv =(MaterialListView)findViewById(R.id.menu_listview);
        GameLogManager glm = new GameLogManager(this);
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
                    android.util.Log.e("query log",cn);
                    sb.append(query.getString(query.getColumnIndexOrThrow(cn)) + "\n");

               // sb.append("remove_line_count: " + query.getInt(query.getColumnIndexOrThrow(cn)) + "\n");
              //  sb.append("play_time: " + query.getInt(query.getColumnIndexOrThrow(cn)) + "sec\n");
            }
                Card card = new Card.Builder(this)
                        .setTag("tag")
                        .withProvider(new CardProvider())
                        .setLayout(R.layout.material_basic_image_buttons_card_layout)
                        .setTitle("log")
                        .setDescription(sb.toString())
                        .setDrawable(R.drawable.sample_android)
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
}
