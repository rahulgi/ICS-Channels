package mobisocial.musubi.protocol;

public class Message {
	/* version */
	public int v;
	/* information about the sender */
	public Sender s;
	/* the iv for the key blocks */
	public byte[] i;
	/* the blind flag */
	public boolean l;
	/* the app id */
	public byte[] a;
	/* the key blocks */
	public Recipient[] r;
	/* the encrypted data */
	public byte[] d;
}
