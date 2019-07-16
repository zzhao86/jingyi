package com.seglino.jingyi.webapi.shiro;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;


public class CustomModularRealmAuthenticator extends ModularRealmAuthenticator {

	@Override
	protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
		assertRealmsConfigured();
		Collection<Realm> realms= getRealms();
		Map<String, Realm> realmMap = new HashMap<String, Realm>(realms.size());
		for (Realm realm : realms) {
			realmMap.put(realm.getName(), realm);
		}
		
		return super.doAuthenticate(authenticationToken);
	}
}
