package mobisocial.musubi.model;

public class MFact {
    public static final String TABLE = "facts";

    public static final String COL_ID = "_id";

    /**
     * The application defining this fact.
     */
    public static final String COL_APP_ID = "app_id";

    /**
     * The id from the fact_types table for this fact.
     */
    public static final String COL_FACT_TYPE_ID = "fact_type_id";

    /**
     * An un-indexed value for this fact.
     */
    public static final String COL_V = "V";

    /**
     * Indexed, type-free signifiers for this fact.
     */
    public static final String COL_A = "A";
    public static final String COL_B = "B";
    public static final String COL_C = "C";
    public static final String COL_D = "D";

    public long id_;
    public long appId_;
    public long fact_type_id;
    public Object A_;
    public Object B_;
    public Object C_;
    public Object D_;
    public Object V_;
}
