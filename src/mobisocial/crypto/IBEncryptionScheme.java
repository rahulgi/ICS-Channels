package mobisocial.crypto;

public class IBEncryptionScheme
{
    public static class MasterKey {
        public final byte[] key_;
        public MasterKey(byte[] key) {
            key_ = key;
        }
    }
    public static class UserKey {
        public final byte[] key_;
        public UserKey(byte[] key) {
            key_ = key;
        }
    }
    public static class ConversationKey 
    {
        public final byte[] encryptedKey_;
        public final byte[] key_;
        public ConversationKey(byte[] encryptedKey, byte[] key)
        {
            encryptedKey_ = encryptedKey;
            key_ = key;
        }
    }    
    
    public final byte[] params_;
    private long nativeParams_; //a pointer to the structure of public parameters
    public final MasterKey masterKey_;
    public IBEncryptionScheme(byte[] params, MasterKey key) {
        params_ = params;
        nativeParams_ = IBEncryptionScheme.unserializeParameters(params_);
        masterKey_ = key;
    }
    public IBEncryptionScheme(byte[] params) {
        params_ = params;
        nativeParams_ = IBEncryptionScheme.unserializeParameters(params_);
        masterKey_ = null;
    }
    public IBEncryptionScheme() {
        long[] nativePointer = new long[1];
        masterKey_ = new MasterKey(IBEncryptionScheme.generateParameters(nativePointer));
        nativeParams_ = nativePointer[0];
        params_ = IBEncryptionScheme.serializeParameters(nativeParams_);
    }
    public UserKey userKey(IBHashedIdentity user)
    {
        if(masterKey_ == null)
            throw new RuntimeException("Missing master key, cannot compute a user key");
        return new UserKey(IBEncryptionScheme.userKey(nativeParams_, masterKey_.key_, user.identity_));
    }
    public ConversationKey randomConversationKey(IBHashedIdentity to)
    {
        //me not likely doing it like this, but there isnt a great way to return
        //multiple parameters, without instantiating pair classes or somethijng
        //like that
        byte[] key = new byte[32];
        byte[] encrypted_key = IBEncryptionScheme.encrypt(key, nativeParams_, to.identity_);
        return new ConversationKey(encrypted_key, key);
    }
    //there may be multiple user keys
    public byte[] decryptConversationKey(UserKey uk, byte[] encryptedKey)
    {
        return IBEncryptionScheme.decrypt(nativeParams_, uk.key_, encryptedKey);
    }

    static {
      System.loadLibrary("IBE_JNI");
    }
    //private methods to manage the global public parameters
    //returns master key, pp updated to be the native pointer
    private static native byte[] generateParameters(long[] pp);
    private static native byte[] serializeParameters(long pp);
    private static native long unserializeParameters(byte[] encoded);
    private static native void freeParameters(long pp);
    
    private static native byte[] userKey(long pp, byte[] mk, byte[] uid);
    //returns encrypted to use over the wire, the key is written to and must be a byte array of 256 bits
    private static native byte[] encrypt(byte[] key, long pp, byte[] uid);
    //returns the key hidden inside the encrypted key
    private static native byte[] decrypt(long pp, byte[] uk, byte[] encrypted_key);
}

