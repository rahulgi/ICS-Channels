package mobisocial.musubi.model;

public class MFeedMember {
	public static final String TABLE = "feed_members";
	public static final String COL_ID = "_id";

	/**
	 * The feed owning this membership.
	 */
	public static final String COL_FEED_ID = "feed_id";

	/**
	 * The identity belonging to the given feed.
	 */
	public static final String COL_IDENTITY_ID = "identity_id";

	public long id_;
	public long feed_;
	public long identity_;
}
