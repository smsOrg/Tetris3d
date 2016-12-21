package org.sms.tetris3d.savepoint;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONObject;
import org.sms.tetris3d.GameStatus;
import org.sms.tetris3d.logs.GameLogUtil;
import org.sms.tetris3d.utils.DBHelper;
import org.apache.commons.lang3.*;
/**
 * Created by hsh on 2016. 12. 16..
 */

/**
 * 세이브 포인트를 관리하는 객체
 */
public class SavePointManager {
    protected DBHelper glu = null;
    protected final Object mSync = new Object();
    public Object getSync(){
        return mSync;
    }
    //protected
    public SavePointManager(Context ctx ){
        glu = new DBHelper(ctx);
    }

    /**
     * 데이터베이스를 가져옵니다
     * @param writeable
     * @return
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
     * 세이브 포인트를 추가합니다
     * @param sp_name
     * @param device_config
     * @param sp
     * @return
     */
    public boolean addSavePoint(String sp_name,JSONObject device_config, SavePoint sp){
        return addSavePoint(getDataBase(true),sp_name, device_config,  sp);
    }

    /**
     * 세이브 포인트를 추가합니다
     * @param writeable_db
     * @param sp_name
     * @param device_config
     * @param sp
     * @return
     */
    protected boolean addSavePoint(SQLiteDatabase writeable_db,String sp_name,JSONObject device_config, SavePoint sp){
        if(sp!=null) {
            ContentValues cv = new ContentValues();
            JSONObject dconf;
            if(device_config!=null) {
               dconf=device_config;
            }else{
                dconf=new JSONObject();
                try {
                    dconf.put("db_version", GameStatus.DB_FILE_VERSION);
                }catch (Exception e){}
            }
            cv.put("save_point_name",sp_name);
            cv.put("device_environment",dconf.toString() );
            sp.setSaveTime(System.currentTimeMillis());
            try {
                Thread.sleep(10);
            }catch (Exception e){}
            cv.put("save_time", sp.getSaveTime());
            cv.put("raw_data", sp.toByteArray());
            writeable_db.insert("localSavePoint", null, cv);

            return true;
        }
        return false;

    }

    /**
     * 모든 세이브 포인트들을 저장의 오름차순, 세이브포인트이름의 내림차순으로 정렬하여 가져옵니다
     * 하지만 세이브포인트이름의 내림차순은 실질적으로 의미는 없다
     * @param readable_db
     * @return
     */
    public Cursor getSavePointData(SQLiteDatabase readable_db){
        return  readable_db.rawQuery("select * from localSavePoint order by save_time desc,save_point_name",new String[]{});
    }

    /**
     * 쓰기 가능한 데이터베이스의 localSavePoint에서 모든 데이터들을 삭제합니다
     * @param writeable_db
     */
    public void clearAllSavePoint(SQLiteDatabase writeable_db){
        writeable_db.execSQL("delete from localSavePoint");
    }

    /**
     * 쓰기 가능한 데이터베이스의 localSavePoint에서 저장시간에 해당하는 데이터를 삭제합니다
     * @param writeable_db
     * @param stime
     */
    public void deleteSavePoint(SQLiteDatabase writeable_db,long stime){
        writeable_db.delete("localSavePoint","save_time=?",new String[]{stime+""});
    }

    /**
     * 모든 데이터들을 매개변수없이 삭제합니다
     */
    public void deleteAllSavePoints(){
        clearAllSavePoint(getDataBase(true));
    }

    /**
     * 데이터베이스 저장관리 객체를 직접가져옵니다
     * @return
     */
    public DBHelper getRawClass(){
        return glu;
    }
}
