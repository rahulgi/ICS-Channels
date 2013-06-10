package mobisocial.musubi.identity;

import mobisocial.crypto.IBEncryptionScheme;
import mobisocial.crypto.IBHashedIdentity;
import mobisocial.crypto.IBIdentity;
import mobisocial.crypto.IBSignatureScheme;

//methods intended to be invoked in a background handler job
public interface IdentityProvider {
	public IBEncryptionScheme getEncryptionScheme();
	public IBSignatureScheme getSignatureScheme();
	public boolean initiateTwoPhaseClaim(IBIdentity ident, String key, int requestId);
	public IBSignatureScheme.UserKey syncGetSignatureKey(IBIdentity ident) throws IdentityProviderException;
	public IBEncryptionScheme.UserKey syncGetEncryptionKey(IBIdentity ident) throws IdentityProviderException;
	//These ones may have to do an implicit lookup because the real principal
	//may be required to fetch a key, e.g. aphid
	public IBSignatureScheme.UserKey syncGetSignatureKey(IBHashedIdentity ident) throws IdentityProviderException;
	public IBEncryptionScheme.UserKey syncGetEncryptionKey(IBHashedIdentity ident) throws IdentityProviderException;
}
