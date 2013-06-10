package mobisocial.musubi.model;

public class MContactDataVersion {
    public static final String TABLE = "contact_data_version";

    /**
     * The id of an android contact raw data item that has been
     * used to fill in the Musubi address book, since this
     * is the primary key for the local contacts, we just direct map;
     */
    public static final String COL_RAW_DATA_ID = "raw_data_id";

    /**
     * The version when this item was synced
     */
    public static final String COL_VERSION = "synced_version";

    public long rawDataId_;
    public long syncedVersion_;
}
