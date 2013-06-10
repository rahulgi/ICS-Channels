package mobisocial.musubi.model;

/**
 * A 1x1 table storing the local device name.
 */
public class MMyDeviceName {
    public static final String TABLE = "device_name";

    public static final String COL_ID = "_id";
    public static final String COL_DEVICE_NAME = "name";

    public long id_;
    public long deviceName_;
}
