package org.sms.tetris3d;

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

public class ScrollingActivity extends AppCompatActivity {
public static class LCardProvider extends com.dexafree.materialList.card.CardProvider{

}
    private void startGame(){
        Intent it = new Intent();
        it.setClass(getApplicationContext(),MainGameActivity.class);
        it.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT|Intent.FLAG_ACTIVITY_NEW_TASK);
        it.putExtra("save_point_mode",false);
it.putExtra("check",(long)(('s'+'m'+'s')<<10)^'s');
        getApplicationContext().startActivity(it);
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
