package mobisocial.musubi;

/**
 * Temporary exception so we can clear all compile errors and revisit the code later.
 *
 */
public class BJDNotImplementedException extends RuntimeException {
    private static final long serialVersionUID = 10203939446489L;

    public static final String MSG_LOCAL_PERSON_ID = "App.instance().getLocalPersonId() was here";

    private BJDNotImplementedException(String msg) {
        super(msg);
    }

    /**
     * Trick eclipse into thinking code beyond the exception is reachable.
     */
    @Deprecated
    public static void except(String msg) {
        throw new BJDNotImplementedException(msg);
    }
}
