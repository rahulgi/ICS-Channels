package mobisocial.crypto;

public class IBSignatureScheme
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
    
    public final byte[] params_;
    private long nativeParams_; //a pointer to the structure of public parameters
    public final MasterKey masterKey_;
    public IBSignatureScheme(byte[] params, MasterKey key) {
        params_ = params;
        nativeParams_ = IBSignatureScheme.unserializeParameters(params_);
        masterKey_ = key;
    }
    public IBSignatureScheme(byte[] params) {
        params_ = params;
        nativeParams_ = IBSignatureScheme.unserializeParameters(params_);
        masterKey_ = null;
    }
    public IBSignatureScheme() {
        long[] nativePointer = new long[1];
        masterKey_ = new MasterKey(IBSignatureScheme.generateParameters(nativePointer));
        nativeParams_ = nativePointer[0];
        params_ = IBSignatureScheme.serializeParameters(nativeParams_);
    }
    public UserKey userKey(IBHashedIdentity user)
    {
        if(masterKey_ == null)
            throw new RuntimeException("Missing master key, cannot compute a user key");
        return new UserKey(IBSignatureScheme.userKey(nativeParams_, masterKey_.key_, user.identity_));
    }
    //user key is a param because users will have multiple keys under the same scheme
    public byte[] sign(IBHashedIdentity from, UserKey from_key, byte[] data)
    {
        return IBSignatureScheme.sign(nativeParams_, from_key.key_, from.identity_, data);
    }
    public boolean verify(IBHashedIdentity from, byte[] signature, byte[] data)
    {
        return IBSignatureScheme.verify(nativeParams_, signature, from.identity_, data);
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
    private static native byte[] sign(long pp, byte[] uk, byte[] uid, byte[] message_hash);
    private static native boolean verify(long pp, byte[] sig, byte[] uid, byte[] message_hash);
}

