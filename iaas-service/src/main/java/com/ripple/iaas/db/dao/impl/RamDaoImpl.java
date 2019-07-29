package com.ripple.iaas.db.dao.impl;

import com.google.inject.Inject;
import com.ripple.iaas.Constants;
import com.ripple.iaas.db.dao.RamDao;
import com.ripple.iaas.db.entities.VMRam;
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
public class RamDaoImpl extends AbstractDAO<VMRam> implements RamDao {

    @Inject public RamDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<VMRam> getByName(String name) {
        CriteriaQuery<VMRam> criteriaQuery = criteriaQuery();
        CriteriaBuilder     builder       = currentSession().getCriteriaBuilder();
        Root<VMRam>          from          = criteriaQuery.from(VMRam.class);
        criteriaQuery.select(from).where(builder.equal(from.get(Constants.NAME), name));
        return currentSession().createQuery(criteriaQuery).uniqueResultOptional();
    }
}
