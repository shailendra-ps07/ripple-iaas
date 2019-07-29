package com.ripple.iaas.db.dao.impl;

import com.ripple.iaas.db.dao.AuthTokenDao;
import com.ripple.iaas.db.entities.AuthToken;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author shailendra.ps
 * @since 28/07/19.
 */
public class InMemoryAuthTokenDao implements AuthTokenDao {

    public static Map<String, AuthToken> accessTokenTable = new HashMap<>();

    public Optional<AuthToken> findAccessTokenById(final String accessTokenId) {
        AuthToken accessToken = accessTokenTable.get(accessTokenId);
        if (accessToken == null) {
            return Optional.empty();
        }
        return Optional.of(accessToken);
    }

    public AuthToken registerUserToken(final String token, String userName) {
        AuthToken accessToken = new AuthToken(token, userName, new DateTime());
        accessTokenTable.put(accessToken.getAccessTokenId(), accessToken);
        return accessToken;
    }

    public void setLastAccessTime(final String token, final DateTime dateTime) {
        AuthToken accessToken = accessTokenTable.get(token);
        accessToken.setLastAccessUTC(dateTime);
        accessTokenTable.put(token, accessToken);
    }

}
