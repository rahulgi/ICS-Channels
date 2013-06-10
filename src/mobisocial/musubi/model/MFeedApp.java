package mobisocial.musubi.model;

public class MFeedApp {
	public static final String TABLE = "feed_apps";
	public static final String COL_ID = "_id";

	/**
	 * The feed owning the app entry.
	 */
	public static final String COL_FEED_ID = "feed_id";

	/**
	 * The app id.
	 */
	public static final String COL_APP_ID = "app_id";

	public long id_;
	public long feed_;
	public long app_;
}
