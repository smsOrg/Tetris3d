package org.sms.tetris3d.players;


import org.json.*;
import org.sms.tetris3d.GameStatus;
import com.trippleit.android.tetris3d.render.OpenGlRenderer;
import org.sms.tetris3d.items.*;
import android.content.*;
import android.preference.*;
import android.util.AttributeSet;

import java.io.Serializable;

/**
 * Created by hsh on 2016. 11. 16..
 */

/**
 * 현재 기기의 사용자로서 다뤄지는 객체
 *
 *
 */
public class DeviceUser extends User implements Serializable{
    protected transient Context mContext = null;
    protected transient   DeviceUserItemManager item_manager;// = new ItemManagerForEachUser();
    public DeviceUserItemManager getItemManager(){
        if(item_manager==null){
            item_manager=new DeviceUserItemManager(getContext());
        }
        return item_manager;
    }

    @Override
    protected DeviceUser myIdentity(){
        return this;
    }
   /* public DeviceUser(){super();
    }*/
    public DeviceUser(Context ctx){
        super();
        setContext(ctx);
        item_manager=new DeviceUserItemManager(ctx);

    }
    public DeviceUser setContext(Context ctx){
        mContext = ctx;
        return this;
    }
    public Context getContext(){
        return mContext;
    }
    @Override
    public com.trippleit.android.tetris3d.shapes.IShape chooseObject(int rand){
        return super.chooseObject(rand);
    }
    public void newShape() {
        int objNum = randInt(0, 5);
        setCurrentObject(getNextObject()!=null?getNextObject():chooseObject(objNum));
        objNum = randInt(0, 5);
        setNextObject(chooseObject(objNum));
        setCurrentPosition(GameStatus.getStartX(), GameStatus.getStartY(), GameStatus.getGameHeight());
    }
    public void dropDown(final OpenGlRenderer ogr) {
        if (GameStatus.isEnd()) return;
        if (ogr.getOneSec() != 0) return;
        synchronized (GameStatus.getGameBoolMatrix()) {
            boolean ret = setCurrentObjectPositionDown();
            if (GameStatus.isDropFast())
                while ((ret=setCurrentObjectPositionDown())) ;

            if (!ret) {
                if (!checkOverlayPlayerBlock(this)) {
                    savePositionToBoolMatrix();
                    if (GameStatus.checkEnd() == false) {
                        newShape();
                    }
                } else {
                    setCurrentPosition(GameStatus.getStartX(), GameStatus.getStartY(), GameStatus.getGameHeight());
                }

            }

        }
    }
    public class DeviceUserItemManager extends ItemManagerForEachUser{
        private Context mContext = null;
        private SharedPreferences spref;
        public static final String SKEY_USER_ITEMS_LIST = "device_usr_item_list";
        public static final String KEY_USER_EQUIPED_ITEMS_LIST = "device_usr_equiped_item_list";
        public static final String KEY_USER_ACQUIRED_ITEMS_LIST = "device_usr_acquired_item_list";
        public Context getContext(){
            return mContext;
        }
        protected void setContext(Context ctx){
            mContext =ctx;
            spref = PreferenceManager.getDefaultSharedPreferences(getContext());
        }
        protected JSONObject getDefaultItemList(){
           final  JSONObject rst = new JSONObject();
            JSONArray ary = new JSONArray();
            JSONArray ary2= new JSONArray();
            int[] lst = {ItemIDs.ResetIDs.ID_POSITION_RESET,ItemIDs.BoomIDs.ID_REMOVE_RANDOM_LAYERS,ItemIDs.SwitchIDs.ID_SWITCH_CURRENT_AND_NEXT_BLOCK};
            try {
            for(int r:lst){
                JSONObject item = new JSONObject();
                item.put(ItemID.KEY_ID,r);
                item.put(ItemID.KEY_ArgumentAdapter_REMOVECNT,0);
                item.put(ItemID.KEY_ArgumentAdapter_OFFSETHEIGHT,0);
                ary.put(item);
                ary2.put(new JSONObject( item.toString()));
            }

                rst.put(KEY_USER_EQUIPED_ITEMS_LIST, ary);
                rst.put(KEY_USER_ACQUIRED_ITEMS_LIST, ary2);
            }catch(Exception e){}
            return rst;
        }
        private ItemID[] getListFromSomeSection(JSONArray ary) throws  Exception{
            final int leng = ary.length();
            ItemID[] rst = new ItemID[leng];
            for(int i=0;i<leng;i++){
                JSONObject obj = ary.getJSONObject(i);
                rst[i] = new ItemID().setID(obj.getInt(ItemID.KEY_ID));
                try{
                    ArgumentAdapter aa = new ArgumentAdapter();
                    aa.offsetHeight = obj.getInt(ItemID.KEY_ArgumentAdapter_OFFSETHEIGHT);
                    aa.removeCnt = obj.getInt(ItemID.KEY_ArgumentAdapter_REMOVECNT);
                    rst[i].setArgumentAdapter(aa);
                }catch(Exception e2){

                }

            }
            return rst;
        }
        public ItemID[] getEquipedItemsIDList(){
            if(spref==null){
                return null;
            }
            else{
                JSONObject json =null;
                try {
                    json = new JSONObject(spref.getString(SKEY_USER_ITEMS_LIST, getDefaultItemList().toString()));
                }catch(Exception e){
                    json=getDefaultItemList();
                }
                try {
                    JSONArray ary = json.getJSONArray(KEY_USER_EQUIPED_ITEMS_LIST);
                    return  getListFromSomeSection(ary);

                }catch(Exception e){

                }
                return null;
            }

        }
        public ItemID[] getAcquiredItemsIDList(){
            if(spref==null){
                return null;
            }
            else{
                JSONObject json =null;
                try {
                    json = new JSONObject(spref.getString(SKEY_USER_ITEMS_LIST, getDefaultItemList().toString()));
                }catch(Exception e){
                    json=getDefaultItemList();
                }
                try {
                    JSONArray ary = json.getJSONArray(KEY_USER_ACQUIRED_ITEMS_LIST);
                    return  getListFromSomeSection(ary);

                }catch(Exception e){

                }
                return null;
            }

        }

        public DeviceUserItemManager(Context ctx){
            super();
            setContext(ctx);
            ItemID[] lst=  getEquipedItemsIDList();
            if(lst!=null)
            for(ItemID item:lst){
                add(AvailableItems.getItemByID(ctx,item.getID(),item.getArgumentAdapter()));
            }
            /*
            add(AvailableItems.getItemByID(ctx,0,null));
            add(AvailableItems.getItemByID(ctx,1,null));
            add(AvailableItems.getItemByID(ctx,3,null));*/
        }
        class ItemID{
            public int mId;
            protected ArgumentAdapter aa;
            public static final String KEY_ID="id";
            public static final String KEY_ArgumentAdapter_REMOVECNT="removeCnt";
            public static final String KEY_ArgumentAdapter_OFFSETHEIGHT="offsetHeight";

            public ItemID setID(int id){
                mId = id;
                return this;
            }
            public int getID(){
                return mId;
            }
            public ItemID setArgumentAdapter(ArgumentAdapter Aa){
                aa = Aa;
                return this;
            }
            public ArgumentAdapter getArgumentAdapter(){
                return aa;
            }
        }
    }
}
