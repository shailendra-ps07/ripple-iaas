package com.ripple.iaas.application.auth;

import com.google.inject.Singleton;
import com.ripple.iaas.db.dao.AuthTokenDao;
import com.ripple.iaas.db.entities.AuthToken;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.Optional;

/**
 * @author shailendra.ps
 * @since 28/07/19.
 */
@AllArgsConstructor
@Singleton
public class AppOauthAuthenticator implements Authenticator<String, AuthUser> {

    public static final int          ACCESS_TOKEN_EXPIRE_TIME_MIN = 30;
    private             AuthTokenDao authTokenDAO;

    @Override
    public Optional<AuthUser> authenticate(String accessTokenId) throws AuthenticationException {

        // Get the access token from the database
        Optional<AuthToken> accessToken = authTokenDAO.findAccessTokenById(accessTokenId);
        if (accessToken == null || !accessToken.isPresent()) {
            return Optional.empty();
        }

        // Check if the last access time is not too far in the past (the access token is expired)
        Period period = new Period(accessToken.get().getLastAccessUTC(), new DateTime());
        if (period.getMinutes() > ACCESS_TOKEN_EXPIRE_TIME_MIN) {
            return Optional.empty();
        }

        // Update the access time for the token
        authTokenDAO.setLastAccessTime(accessTokenId, new DateTime());

        // Return the user's id for processing
        return Optional.of(AuthUser.builder()
                .name(accessToken.get().getUserId())
                .role(AppAuthorizer.accountTypeTable.get(accessToken.get().getUserId()).name())
                .build());
    }
}
