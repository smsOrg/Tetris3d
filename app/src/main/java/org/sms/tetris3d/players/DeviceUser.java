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
 *  @version 2.1
 *
 *  @author 황세현
 *
 */
public class DeviceUser extends User implements Serializable{
    protected transient Context mContext = null;
    protected transient   DeviceUserItemManager item_manager;// = new ItemManagerForEachUser();

    /**
     * 아이템 관리 객체를 가져옵니다
     *
     * @return 아이템 관리 객체
     */
    public DeviceUserItemManager getItemManager(){
        if(item_manager==null){
            item_manager=new DeviceUserItemManager(getContext());
        }
        return item_manager;
    }

    /**
     * 현재 객체를 반환합니다
     * @return this class
     */
    @Override
    protected DeviceUser myIdentity(){
        return this;
    }
   /* public DeviceUser(){super();
    }*/

    /**
     *  객체를 생성하고 context를 설정합니다
     * @param ctx
     */
    public DeviceUser(Context ctx){
        super();
        setContext(ctx);
        item_manager=new DeviceUserItemManager(ctx);

    }

    /**
     *  context를 설정하고 객체를 반환합니다
     * @param ctx
     * @return this
     */
    public DeviceUser setContext(Context ctx){
        mContext = ctx;
        return this;
    }

    /**
     *  context를 가져옵니다
     * @return context
     */
    public Context getContext(){
        return mContext;
    }

    /**
     * 모양을 가져옵니다
     *
     * @param rand
     * @return shape
     */
    @Override
    public com.trippleit.android.tetris3d.shapes.IShape chooseObject(int rand){
        return super.chooseObject(rand);
    }

    /**
     * 현재 디바이스 사용자에게 다음 모양과 현재모양을 설정합니다
     */
    public void newShape() {
        int objNum = randInt(0, 5);
        setCurrentObject(getNextObject()!=null?getNextObject():chooseObject(objNum));
        objNum = randInt(0, 5);
        setNextObject(chooseObject(objNum));
        setCurrentPosition(GameStatus.getStartX(), GameStatus.getStartY(), GameStatus.getGameHeight());
    }

    /**
     * 현재 모양을 1층씩 떨어트립니다
     * @param ogr
     */
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

    /**
     * 디바이스 사용자의 등록아이템을 json을 이용해 xml에서 불러오고 저장합니다
     *
     * @version 1.2
     *
     * @author 황세현
     */
    public class DeviceUserItemManager extends ItemManagerForEachUser{
        private Context mContext = null;
        private SharedPreferences spref;
        public static final String SKEY_USER_ITEMS_LIST = "device_usr_item_list";
        public static final String KEY_USER_EQUIPED_ITEMS_LIST = "device_usr_equiped_item_list";
        public static final String KEY_USER_ACQUIRED_ITEMS_LIST = "device_usr_acquired_item_list";
        public Context getContext(){
            return mContext;
        }

        /**
         * context 객체를 설정합니다
         * @param ctx
         */
        protected void setContext(Context ctx){
            mContext =ctx;
            spref = PreferenceManager.getDefaultSharedPreferences(getContext());
        }

        /**
         * 기본적인 아이템 리스트를 생성합니다
         * @return jsonobj
         */
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
