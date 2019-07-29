package com.ripple.iaas.db.dao.impl;

import com.google.inject.Inject;
import com.ripple.iaas.Constants;
import com.ripple.iaas.db.dao.CpuCoreDao;
import com.ripple.iaas.db.entities.VMCpuCores;
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
public class CpuCoresDaoImpl extends AbstractDAO<VMCpuCores> implements CpuCoreDao {

    @Inject public CpuCoresDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<VMCpuCores> getByName(String name) {
        CriteriaQuery<VMCpuCores> criteriaQuery = criteriaQuery();
        CriteriaBuilder     builder       = currentSession().getCriteriaBuilder();
        Root<VMCpuCores>          from          = criteriaQuery.from(VMCpuCores.class);
        criteriaQuery.select(from).where(builder.equal(from.get(Constants.NAME), name));
        return currentSession().createQuery(criteriaQuery).uniqueResultOptional();
    }
}
