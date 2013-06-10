package mobisocial.musubi.encoding;

import mobisocial.crypto.IBHashedIdentity;

public class NeedsKey extends Exception {
	public final IBHashedIdentity identity_;
	public static class Signature extends NeedsKey {
		public Signature(IBHashedIdentity identity) {
			super(identity);
		}
	}
	public static class Encryption extends NeedsKey {
		public Encryption(IBHashedIdentity identity) {
			super(identity);
		}
	}
	public NeedsKey(IBHashedIdentity identity) {
		identity_ = identity;
	}
}
