package mobisocial.musubi.model;

public class MMissingMessage {
    public static final String TABLE = "missing_messages";

    /**
     * Primary ID for an missing message
     */
    public static final String COL_ID = "_id";
    
    /**
     * Integer referencing the stream of messages from a device
     */
    public static final String COL_DEVICE_ID = "device_id";

    /**
     * A sequence number that was not received from this device
     */
    public static final String COL_SEQUENCE_NUMBER = "seq_num";
    
    public long id_;
    public long deviceId_;
    public long sequenceNumber_;
}
