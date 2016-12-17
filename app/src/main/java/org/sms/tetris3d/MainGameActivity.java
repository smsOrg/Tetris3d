package org.sms.tetris3d;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.opengl.*;
import android.content.*;

import org.json.JSONObject;
import org.sms.tetris3d.dialogs.DialogItem;
import org.sms.tetris3d.logs.GameLogManager;
import org.sms.tetris3d.render.*;
import org.sms.tetris3d.interfaces.*;
import org.sms.tetris3d.controls.*;
import org.sms.tetris3d.savepoint.SavePoint;
import org.sms.tetris3d.savepoint.SavePointManager;
import org.sms.tetris3d.views.ItemViewLayout;

import com.trippleit.android.tetris3d.controls.*;

import java.io.Serializable;
import java.util.*;
/**
 * Created by hsh on 2016. 11. 19..
 */

public class MainGameActivity extends Activity {
    private boolean timerLoopAvailable = true;
    protected SavePointManager spm;
    AlertDialog getDialog(final DialogItem[] items){
       return getDialogAsBuilder(items).create();
    }

    AlertDialog.Builder getDialogAsBuilder(final DialogItem[] items){
        CharSequence[] vals = new CharSequence[items.length];
        for(int i =0;i<vals.length;i++){
            vals[i] = items[i].toString();
        }
        return new AlertDialog.Builder(this).setItems(vals,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {

                items[which].onClickItem();
            }
        }).setCancelable(false);
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

    public void saveSavePoint(){
        synchronized (spm.getSync()) {
            Toast.makeText(getApplicationContext(),"saving...",Toast.LENGTH_LONG).show();
            final String sp_name = "sp_"+((float)(System.currentTimeMillis()/10)/10.0f);
            final JSONObject jobj = new JSONObject();
            try {
                jobj.put("game_db_version", GameStatus.DB_FILE_VERSION);
                jobj.put("android_sdk_version", Build.VERSION.SDK_INT);
                jobj.put("device_time", new Date().toString());
                jobj.put("device_config_version", 1.0f);
            } catch (Exception e) {

            }
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    spm.addSavePoint(sp_name, jobj, SavePoint.createSavePoint());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "saved!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
            t.start();
        }
    }
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
    GameLogManager glm;
    android.support.v7.app.AlertDialog endDialog = null;
    public void onCreate(Bundle onSavedInstanceState){
        super.onCreate(onSavedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        setContentView(R.layout.activity_main);
        if((getIntent().getLongExtra("check",-2)^'s')>>10 !='s'+'m'+'s'){
            finish();
        }

        ItemViewLayout ivl = (ItemViewLayout)findViewById(R.id.item_layout);
        glm = new GameLogManager(getApplicationContext());
        dialog = getDialogAsBuilder(new DialogItem[]{new DialogItem(){
            @Override
            public void onClickItem(){

                changePauseState();

            }
        }.setTitle(getString(R.string.pausedialog_item_resume)),

                new DialogItem(){
                    @Override
                    public void onClickItem(){

                        saveSavePoint();

                    }
                }.setTitle("save on some point"),
                new DialogItem(){
                    @Override
                    public void onClickItem(){

                        spm.deleteAllSavePoints();

                    }
                }.setTitle("clear all point"),
                new DialogItem(){
            @Override
            public void onClickItem(){
                restartActivity();
            }
        }.setTitle(getString(R.string.pausedialog_item_restart_game)),
                new DialogItem(){
                    @Override
                    public void onClickItem(){
                        finish();
                    }
                }.setTitle("exit")
        }).setIcon(R.drawable.pause_img).setTitle(R.string.pausedialog_title) .create();
        endDialog= new  android.support.v7.app.AlertDialog.Builder(MainGameActivity.this)
                .setTitle(R.string.game_over_dialg_title)
                .setMessage(R.string.game_over_dialg_prefix_content)
                .setPositiveButton(R.string.game_over_dialg_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNeutralButton(R.string.game_over_dialg_restart, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        restartActivity();
                    }
                }).setCancelable(false).create();
        final GLSurfaceView glView = (GLSurfaceView) findViewById(R.id.glSurface);
        final  GameRenderer renderer = new GameRenderer();
        spm = new SavePointManager(this);
        Object tSp = null;
        if(getIntent().getBooleanExtra("save_point_mode",false)&& (tSp = getIntent().getSerializableExtra("mSavePoint"))!=null&&tSp instanceof  SavePoint) {
            SavePoint mSp = (SavePoint)tSp;
            try {
                GameStatus.initFromSavePoint(this, mSp);
            }catch (Exception e){
                GameStatus.init(this);
            }
        }else{
            GameStatus.init(this);
        }
        glView.setRenderer(renderer);
        final GLSurfaceView glView2 = (GLSurfaceView) findViewById(R.id.glSurface2);
        final  NextBlockRenderer renderer2 = new NextBlockRenderer();
        LinearLayout llt = (LinearLayout)findViewById(R.id.mainviewcontrolpanel);
        llt.bringToFront();
        llt.getLayoutParams().height = (int)(getResources().getDisplayMetrics().heightPixels*0.108);

        llt.setLayoutParams(llt.getLayoutParams());
        ivl.addViewFromList(GameStatus.getDeviceUser().getItemManager());
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


       // GameStatus.getPlayers().add(new Computer());
        glView.setOnTouchListener(new SwipeControls(this));
        //glView2.setOnClickListener(new ChangeBlockControls());

        Button b1 = (Button) findViewById(R.id.btnUp);
        Button b2 = (Button) findViewById(R.id.btnDown);
        Button b3 = (Button) findViewById(R.id.btnLeft);
        Button b4 = (Button) findViewById(R.id.btnRight);

        ButtonControls bc = new ButtonControls(this);

        b1.setOnTouchListener(bc);
        b2.setOnTouchListener(bc);
        b3.setOnTouchListener(bc);
        b4.setOnTouchListener(bc);
/*
        Button rot_b1 = (Button) findViewById(R.id.btn_rot_x);
        Button rot_b2 = (Button) findViewById(R.id.btn_rot_y);
        Button rot_b3 = (Button) findViewById(R.id.btn_rot_z);

        RotateControls rc = new RotateControls();

        rot_b1.setOnClickListener(rc);
        rot_b2.setOnClickListener(rc);
        rot_b3.setOnClickListener(rc);
*/
        final TextView tv = (TextView) findViewById(R.id.tvGameOver);
        Thread timer = new Thread() {
            @Override
            public void run() {
                super.run();
                //int time = 0;
                while (timerLoopAvailable) {

                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(!GameStatus.isStarting()&&!GameStatus.isPaused()) {

                            GameStatus.setPlayTime(GameStatus.getPlayTime()+1);
                            //time++;
                        }
                        final long temp = GameStatus.getPlayTime();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (GameStatus.isEnd()) {
                                    tv.setText("GAME OVER :(");
                                    if(timerLoopAvailable) {
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                            JSONObject config = GameStatus.getConfigData();
                                            Date dt = new Date();
                                            try

                                            {
                                                config.put("end_time", dt.toString());
                                            }

                                            catch(
                                            Exception e
                                            )

                                            {

                                            }

                                            glm.addLog(glm.getDataBase(true),config,GameStatus.getRemoveLineCount(),temp,dt.getTime());
                                        }
                                        }).start();
                                    }
                                    timerLoopAvailable = false;
                                    if (!endDialog.isShowing()) {
                                        try {
                                            endDialog.show();
                                        }catch(Exception e){}
                                    }

                                } else {
                                    tv.setText("Time: " + temp);
                                }
                            }
                        });
                    }

            }
        };
        timer.start();
    }

    //add minsu-----------------------------(오류생기면이부분지우면)
	public void ssstop(View v) {
		showDialog(1);
	}

	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		final String items[] = {"되돌아가기", "다시시작", "옵션", "종료"};
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("일시정지");

		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(getBaseContext(), items[which], Toast.LENGTH_SHORT).show();
                //dialog modify please
			}
		});

		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		return builder.create();
	}
//------------------------------------------
    
    @Override
    protected void onDestroy() {
        timerLoopAvailable=false;
        super.onDestroy();
    }


}
