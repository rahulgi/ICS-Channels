package mobisocial.crypto;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//an IBIdentity can only be created by someone who knows the real identifier
//the hashed identity is the one the network uses for transport
public class IBIdentity extends IBHashedIdentity
{
    public final String principal_;
    public IBIdentity(Authority authority, String principal, long temporalFrame)
    {
        super(authority, digestPrincipal(principal), temporalFrame);
        this.principal_ = principal;
    }
    public static byte[] digestPrincipal(String principal) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(principal.getBytes());
            return md.digest();
        } catch(NoSuchAlgorithmException e) {
            throw new RuntimeException("Platform doesn't support sha256?!?!", e);
        }
    }
    public IBIdentity at(long temporalFrame) {
    	return new IBIdentity(authority_, principal_, temporalFrame);
    }
}