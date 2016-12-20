package org.sms.tetris3d.items;

import android.graphics.drawable.Drawable;

import org.sms.tetris3d.GameStatus;
import org.sms.tetris3d.R;
import org.sms.tetris3d.players.*;
import android.content.*;
import android.os.Build;

import java.io.Serializable;

/**
 * Created by hsh on 2016. 11. 24..
 */

public class AvailableItems implements Serializable{
    /*public static class ArgumentAdapter implements Serializable{
        public int removeCnt=0;
        public int offsetHeight=0;
    }*/

    /**
     * 아이템 객체를 지정된 아이디로 가져옵니다
     *
     * @param ctx
     * @param id
     * @param aa
     * @return 아이템 객체
     */
    public static BaseItem getItemByID(Context ctx,final int id,final ArgumentAdapter aa){
        switch (id){
            case ItemIDs.ResetIDs.ID_POSITION_RESET:
            case 0:{
                return getPositionResetItem(ctx);

            }
            case ItemIDs.BoomIDs.ID_REMOVE_RANDOM_LAYERS:
            case 1:{
                if(Build.VERSION.SDK_INT <21) return getRandomLayersRemoverItem().setItemIcon(ctx.getResources().getDrawable(R.drawable.bomb)).setIconResourceId(R.drawable.bomb);
                else return getRandomLayersRemoverItem().setItemIcon(ctx.getDrawable(R.drawable.bomb)).setIconResourceId(R.drawable.bomb);
            }
            case ItemIDs.BoomIDs.ID_REMOVE_LAYERS:
            case 2:{
                if(aa!=null) {
                    if(Build.VERSION.SDK_INT <21){
                        return getLayerRemoverItem(aa.offsetHeight,aa.removeCnt).setItemIcon(ctx.getResources().getDrawable(R.drawable.bomb)).setIconResourceId(R.drawable.bomb);
                    }else
                    return getLayerRemoverItem(aa.offsetHeight,aa.removeCnt).setItemIcon(ctx.getDrawable(R.drawable.bomb)).setIconResourceId(R.drawable.bomb);
                }else{
                    //throw new NoArgumentException();
                    return null;
                }
            }
            case ItemIDs.SwitchIDs.ID_SWITCH_CURRENT_AND_NEXT_BLOCK:
            case 3:{
               // ctx.getResources().getIdentifier("","",ctx.getPackageName());
                if(Build.VERSION.SDK_INT <21){
                    return getBlockChangeItem(ctx.getResources().getDrawable(R.drawable.switchh)).setIconResourceId(R.drawable.switchh);
                }else
                return getBlockChangeItem(ctx.getDrawable(R.drawable.switchh)).setIconResourceId(R.drawable.switchh);

            }
            default:return null;
        }
    }

    /**
     * 미리설정된 내려오는 블럭의 위치를 초기화하는 아이템 객체를 가져옵니다
     *
     *
     * @param ctx
     * @return 아이템 객체
     */
    public static BaseItem getPositionResetItem(Context ctx){
        BaseItem item = new BaseItem(0){
            @Override
            public long getItemCount() {
                return 0;
            }

            @Override
            public boolean isSupportedCoolTime() {
                return true;
            }

            @Override
            public Drawable getItemIcon() {
                return null;
            }
        }.setItemListener(new ItemListener() {
            @Override
            public void onActiveItem(User usr) {
                usr.setCurrentPosition(GameStatus.getStartX(),GameStatus.getStartY(),GameStatus.getGameHeight());

            }

            @Override
            public void onCoolItem(boolean isCool) {

            }

            @Override
            public void onRemoveItem(User usr) {

            }

            @Override
            public void onAddItem(User usr) {

            }
        }).setCoolTime(12*1000);
        if(Build.VERSION.SDK_INT <21){
            item.setItemIcon(ctx.getResources().getDrawable(R.drawable.arrow_up)).setIconResourceId(R.drawable.arrow_up);
        }
        else{
            item.setItemIcon(ctx.getDrawable(R.drawable.arrow_up)).setIconResourceId(R.drawable.arrow_up);
        }
        return item;
    }

    /**
     * 범위안의 임의의 정수값을 가져옵니다
     * @param start
     * @param end
     * @return 범위안의 정수값
     */
    protected static int randInt(int start,int end){
        if(start>end){
            return randInt(end,start);
        }else
        return ((int)(Math.random()*Math.pow(10, (int)Math.log10(end)+2))%(end-start+1))+start;
    }
    /**
     * 미리설정된 랜덤으로 설된된 갯수만큼의 층을 강제제거하는  아이템 객체를 가져옵니다
     *
     *
     *
     * @return 아이템 객체
     */
    public static BaseItem getRandomLayersRemoverItem(){

        return getLayerRemoverItem(0,0).setItemListener(new ItemListener() {
            @Override
            public void onActiveItem(User usr) {
                synchronized (GameStatus.getGameBoolMatrix()){
                    int maxAvailableHeight = 0;
                    for(int k=0;k<=GameStatus.getGameHeight();k++){
                        boolean isExist = false;
                        for(int i=0;!isExist&&i<GameStatus.getGridSize();i++){
                            for(int j=0;!isExist&&j<GameStatus.getGridSize();j++){
                                isExist = GameStatus.getGameBoolMatrix()[i][j][k];
                            }
                        }
                        if(isExist){
                            maxAvailableHeight=k;
                        }
                    }
                    int oH = randInt(0,maxAvailableHeight);
                    int cnt = randInt(oH,GameStatus.getGameHeight()-oH);
                    if( cnt>=GameStatus.getGameHeight()){
                        int rm_ln_cnt = 0;
                        boolean[][][] b = GameStatus.getGameBoolMatrix();
                        synchronized (b) {
                            for(int k=0;k<b[0][0].length;k++) {
                                if(GameStatus.isExistSomeBlockInLayer(k)){
                                    rm_ln_cnt++;
                                }
                            }

                        }
                        GameStatus.restartGameBoolMatrix();
                    }else if(oH>=0)
                        GameStatus.forceRemoveRows(oH,cnt);
                }
            }

            @Override
            public void onCoolItem(boolean isCool) {

            }

            @Override
            public void onRemoveItem(User usr) {

            }

            @Override
            public void onAddItem(User usr) {

            }
        });
    }

    /**
     * 미리설정된 폭탄 아이템객체이지만 매개변수로 수동조절이가능하도록 구현한 메소드
     *
     * @param offsetHeight
     * @param removeCnt
     * @return 아이템 객체
     */

    public static BaseItem getLayerRemoverItem(final int offsetHeight,final int removeCnt){
        BaseItem item = new BaseItem(1){
            @Override
            public long getItemCount() {
                return 0;
            }

            @Override
            public boolean isSupportedCoolTime() {
                return true;
            }

            @Override
            public Drawable getItemIcon() {
                return null;
            }
        }.setItemListener(new ItemListener() {
            @Override
            public void onActiveItem(User usr) {
                if(removeCnt<0 || removeCnt>=GameStatus.getGameHeight()){
                    GameStatus.restartGameBoolMatrix();
                }else
                    GameStatus.forceRemoveRows(offsetHeight,removeCnt);

            }
            @Override
            public void onCoolItem(boolean isCool) {

            }

            @Override
            public void onRemoveItem(User usr) {

            }

            @Override
            public void onAddItem(User usr) {

            }
        }).setCoolTime(60*1000);
        return item;
    }
    /**
     * 미리설정된 현재 모양과 다음에 나타날 모양 블럭을 바꿔버리는 아이템 객체를 가져옵니다
     *
     *
     * @param icon
     * @return 아이템 객체
     */

    public static BaseItem getBlockChangeItem(Drawable icon){
        return new BaseItem(2,icon){
            @Override
            public long getItemCount() {
                return 0;
            }

            @Override
            public boolean isSupportedCoolTime() {
                return true;
            }

            @Override
            public Drawable getItemIcon() {
                return null;
            }
        }.setItemListener(new ItemListener() {
            @Override
            public void onActiveItem(User usr) {
                    //DeviceUser du = (DeviceUser)GameStatus.getPlayers().get(0);
                    //du.swipeBlock(false);
                if (usr!=null && usr instanceof DeviceUser) {
                    ((DeviceUser)usr).swipeBlock(false);
                }
            }
            @Override
            public void onCoolItem(boolean isCool) {

            }

            @Override
            public void onRemoveItem(User usr) {

            }

            @Override
            public void onAddItem(User usr) {

            }
        }).setCoolTime(2*1000).setCRT(1000/25);
       // return item;
    }
}
