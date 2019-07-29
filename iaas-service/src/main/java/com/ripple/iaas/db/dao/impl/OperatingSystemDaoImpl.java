package com.ripple.iaas.db.dao.impl;

import com.google.inject.Inject;
import com.ripple.iaas.Constants;
import com.ripple.iaas.db.dao.OperatingSystemDao;
import com.ripple.iaas.db.entities.OperatingSystem;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

/**
 * @author shailendra.ps
 * @since 28/07/19.
 */
public class OperatingSystemDaoImpl extends AbstractDAO<OperatingSystem> implements OperatingSystemDao {

    @Inject public OperatingSystemDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<OperatingSystem> getByName(String name) {
        CriteriaQuery<OperatingSystem> criteriaQuery = criteriaQuery();
        CriteriaBuilder     builder       = currentSession().getCriteriaBuilder();
        Root<OperatingSystem>          from          = criteriaQuery.from(OperatingSystem.class);
        criteriaQuery.select(from).where(builder.equal(from.get(Constants.NAME), name));
        return currentSession().createQuery(criteriaQuery).uniqueResultOptional();
    }
}
