package mobisocial.musubi.model;

public class MAppAction {
    public static final String TABLE = "app_actions";
	
    public static final String COL_ID = "_id";
    /**
     * app id
     */
    public static final String COL_APP_ID = "app_id";
    /**
     * action, e.g. edit, view
     */
    public static final String COL_ACTION = "action";
    /**
     * type
     */
    public static final String COL_OBJ_TYPE = "obj_type";

    public long id_;
    public long appId_;
    public String objType_;
    public String action_;
}
