package mobisocial.musubi.model;

public class MDevice {
    public static final String TABLE = "device_identities";

    /**
     * Primary ID for an devices
     */
    public static final String COL_ID = "_id";
    
    /**
     * Integer referencing the identity that owns the device
     */
    public static final String COL_IDENTITY_ID = "identity_id";

    /**
     * The 8-byte identity of the device the owner has arbitrarily picked
     */
    public static final String COL_DEVICE_NAME = "device_id";

    /**
     * The next sequence number for the outbound communication channel with
     * this device.
     */
    public static final String COL_MAX_SEQUENCE_NUMBER = "max_seq_number";
    
    public long id_;
    public long identityId_;
    public long deviceName_;
    public long maxSequenceNumber_;
    

}
