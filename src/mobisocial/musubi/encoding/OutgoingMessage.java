package mobisocial.musubi.encoding;

import mobisocial.musubi.model.MIdentity;

public class OutgoingMessage {
	/* the reference to the identity that i send the message as */
	public MIdentity fromIdentity_;
	/* a list of all of the recipients, some of which I may or may not really know, it probably includes me */
	public MIdentity[] recipients_;
	/* the actual private message bytes that are decrypted */
	public byte[] data_;
	/* the hash of data_ */
	public byte[] hash_;
	/* a flag that control whether client should see the full recipient list */
	public boolean blind_;
	/* the id of the application namespace */
	public byte[] app_;
}
