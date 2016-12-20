package org.sms.tetris3d.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.sms.tetris3d.GameStatus;

/**
 * Created by hsh on 2016. 11. 29..
 */

/**
 * 메인 데이터베이스관리객체
 *
 *  @version 1.2
 *
 *  @author 황세현
 *
 */
public class DBHelper  extends SQLiteOpenHelper {
    /**
     * db파일명과 db버전은 이미설정되었으므로 context객체만 외부에서 가져온다
     * @param ctx
     */
    public DBHelper(Context ctx){
        super(ctx,"game.db",null, GameStatus.DB_FILE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createEssentialTables(db);
    }

    /**
     * 필수적인 데이터베이스의 테이블이 존재하는 지 판단후 존재하지 않으면 생성하는 메소드
     * @param db
     */
    protected void createEssentialTables(SQLiteDatabase db){
        db.execSQL("create table if not exists log(id integer primary key,config_data text,remove_line_count integer not null,play_time integer not null,time_stamp integer not null);");
        db.execSQL("create table if not exists deviceUserItems(id integer primary key,item_id integer not null unique);");
        db.execSQL("create table if not exists localSavePoint(id integer primary key,   save_point_name text not null check(length(save_point_name) > 2),   device_environment text,   save_time integer not null,   raw_data blob not null);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       if(newVersion>oldVersion){
           createEssentialTables(db);
       }
    }
}
