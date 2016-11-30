package org.sms.tetris3d.items;

import android.graphics.drawable.Drawable;

import org.sms.tetris3d.GameStatus;
import org.sms.tetris3d.players.*;
import android.content.*;
/**
 * Created by hsh on 2016. 11. 24..
 */

public class AvailableItems {
    public static class ArgumentAdapter{
        public int removeCnt=0;
        public int offsetHeight=0;
    }
    public static BaseItem getItemByID(Context ctx,final int id,final ArgumentAdapter aa){
        switch (id){
            case 0:{
                return getPositionResetItem();

            }
            case 1:{
                return getRandomLayersRemoverItem();
            }

            case 2:{
                if(aa!=null) {
                    return getLayerRemoverItem(aa.offsetHeight,aa.removeCnt);
                }else{
                    return null;
                }
            }
            case 3:{
                return getBlockChangeItem();

            }
            default:return null;
        }
    }
    public static BaseItem getPositionResetItem(){
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
        return item;
    }
    protected static int randInt(int start,int end){
        if(start>end){
            return randInt(end,start);
        }else
        return ((int)(Math.random()*Math.pow(10, (int)Math.log10(end)+2))%(end-start))+start;
    }
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


    public static BaseItem getBlockChangeItem(){
        BaseItem item = new BaseItem(2){
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
                    DeviceUser du = (DeviceUser)GameStatus.getPlayers().get(0);
                    du.swipeBlock(false);
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
        }).setCoolTime(4*1000);
        return item;
    }
}
