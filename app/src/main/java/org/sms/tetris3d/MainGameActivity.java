package org.sms.tetris3d;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.opengl.*;
import android.content.*;

import org.sms.tetris3d.dialogs.DialogItem;
import org.sms.tetris3d.players.Computer;
import org.sms.tetris3d.render.*;
import org.sms.tetris3d.interfaces.*;
import org.sms.tetris3d.controls.*;
import android.graphics.drawable.*;
import android.graphics.*;
import com.nhaarman.supertooltips.*;
import com.trippleit.android.tetris3d.controls.*;

/**
 * Created by hsh on 2016. 11. 19..
 */

public class MainGameActivity extends Activity  {
    AlertDialog getDialog(final DialogItem[] items){
        CharSequence[] vals = new CharSequence[items.length];
        for(int i =0;i<vals.length;i++){
            vals[i] = items[i].toString();
        }
       return new AlertDialog.Builder(this).setItems(vals,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {

                items[which].onClickItem();
            }
        }).setCancelable(false).create();
    }
protected  void restartActivity(){
    if (Build.VERSION.SDK_INT >= 11) {
        recreate();
    } else {
        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);

        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}
    private boolean timerLoopAvailable = true;

    @Override
    public void onBackPressed() {
        GameStatus.setGameStatus(GameStatus.GAME_STATUS.PAUSE);
        if (dialog!=null && dialog.isShowing()) {
            dialog.hide();
        }

        else
        dialog.show();
    }
private void changePauseState(){
    if(GameStatus.getGameStatus()== GameStatus.GAME_STATUS.PAUSE){
        GameStatus.setGameStatus(GameStatus.GAME_STATUS.ONGOING);
    }else
        GameStatus.setGameStatus(GameStatus.GAME_STATUS.PAUSE);
}
    AlertDialog dialog = null;
    public void onCreate(Bundle onSavedInstanceState){
        super.onCreate(onSavedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        /*if((getIntent().getLongExtra("check",-2)^'s')>>10 !='s'+'m'+'s'){
            finish();
        }*/
        dialog = getDialog(new DialogItem[]{new DialogItem(){
            @Override
            public void onClickItem(){

                changePauseState();

            }
        }.setTitle("back"),new DialogItem(){
            @Override
            public void onClickItem(){
                restartActivity();
            }
        }.setTitle("restart"),
                new DialogItem(){
                    @Override
                    public void onClickItem(){
                        finish();
                    }
                }.setTitle("exit")
        });
        final GLSurfaceView glView = (GLSurfaceView) findViewById(R.id.glSurface);
       final  GameRenderer renderer = new GameRenderer();
        glView.setRenderer(renderer);
        final GLSurfaceView glView2 = (GLSurfaceView) findViewById(R.id.glSurface2);
       final  NextBlockRenderer renderer2 = new NextBlockRenderer();
        LinearLayout llt = (LinearLayout)findViewById(R.id.mainviewcontrolpanel);
        llt.bringToFront();
        llt.getLayoutParams().height = (int)(getResources().getDisplayMetrics().heightPixels*0.108);

        llt.setLayoutParams(llt.getLayoutParams());
        glView2.getLayoutParams().width = llt.getLayoutParams().height;
        glView2.setLayoutParams(glView2.getLayoutParams());
        glView2.setRenderer(renderer2);

        glView.requestFocus();
        glView.setFocusableInTouchMode(true);
        final TextView lncnt_view = (TextView)findViewById(R.id.line_count_view);
        lncnt_view.setText(getString(R.string.prefix_string_line_count)+ org.sms.tetris3d.GameStatus.getRemoveLineCount());
        GameStatus.setOnRemoveOneLayer(new OnRemoveLayer() {
            @Override
            public void onRemove(final long rem_count) {
                runOnUiThread(new Runnable(){
                    public void run(){
                        lncnt_view.setText(getString(R.string.prefix_string_line_count)+rem_count);
                    }
                });
            }
        });

        GameStatus.init(this);
       // GameStatus.getPlayers().add(new Computer());
        glView.setOnTouchListener(new SwipeControls(this));
        glView2.setOnClickListener(new ChangeBlockControls());

        Button b1 = (Button) findViewById(R.id.btnUp);
        Button b2 = (Button) findViewById(R.id.btnDown);
        Button b3 = (Button) findViewById(R.id.btnLeft);
        Button b4 = (Button) findViewById(R.id.btnRight);

        ButtonControls bc = new ButtonControls(this);
        b1.setOnTouchListener(bc);
        b2.setOnTouchListener(bc);
        b3.setOnTouchListener(bc);
        b4.setOnTouchListener(bc);

Button rot_b1 = (Button) findViewById(R.id.btn_rot_x);
        Button rot_b2 = (Button) findViewById(R.id.btn_rot_y);
        Button rot_b3 = (Button) findViewById(R.id.btn_rot_z);
RotateControls rc = new RotateControls();
       rot_b1.setOnClickListener(rc);
        rot_b2.setOnClickListener(rc);
        rot_b3.setOnClickListener(rc);

        final TextView tv = (TextView) findViewById(R.id.tvGameOver);
        Thread timer = new Thread() {
            @Override
            public void run() {
                super.run();
                int time = 0;
                while (timerLoopAvailable) {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    time++;
                    final int temp = time;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if(GameStatus.isEnd()) {
                                tv.setText("GAME OVER :(");
                                timerLoopAvailable=false;
                            }
                            else {

                                tv.setText("Time: " + temp);
                            }
                        }
                    });
                }
            }
        };
        timer.start();
    }



    @Override
    protected void onDestroy() {
        timerLoopAvailable=false;
        super.onDestroy();
    }


}
