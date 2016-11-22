package org.sms.tetris3d.logs;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.*;
/**
 * Created by hsh on 2016. 11. 22..
 */

public class GameLogUtil extends SQLiteOpenHelper {

    public GameLogUtil(Context ctx,int version ){
        super(ctx,"game.db",null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists log(id integer auto_increment,config_data text,remove_line_count integer,play_time integer);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
