package org.sms.tetris3d.logs;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.*;
import org.sms.tetris3d.GameStatus;

/**
 * Created by hsh on 2016. 11. 22..
 */

public class GameLogManager {

    GameLogUtil glu = null;
    public GameLogManager(Context ctx ){
        glu = new GameLogUtil(ctx, GameStatus.DB_FILE_VERSION);
    }
    public SQLiteDatabase getDataBase(boolean writeable){
        if(writeable){
           return glu.getWritableDatabase();
        }
        else{
            return glu.getReadableDatabase();
        }
    }
    public void addLog(SQLiteDatabase writeable_db, JSONObject config, long line_count,int play_time){
        ContentValues cv = new ContentValues();
        cv.put("config_data",config.toString());
        cv.put("remove_line_count",line_count);
        cv.put("play_time",play_time);
        writeable_db.insert("log",null,cv);

}
    public Cursor getLog(SQLiteDatabase readable_db){
       return  readable_db.rawQuery("select * from log order by remove_line_count desc, play_time",new String[]{});
    }

}
