package org.sms.tetris3d.items;

/**
 * Created by hsh on 2016. 12. 16..
 */

public class ItemIDs {
    public final static int OFFSET_ID = 0;
    public final static int MAX_ID = Integer.MAX_VALUE;
    public final static int UNKNOWN_ID = -1;
    public static class BoomIDs extends ItemIDs{

        public final static int OFFSET_ID = 1000;

        public final static int MAX_ID = BoomIDs.OFFSET_ID+1000-1;


        public final static int ID_REMOVE_LAYER = OFFSET_ID;
        public final static int ID_REMOVE_LAYERS = ID_REMOVE_LAYER+1;
        public final static int ID_REMOVE_RANDOM_LAYER = OFFSET_ID+2;
        public final static int ID_REMOVE_RANDOM_LAYERS = ID_REMOVE_RANDOM_LAYER+1;
    }
    public static class ResetIDs extends ItemIDs{

        public final static int OFFSET_ID = 2000;

        public final static int MAX_ID = ResetIDs.OFFSET_ID+1000-1;

        public final static int ID_POSITION_RESET = OFFSET_ID;
    }
    public static class SwitchIDs extends ItemIDs{
        public final static int OFFSET_ID = 3000;
        public final static int MAX_ID = SwitchIDs.OFFSET_ID+1000-1;

        public final static int ID_SWITCH_CURRENT_AND_NEXT_BLOCK = OFFSET_ID;

    }
    public static class EtcIDs extends ItemIDs{
        public final static int OFFSET_ID = 900000;
        public final static int MAX_ID = EtcIDs.OFFSET_ID+10000-1;

    }
}
