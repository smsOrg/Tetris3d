package org.sms.tetris3d.logs;

import android.content.*;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.*;
import org.sms.tetris3d.GameStatus;

/**
 * Created by hsh on 2016. 11. 22..
 */

/**
 * 게임 기록을 관리하는 클래스
 *
 * @version 1.1
 *
 * @author 황세현
 *
 */
public class GameLogManager {
    /**
     *
     */
    GameLogUtil glu = null;

    /**
     *
     * @param ctx
     */
    public GameLogManager(Context ctx ){
        glu = new GameLogUtil(ctx);
    }

    /**
     *  읽기만하거나 쓰기도할수있는 데이터베이스 객체를 가져옵니다
     * @param writeable
     * @return 데이터베이스 객체
     */
    public SQLiteDatabase getDataBase(boolean writeable){
        if(writeable){
           return glu.getWritableDatabase();
        }
        else{
            return glu.getReadableDatabase();
        }
    }

    /**
     *  쓰기 가능한 데이터 베이스에 게임 기록을 기록합니다
     * @param writeable_db
     * @param config
     * @param line_count
     * @param play_time
     * @param timestamp
     */
    public void addLog(SQLiteDatabase writeable_db, JSONObject config, long line_count,long play_time,long timestamp){
        ContentValues cv = new ContentValues();
        cv.put("config_data",config.toString());
        cv.put("remove_line_count",line_count);
        cv.put("play_time",play_time);
        cv.put("time_stamp",timestamp);
        writeable_db.insert("log",null,cv);

}

    /**
     * 모든 게임 기록을 지운층수의 오름차순 게임시간의 내림차순으로 정렬하여 가져옵니다
     * @param readable_db
     * @return 읽기전용인 데이터베이스의 데이터 포함객체
     */
    public Cursor getLog(SQLiteDatabase readable_db){
       return  readable_db.rawQuery("select * from log order by remove_line_count desc, play_time",new String[]{});
    }

    /**
     * 모든 게임기록을 삭제합니다
     *
     * @param writeable_db
     */
    public void clearAllLog(SQLiteDatabase writeable_db){
        writeable_db.execSQL("delete from log");
    }

    /**
     * 데이터베이스 관리 객체를 직접가져옵니다
     *
     * @return 데이터베이스관리객체
     */
    public GameLogUtil getRawClass(){
        return glu;
    }
}
