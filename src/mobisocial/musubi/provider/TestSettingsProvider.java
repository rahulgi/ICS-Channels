package mobisocial.musubi.provider;

import mobisocial.musubi.identity.IdentityProvider;


public interface TestSettingsProvider {
	static class Settings {
		public IdentityProvider mAlternateIdentityProvider;
		public boolean mShouldDisableAddressBookSync;
		public boolean mSynchronousKeyFetchInMessageEncodeDecode;
	}
	Settings getSettings();
}
