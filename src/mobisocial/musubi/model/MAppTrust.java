package mobisocial.musubi.model;

public class MAppTrust {
    public static final long PERM_NONE = 0;
    //
    public static final long PERM_READ = 1;
    public static final long PERM_DELETE = 2;
    //
    public static final long PERM_ALL =  3;
	
    public static final String TABLE = "app_trusts";
	
    public static final String COL_ID = "_id";
    /**
     * Domain being granted access to the feed
     */
    public static final String COL_DOMAIN_ID = "domain_id";
    /**
     * feed access is granted to
     */
    public static final String COL_FEED_ID = "feed_id";
    /**
     * permissions bitmask
     */
    public static final String COL_PERMISSIONS = "permissions";

    public long id_;
    public byte[] publicKey_;
    public long domainId_;
    public long feedId_;
    public long permissions_;
}
