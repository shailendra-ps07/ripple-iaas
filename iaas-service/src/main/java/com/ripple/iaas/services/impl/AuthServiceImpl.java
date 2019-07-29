package com.ripple.iaas.services.impl;

import com.google.inject.Inject;
import com.ripple.iaas.db.dao.impl.InMemoryAuthTokenDao;
import com.ripple.iaas.db.dao.impl.MysqlAuthTokenDao;
import com.ripple.iaas.db.entities.AuthToken;
import com.ripple.iaas.exceptions.IaaSServiceException;
import com.ripple.iaas.models.response.AuthResponse;
import com.ripple.iaas.services.AuthService;
import com.ripple.iaas.utils.JWTHelper;
import org.hibernate.HibernateException;

import java.util.Optional;

/**
 * @author shailendra.ps
 * @since 28/07/19.
 */
public class AuthServiceImpl implements AuthService {

    private final InMemoryAuthTokenDao inMemoryAuthTokenDao;
    private final MysqlAuthTokenDao mysqlAuthTokenDao;

    @Inject public AuthServiceImpl(InMemoryAuthTokenDao inMemoryAuthTokenDao, MysqlAuthTokenDao mysqlAuthTokenDao) {
        this.inMemoryAuthTokenDao = inMemoryAuthTokenDao;
        this.mysqlAuthTokenDao = mysqlAuthTokenDao;
    }

    public AuthResponse getJWTToken(String userName) throws IaaSServiceException {
        try {
            Optional<AuthToken> accessTokenOptional = inMemoryAuthTokenDao.findAccessTokenById(userName);
            if (!accessTokenOptional.isPresent()) {
                accessTokenOptional = mysqlAuthTokenDao.findAccessTokenById(userName);
            }
            if (!accessTokenOptional.isPresent()) {
                throw new IaaSServiceException("Token is not found in storage");
            }
            return AuthResponse.builder()
                    .token(accessTokenOptional.get().getAccessTokenId())
                    .build();
        } catch (HibernateException e) {
            throw new IaaSServiceException(e);
        }
    }

    public AuthResponse registerUserToken(String userId) throws IaaSServiceException {
        try {
            String    token       = JWTHelper.createJWT(userId, -1);
            AuthToken accessToken = mysqlAuthTokenDao.registerUserToken(token, userId);
            inMemoryAuthTokenDao.registerUserToken(token, userId);
            return AuthResponse.builder()
                    .token(accessToken.getAccessTokenId())
                    .build();
        } catch (IllegalArgumentException | HibernateException e) {
            throw new IaaSServiceException(e);
        }
    }
}
