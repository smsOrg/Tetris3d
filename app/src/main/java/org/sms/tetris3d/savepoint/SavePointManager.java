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
    public SQLiteDatabase getDataBase(boolean writeable){
        if(writeable){
            return glu.getWritableDatabase();
        }
        else{
            return glu.getReadableDatabase();
        }
    }
    public boolean addSavePoint(String sp_name,JSONObject device_config, SavePoint sp){
        return addSavePoint(getDataBase(true),sp_name, device_config,  sp);
    }
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
    public Cursor getSavePointData(SQLiteDatabase readable_db){
        return  readable_db.rawQuery("select * from localSavePoint order by save_time desc,save_point_name",new String[]{});
    }

    public void clearAllSavePoint(SQLiteDatabase writeable_db){
        writeable_db.execSQL("delete from localSavePoint");
    }
    public void deleteSavePoint(SQLiteDatabase writeable_db,long stime){
        writeable_db.delete("localSavePoint","save_time=?",new String[]{stime+""});
    }
    public void deleteAllSavePoints(){
        clearAllSavePoint(getDataBase(true));
    }

}
