package mobisocial.musubi.util;
import java.util.Date;

import android.database.ContentObserver;
import android.os.Handler;

public class LessSpammyContentObserver extends ContentObserver {
    //at most 4 / minute
    private static final int ONCE_PER_PERIOD = 15 * 1000;
	private long mLastRun;
	private boolean mScheduled;
	private Handler mHandler;

    public LessSpammyContentObserver(Handler handler) {
        super(handler);
        mHandler = handler;
    }
    public void resetTimeout() {
    	mLastRun = 0;
    }
    @Override
    public final void onChange(boolean selfChange) {
        long now = new Date().getTime();
    	if(mLastRun + ONCE_PER_PERIOD > now) {
    		//wake up when the period expires
    		if(!mScheduled) {
    			mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
				        mScheduled = false;
						dispatchChange(false);
					}
				}, ONCE_PER_PERIOD - (now - mLastRun) + 1);
    		}
    		mScheduled = true;
    		//skip this update
    		return;
    	}
    	mLastRun = now;
    	lessSpammyOnChange(selfChange);
    }

	public void lessSpammyOnChange(boolean selfChange) {};
}
