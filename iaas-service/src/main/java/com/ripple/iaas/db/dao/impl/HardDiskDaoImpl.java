package com.ripple.iaas.db.dao.impl;

import com.google.inject.Inject;
import com.ripple.iaas.Constants;
import com.ripple.iaas.db.dao.HardDiskDao;
import com.ripple.iaas.db.entities.VMHardDisk;
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
public class HardDiskDaoImpl extends AbstractDAO<VMHardDisk> implements HardDiskDao {

    @Inject public HardDiskDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<VMHardDisk> getByName(String name) {
        CriteriaQuery<VMHardDisk> criteriaQuery = criteriaQuery();
        CriteriaBuilder     builder       = currentSession().getCriteriaBuilder();
        Root<VMHardDisk>          from          = criteriaQuery.from(VMHardDisk.class);
        criteriaQuery.select(from).where(builder.equal(from.get(Constants.NAME), name));
        return currentSession().createQuery(criteriaQuery).uniqueResultOptional();
    }
}
