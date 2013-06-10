package mobisocial.musubi.model;

import mobisocial.musubi.model.helpers.MyAccountManager;

/**
 * @see MyAccountManager
 */
public class MMyAccount {
	public static final String NONIDENTITY_SPECIFIC_WHITELIST_ACCOUNT = "global_whitelist";
    public static final String LOCAL_WHITELIST_ACCOUNT = "local_whitelist";
    public static final String PROVISIONAL_WHITELIST_ACCOUNT = "provisional_whitelist";
    public static final String INTERNAL_ACCOUNT_TYPE = "mobisocial.musubi.internal";

    public static final String TABLE = "my_accounts";

    public static final String COL_ID = "_id";

    public static final String COL_ACCOUNT_NAME = "account_name";

    public static final String COL_ACCOUNT_TYPE = "account_type";

    /**
     * Identity linked with this account.
     */
    public static final String COL_IDENTITY_ID = "identity_id";

    /**
     * A fanout feed for identities known to this account.
     */
    public static final String COL_FEED_ID = "feed_id";

    public long id_;
    public String accountName_;
    public String accountType_;
    public Long identityId_;
    public Long feedId_;
}
