package mobisocial.crypto;

public class CorruptIdentity extends Exception {
	public CorruptIdentity() {
	}

	public CorruptIdentity(String s, Throwable e) {
		super(s, e);
	}
}
