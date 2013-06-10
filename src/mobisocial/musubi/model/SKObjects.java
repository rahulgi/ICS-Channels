package mobisocial.musubi.model;

import mobisocial.musubi.model.helpers.ViewColumn;
import mobisocial.socialkit.musubi.DbObj;

public class SKObjects {
    public static final String TABLE = "sk_objects";

    /**
     * The columns of SocialKit's objects view. App_id is the string given in the
     * apps table for the given app.id_.
     */
    public static final ViewColumn[] VIEW_COLUMNS = new ViewColumn[] {
        new ViewColumn(DbObj.COL_ID, DbObj.TABLE),
        new ViewColumn(DbObj.COL_TYPE, DbObj.TABLE),
        new ViewColumn(DbObj.COL_FEED_ID, DbObj.TABLE),
        new ViewColumn(DbObj.COL_IDENTITY_ID, DbObj.TABLE),
        new ViewColumn(DbObj.COL_PARENT_ID, DbObj.TABLE),
        new ViewColumn(DbObj.COL_JSON, DbObj.TABLE),
        new ViewColumn(DbObj.COL_TIMESTAMP, DbObj.TABLE),
        new ViewColumn(MApp.COL_APP_ID, MApp.TABLE),
        new ViewColumn(DbObj.COL_UNIVERSAL_HASH, DbObj.TABLE),
        new ViewColumn(DbObj.COL_SHORT_UNIVERSAL_HASH, DbObj.TABLE),
        new ViewColumn(DbObj.COL_RAW, DbObj.TABLE),
        new ViewColumn(DbObj.COL_INT_KEY, DbObj.TABLE),
        new ViewColumn(DbObj.COL_STRING_KEY, DbObj.TABLE),
        new ViewColumn(DbObj.COL_LAST_MODIFIED_TIMESTAMP, DbObj.TABLE),
        new ViewColumn(DbObj.COL_RENDERABLE, DbObj.TABLE),
    };
}
