package mobisocial.musubi.model;

public class MAppDomain {
    public static final String TABLE = "app_domains";
	
    public static final String COL_ID = "_id";
    /**
     * app domain public key
     */
    public static final String COL_PUBLIC_KEY = "public_key";

    public long id_;
    public byte[] publicKey_;
}
