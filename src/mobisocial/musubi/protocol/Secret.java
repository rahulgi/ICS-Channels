package mobisocial.musubi.protocol;

public class Secret {
	/* the hash of the the decrypted data field */
	public byte[] h;
	/* the sequence number for the message */
	public long q;
	/* the actual aes key for the message body */
	public byte[] k;
}
