package com.ripple.iaas.db.dao.impl;

import com.ripple.iaas.Constants;
import com.ripple.iaas.db.dao.UserDao;
import com.ripple.iaas.db.entities.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * @author shailendra.ps
 * @since 30/04/19.
 */
public class UserDaoImpl extends AbstractDAO<User> implements UserDao {

    @Inject
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<User> getById(String emailId, String mobileNo) {
        CriteriaQuery<User> criteriaQuery = criteriaQuery();
        CriteriaBuilder               builder       = currentSession().getCriteriaBuilder();
        Root<User>          from          = criteriaQuery.from(User.class);
        Predicate p = builder.equal(
                from.get(Constants.UsersConstants.EMAIL_ID), emailId);

        if(null != mobileNo) {
            p = builder.or(p, builder.equal(from.get(Constants.UsersConstants.MOBILE_NO), mobileNo));
        }
        criteriaQuery.select(from).where(p);
        List<User> userList = currentSession().createQuery(criteriaQuery).getResultList();
        if(userList.size() > 0) {
            return Optional.of(userList.get(0));
        }
        return Optional.empty();
    }

    public User registerUser(User user) {
        currentSession().save(user);
        return user;
    }

    public void removeUser(User user) {
        currentSession().delete(user);
    }

}
