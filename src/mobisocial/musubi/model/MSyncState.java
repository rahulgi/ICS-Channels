package mobisocial.musubi.model;


/**
 * stores the information required to keep track of the
 * continuous sync with the local address book
 */
public class MSyncState {
    public static final String TABLE = "sync_state";

    public static final String COL_ID = "_id";

    public static final String COL_MAX_CONTACT = "max_contact_id_seen";

    public static final String COL_MAX_DATA = "max_data_id_seen";
    
    public static final String COL_LAST_FACEBOOK_UPDATE_TIME = "last_facebook_update_time";

    //TODO: other options used for the contact sync adapter?

    public long id_;
    public long accountName_;
    public long accountType_;
}
