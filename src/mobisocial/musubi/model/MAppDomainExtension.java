package mobisocial.musubi.model;

public class MAppDomainExtension {
    public static final String TABLE = "extended_domains";
	
    public static final String COL_ID = "_id";
    /**
     * the app domain that is being extended
     */
    public static final String COL_DOMAIN_ID = "domain_id";
    /**
     * the app that is trusted with data in this domain
     */
    public static final String COL_EXTENDED_TRUST_TO_DOMAIN_ID = "extended_to";

    public long id_;
    public long domainId_;
    public long trustedDomainId_; //there are n of these
	
}
