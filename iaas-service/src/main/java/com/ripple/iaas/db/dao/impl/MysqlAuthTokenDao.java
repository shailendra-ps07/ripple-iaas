package com.ripple.iaas.db.dao.impl;

import com.google.inject.Inject;
import com.ripple.iaas.db.dao.AuthTokenDao;
import com.ripple.iaas.db.entities.AuthToken;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

import static com.ripple.iaas.Constants.UserAuthToken.ACCESS_TOKEN_ID;

/**
 * @author shailendra.ps
 * @since 29/07/19.
 */
public class MysqlAuthTokenDao extends AbstractDAO<AuthToken> implements AuthTokenDao {

    @Inject
    public MysqlAuthTokenDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    private List<AuthToken> getAllAccessToken() {
        CriteriaQuery<AuthToken> criteriaQuery = criteriaQuery();
        CriteriaBuilder          builder       = currentSession().getCriteriaBuilder();
        Root<AuthToken>          from          = criteriaQuery.from(AuthToken.class);
        return currentSession().createQuery(criteriaQuery).getResultList();
    }

    public Optional<AuthToken> findAccessTokenById(final String accessTokenId) {
        CriteriaQuery<AuthToken> criteriaQuery = criteriaQuery();
        CriteriaBuilder          builder       = currentSession().getCriteriaBuilder();
        Root<AuthToken>          from          = criteriaQuery.from(AuthToken.class);
        criteriaQuery.select(from).where(builder.equal(from.get(ACCESS_TOKEN_ID), accessTokenId));
        return currentSession().createQuery(criteriaQuery).uniqueResultOptional();
    }

    public AuthToken registerUserToken(final String token, String userName) {
        AuthToken authToken = new AuthToken();
        authToken.setAccessTokenId(token);
        authToken.setUserId(userName);
        authToken.setLastAccessUTC(new DateTime());
        return persist(authToken);
    }

    public void setLastAccessTime(final String token, final DateTime dateTime) {
        CriteriaQuery<AuthToken> criteriaQuery = criteriaQuery();
        CriteriaBuilder          builder       = currentSession().getCriteriaBuilder();
        Root<AuthToken>          from          = criteriaQuery.from(AuthToken.class);
        criteriaQuery.select(from).where(builder.equal(from.get(ACCESS_TOKEN_ID), token));
        Optional<AuthToken> userAuthOptional = currentSession().createQuery(criteriaQuery).uniqueResultOptional();
        userAuthOptional.ifPresent(authToken -> authToken.setLastAccessUTC(dateTime));

    }

}
