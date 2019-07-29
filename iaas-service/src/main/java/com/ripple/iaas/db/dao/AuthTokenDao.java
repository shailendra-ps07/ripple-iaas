package com.ripple.iaas.db.dao;

import com.ripple.iaas.db.entities.AuthToken;
import org.joda.time.DateTime;

import java.util.Optional;

/**
 * @author shailendra.ps
 * @since 28/07/19.
 */
public interface AuthTokenDao {

    Optional<AuthToken> findAccessTokenById(final String accessTokenId);

    void setLastAccessTime(final String accessTokenId, final DateTime dateTime);

}
