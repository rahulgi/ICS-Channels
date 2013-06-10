package mobisocial.musubi.protocol;

public class Recipient {
	/* the serialized hashed identity, including the type, hashed principal, and time period */
	public byte[] i;
	/* the IBE encrypted key block*/
	public byte[] k;
	/* the IBE signature block, signature for the key block||device, the identity is in the sender block of the message*/
	public byte[] s;
	/* the encrypted block of secrets for the message for this person */
	public byte[] d;
	/* the IBE encrypted MAC key */
	public byte[] m;
	/* the IBE signature for the MAC key */
	public byte[] v;
	/* the HMAC-SHA256 signature of the encrypted message */
	public byte[] g;
}
