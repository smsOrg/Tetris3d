package org.sms.tetris3d;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.opengl.*;
import android.content.*;
import org.sms.tetris3d.players.Computer;
import org.sms.tetris3d.render.*;
import org.sms.tetris3d.interfaces.*;
import org.sms.tetris3d.controls.*;
import com.trippleit.android.tetris3d.controls.*;
/**
 * Created by hsh on 2016. 11. 19..
 */

public class MainGameActivity extends Activity {
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
    public void onCreate(Bundle onSavedInstanceState){
        super.onCreate(onSavedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        final GLSurfaceView glView = (GLSurfaceView) findViewById(R.id.glSurface);
       final  GameRenderer renderer = new GameRenderer();
        glView.setRenderer(renderer);
        final GLSurfaceView glView2 = (GLSurfaceView) findViewById(R.id.glSurface2);
       final  NextBlockRenderer renderer2 = new NextBlockRenderer();
        LinearLayout llt = (LinearLayout)findViewById(R.id.mainviewcontrolpanel);
        llt.getLayoutParams().height = (int)(getResources().getDisplayMetrics().heightPixels*0.108);
        Button pause_btn = (Button)findViewById(R.id.btn_pause);
        pause_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GameStatus.getGameStatus()== GameStatus.GAME_STATUS.PAUSE){
                    GameStatus.setGameStatus(GameStatus.GAME_STATUS.ONGOING);
                }else
                    GameStatus.setGameStatus(GameStatus.GAME_STATUS.PAUSE);
            }
        });
        ((Button)findViewById(R.id.btn_restart)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartActivity();
            }
        });
        llt.setLayoutParams(llt.getLayoutParams());
        glView2.getLayoutParams().width = llt.getLayoutParams().height;
        glView2.setLayoutParams(glView2.getLayoutParams());
        glView2.setRenderer(renderer2);

        glView.requestFocus();
        glView.setFocusableInTouchMode(true);
        final TextView lncnt_view = (TextView)findViewById(R.id.line_count_view);
        GameStatus.setOnRemoveOneLayer(new OnRemoveLayer() {
            @Override
            public void onRemove(final long rem_count) {
                runOnUiThread(new Runnable(){
                    public void run(){
                        lncnt_view.setText(rem_count+"");
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
