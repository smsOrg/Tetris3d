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

    /**
     *  static method로 생성할수 있도록 생성자의 접근을 제한
     */
    protected  SavePoint(){
    }

    /**
     * 저장된 카메라의 높이를 가져옵니다
     * @return 카메라의 높이
     */
    public float getCameraH(){
        return cameraH;
    }
    /**
     * 저장된 카메라의 각도를 가져옵니다
     * @return 카메라의 각도
     */
    public float getCameraR(){
        return cameraR;
    }

    /**
     * 카메라의 각도를 저장합니다
     * @param val
     * @return 현재 객체
     */
    public SavePoint setCameraR(float val){
        cameraR = val;
        return this;
    }
    /**
     * 카메라의 높이를 저장합니다
     * @param val
     * @return 현재 객체
     */
    public SavePoint setCameraH(float val){
        cameraH = val;
        return this;
    }

    /**
     * 카메라의 높이와 각도를 한번에 설정합니다
     * @param r
     * @param h
     * @return 현재 객체
     */
    public SavePoint setCamera(float r,float h){
        return setCameraR(r).setCameraH(h);
    }

    /**
     * 카메라의 높이와 각도를 한번에 가져옵니다
     * @return 실수배열
     */
    public float[] getCamera(){
        float[] r = new float[2];
        r[0]=getCameraR();
        r[1]=getCameraH();
        return r;
    }

    /**
     * 게임 보드게임판의 높이(층)을 설정합니다
     * @param height
     * @return 현재객체
     */
    public SavePoint setGameHeight(int height){
        gameHeight=height;
        return this;
    }
    /**
     * 게임 보드게임판의 높이(층)을 가져옵니다
     */
    public int getGameHeight(){
        return gameHeight;
    }

    /**
     * 게임보드판의 한 면의 길이를 설정합니다
     * @param sz
     * @return 현재객체
     */
    public SavePoint setGridSize(int sz){
        gridSize=sz;
        return this;
    }

    /**
     * 면의 길이를 가져옵니다
     * @return 선의 길이
     */
    public int getGridSize(){
        return gridSize;
    }

    /**
     * 저장된 x좌표를 가져옵니다
     * @return x좌표
     */

    public int getStartX(){
        return startX;
    }
    /**
     * 저장된 y좌표를 가져옵니다
     * @return y좌표
     */
    public int getStartY(){
        return startY;
    }

    /**
     * 저장할 현재모양의 x좌표를 설정합니다
     * @param stx
     * @return 현재 객체
     */
    public SavePoint setStartX(int stx){
        startX = stx;
        return this;
    }
    /**
     * 저장할 현재 모양의 y좌표를 설정합니다
     * @param sty
     * @return 현재 객체
     */
    public SavePoint setStartY(int sty){
        startY=sty;
        return this;
    }

    /**
     * 게임 보드판을 저장합니다
     * @param val
     * @return 현재 객체
     */
    public SavePoint setGameMatrix(boolean[][][] val){
        game_mat = val;
        return this;
    }

    /**
     * 저장된 게임 보드판을 불러옵니다
     * @return 게임보드판
     */
    public boolean[][][] getGameMatrix(){
        return game_mat;
    }

    /**
     * 게임 보드판의 색들을 저장합니다
     * @param val
     * @return 현재객체
     */
    public SavePoint setGameMatColor(String[][][] val){
        game_geoColor = val;
        return this;
    }

    /**
     * 저장된 게임보드판의 색들을 불러옵니다
     * @return 색깔배열
     */
    public String[][][] getGameMatColor(){
        return game_geoColor;
    }

    /**
     * 유저가 제거한 층수를 저장합니다
     * @param val
     * @return
     */
    public SavePoint setRemoveLineCount(long val){
        rm_line=val;
        return this;
    }

    /**
     * 저장된 제거 층수를 불러옵니다
     * @return 제거 층수
     */
    public long getRemoveLineCount(){
        return rm_line;
    }

    /**
     * 현재 객체를 생성한 시간의 timestamp를 저장합니다
     * @param val
     * @return
     */
    public SavePoint setSaveTime(long val){
        save_time = val;
        return this;
    }

    /**
     * 저장한 timestamp를 불러옵니다
     * @return timestamp
     */
    public long getSaveTime(){
        return save_time;
    }

    /**
     * 플레이한 시간을 저장합니다
     * @param val
     * @return 현재객체
     */
    public SavePoint setPlayTime(long val){
        play_second = val;
        return this;
    }

    /**
     * 저장된 플레이 시간을 불러옵니다
     * @return 플레이시간
     */
    public long getPlayTime(){
        return play_second;
    }

    /**
     * 현재 디바이스 사용자 객체를 통째로 저장합니다
     * @param usr
     * @return 현재객체
     */
    public SavePoint setUserData(DeviceUser usr){
        mUserData = usr;
        return this;
    }

    /**
     * 저장된 디바이스 사용자 객체를 불러옵니다
     * @return 사용자객체
     */
    public DeviceUser getUserData(){
        return mUserData;
    }

    /**
     * 현재객체인 savepoint객체를 직열화해 byte배열로 변환해 반환합니다
     * @return byte배열
     */
    public byte[] toByteArray(){
        byte[] rst=null;
        rst = SerializationUtils.serialize(this);
        return rst;
    }

    /**
     * 다른 savepoint객체의 값들을 현재객체로 복사합니다
     * @param sp
     * @return 성공여부
     */
    public boolean copyAllDataFromOtherSavePoint(SavePoint sp){
        if(!toString().equals(sp.toString())){
            setGameMatColor(sp.getGameMatColor());
            setGameMatrix(sp.getGameMatrix());
            setRemoveLineCount(sp.getRemoveLineCount());
            setSaveTime(sp.getSaveTime());
            setUserData(sp.getUserData());
            setGameHeight(sp.getGameHeight());
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

    /**
     * byte배열로부터 임시 savepoint객체를 생성하여 데이터를 파싱합니다
     * @param bs
     * @return 성공여부
     */
    public boolean parseFromByteArray(byte[] bs){
        Object obj = SerializationUtils.deserialize(bs);
        if(obj!=null && obj instanceof SavePoint){
            return copyAllDataFromOtherSavePoint((SavePoint)obj);
        }
        return false;
    }

    /**
     * byte배열로부터 savepoint객체를 생성하고 반환합니다
     * @param bs
     * @return
     */
    public static SavePoint createSavePointFromByteArray(final byte[] bs){
        SavePoint sp = new SavePoint();
        sp.parseFromByteArray(bs);
        return sp;
    }

    /**
     * 메인 게임 관리 객체로부터 값들을 가져와 저장하고 savepoint객체를 생성합니다
     * @return
     */
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

    /**
     * 직열화할때 직열화할 데이터나 객체를 변환합니다
     * @param out
     * @throws IOException
     */
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

    /**
     * 직렬화된 데이터들을 역직렬화해 객체나 데이터를 설정합니다
     * @param in
     * @throws IOException
     * @throws ClassNotFoundException
     */
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
