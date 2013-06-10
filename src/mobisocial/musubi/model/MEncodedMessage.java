package mobisocial.musubi.model;

public class MEncodedMessage {
    public static final String TABLE = "encoded_messages";

    /**
     * Primary ID for an encoded message
     */
    public static final String COL_ID = "_id";

    /**
     * The identity id of the sender
     */
    public static final String COL_SENDER = "from_identity_id";

    /**
     * The raw message body
     */
    public static final String COL_ENCODED = "encoded";

    /**
     * The device id of the sender
     * If device id == my device id, this is an outgoing message.
     */
    public static final String COL_DEVICE_ID = "from_device_id";


    /**
     * The hash of the decrypted contents of this message
     */
    public static final String COL_HASH = "hash";

    /**
     * The short hash that things are indexed by 
     */
    public static final String COL_SHORT_HASH = "seq_number";

    /**
     * 1 if the encoded message is outbound.
     */
    public static final String COL_OUTBOUND = "outbound";

    /**
     * A flag indicating whether this message has been handled.  for outgoing
     * messages this means sent.  for incoming messages this means decrypted
     * and added to the obj table.  decrypted messages may stall because they
     * require the user to request a new key from the server.  generally speaking
     * the time frame should jump directly to a new one, and everyone should
     * use the same so a thread that wakes up on any new key discovery can
     * probably just rescan all the messages without too much fuss.
     */
    public static final String COL_PROCESSED = "processed";

    public static final String COL_PROCESSED_TIME = "processed_time";
    
    public long id_;
    public byte[] encoded_;
    public byte[] hash_;
    public Long fromIdentityId_;
    public Long fromDevice_;
    public Long shortHash_;
    public boolean outbound_;
    public boolean processed_;
    public long processedTime_;

}
