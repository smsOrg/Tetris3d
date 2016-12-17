package org.sms.tetris3d.savepoint;

import org.json.JSONObject;
import org.sms.tetris3d.GameStatus;
import org.sms.tetris3d.players.DeviceUser;

import java.io.ByteArrayOutputStream;
import java.io.*;
import org.apache.commons.lang3.*;
/**
 * Created by hsh on 2016. 12. 16..
 */

public class SavePoint implements Externalizable {
    private static final long serialVersionUID = 1L;
    protected boolean[][][] game_mat;
    protected String[][][] game_geoColor;
    protected long rm_line , save_time;
    protected int gameHeight;
    protected int gridSize;
    protected int startX, startY;
    protected float cameraH,cameraR;
    public long play_second;
    public DeviceUser mUserData;

    protected  SavePoint(){
    }


    public float getCameraH(){
        return cameraH;
    }
    public float getCameraR(){
        return cameraR;
    }
    public SavePoint setCameraR(float val){
        cameraR = val;
        return this;
    }
    public SavePoint setCameraH(float val){
        cameraH = val;
        return this;
    }
    public SavePoint setCamera(float r,float h){
        return setCameraR(r).setCameraH(h);
    }
    public float[] getCamera(){
        float[] r = new float[2];
        r[0]=getCameraR();
        r[1]=getCameraH();
        return r;
    }
    public SavePoint setGameHeight(int height){
        gameHeight=height;
        return this;
    }

    public int getGameHeight(){
        return gameHeight;
    }

    public SavePoint setGridSize(int sz){
        gridSize=sz;
        return this;
    }

    public int getGridSize(){
        return gridSize;
    }

    public int getStartX(){
        return startX;
    }
    public int getStartY(){
        return startY;
    }

    public SavePoint setStartX(int stx){
        startX = stx;
        return this;
    }

    public SavePoint setStartY(int sty){
        startY=sty;
        return this;
    }

    public SavePoint setGameMatrix(boolean[][][] val){
        game_mat = val;
        return this;
    }

    public boolean[][][] getGameMatrix(){
        return game_mat;
    }

    public SavePoint setGameMatColor(String[][][] val){
        game_geoColor = val;
        return this;
    }

    public String[][][] getGameMatColor(){
        return game_geoColor;
    }

    public SavePoint setRemoveLineCount(long val){
        rm_line=val;
        return this;
    }

    public long getRemoveLineCount(){
        return rm_line;
    }
    public SavePoint setSaveTime(long val){
        save_time = val;
        return this;
    }
    public long getSaveTime(){
        return save_time;
    }

    public SavePoint setPlayTime(long val){
        play_second = val;
        return this;
    }

    public long getPlayTime(){
        return play_second;
    }

    public SavePoint setUserData(DeviceUser usr){
        mUserData = usr;
        return this;
    }

    public DeviceUser getUserData(){
        return mUserData;
    }

    public byte[] toByteArray(){
        byte[] rst=null;
        rst = SerializationUtils.serialize(this);
        return rst;
    }
    public boolean copyAllDataFromOtherSavePoint(SavePoint sp){
        if(!toString().equals(sp.toString())){
            setGameMatColor(sp.getGameMatColor());
            setGameMatrix(sp.getGameMatrix());
            setRemoveLineCount(sp.getRemoveLineCount());
            setSaveTime(sp.getSaveTime());
            setUserData(sp.getUserData());
            setGridSize(sp.getGridSize());
            setStartX(sp.getStartX());
            setStartY(sp.getStartY());
            setCameraH(sp.getCameraH());
            setCameraR(sp.getCameraR());
            setPlayTime(sp.getPlayTime());
            return true;
        }
        return false;
    }
    public boolean parseFromByteArray(byte[] bs){
        Object obj = SerializationUtils.deserialize(bs);
        if(obj!=null && obj instanceof SavePoint){
            return copyAllDataFromOtherSavePoint((SavePoint)obj);
        }
        return false;
    }
    public static SavePoint createSavePointFromByteArray(final byte[] bs){
        SavePoint sp = new SavePoint();
        sp.parseFromByteArray(bs);
        return sp;
    }

    public static SavePoint createSavePoint(){
        SavePoint sp = new SavePoint();
        synchronized (GameStatus.getGameBoolMatrix()) {
            sp.setCamera(GameStatus.getCameraR(), GameStatus.getCameraH())
                    .setGameHeight(GameStatus.getGameHeight()).setGameMatColor(GameStatus.getGameColorMatrix()).setPlayTime(GameStatus.getPlayTime())
                    .setStartY(GameStatus.getStartY()).setStartX(GameStatus.getStartX()).setGridSize(GameStatus.getGridSize()).setGameMatrix(GameStatus.getGameBoolMatrix())
                    .setRemoveLineCount(GameStatus.getRemoveLineCount()).setUserData(GameStatus.getDeviceUser());
        }

        return sp;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(getRemoveLineCount());
        out.writeLong(getSaveTime());
        out.writeInt(getGridSize());
        out.writeInt(getStartX());
        out.writeInt(getStartY());
        out.writeInt(getGameHeight());
        out.writeFloat(getCameraH());
        out.writeFloat(getCameraR());
        out.writeLong(getPlayTime());
        out.flush();
        out.writeObject(game_mat); //getGameMatrix());
        out.flush();
        out.writeObject(game_geoColor); //getGameMatColor());
        out.flush();
        out.writeObject(mUserData);
        out.flush();
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

        setRemoveLineCount(in.readLong());
        setSaveTime(in.readLong());
        setGridSize(in.readInt());
        setStartX(in.readInt());
        setStartY(in.readInt());
        setGameHeight(in.readInt());
        setCameraH(in.readFloat());
        setCameraR(in.readFloat());
        setPlayTime(in.readLong());
        game_mat = (boolean[][][])in.readObject();
        if(game_mat!=null){
            android.util.Log.e("game_mat is not null: ","game matrix length= "+game_mat.length);
        }else{
            android.util.Log.e("game_mat is null: ","game matrix length= null");
        }
        game_geoColor = (String[][][])in.readObject();

        mUserData= (DeviceUser)in.readObject();

        //setGameMatrix((boolean[][][])in.readObject());
        //setGameMatColor((String[][][])in.readObject());

    }
    /*public String getUserDataAsString(){
        if(mUserData!=null){
            return mUserData.toString();
        }else return null;
    }*/
}
