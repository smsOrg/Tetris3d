package org.sms.tetris3d.logs;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.*;

import org.sms.tetris3d.utils.DBHelper;

/**
 * Created by hsh on 2016. 11. 22..
 */

/**
 * 데이터베이스 관리객체
 *
 * @version 1.0
 *
 * @author 황세현
 *
 */
public class GameLogUtil extends DBHelper {
    /**
     * 단순히 데이터베이스 관리객체를 상속만합니다
     * @param ctx
     */
    public GameLogUtil(Context ctx){
        super(ctx);
    }

}
