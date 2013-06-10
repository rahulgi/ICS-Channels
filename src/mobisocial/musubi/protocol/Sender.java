package mobisocial.musubi.protocol;

public class Sender {
	/* the serialized hashed identity of the sender who signed this message, including the type, hashed principal, and time period */
	public byte[] i;
	/* the device identifier */
	public byte[] d;
}
