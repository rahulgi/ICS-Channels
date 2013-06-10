package mobisocial.musubi.encoding;

import mobisocial.musubi.model.MDevice;
import android.util.Base64;

public class DiscardMessage extends Exception {
	
	public static class InvalidAuthority extends DiscardMessage {
		public InvalidAuthority() {
			super("Message not allowed to be x-mited using local authority");
		}
	}
	public static class NotToMe extends DiscardMessage {
		public NotToMe(String msg) {
			super(msg);
		}
	}
	public static class Duplicate extends DiscardMessage {
		public final MDevice mFrom;
		public Duplicate(MDevice from, byte[] hash) {
			super("duplicate message hash=" + Base64.encodeToString(hash, Base64.DEFAULT));
			mFrom = from;
		}
	}
	public static class Blacklist extends DiscardMessage {
		public Blacklist(String msg) {
			super(msg);
		}
	}
	public static class Corrupted extends DiscardMessage {
		public Corrupted(String msg) {
			super(msg);
		}
		public Corrupted(String msg, Throwable t) {
			super(msg, t);
		}
	}
	public static class BadSignature extends DiscardMessage {
		public BadSignature(String msg) {
			super(msg);
		}
	}
	public static class BadObjFormat extends DiscardMessage {
        public BadObjFormat(String msg) {
            super(msg);
        }
        public BadObjFormat(String msg, Throwable t) {
            super(msg, t);
        }
    }
	public DiscardMessage(String msg) {
		super(msg);
	}
	public DiscardMessage(String msg, Throwable t) {
		super(msg, t);
	}
}
